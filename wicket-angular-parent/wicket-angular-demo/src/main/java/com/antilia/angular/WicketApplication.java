package com.antilia.angular;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.antilia.angular.example.IPersonService;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.example.angularajax.StatelessMasterDetailPage;
import com.antilia.angular.example.basic.BasicListViewLazyPage;
import com.antilia.angular.example.basic.BasicListViewPage;
import com.antilia.angular.example.filtering.FilterPersonListViewPage;
import com.antilia.angular.repeater.IJSONifier;
import com.antilia.angular.repeater.JsonAngularListViewResourceReference;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.antilia.Angular#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	public static final List<Person> PERSONS = new ArrayList<Person>();
	
	private static final String[] NAMES = {
		"Julio", "Camila", "Martina", "Martin", "Juan", 
		"Pedro", "Pitter", "John", "Lucia", "Carla", "Juana", "Juan", "Joan", "Leo"};
	
	private static final String[] LASTNAMES = {
		"Perez", "Kurtz", "Martin", "Juan", "Dos Pasos",
		"Reinaldo", "Castro", "Zenca", "Perla", "Dominguez", "Kobayashi", "Cupper", "Messi", "Zaviola"};

	public static final String PERSONS_MOUNT_POINT = "/persons/";
	
	private static Random random = new Random();
	
	private static  RandomString randomString = new RandomString(500);
	
	private static JsonAngularListViewResourceReference<Person> personsResource;
	
	static {
		for(long i = 0; i < 300; i++) {
			Person person = new Person(i);
			person.setName(NAMES[random.nextInt(NAMES.length)]);
			person.setLastName(LASTNAMES[random.nextInt(LASTNAMES.length)]);
			person.setAge(random.nextInt(100));
			person.setLongDescription(randomString.nextString());
			person.setRegistered((new Date(0+10000*random.nextInt(1000000))));
			String email = person.getName()+"."+person.getLastName()+"@mycompnay.com";
			person.setEmail(email);
			Gravatar gravatar = new Gravatar();			
			gravatar.setSize(30);
			gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
			String url =gravatar.getUrl(email);
			person.setImageUrl(url);
			
			gravatar = new Gravatar();			
			gravatar.setSize(150);
			gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
			url =gravatar.getUrl(email);
			person.setBigImageUrl(url);
			PERSONS.add(person);
		}
	}
	
	public static class PersonService implements IPersonService {
		
		@Override
		public Person find(Long id) {
			for(Person person: PERSONS) {
				if(person.getId().equals(id)) {
					return person;
				}
			}
			return null;
		}
	}
	
	private PersonService personService;
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		personService = new PersonService();
		
		mountResource(PERSONS_MOUNT_POINT, personsResource = new JsonAngularListViewResourceReference<Person>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected IJSONifier<Person> getIjsoNifier() {
				return PersonsJSonifier.getInstance();
			}

			@Override
			protected Iterator<? extends Person> getListIterator() {
				return PERSONS.iterator();
			}
		});
		
		mountPage("/basic", BasicListViewPage.class);
		mountPage("/basic-lazy", BasicListViewLazyPage.class);
		mountPage("/filtering", FilterPersonListViewPage.class);
		
		mountPage("/stateless-master-detail", StatelessMasterDetailPage.class);
		
		
		// add your configuration here
	}

	public IPersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	/**
	 * 
	 * @return
	 */
	public static WicketApplication getApplication() {
		return (WicketApplication)get();
	}

	public static JsonAngularListViewResourceReference<Person> getPersonsResource() {
		return personsResource;
	}
}
