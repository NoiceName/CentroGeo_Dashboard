$(function() {

    $('#send').click(function(event){
        event.preventDefault();
        let inp = document.getElementById('action');
        console.log(inp.value);
        if(inp.value!=='RESET'){
            alert('Please ensure that your input is correct!');
            return;
        } else {
            let form = $('#resetDBForm');
            $.ajax({
                    type: 'POST',
                    url: '/CentroGeo/resources/reset_db',
                    data: form.serialize(),
                    dataType: 'json',
                    contentType: 'application/x-www-form-urlencoded',
                    success: function(resp){
                        console.log(resp);
                        if(resp.result==='true'){
                            window.location.replace('/CentroGeo/initialSetup/initialise.html');
                        } else {
                            alert("Please ensure that your input is correct");
                        }
                    }
                }
            );
       }

    });

});