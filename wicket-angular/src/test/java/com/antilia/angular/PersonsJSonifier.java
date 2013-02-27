/**
 * 
 */
package com.antilia.angular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.antilia.angular.repeater.IJSONifier;

/**
 * @author reiern70
 *
 */
public class PersonsJSonifier implements IJSONifier<Person> {

	private static final long serialVersionUID = 1L;
	
	private static final PersonsJSonifier instance = new PersonsJSonifier();
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	/**
	 * 
	 */
	private PersonsJSonifier() {
	}

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IJSONifier#toJSON(java.lang.Object)
	 */
	@Override
	public String toJSON(Person bean) {
		return "{"
				+toJSON("id", Long.toString(bean.getId()), true)
				+toJSON("name",bean.getName(), true)
				+toJSON("lastName",bean.getLastName(), true)
				+toJSON("age",bean.getAge(), true)
				+toJSON("email",bean.getEmail(), true)
				+toJSON("img",bean.getImageUrl(), true)
				+toJSON("registered",DATE_FORMAT.format(bean.getRegistered()), false)
				+"}";
	}
	
	private String toJSON(String name, String value, boolean comma) {
		return "\""+name+"\":\""+value+"\""+(comma?",":"");
	}
	
	private String toJSON(String name, int value, boolean comma) {
		return "\""+name+"\": "+value+""+(comma?",":"");
	}


	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IJSONifier#formJSON(java.lang.String)
	 */
	@Override
	public Person formJSON(String json) {
		return null;
	}

	public static PersonsJSonifier getInstance() {
		return instance;
	}

}
