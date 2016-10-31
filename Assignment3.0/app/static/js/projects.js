(function($) {
    $(function() {
       $.getJSON('/get_log', {
      }, function(data) {
        $("#project_div").text(data);
      });
    });
})(jQuery);