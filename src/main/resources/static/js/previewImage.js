$(document).ready(function(){

    $("#upload-file-zone").click(function() {
        $("#upload-file-input").click()
    });

    $("#upload-file-input").change(function () {
        console.log("change")
        const files = $("#upload-file-input").prop("files");
        const [file] = files;
        const tempURL = file ? URL.createObjectURL(file) : "";

        $("#image-preview").attr("src", tempURL);
        $("#image-preview").toggleClass("d-none");
        $("#upload-file-guide").toggleClass("d-none");

    })

});