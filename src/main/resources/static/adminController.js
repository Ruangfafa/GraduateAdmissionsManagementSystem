document.addEventListener("DOMContentLoaded", function() {
    flatpickr("#initDate", {
        minDate: "today",
        dateFormat: "Y-m-d",
        enableTime: false
    });
});

function getUidFromUrl() {
    const match = window.location.pathname.match(/\/admin\/(\d+)/);
    return match ? match[1] : null;
}

function createNewEvent() {
    const uid = getUidFromUrl(); // 动态获取 uid
    const initDate = document.getElementById("initDate").value;
    const duration = document.getElementById("duration").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    if (!initDate || !duration || !title || !description) {
        alert("Please fill in all fields.");
        return;
    }

    const eventData = {
        initDate: initDate,
        durations: duration,
        info: title + "%" + description
    };


    $.ajax({
        url: `/admin/${uid}`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(eventData),
        success: function(response) {
            alert("Event created successfully!");
            document.getElementById("initDate").value = '';
            document.getElementById("duration").value = '';
            document.getElementById("title").value = '';
            document.getElementById("description").value = '';
        },
        error: function(error) {
            console.error("Error creating event:", error);
            alert("Failed to create event. Please try again.");
        }
    });
}
