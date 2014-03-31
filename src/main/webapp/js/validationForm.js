$(document).ready(
		function() {
			$.validator.addMethod(
				"dateControl",
				function(value, element) {
					 try { 
						jQuery.datepicker.parseDate("yy-mm-dd", value);
					 	return true;
					 } catch(e) { 
			            return false;
					 }
				},
				"Please enter a date in the format yyyy-mm-dd.");
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
