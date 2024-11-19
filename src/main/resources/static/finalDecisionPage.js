document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('.final-decision-form');

    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();

            const profUID = this.getAttribute('data-prof-id');
            const adminUID = this.getAttribute('data-admin-id');
            const eventUID = this.getAttribute('data-event-id');

            const selectedCheckboxes = this.querySelectorAll('input[name="selectedStudents"]:checked');
            const selectedStudents = Array.from(selectedCheckboxes).map(cb => cb.value);

            const successMessage = document.getElementById(`successMessage-${profUID}`);
            const errorMessage = document.getElementById(`errorMessage-${profUID}`);

            fetch(`/admin/${adminUID}/adminEvent/${eventUID}/profprofile/${profUID}/finalDecision`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.getAttribute('content')
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
                    console.log('Success:', result);
                })
                .catch(error => {
                    errorMessage.textContent = 'Error saving final decisions: ' + error;
                    errorMessage.style.display = 'block';
                    successMessage.style.display = 'none';
                    console.error('Error:', error);
                });
        });
    });
});