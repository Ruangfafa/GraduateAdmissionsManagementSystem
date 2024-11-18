document.addEventListener("DOMContentLoaded", function () {
    const urlParts = window.location.pathname.split('/');
    const profUID = urlParts[2]; // Extract profUID from the URL
    const eventUID = urlParts[4]; // Extract eventUID from the URL

    fetchAssignedStudents(profUID, eventUID);
});

async function fetchAssignedStudents(profUID, eventUID) {
    try {
        // Fetch the assigned students for the given professor and event
        const response = await fetch(`/professors/${profUID}/profEvent/${eventUID}/assigned-students`);
        const studentIds = await response.json();
        console.log("Fetched students:", studentIds);

        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = ""; // Clear the table body

        studentIds.forEach(studentId => {
            const row = document.createElement("tr");

            // Add checkbox
            const selectCell = document.createElement("td");
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.value = studentId;
            selectCell.appendChild(checkbox);
            row.appendChild(selectCell);

            // Add student ID
            const studentIdCell = document.createElement("td");
            studentIdCell.textContent = studentId;
            row.appendChild(studentIdCell);

            // Placeholder for application file
            const applicationFileCell = document.createElement("td");
            applicationFileCell.textContent = "N/A"; // Update if needed
            row.appendChild(applicationFileCell);

            // Add evaluation dropdown
            const ratingCell = document.createElement("td");
            const select = document.createElement("select");
            ["Don’t recommend for admission", "Recommend but not interested in supervision",
                "Recommend but no funding", "Recommend and yes to funding"].forEach(optionText => {
                const option = document.createElement("option");
                option.value = optionText;
                option.textContent = optionText;
                select.appendChild(option);
            });
            ratingCell.appendChild(select);
            row.appendChild(ratingCell);

            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error("Error fetching assigned students:", error);
    }
}

function submitSelection() {
    const selectedStudents = [];
    const tableBody = document.getElementById("table-body");

    // Loop through each row to find selected students
    tableBody.querySelectorAll("tr").forEach(row => {
        const checkbox = row.querySelector("input[type='checkbox']");
        const ratingDropdown = row.querySelector("select");

        if (checkbox && checkbox.checked) {
            selectedStudents.push({
                studentId: checkbox.value,
                rating: ratingDropdown.value
            });
        }
    });

    // Send the selected students to the backend
    fetch('/professors/submit-selection', {
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
