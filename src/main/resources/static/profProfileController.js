$(document).ready(function() {
    loadProfiles();

    $('#profProfileForm').submit(function(event) {
        event.preventDefault();
        submitProfile();
    });
});

function submitProfile() {
    var research = $('#research').val();
    var info = $('#info').val();
    var profUID = 152; // Rossa userUID for now
    var eventUID = 770; // Example eventUID for now

    var profProfile = {
        research: research,
        info: info,
        profUID: profUID,
        eventUID: eventUID
    };

    $.ajax({
        url: '/prof/' + profUID + '/profevent/' + eventUID,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(profProfile),
        success: function(data) {
            alert('Profile submitted successfully!');
            loadProfiles();
        },
        error: function() {
            alert('Error submitting profile!');
        }
    });
}

function loadProfiles() {
    var profUID = 152; // Rossa's userUID for now
    var eventUID = 770; // Example eventUID for now

    $.ajax({
        url: '/prof/' + profUID + '/profevent/' + eventUID,
        type: 'GET',
        success: function(data) {
            var profileList = $('#profileList');
            profileList.empty();
            data.forEach(function(profile) {
                profileList.append('<p>Research: ' + profile.research + ', Info: ' + profile.info + '</p>');
            });
        },
        error: function() {
            alert('Error loading profiles!');
        }
    });
}
