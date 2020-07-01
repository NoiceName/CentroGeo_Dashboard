
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
                        alert('Database schema already exists!');
                    }
                    else if (resp.result==='success'){
                        alert('Database has been successfully initiated and admin user has been set!');
                    }
                }
            }
        );

    });

});