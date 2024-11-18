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
