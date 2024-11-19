document.addEventListener('DOMContentLoaded', function() {
    // Get all forms with class 'final-decision-form'
    const forms = document.querySelectorAll('.final-decision-form');

    forms.forEach(form => {
        const profUID = form.id.split('-')[1];
        const successMessage = document.getElementById(`successMessage-${profUID}`);
        const errorMessage = document.getElementById(`errorMessage-${profUID}`);

        form.addEventListener('submit', function(event) {
            event.preventDefault();

            // Get selected students for this professor
            const selectedCheckboxes = form.querySelectorAll('input[name="selectedStudents"]:checked');
            const selectedStudents = Array.from(selectedCheckboxes).map(cb => cb.value);

            // Get adminUID and eventUID from the URL
            const pathParts = window.location.pathname.split('/');
            const adminUID = pathParts[2];
            const eventUID = pathParts[4];

            // Send POST request
            fetch(`/admin/${adminUID}/adminEvent/${eventUID}/profprofile/${profUID}/finalDecision`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: selectedStudents.length > 0 ?
                    `selectedStudents[]=${selectedStudents.join('&selectedStudents[]=')}`
                    : 'selectedStudents[]='
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(result => {
                    successMessage.textContent = `Final decisions saved successfully for professor ${profUID}`;
                    successMessage.style.display = 'block';
                    errorMessage.style.display = 'none';
                })
                .catch(error => {
                    errorMessage.textContent = 'Error saving final decisions: ' + error;
                    errorMessage.style.display = 'block';
                    successMessage.style.display = 'none';
                });
        });
    });
});
