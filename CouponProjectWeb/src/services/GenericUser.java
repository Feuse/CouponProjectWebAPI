package services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GenericUser {
	
	private String name = "admin";
	private String password=  "1234";
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
}