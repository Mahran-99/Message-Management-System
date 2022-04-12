package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="user") // sending xml
public class User {
	private String Username;
	private int id;

	public String getName	() {
		return Username;
	}

	public void setName(String name) {
		this.Username = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		return id+"::"+Username;
	}

}
