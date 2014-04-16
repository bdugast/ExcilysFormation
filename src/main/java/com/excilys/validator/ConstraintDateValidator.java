package com.excilys.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

public class ConstraintDateValidator implements
		ConstraintValidator<DateValidator, String> {

	@Override
	public void initialize(DateValidator arg0) {
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		String country = LocaleContextHolder.getLocale().getLanguage();

		DateTimeFormatter fmt = null;
		String reg = null;
		
		switch (country) {
		case "fr":
			reg = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(\\d{4})$";
			fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
			break;

		default:
			reg = "^(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";
			fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
			break;
		}

		if (!arg0.equals("")) {
			if (!arg0.matches(reg)) {
				return false;
			} else {
				try {
					fmt.parseDateTime(arg0);
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

}
