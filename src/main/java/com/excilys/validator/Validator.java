package com.excilys.validator;


public interface Validator<E> {
	/**
	 * Get and id and check this one is correct, and exist in the DB
	 * @param id
	 * 		id to check
	 * @return
	 * 		true or false depending if the computer exist
	 */
	public boolean validateId(String id);
}
