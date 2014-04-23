$(function() {
	function readCookie(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) == 0)
				return c.substring(nameEQ.length, c.length);
		}
		return null;
	}

	var cookie = readCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");

	var pattern = "mm-dd-yy";
	var errorMinMsg = "Please set at least 2 characters";
	var errorEmptyMsg = "Please set a name";
	var errorDate = "DATE IS NOT GOOD!!!! this => mm-dd-yyyy";

	if(cookie==("fr")){
		pattern = "dd-mm-yy";
		errorMinMsg = "2 caract&egrave;res minimum!!!";
		errorEmptyMsg = "Met un nom trololo";
		errorDate = "LA DATE EST FAUSSE!!!! Fait &ccedil;a => dd-mm-yyyy" ;
	}

	$(document).ready(function() {
		$.validator.addMethod("dateControl", function(value, element) {
			try {
				jQuery.datepicker.parseDate(pattern, value);
				return true;
			} catch (e) {
				return false;
			}
		}, errorDate);

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
			},
			messages: {
				name: {
					required : errorEmptyMsg,
					minlength : errorMinMsg
				}
			}
		});	
	});

});