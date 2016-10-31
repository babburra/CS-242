$('a.list-group-item').click(function(e) {
    $('#preview').attr('src', $(this).attr('href'));
    e.preventDefault();
});
