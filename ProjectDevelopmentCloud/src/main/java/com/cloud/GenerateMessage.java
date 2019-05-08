package com.cloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

/**
 * Servlet implementation class GenerateMessage
 */
public class GenerateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random r=new Random();
		response.getWriter().println("<p>creating messages</p>");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		long t1 = System.currentTimeMillis();
		
		Integer nb_messages = 120;
		if ( request.getParameter("nb") != null) {
			nb_messages = Integer.parseInt(request.getParameter("nb"));
		}
		String user_pseudo = "user1";
		MessageEndpoint me = new MessageEndpoint();
		
		for(int i = 0; i < nb_messages; i++) {
			
			me.createMessage(user_pseudo, "coucou " + i, null, null);
		}
		
		long t2 = System.currentTimeMillis() - t1;
		response.getWriter().println("<p>done in " + t2 + " ms</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
