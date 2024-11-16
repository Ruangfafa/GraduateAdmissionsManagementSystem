
async function fetchAssignedStudents(profUID) {

    const response = await fetch(`/professors/${profUID}/assigned-students`);
    const studentIds = await response.json();

    const tableBody = document.getElementById("table-body");
    tableBody.innerHTML = "";

    studentIds.forEach(studentId => {
        const row = document.createElement("tr");


        // Selection checkbox for each student
        const selectCell = document.createElement("td");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.value = studentId;  // Store student ID as the value
        selectCell.appendChild(checkbox);
        row.appendChild(selectCell);

        const studentIdCell = document.createElement("td");
        studentIdCell.textContent = studentId;
        row.appendChild(studentIdCell);

        // Evaluation dropdown cell
        const ratingCell = document.createElement("td");
        const select = document.createElement("select");
        ["Don’t recommend for admission", "recommend but not interested in supervision",
            "recommend but no funding", "recommend and yes to funding"].forEach(rating => {
            const option = document.createElement("option");
            option.value = rating;
            option.textContent = rating;
            select.appendChild(option);
        });
        ratingCell.appendChild(select);
        row.appendChild(ratingCell);

        tableBody.appendChild(row);
    });
}

// Function to handle the submission of the professor's selection
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