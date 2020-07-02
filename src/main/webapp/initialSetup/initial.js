
$(function() {

    $('#send').click(function(event){
        event.preventDefault();
        let form = $('#initialiseDBForm');
        $.ajax({
                type: 'POST',
                url: '/CentroGeo/resources/initialize_db',
                data: form.serialize(),
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                success: function(resp){
                    if(resp.result==='bad_login'){
                        alert('The provided database information is incorrect');
                    }
                    else if (resp.result==='bad_exists'){
                        alert('Database schema ("projectschema") already exists! Please delete it manually and try again!');
                    }
                    else if (resp.result==="bad_params"){
                        alert('One of the parameters was not given please check your input and try again');
                    }
                    else if (resp.result==='success'){
                        alert('Database has been successfully initiated and admin user has been set!');
                    }
                    else if (resp.result==="bad_created"){
                        alert('Database has already been initialised if you would like to reset the schema, please login and go to the reset page');
                    }
                }
            }
        );

    });

});