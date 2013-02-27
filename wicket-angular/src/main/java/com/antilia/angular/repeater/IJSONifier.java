/**
 * 
 */
package com.antilia.angular.repeater;

import java.io.Serializable;

/**
 * Converter from a bean to JSON and vice versa.
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70).
 * 
 * <B> the type of the object to translate.
 */
public interface IJSONifier<B> extends Serializable {
	
	
	/**
	 * Translates an object to JSON.
	 * 
	 * @param bean
	 * @return
	 */
	String toJSON(B bean);

	/**
	 * Translates JSON to the object.
	 * 
	 * @param json
	 * @return
	 */
	B formJSON(String json);
}
