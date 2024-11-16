
async function fetchAssignedStudents(profUID) {
    const response = await fetch(`/professors/${profUID}/assigned-students`);
    const studentIds = await response.json();

    const tableBody = document.getElementById("applications-body");
    tableBody.innerHTML = "";

    studentIds.forEach(studentId => {
        const row = document.createElement("tr");

        const studentIdCell = document.createElement("td");
        studentIdCell.textContent = studentId;
        row.appendChild(studentIdCell);

        const fileCell = document.createElement("td");
        fileCell.textContent = "File.pdf";  // Placeholder, replace with actual data
        row.appendChild(fileCell);

        const ratingCell = document.createElement("td");
        const select = document.createElement("select");
        ["EXCELLENT", "GOOD", "AVERAGE", "BELOW_AVERAGE", "POOR"].forEach(rating => {
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
