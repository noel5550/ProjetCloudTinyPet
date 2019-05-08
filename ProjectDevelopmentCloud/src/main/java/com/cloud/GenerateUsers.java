package com.cloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

/**
 * Servlet implementation class GenerateUsers
 */
public class GenerateUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateUsers() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random r=new Random();
		response.getWriter().println("<p>creating pinpin</p>");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		long t1 = System.currentTimeMillis();
		
		Integer max = 200;
		if ( request.getParameter("nb") != null) {
			max = Integer.parseInt(request.getParameter("nb"));
		}
		Integer maxFollowers = 100;
		if ( request.getParameter("nbf") != null) {
			maxFollowers = Integer.parseInt(request.getParameter("nbf"));
		}
		Integer millier = 0;
		if ( request.getParameter("millier") != null) {
			millier = Integer.parseInt(request.getParameter("millier"));
		}
		
		ArrayList<Entity> listEntity = new ArrayList<>();
		for (int i = millier; i < 200+millier; i++) {
			
	  		Entity user = new Entity("User","user" + i);
	  		ArrayList<String> listFollowers = new ArrayList<>();
	  		for (int j = 0; j < maxFollowers; j++) {
	  			listFollowers.add("user" + r.nextInt(max));
	  		}
	  		user.setProperty("listFollowers", listFollowers);
	  		user.setProperty("pseudo", "user" + i);
	  		user.setProperty("mdp", "user" + i);
	  		listEntity.add(user);
	  		
		}
		datastore.put(listEntity);
			
		long t2 = System.currentTimeMillis() - t1;
		response.getWriter().println("<p>done in " + t2 + " ms</p>");
		
	}
}