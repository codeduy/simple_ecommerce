console.log("index.js")
$(document).ready(function(){
    $('[data-bs-toggle="tooltip"]').each(function () {
        new bootstrap.Tooltip(this);
    });
});

