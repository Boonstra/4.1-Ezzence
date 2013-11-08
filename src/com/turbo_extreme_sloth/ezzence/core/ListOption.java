package com.turbo_extreme_sloth.ezzence.core;

public class ListOption {
	
	private Class<?> value;
	private String name;
	
	/**
	 * Constructor for initialisation of an listoption
	 * @param name the shown name of the option
	 * @param value the class this option is attached to
	 */
	public ListOption(String name, Class<?> value){
		this.value = value;
		this.name = name;
	}


	/**
	 * gets the class
	 * @return class the attached class to this option
	 */
	public Class<?> getValue() {
		return value;
	}
	
	/**
	 * gets the name of this option
	 * @return the name of this option
	 */
	public String getName(){
		return this.name;
	}


	@Override
	public String toString() {
		return name;
	}
	
	
	

}
