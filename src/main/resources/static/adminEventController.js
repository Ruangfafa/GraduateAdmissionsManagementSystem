document.addEventListener("DOMContentLoaded", function () {
    const eventUID = document.getElementById("eventUID").value;

    fetch(`/api/adminEvent/${eventUID}/applications`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const applicationList = document.getElementById("applicationList");
            applicationList.innerHTML = '';

            data.forEach(application => {
                const li = document.createElement("li");
                li.textContent = `Application ID: ${application.applicationUID}, User ID: ${application.userUID}, Professor Comment: ${application.profcomment}`;
                applicationList.appendChild(li);
            });
        })
        .catch(error => console.error("Error fetching applications:", error));
});

function getUidFromUrl() {
    const match = window.location.pathname.match(/\/(admin|student|professor)\/(\d+)/);
    if (match) {
        return match[2];
    }
    return null;
}

function getEventUIDFromUrl() {
    const match = window.location.pathname.match(/\/(admin|student|professor)\/\d+\/\w+\/(\d+)/);
    if (match){
        return match[2];
    }
    return null;
}

function fetchStudentApplicationDetails() {
    const applicationUID = document.getElementById("adminSearchApplicationID").value;
    const eventID = getEventUIDFromUrl();
    const userUID = getUidFromUrl();
    if (!applicationUID) {
        alert("Please enter an Application ID.");
        return;
    }

    $.ajax({
        url: `/admin/${userUID}/adminEvent/${eventID}/adminApp/${applicationUID}/api`,
        type: "GET",
        success: function(response) {
            window.location.href = response;
        },
        error: function(error) {
            console.error("Error fetching Application details:", error);
            alert("Failed to fetch Application details. Please try again.");
        }
    });
}

function fetchFinalDecision() {
    const eventID = getEventUIDFromUrl();
    const userUID = getUidFromUrl();
    $.ajax({
        url: `/admin/${userUID}/adminEvent/${eventID}/fetchFinalDecision/api`,
        type: "GET",
        success: function(response) {
            window.location.href = response;
        },
        error: function(error) {
            console.error("Error", error);
            alert("Failed to fetch. Please try again.");
        }
    });
}