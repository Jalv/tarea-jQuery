package edu.upc.eetac.dsa;

import java.util.Date;

public class File {
	private String name;
	private String description;
	private Date creationdate;
	private int size;
	private String[] taglist;
	private String url;

	public File(){

	}

	public File (String name){
		this.name = name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String[] getTaglist() {
		return taglist;
	}

	public void setTaglist(String[] taglist) {
		this.taglist = taglist;
	}

} 
