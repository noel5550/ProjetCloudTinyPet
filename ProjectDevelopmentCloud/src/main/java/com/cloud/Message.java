package com.cloud;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

public class Message implements java.io.Serializable {
	String contenu;
	Key parent;
	String cleImage;
	String urlImage;
	Date date;
	
	public Message () {
		
	}
	
	public Message (String contenu, Key cle) {
		this.contenu = contenu;
		this.parent = cle;
		this.cleImage = "";
		this.urlImage = "";
	}
	
	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Key getParent() {
		return parent;
	}

	public void setParent(Key parent) {
		this.parent = parent;
	}

	public String getCleImage() {
		return cleImage;
	}

	public void setCleImage(String cleImage) {
		this.cleImage = cleImage;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static ArrayList<String> getHashtag(String msg) {
		ArrayList<String> list = new ArrayList<String>();
		if(msg.contains("#")) {
			String s= "";
			Boolean currentHashtag = false;
			for(int i = 0; i < msg.length(); i++) {
				if(currentHashtag) {
					if(msg.charAt(i) == ' ') {
						currentHashtag = false;
						list.add(s);
						s ="";
					}else if (msg.charAt(i) == '#') {
						list.add(s);
						s ="";
					}else {
						s += msg.charAt(i);
					}
				}
				else if (msg.charAt(i) == '#') {
					currentHashtag = true;
				}
			}
			if(currentHashtag) {
				list.add(s);
			}
		}
		return list;
	}
}
