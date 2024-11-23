document.addEventListener("DOMContentLoaded", function() {
    const userID = getUidFromUrl();
    const userType = "student";

    if (userID && userType) {
        $.ajax({
            url: `/student/${userID}/api/eventList`,
            type: "GET",
            success: function(events) {
                const eventList = document.getElementById(`eventList`);
                events.forEach(event => {
                    const li = document.createElement("li");
                    li.textContent = `ID: ${event.eventID}, Title: ${event.title}`;
                    eventList.appendChild(li);
                });
            },
            error: function(error) {
                console.error("Error fetching event list:", error);
            }
        });
    }
});

function getUidFromUrl() {
    const match = window.location.pathname.match(/\/(admin|student|professor)\/(\d+)/);
    if (match) {
        return match[2];
    }
    return null;
}
function getUserTypeFromUrl() {
    const match = window.location.pathname.match(/\/(admin|student|professor)/);
    if (match){
        return match[1];
    }
    return null;
}

function fetchStudentEventDetails() {
    const eventID = document.getElementById("studentSearchEventID").value;
    const userUID = getUidFromUrl();
    const userType = getUserTypeFromUrl();
    if (!eventID) {
        alert("Please enter an Event ID.");
        return;
    }

    $.ajax({
        url: `/${userType}/${userUID}/api/eventDetails/${eventID}`,
        type: "GET",
        success: function(response) {
            // Redirect the user to the returned URL
            window.location.href = response;
        },
        error: function(error) {
            console.error("Error fetching event details:", error);
            alert("Failed to fetch event details. Please try again.");
        }
    });
}
