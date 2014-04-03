package com.excilys.validator;

import java.util.HashMap;

public interface Validator<E> {
	public HashMap<String, String> validate(E obj);
	public boolean validateId(String id);
}
