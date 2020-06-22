
$(function() {
    let delBtn = document.getElementById('delSim');
    delBtn.addEventListener('click', function(){
        let selectedSimObj = getSelectedSimulationObj();
        console.log("hi");
        if(selectedSimObj == null){
            $('#deleteSimConfirmation').modal('hide');
        }
        else {
            let id = selectedSimObj.simulationId;
            //Sends delete request to the server
            $.ajax({
                type: 'DELETE',
                url: '/simulations/'+id,
                success: function(resp){
                    console.log(resp);
                }
            });
        }
    })
});

