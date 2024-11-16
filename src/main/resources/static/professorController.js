document.addEventListener("DOMContentLoaded", function() {
    const userID = getUserIDFromUrl();
    const userType = "professor";

    if (userID && userType) {
        $.ajax({
            url: `/professor/${userID}/api/eventList`,
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
    const match = window.location.pathname.match(/\/professor\/(\d+)/);
    return match ? match[1] : null;
}

function fetchProfessorEventDetails() {
    const eventID = document.getElementById("professorSearchEventID").value;
    if (!eventID) {
        alert("Please enter an Event ID.");
        return;
    }

    $.ajax({
        url: `/professor/api/eventDetails/${eventID}`,
        type: "GET",
        success: function(event) {
            if (event.title) {
                document.getElementById("professorEventTitle").textContent = event.title;
                document.getElementById("professorEventDescription").textContent = event.description;
            } else {
                alert("Event not found.");
                document.getElementById("professorEventTitle").textContent = '';
                document.getElementById("professorEventDescription").textContent = '';
            }
        },
        error: function(error) {
            console.error("Error fetching event details:", error);
            alert("Failed to fetch event details. Please try again.");
        }
    });
}
