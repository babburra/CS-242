	
cmds = {
	average_pixel: function(cmd_args) {
		var boxID = cmd_args['box_id'];
		var viewerID = cmd_args['viewer_id'];

		var boxExists = LSST.state.boxes.exists(boxID);
		var viewerExists = LSST.state.viewers.exists(viewerID);
		if (boxExists && viewerExists) {

			// The region to do the calculation over
			var region = cmd_args['region'];

			// A handle to the viewer
			var viewer = LSST.state.viewers.get(viewerID);
			// A handle to the ff image viewer
			var imageViewer = viewer;
			// A handle to the box to use
			var box = LSST.state.boxes.get(boxID);

			// Clear the box of any existing information
			cmds.clear_box( { 'box_id' : boxID } );

			// Clear the viewer
			var plotID = viewerID;
			var regionID = plotID + '-boundary';
			if (imageViewer[regionID]) {
				firefly.removeRegionData(imageViewer[regionID], regionID);
				imageViewer[regionID] = undefined;
			}

			// Show region on image viewer
			var imageRegion = region_to_overlay(region);
			imageViewer[regionID] = [ imageRegion ];
			if (firefly.overlayRegionData) {
				firefly.overlayRegionData( [ imageRegion ], regionID, "Boundary", plotID);
			}

			var boxText = [
				'Processing average_pixel...'
			];
			box.setText(boxText);

			// Call average_pixel python task
			var params = parse_region(region);
			executeBackendFunction('average', params,
				function(data) {
					boxText = [
						'average_pixel',
						'Viewer: ' + viewerID,
						[
							'Region:'
						].concat(region_to_boxtext(region)),
						':line-dashed:',
						new BoxText('Average Pixel Value', data['result'])
					];
					box.setText(boxText);
				},

				function(data) {
					// Called when there was a problem with the promise function
					boxText = [
						'There was a problem with executing the average_pixel function',
						'\n',
						'Please make sure all parameters were typed in correctly',
						new BoxText('Data', data, false)
					];

					box.setText(boxText);
				}
			);
		}
		else if (!boxExists) {
			LSST.state.term.echo('A box with the name \'' + boxID + '\' does not exist!');
		}
		else if (!viewerExists) {
			LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	},

	clear_box: function(cmd_args) {
		var boxID = cmd_args['box_id'];

		if (LSST.state.boxes.exists(boxID)) {
			var box = LSST.state.boxes.get(boxID);

			box.clear();
		} else {
			LSST.state.term.echo('A box with the name \'' + boxID + '\' does not exist!');
		}
	},

	create_box: function(cmd_args) {
		var boxID = cmd_args['box_id'];

		if (!LSST.state.boxes.exists(boxID)) {
			var box = new Box(boxID);
			box.dom.draggable( {
				distance : 10,
				handle : '.box-title',
				drag : onChangeFocus
			});

			box.dom.on('click', onChangeFocus);

			var closeData = {
				onClick : cmds.delete_box,
				parameters : { box_id : boxID },
			}
			var miniData = {
				onClick : cmds.hide_box,
				parameters : { box_id : boxID },
			}
			var toolbarDesc = [
				new LSST_TB.ToolbarElement('close', closeData),
				new LSST_TB.ToolbarElement('mini', miniData),
			];
			var options = {
				bShowOnHover : true,
			};
			box.dom.lsst_toolbar(toolbarDesc, options);

			// Resizable
			box.dom.resizable( {
				handles : 'se',
			} );

			LSST.state.boxes.add(boxID, box);

			cmds.show_box( { 'box_id' : boxID } );
		}
		else {
            LSST.state.term.echo('A box with the name \'' + boxID + '\' already exists!');
		}
	},

	create_viewer: function(cmd_args) {
		var viewerID = cmd_args['viewer_id'];

		if (!LSST.state.viewers.exists(viewerID)) {
			var viewer = new Viewer(viewerID);

			viewer.readout.register('SELECT_REGION', selectRegion);

			var c = jQuery(viewer.container);
			// Add draggable
			c.draggable( {
				distance : 10,
				cancel : '.viewer-view',
				drag : onChangeFocus
			} );

			// Add resizable
			var w = c.css('width');
			var h = c.css('height');
			c.css('min-height', h);
			c.resizable( {
				handles : 'se',
				alsoResize : c.children('viewer-view'),
				minWidth : w,
				minHeight : h,
			} );

			viewer.container.on('click', onChangeFocus);

			LSST.state.viewers.add(viewerID, viewer);

			cmds.show_viewer( { 'viewer_id' : viewerID } );
		}
		else {
			LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' already exist!');
		}
	},

	delete_box: function(cmd_args) {
		var boxID = cmd_args['box_id'];

		if (LSST.state.boxes.exists(boxID)) {
			LSST.state.boxes.get(boxID).destroy();

			LSST.state.boxes.remove(boxID);

			LSST.state.term.deleteParameterAuto('box_id', boxID);
		}
		else {
			LSST.state.term.echo('A box with the name \'' + boxID + '\' does not exist!');
		}
	},

	hide_box: function(cmd_args) {
		var boxID = cmd_args['box_id'];

		if (LSST.state.boxes.exists(boxID)) {
			// A handle to the box
			var box = LSST.state.boxes.get(boxID);
			box.minimize();
			box.dom.draggable('option', 'handle', '.box-title-mini');

			var toolbar = box.dom.children('.LSST_TB-toolbar');
			var mini = jQuery(toolbar.children()[1]);
			mini.attr('src', 'js/toolbar/images/maximize_40x40.png');
			mini.data('onClick', cmds.show_box);
		}
		else {
			LSST.state.term.echo('A box with the name \'' + boxID + '\' does not exist!');
		}
	},

	hot_pixel: function(cmd_args) {
		var viewerID = cmd_args['viewer_id'];
		if (LSST.state.viewers.exists(viewerID)) {

			var threshold = 'max';
			if (cmd_args['threshold']!=='max'){
					threshold = parseInt(cmd_args['threshold']);
			}
			var region = parse_region(cmd_args['region']);

			// A handle to the ff image viewer
			var imageViewer = LSST.state.viewers.get(viewerID);

			var regionID = viewerID + '-hotpixel';
			if (imageViewer[regionID]) {
				firefly.removeRegionData(imageViewer[regionID], regionID);
				imageViewer[regionID] = undefined;
			}

			read_hotpixels(
				{
					filename: "default",
					threshold: threshold,
					"region": region
				},
				function(regions) {
					imageViewer[regionID] = regions;
					firefly.overlayRegionData(regions, regionID, 'hotpixel', viewerID);
				}
			);

		}
		else {
            LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	},

	show_boundary: function(cmd_args) {
		var viewerID = cmd_args['viewer_id'];
		var plotid = viewerID; // ffview as a default
		var region_id = plotid + '-boundary';
        var viewer = LSST.state.viewers.get(viewerID);
        if (viewer){
            if (!(viewer.show_boundary)){
                if (viewer.header){
                    firefly.overlayRegionData(viewer.header["regions_ds9"], region_id, 'Boundary', plotid);
                    viewer.show_boundary = true;
                }else{
                    read_boundary(plotid, function(regions) { // Asynchronous
                        viewer.header = regions;
                        firefly.overlayRegionData(viewer.header["regions_ds9"], region_id, 'Boundary', plotid);
                        viewer.show_boundary = true;
                    })
                }
            }else{
                LSST.state.term.echo("Boundary of this viewer is already drawn.")
            }
        }else{
			LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
        }
	},

	show_box: function(cmd_args) {
		var boxID = cmd_args['box_id'];

		if (LSST.state.boxes.exists(boxID)) {
			// A handle to the box
			var box = LSST.state.boxes.get(boxID);
			box.maximize();
			box.dom.draggable('option', 'handle', '.box-title');

			var focusFunc = onChangeFocus;
			focusFunc.bind(box.dom);
			focusFunc();

			var toolbar = box.dom.children('.LSST_TB-toolbar');
			var max = jQuery(toolbar.children()[1]);
			max.attr('src', 'js/toolbar/images/minimize_40x40.png');
			max.data('onClick', cmds.hide_box);
		}
		else {
			LSST.state.term.echo('A box with the name \'' + boxID + '\' does not exist!');
		}
	},

	uv_freq: function(cmd_args){

	    var viewerID = cmd_args['viewer_id'];

	    if (LSST.state.viewers.exists(viewerID)) {
			var viewer = LSST.state.viewers.get(viewerID);

			var timeAsMilli = cmd_args['time_in_millis'];
			// 5000 milliseconds is the lower bound
			timeAsMilli = Math.min(timeAsMilli, 5000);

			// Stop timer for viewer
			clearInterval(viewer.uv.timer_id);

			// Set new update frequency
			viewer.uv.freq = timeAsMilli;

			// Reset timer
			viewer.uv.timer_id =
				setInterval(
				    function() {
				    	cmds.uv_update( { 'viewer_id' : viewerID } )
				    },
				    viewer.uv.freq
				);
		}
		else {
            LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	},

	uv_pause: function(cmd_args) {
	    var viewerID = cmd_args['viewer_id'];

	    if (LSST.state.viewers.exists(viewerID)) {
			var viewer = LSST.state.viewers.get(viewerID);

			var id = viewerID + '---pause_resume';
			var button = jQuery('input[data-buttonID="' + id + '"]');
			button.attr('value', 'Resume');

			viewer.uv.paused = true;
		}
		else {
            LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	},

	uv_resume: function(cmd_args) {
	    var viewerID = cmd_args['viewer_id'];

	    if (LSST.state.viewers.exists(viewerID)) {
			var viewer = LSST.state.viewers.get(viewerID);

			var id = viewerID + '---pause_resume';
			var button = jQuery('input[data-buttonID="' + id + '"]');
			button.attr('value', 'Pause');

			viewer.uv.paused = false;
			cmds.uv_load_new( { 'viewer_id' : viewerID } );
		}
		else {
            LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	},

	uv_update: function(cmd_args) {
	    var viewerID = cmd_args['viewer_id'];

	    if (LSST.state.viewers.exists(viewerID)) {
			var viewer = LSST.state.viewers.get(viewerID);

			var CHECK_IMAGE_PORT = "8099";
		    var CHECK_IMAGE_URL = "http://172.17.0.1:" + CHECK_IMAGE_PORT + "/vis/checkImage";
		    var params = {
		    	'since': viewer.uv.image_ts
		   	};

		    jQuery.getJSON(CHECK_IMAGE_URL, params, function(data) {

		        if (data) {
		            // There's a new image.
		            viewer.uv.image_ts = data.timestamp;
					viewer.uv.newest_image = data.uri;

					if (!viewer.uv.paused) {
						cmds.uv_load_new( { 'viewer_id' : viewerID } );
					}
					else {
						var id = viewerID + '---update_now';
						var button = jQuery('input[data-buttonID="' + id + '"]');
						button.prop('disabled', false);
						button.attr('value', 'There is a new image available. Click to load.');
					}
				}

				if (viewer.uv.newest_image == null) {
					// Displayed when there is no new image, or the new image is done loading.
					var id = viewerID + '---update_now';
					var button = jQuery('input[data-buttonID="' + id + '"]');
					button.prop('disabled', true);
					button.attr('value', 'There are no new images.');
				}
		    });
        }
		else {
            LSST.state.term.echo('A viewer with the name \'' + viewerID + '\' does not exist!');
		}
	}
}