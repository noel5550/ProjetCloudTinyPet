package com.cloud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;

@Api(name = "cloud")
public class UserEndpoint {
	
	public UserEndpoint() {
	}
	
	@ApiMethod(
	        path = "user/get/{email}/{auth}",
	        httpMethod = HttpMethod.GET
	    )
	public User getUser(@Named("email") String email, @Named("auth") String auth) {
		return new User(email, auth);
	}
	
	@ApiMethod(
	        path = "user/getCloud/{pseudo}",
	        httpMethod = HttpMethod.GET
	    )
	public UserCloud getUserCloud(@Named("pseudo") String pseudo) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
		Filter pf = new FilterPredicate("pseudo", FilterOperator.EQUAL, pseudo);
		Query q = new Query("UserCloud").setFilter(pf);

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
		
		if(results.size()>0) {
			Entity e = results.get(0);
			UserCloud uc = new UserCloud((String)e.getProperty("pseudo"));
			uc.setListFollowers((ArrayList<String>)e.getProperty("listFollowers"));
			return uc;
		}
		
		return null;				
	}
	
	@ApiMethod(
	        path = "user/create",
	        httpMethod = HttpMethod.POST
	    )
	public User createUser(@Named("email") String email, @Named("auth") String auth) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		User u = new User(email, auth);
		
		if(u != null) {
			Entity user = new Entity("UserCloud");
			user.setProperty("listFollowers", null);
	  		user.setProperty("pseudo",u.getNickname());
	  		datastore.put(user);
	  		return u;
		}
		return null;
	}
	
	@ApiMethod(
	        path = "user/follow",
	        httpMethod = HttpMethod.POST
	    )
	public UserCloud followUser(@Named("user") String user, @Named("follower") String follower) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		UserCloud uc = this.getUserCloud(user);
		ArrayList<String> list = uc.getListFollowers();
		if(list == null) {
			list = new ArrayList<>();
		}
		list.add(follower);
		uc.setListFollowers(list);
	
		Entity u;
	        
		Filter pf = new FilterPredicate("pseudo", FilterOperator.EQUAL, user);
		Query q = new Query("UserCloud").setFilter(pf);

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
		
		if(results.size()>0) {
			u = results.get(0);
			u.setProperty("listFollowers", uc.getListFollowers());
	  		u.setProperty("pseudo",uc.getPseudo());
	  		datastore.put(u);
	  		return uc;
		} else {
			return null;
		}
	}
}
