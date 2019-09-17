package services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import beans.ClientType;

@XmlRootElement
public class Credentials {
	String username;
	String password;
	ClientType clientType;

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	@XmlElement
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "credentials [username=" + username + ", password=" + password + ", clientType=" + clientType + "]";
	}
}
