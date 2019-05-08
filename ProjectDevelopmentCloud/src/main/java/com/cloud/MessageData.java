package com.cloud;

import java.util.ArrayList;

import javax.jdo.annotations.Index;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class MessageData implements java.io.Serializable {
	
	//mettre les hashtag et les followers de l'user
	//il faut que sa key soit son user
	//on fait les recherche d'user dessus 
	@Index
	ArrayList<String> listHashtag = new ArrayList<>();
	@Index
	ArrayList<String> listFollowers = new ArrayList<>();
	Key parent;
	
	public MessageData(){
		
	}

	public ArrayList<String> getListHashtag() {
		return listHashtag;
	}

	public void setListHashtag(ArrayList<String> listHashtag) {
		this.listHashtag = listHashtag;
	}

	public ArrayList<String> getListFollowers() {
		return listFollowers;
	}

	public void setListFollowers(ArrayList<String> listFollowers) {
		this.listFollowers = listFollowers;
	}
	
	public Key getParent() {
		return parent;
	}

	public void setParent(Key parent) {
		this.parent = parent;
	}

	public void createMessageData() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  		Entity msg = new Entity("MessageData",this.parent);
  		msg.setProperty("listFollowers",this.listFollowers);
  		msg.setProperty("listHashtag",this.listHashtag);
  	
  		datastore.put(msg);
	}
	
}
