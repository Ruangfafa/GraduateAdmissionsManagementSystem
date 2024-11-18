// Extract profUID and eventUID from URL
function getUrlParams() {
    const pathSegments = window.location.pathname.split('/');
    const profIndex = pathSegments.indexOf('professor');
    const eventIndex = pathSegments.indexOf('profEvent');

    if (profIndex === -1 || eventIndex === -1) {
        console.error('Could not find professor or event in URL');
        return null;
    }

    return {
        profUID: pathSegments[profIndex + 1],
        eventUID: pathSegments[eventIndex + 1]
    };
}

// Load existing profiles
async function loadProfiles() {
    const params = getUrlParams();
    if (!params) return;

    try {
        const response = await fetch(`/professor/${params.profUID}/profEvent/${params.eventUID}/data`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const profiles = await response.json();

        const profilesList = document.getElementById('profilesList');
        profilesList.innerHTML = '<h3>Existing Profiles</h3>';

        if (profiles.length === 0) {
            profilesList.innerHTML += '<p>No profiles found for this professor and event.</p>';
            return;
        }

        profiles.forEach(profile => {
            profilesList.innerHTML += `
                <div class="profile">
                    <p><strong>Profile UID:</strong> ${profile.profProfileUID}</p>
                    <p><strong>Research:</strong> ${profile.research || 'N/A'}</p>
                    <p><strong>Info:</strong> ${profile.info || 'N/A'}</p>
                    <p><strong>Assigned Students:</strong> ${profile.assignedstduidlist || 'None'}</p>
                    <p><strong>Final List:</strong> ${profile.finalstdlist || 'None'}</p>
                </div>
            `;
        });
    } catch (error) {
        console.error('Error loading profiles:', error);
        document.getElementById('profilesList').innerHTML =
            '<p class="error">Error loading profiles. Please try again later.</p>';
    }
}

// Initialize page
document.addEventListener('DOMContentLoaded', () => {
    const params = getUrlParams();
    if (!params) return;


    document.getElementById('currentContext').innerHTML = `
        <p><strong>Professor UID:</strong> ${params.profUID}</p>
        <p><strong>Event UID:</strong> ${params.eventUID}</p>
    `;

    // Load profiles
    loadProfiles();

    //form submission
    const form = document.getElementById('profProfileForm');
    form.addEventListener('submit', async (e) => {
        e.preventDefault(); // Prevent form from submitting normally

        const formData = {
            profUID: parseInt(params.profUID),
            eventUID: parseInt(params.eventUID),
            research: document.getElementById('research').value,
            info: document.getElementById('info').value
        };

        try {
            console.log('Submitting form data:', formData);
            const response = await fetch(`/professor/${params.profUID}/profEvent/${params.eventUID}/submit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            console.log('Success:', result);
            alert('Profile submitted successfully!');
            loadProfiles();
            form.reset();
        } catch (error) {
            console.error('Error:', error);
            alert('Error submitting profile: ' + error.message);
        }
    });
});