package com.cloud;

import java.util.ArrayList;


public class UserCloud implements java.io.Serializable {
	
	ArrayList<String> listFollowers;
	String pseudo;
	
	public UserCloud () {
		listFollowers = new ArrayList<>();
	}
	
	public UserCloud (String p) {
		pseudo = p;
		listFollowers = new ArrayList<>();
	}

	public ArrayList<String> getListFollowers() {
		return listFollowers;
	}

	public void setListFollowers(ArrayList<String> listFollowers) {
		this.listFollowers = listFollowers;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
	
	
}
