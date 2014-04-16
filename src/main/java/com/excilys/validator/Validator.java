package com.excilys.validator;


public interface Validator<E> {
//	public HashMap<String, String> validate(E obj);
	public boolean validateId(String id);
}
