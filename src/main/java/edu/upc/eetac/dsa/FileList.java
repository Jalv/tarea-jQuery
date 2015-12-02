package edu.upc.eetac.dsa;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileList {
	
	private List<File> files;
	
	public FileList() {
		super();
		files = new ArrayList<>();
	}
	
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	public void setTodo(File file) {
		this.files.add(file);
	}

		
}

