document.addEventListener("DOMContentLoaded", function() {
    flatpickr("#initDate", {
        minDate: "today",
        dateFormat: "Y-m-d",
        enableTime: false
    });

    function populateSelectOptions(selectId) {
        const select = document.getElementById(selectId);
        for (let i = 1; i <= 100; i++) {
            const option = document.createElement("option");
            option.value = i;
            option.text = i;
            select.appendChild(option);
        }
    }

    populateSelectOptions("duration1");
    populateSelectOptions("duration2");
    populateSelectOptions("duration3");
    populateSelectOptions("duration4");
    populateSelectOptions("duration5");

    $.ajax({
        url: "/admin/api/eventList",
        type: "GET",
        success: function(events) {
            const eventList = document.getElementById("eventList");
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

function createNewEvent() {
    const uid = getUidFromUrl();
    const initDate = document.getElementById("initDate").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    const duration1 = document.getElementById("duration1").value;
    const duration2 = document.getElementById("duration2").value;
    const duration3 = document.getElementById("duration3").value;
    const duration4 = document.getElementById("duration4").value;
    const duration5 = document.getElementById("duration5").value;

    if (!initDate || !duration1 || !duration2 || !duration3 || !duration4 || !duration5 || !title || !description) {
        alert("Please fill in all fields.");
        return;
    }

    const eventData = {
        initDate: initDate,
        dur1: parseInt(duration1),
        dur2: parseInt(duration2),
        dur3: parseInt(duration3),
        dur4: parseInt(duration4),
        dur5: parseInt(duration5),
        title: title,
        description: description
    };

    $.ajax({
        url: `/admin/${uid}`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(eventData),
        success: function(response) {
            alert("Event created successfully!");
            document.getElementById("initDate").value = '';
            document.getElementById("duration1").value = '';
            document.getElementById("duration2").value = '';
            document.getElementById("duration3").value = '';
            document.getElementById("duration4").value = '';
            document.getElementById("duration5").value = '';
            document.getElementById("title").value = '';
            document.getElementById("description").value = '';
        },
        error: function(error) {
            console.error("Error creating event:", error);
            alert("Failed to create event. Please try again.");
        }
    });
}

function fetchEventDetails() {
    const eventID = document.getElementById("searchEventID").value;
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

