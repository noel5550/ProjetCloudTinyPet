package com.cloud;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet implementation class RechercheUser
 */
public class RechercheUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechercheUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			if (request.getParameter("pseudo") != null) {
				Filter keyFilter = new FilterPredicate("pseudo", FilterOperator.EQUAL, request.getParameter("pseudo"));
				Query q = new Query("UserCloud").setFilter(keyFilter);
				List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
	
	            request.setAttribute("users", results);
			}
            
			this.getServletContext().getRequestDispatcher("/WEB-INF/findUser.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		
		if(request.getParameter("rechercheUser") != null) {
			response.sendRedirect("/findUser?pseudo=" + request.getParameter("valueRecherche"));
		}else if(request.getParameter("userFollow") != null) {
			UserEndpoint ue = new UserEndpoint();
			ue.followUser(request.getParameter("userFollow"), userService.getCurrentUser().getNickname());
			response.sendRedirect("/findUser?nomAjout=" + request.getParameter("userFollow"));
		}
	}
}
