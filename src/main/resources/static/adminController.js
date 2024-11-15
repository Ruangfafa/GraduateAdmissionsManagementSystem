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
});


function createNewEvent() {
    const uid = getUidFromUrl(); // 动态获取 uid
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

    const duration = `${duration1},${duration2},${duration3},${duration4},${duration5}`;

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

