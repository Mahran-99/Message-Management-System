package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "topics") // sending xml

public class Topics {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return id+"::"+name;
	}
	
}
