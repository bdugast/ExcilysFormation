$(document).ready(
		function() {
			$.validator.addMethod(
				"dateControl",
				function(value, element) {
					return value.match(/^$/) || value.match(/^(\d{4})([\/-])(0[1-9]|1[012])\2(0[1-9]|[12][0-9]|3[01])$/);
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
