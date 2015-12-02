package edu.upc.eetac.dsa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.upc.eetac.dsa.File;

public enum FileDao {
	instance;

	private Map<String, File> contentProvider = new HashMap<String, File>();

	private FileDao() {
		File file = new File("first.java");
		file.setDescription("codigo de prueba");
		String[] checklist = {"Java", "Hello World"};
		file.setUrl("http://prova/first.java");
		file.setTaglist(checklist);
		String string = "02-07-2014";
		Date date;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(string);
			file.setCreationdate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentProvider.put("first.java", file);

		file = new File("second.java");
		file.setDescription("codigo de prueba");
		String[] checklist2 = {"Java", "Hello World 2"};
		file.setUrl("http://prova/second.java");
		file.setTaglist(checklist2);
		string = "02-04-2015";
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(string);
			file.setCreationdate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentProvider.put("second.java", file);

		file = new File("third.java");
		file.setDescription("codigo de prueba");
		String[] checklist3 = {"Java", "OK"};
		file.setUrl("http://prova/third.java");
		file.setTaglist(checklist3);
		string = "07-12-2014";
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(string);
			file.setCreationdate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentProvider.put("third.java", file);

	
	
	}
	
	public Map<String, File> getModel(){
		return contentProvider;
	}

} 
