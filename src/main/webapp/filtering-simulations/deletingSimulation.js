
$(function() {
    let delBtn = document.getElementById('delSim');
    delBtn.addEventListener('click', function(){
        let selectedSimObj = getSelectedSimulationObj();
        if(selectedSimObj == null){
            $('#deleteSimConfirmation').modal('hide');
        }
        else {
            let id = selectedSimObj.simulationId;
            console.log("deleting simulation: " + id);
            //Sends delete request to the server
            $.ajax({
                type: 'DELETE',
                url: '/CentroGeo/resources/simulations/'+id,
                success: function(resp){
                    alert('Simulation: ' + selectedSimObj.name + " was deleted");
                    refreshSimulations();
                    clearSimulationNameDisplays();
                    clearSelectedSimulation();
                },
                error: function(){
                    alert("API responded with an error");
                }
            });
        }
    })
});

$(function(){
    $('#deleteSelectedSimulation').click(function(){
        let selectedSim = getSelectedSimulationObj();
        let selectedSimDiv = createSimulationDiv(selectedSim);
        let container = document.getElementById('deleteSimulationContainer');
        container.appendChild(selectedSimDiv);
    });
});

//Empty the delete simulation container when the modal is hides
$(function(){
    $('#deleteSimConfirmation').on('hidden.bs.modal', function(){
        emptyDeleteSimContainer();
    });
});

//Empty the delete simulation container
function emptyDeleteSimContainer(){
    let container = document.getElementById('deleteSimulationContainer');
    while(container.firstChild){
        container.removeChild(container.lastChild);
    }
}


