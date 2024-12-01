function getEventUIDFromURL() {
    const pathSegments = window.location.pathname.split("/");
    const eventUIDIndex = pathSegments.indexOf("adminEvent") + 1;
    return pathSegments[eventUIDIndex];
}

function assignedToProf() {
    const applicationUID = $("#applicationUID").val();
    const profUID = $("#assignedToProf").val();
    const eventUID = $("#eventUID").val() || getEventUIDFromURL();

    $.ajax({
        url: `/admin/${applicationUID}/assignToProf`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ profUID: profUID, eventUID: eventUID }),
        success: function(response) {
            alert("Student assigned to professor successfully!");
        },
        error: function(xhr, status, error) {
            console.error("Error:", xhr.responseText);
            alert("Error assigning student to professor. Status: " + xhr.status);
        }
    });
}

function viewFile(fileType){
    console.log(window.location.pathname);
    $.ajax({
        url: window.location.pathname,
        type: 'POST',
        contentType: 'text/plain',
        data: fileType,
        success: function (data) {
            if (data) {
                // console.log(data)
                let pdfW = window.open("");
                pdfW.document.write("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
                    encodeURI(data) + "'></iframe>")
            } else
                alert("File Loading error!");
        },
        error: function () {
            console.log('Error ${error}');
            alert("Login Error!");
        }
    });
}
