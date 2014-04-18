$(document).ready(
		function() {
			$.validator.addMethod(
				"dateControl",
				function(value, element) {
					
					function readCookie(name) {
						var nameEQ = name + "=";
						var ca = document.cookie.split(';');
						for(var i=0;i < ca.length;i++) {
							var c = ca[i];
							while (c.charAt(0)==' ') c = c.substring(1,c.length);
							if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
						}
						return null;
					}

					var cookie = readCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");
					var pattern = "mm-dd-yy";
					
					if(cookie==("fr")){
						pattern = "dd-mm-yy";
					}
					
					if(value==""){
						return true;
					}else{
						try { 
							$.datepicker.parseDate(pattern, value);
							return true;
						} catch(e) { 
							return false;
						}
					}
				},
				"JS Please enter a date in the format");
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
