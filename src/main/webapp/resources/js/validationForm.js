$(document).ready(
		function() {
			$.validator.addMethod(
				"dateControl",
				function(value, element) {
					if(value==""){
						return true;
					}else{
						try { 
							$.datepicker.parseDate("dd-mm-yy", value);
							return true;
						} catch(e) { 
							return false;
						}
					}
				},
				"JS Please enter a date in the format dd-mm-yyyy.");
			$('#formValidation').validate({
				rules : {
					name : {
						minlength : 2,
						required : true
					},
					introduced : {
						dateControl : true
					},
					discontinued : {
						dateControl : true
					}
				}
			});
		});
