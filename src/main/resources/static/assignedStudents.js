let profUID;
let eventUID;

document.addEventListener("DOMContentLoaded", function () {
    const urlParts = window.location.pathname.split('/');
    profUID = urlParts[2]; // Extract profUID from the URL
    eventUID = urlParts[4]; // Extract eventUID from the URL

    fetchAssignedStudents(profUID, eventUID);

});

async function fetchAssignedStudents(profUID, eventUID) {
    try {
        const response = await fetch(`/professor/${profUID}/profEvent/${eventUID}/assigned-students`);
        const applicationInfos = await response.json();
        console.log("Fetched students:", applicationInfos);

        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = ""; // Clear the table body

        // applicationInfos.forEach(studentId => {
        for (let stdId in applicationInfos) {
            const row = document.createElement("tr");

            // Add checkbox
            const selectCell = document.createElement("td");
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.value = stdId;
            selectCell.appendChild(checkbox);
            row.appendChild(selectCell);

            // Add student ID
            const studentIdCell = document.createElement("td");
            studentIdCell.textContent = stdId;
            row.appendChild(studentIdCell);

            // Add student CV preview button
            const studentCVCell = document.createElement("td");
            const cvButton = document.createElement("button");
            cvButton.textContent = "View CV";
            cvButton.addEventListener('click', function () {
                viewFile("cv", stdId);
            });
            studentCVCell.appendChild(cvButton);
            row.appendChild(studentCVCell);

            // Add student diploma preview button
            const studentDiplomaCell = document.createElement("td");
            const diplomaButton = document.createElement("button");
            diplomaButton.textContent = "View Diploma";
            diplomaButton.addEventListener('click', function () {
                viewFile("dp", stdId);
            });
            studentDiplomaCell.appendChild(diplomaButton);
            row.appendChild(studentDiplomaCell);

            // Add student CV preview button
            const studentGradeCell = document.createElement("td");
            const gradeButton = document.createElement("button");
            gradeButton.textContent = "View grade";
            gradeButton.addEventListener('click', function () {
                viewFile("gd", stdId);
            });
            studentGradeCell.appendChild(gradeButton);
            row.appendChild(studentGradeCell);

            // Add student desired prof
            const desiredProf = document.createElement("td");
            desiredProf.textContent = applicationInfos[stdId][0];
            row.appendChild(desiredProf);

            // Add student desired prof
            const researchField = document.createElement("td");
            researchField.textContent = applicationInfos[stdId][1];
            row.appendChild(researchField);

            // Add evaluation dropdown
            const ratingCell = document.createElement("td");
            const select = document.createElement("select");

            // Map numeric values to meaningful descriptions
            const ratingDescriptions = {
                "0": "Don’t recommend for admission",
                "1": "Recommend but not interested in supervision",
                "2": "Recommend but no funding",
                "3": "Recommend and yes to funding"
            };

            // Create options with numeric values
            Object.entries(ratingDescriptions).forEach(([value, text]) => {
                const option = document.createElement("option");
                option.value = value; // Numeric value as a string
                option.textContent = text; // Description
                select.appendChild(option);
            });

            ratingCell.appendChild(select);
            row.appendChild(ratingCell);

            tableBody.appendChild(row);
            // });
        }
    } catch (error) {
        console.error("Error fetching assigned students:", error);
    }
}

function submitSelection() {
    const selectedStudents = [];
    const tableBody = document.getElementById("table-body");

    tableBody.querySelectorAll("tr").forEach(row => {
        const checkbox = row.querySelector("input[type='checkbox']");
        const ratingDropdown = row.querySelector("select");

        if (checkbox && checkbox.checked) {
            selectedStudents.push({
                studentId: checkbox.value,
                rating: ratingDropdown.value // Numeric value
            });
        }
    });

    // Log the payload being sent
    console.log("Payload being sent:", selectedStudents);

    fetch(`/professor/${profUID}/submit-selection`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(selectedStudents)
    }).then(response => {
        if (response.ok) {
            alert('Selection submitted successfully!');
        } else {
            alert('Failed to submit selection.');
        }
    });
}
function getUidFromUrl() {
    const match = window.location.pathname.match(/\/(admin|student|professor)\/(\d+)/);
    if (match) {
        return match[2];
    }
    return null;
}
function getEventUIDFromUrl() {
    const match = window.location.pathname.match(/\/profEvent\/(\d+)/);
    if (match) {
        return match[1];
    }
    return null;
}
function editProfProfile() {
    const eventID = getEventUIDFromUrl();
    const userUID = getUidFromUrl();

    $.ajax({
        url: `/professor/${userUID}/profEvent/${eventID}/api/editPage`,
        type: "GET",
        success: function(response) {
            // Redirect the user to the returned URL
            window.location.href = response;
        },
        error: function(error) {
            console.error("Error Redirecting to /page", error);
            alert("Error Redirecting to /page. Please try again.");
        }
    });
}

function viewFile(fileType, studentId){
    console.log(window.location.pathname);
    $.ajax({
        url: window.location.pathname,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({fileType: fileType, studentUID: studentId}),
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