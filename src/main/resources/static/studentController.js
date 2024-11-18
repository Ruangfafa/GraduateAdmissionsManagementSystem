document.addEventListener("DOMContentLoaded", function() {
    const userID = getUserIDFromUrl();
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

function getUserIDFromUrl() {
    const match = window.location.pathname.match(/\/student\/(\d+)/);
    return match ? match[1] : null;
}

function fetchStudentEventDetails() {
    const eventID = document.getElementById("studentSearchEventID").value;
    if (!eventID) {
        alert("Please enter an Event ID.");
        return;
    }

    $.ajax({
        url: `/student/api/eventDetails/${eventID}`,
        type: "GET",
        success: function(event) {
            if (event.title) {
                document.getElementById("studentEventTitle").textContent = event.title;
                document.getElementById("studentEventDescription").textContent = event.description;
            } else {
                alert("Event not found.");
                document.getElementById("studentEventTitle").textContent = '';
                document.getElementById("studentEventDescription").textContent = '';
            }
        },
        error: function(error) {
            console.error("Error fetching event details:", error);
            alert("Failed to fetch event details. Please try again.");
        }
    });
}
