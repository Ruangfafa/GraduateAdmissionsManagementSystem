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
