// Extract stdUID and eventUID from URL
function getUrlParams() {
    const pathSegments = window.location.pathname.split('/');
    const stdIndex = pathSegments.indexOf('student');
    const eventIndex = pathSegments.indexOf('stdEvent');

    if (stdIndex === -1) {
        console.error('Could not find student in URL');
        return null;
    }else if(eventIndex === -1){
        console.error('Could not find event in URL');
        return null;
    }

    return {
        stdUID: pathSegments[stdIndex + 1],
        eventUID: pathSegments[eventIndex + 1]
    };
}

// Insert selected prof to the prof table
function selectProf(){
    var profList = document.getElementById("profList");
    var profTable = document.getElementById("selectedProfs");
    var selectedProf = profList.options[profList.selectedIndex];

    if (checkSelectedProf(selectedProf)){
        var rowIndex = profTable.rows.length

        var removeButton = document.createElement("button");
        removeButton.innerText = "Remove";
        removeButton.value = selectedProf.value;
        removeButton.onclick = function () {
            removeSelectedProf(removeButton.value);
        };

        var insertedRow = profTable.insertRow(rowIndex);
        insertedRow.value = selectedProf.value;

        var profNameCell = insertedRow.insertCell(0);
        var removeButtonCell = insertedRow.insertCell(1);

        profNameCell.innerText = selectedProf.text;
        removeButtonCell.appendChild(removeButton);
    }
    profList.value = 'default';  // set the selected value to 'default'
}

// Check if new selected prof already existed or if the selected prof is the default option
// if new selected is either default or already existed, return False
function checkSelectedProf(newSelected){
    var returnedVal = true;
    if(newSelected.value == "default"){
        returnedVal = false;
    }else {
        var profTable = document.getElementById("selectedProfs");
        for (var i = 0; i < profTable.rows.length; i++){
            if (profTable.rows[i].value == newSelected.value){
                returnedVal = false;
                break;
            }
        }
    }
    return returnedVal;
}

function removeSelectedProf(removedProf){
    var profTable = document.getElementById("selectedProfs");
    for (var i = 0; i < profTable.rows.length; i++){
        if (profTable.rows[i].value == removedProf){
            profTable.deleteRow(i);
            break;
        }
    }
}

let cvByte = "";

async function getFilePromise(file) {
    let promise = convertFileToBase64(file);
    return await promise;
}
function convertFileToBase64(file) {
    return new Promise(function (resolve, reject) {
        let reader = new FileReader();
        reader.onload = function () { resolve(reader.result); };
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}

function submitApplication(){
    var CVFile = document.getElementById("CV");
    var diplomaFile = document.getElementById("diploma");
    var gradeFile = document.getElementById("grade");
    var selectedProfs = document.getElementById("selectedProfs");
    var researchFields = document.getElementById("researchFields");

    var submitMsgLabel = document.getElementById("invalidMessage");

    var message = "Successfully submitted!";

    if(CVFile.files.length == 1) {
        var cvPromise = getFilePromise(CVFile.files[0]);
        cvPromise.then(function (result) {
            cvByte = result;
            console.log("finish loading cv")
        })
    }

    if (CVFile.files.length != 1) {
        message = "Missing CV File!";
    }else if (diplomaFile.files.length != 1){
        message = "Missing Diploma File!";
    }else if (gradeFile.files.length != 1){
        message = "Missing Grade Audit File!";
    }else if(selectedProfs.rows.length == 0){
        message = "Missing desired professor(s)!";
    }else if (researchFields.value.trim() == "") {
        message = "Missing Research Field information!";
    }else if (cvByte == ""){
        message = "Waiting for loading CV!";
    }else {
        console.log(cvByte);

        const params = getUrlParams();
        var desiredProfsUID = [];
        for (var i = 0; i < selectedProfs.rows.length; i++) {
            desiredProfsUID.push(selectedProfs.rows[i].value.toString());
        }

        $.ajax({
            url: '/student/'+params.eventUID+'/stdEvent/'+params.stdUID,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                coverLetterFile: cvByte.split(',')[1],
                diplomaFile: diplomaFile.files[0].name,
                gradeAuditFile: gradeFile.files[0].name,
                userUID: params.stdUID,
                eventUID: params.eventUID,
                desireProfessors: desiredProfsUID.join(),
                stdFields: researchFields.value.toString(),
                profcomment: null
            }),
            success: function (data) {
                if (data) {
                } else
                    alert("Failed Create Application");
                },
            error: function () {
                console.log('Error ${error}');
                alert("Post Error!");
            }
        });
    }
    submitMsgLabel.innerText = message;
}


document.addEventListener("DOMContentLoaded", function () {
    const params = getUrlParams();
    fetchProfProfiles(params.eventUID);
});

function fetchProfProfiles(eventUID) {
    $.ajax({
        url: '/student/stdEvent/' + eventUID + '/profProfiles',
        type: 'GET',
        success: function (profProfiles) {
            displayProfProfiles(profProfiles);
        },
        error: function () {
            console.error('Error fetching profProfiles');
        }
    });
}

function displayProfProfiles(profProfiles) {
    var profProfileTableBody = document.getElementById("profProfileTableBody");
    profProfileTableBody.innerHTML = ""; // Clear any existing rows

    profProfiles.forEach(function (profProfile) {
        var row = document.createElement("tr");

        var profUIDCell = document.createElement("td");
        profUIDCell.innerText = profProfile.profUID;
        row.appendChild(profUIDCell);

        var researchCell = document.createElement("td");
        researchCell.innerText = profProfile.research || "N/A";
        row.appendChild(researchCell);

        var infoCell = document.createElement("td");
        infoCell.innerText = profProfile.info || "N/A";
        row.appendChild(infoCell);

        profProfileTableBody.appendChild(row);
    });
}

