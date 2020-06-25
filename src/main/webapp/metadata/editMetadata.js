
 $(document).ready(function (){
	 	var domSelect = $('#tag');
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
	 
$(function() {
	//add new tags in the select list
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
	
	/**
	 * select multiple tags,
	 * For windows: Hold down the control (ctrl) button to select multiple options
	 * For Mac: Hold down the command button to select multiple options 
	 */ 
	$('#tag').change(function(event){
		event.preventDefault();
		var choices = $('#tag option');
		var values = [];
	
		for(i = 0;i<choices.length; i++){		 
			if(choices[i].selected){
				var currentOption = choices[i].text;
				values.push(currentOption);
			}
		} 
	    $('#tagValues').val(values);
		console.log(values);		
	});
	
    $("#remove").click(function (event) {
		 event.preventDefault();
		 var options = [];
		 var domSelect = $('#tag');
		 
		 if(options === null){
			 alert('sorry, no tags can be deleted!');
		 }
		 else {
			 options = JSON.parse(localStorage.getItem('tag-options'));
		 }
		 var removeTag = $("#tag option:selected").val();        		
		 var index = options.indexOf(removeTag);

         if(index > -1){
				options.splice(index, 1);
          }		 
		 localStorage.setItem("tag-options", JSON.stringify(options)); 
		 $("#tag option:selected").remove();
		 console.log(JSON.parse(localStorage.getItem('tag-options')));
         alert("Selected tag removed.");
     });
	   
	  $('#myForm' ).submit(function( event ) {
			event.preventDefault();

			var title = $( '#title' );
			var name = $( '#name' );
			var date = $( '#date' );
			var tag = $( '#tagValues' );
			var description = $( '#description' );
			
			var formData = JSON.stringify({
				'title' : title.val(),
				'simulation_name' : name.val(),
				'date': date.val(),
				'tag_values': tag.val(),
				'description': description.val()
			});
			
			console.log(formData);
			//Send the metadata to the server.
			//Wait for the server response 
			var request=$.ajax({
				url: '/CentroGeo/resources/simulations/metadata',
				data: formData,
				type: 'POST',
				dataType: 'json',
				contentType : 'application/json',
				success:function (resp) {
					
					     console.log(resp.result); // no result!???
					     
							if(resp.result =='true'){
								//close the page modal and pop up alert!
								alert("success!");
								console.log("success!")
							}
							else {			
								alert("oops! something wrong..");				
							}
					     },
					     
	            error: function(jqXHR, textStatus, errorThrown) {        	
	            	console.log(jqXHR, textStatus, errorThrown);
	            }
			})
			console.log("data sent");

			});
});

