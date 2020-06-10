
$(function () {
	
	$( '#myform' ).submit(function( event ) {
		event.preventDefault();
		
		var title = $( '#title' );
		var editor = $( '#editor' );
		var date = $( '#date' );
		var tag = $( '#tag' );
		var description = $( '#description' );
		
		var formData = JSON.stringify({
			'title' : title.val(),
			'editor' : editor.val(),
			'date': date.val(),
			'tag': tag.val(),
			'description': description.val()
			
		});

		//Send the metadata to the server.
		//Wait for the server response execute updating on a successfull request
		$.ajax({
			url: '/CentroGeo/resources/simulations/metadata',
			data: formData,
			//try with application/json later
			type: 'POST',
			dataType: 'json',
			contentType : 'application/json',
			success:function (resp) {successAdded(resp)},
             error: function(jqXHR, textStatus, errorThrown) {
                alert('Cannot contact the server!');
            }
		});
		console.log("Request sent!");
		});
		
		
		
		
	 //var options =['congestion','date'];
	 //localStorage.setItem("tag-options", JSON.stringify(options));
	  var domSelect = $('#tag');
	
	 $(document).ready(function (){
	  // check if localStorage has option list   
	    
		var selectHTML = "";
		var myArray = JSON.parse(localStorage.getItem('tag-options'));	
		if(myArray !== null){		
		
			for(i = 0; i < myArray.length; i++) {
			/* get add new option element if it exists in the localstorage */

				selectHTML += "<option value='" +myArray[i] + "'>" + myArray[i] + "</option>";
			}		
			domSelect.append(selectHTML);
			console.log(selectHTML);
     
		}	
    });		

	   
	 $("#add-new-tag").click (function(event) {
	   
	     event.preventDefault();
	   // check if the storage has options	    
	   var options = [];					 
	   var tagVal = $('#newTag').val();
	   var optionsFromStorage = JSON.parse(localStorage.getItem('tag-options'));
	   if(optionsFromStorage !== null){
		   options = optionsFromStorage;
	   }
	   
	   // store new option from the add tag form
	   options.push(tagVal);
	   	    
	   // save the options array into the localstorage 
	   localStorage.setItem("tag-options", JSON.stringify(options));
	   
	   // use for debug 
	   console.log(JSON.parse(localStorage.getItem('tag-options')));
	   
	   $('#tag').append(new Option(tagVal, tagVal));   
	   alert("New tag added!");
	   
	   });
	   
     $("#remove").click(function (event) {
		 event.preventDefault();
		 var options = [];
		 
		 if(options === null){
			 alert('sorry, no tags can be deleted!');
		 }
		 else {
			 options = JSON.parse(localStorage.getItem('tag-options'));
		 }
		 
         $("#tag option:selected").remove();
		 var removeTag = domSelect.children("option:selected").val();
		 
		 var index = options.indexOf(removeTag);

         if(index > -1){
				options.splice(index, 1);
           }

     	   
		 
		 localStorage.setItem("tag-options", JSON.stringify(options));
		 console.log(JSON.parse(localStorage.getItem('tag-options')));
         alert("Selected tag removed.");
      });

}); 



function successAdded(resp) {
	console.log(resp.result)
	if(resp.result=='true'){
		//close the page modal and pop up alert!
		window.alert("success!");
	}
	else if (resp.result=='false') {
		
		window.alert("oops! something wrong..");
		
	}
}

