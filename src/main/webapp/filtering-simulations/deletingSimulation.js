
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
                    refreshSimulations();
                    clearSimulationNameDisplays();
                    clearSelectedSimulation();
                }
            });
        }
    })
});

