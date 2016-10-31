$(document).ready(function () {
	//hide comment box from user when the page is loaded
    $("div#commentdiv").hide();
});


$('a.list-group-item').click(function(e) {
	//show the comment box after user has selected a project
	$('div#commentdiv').show();

	//load source code from svn 
    $('#preview').attr('src', $(this).attr('href'));

    //parse the link, get current project
    var str = $(this).attr('href');
    var assignment = str.substring(str.indexOf("ywang443/") + 9);
    assignment = assignment.substring(0, assignment.indexOf("/"));

    //set cookie
    var str1 = "assignment=";
    document.cookie = str1.concat(assignment);

    //show exisiting comments
    $.ajax({
    	url: './comment_contents.php',
    	type: 'POST', 
    	data: { "queryComment": assignment},

    	success: function(result){
    		$('#show_comments').html(result);
    	},
    });

    e.preventDefault();
});


$('#comment_form').submit(function(e)
{	
	e.preventDefault();

	//submit comments to database
	$.ajax({
	url: 'comment.php',
	type: 'POST',
	data: $(this).serialize(), // it will serialize the form data
        dataType: 'html'
    })

    .done(function(data){
    	//reset the form 
    	var frm = document.getElementsByName('comment-form')[0];
    	frm.reset();

    	//take the assignment variable from cookie
    	var assignment = document.cookie;

    	//query all comments about current assignment
    	$.ajax({
	    	url: './comment_contents.php',
	    	type: 'POST', 
	    	data: { "queryComment": assignment},
	    	success: function(result){
	    		$('#show_comments').html(result);
	    	},
    	});
    });

});
