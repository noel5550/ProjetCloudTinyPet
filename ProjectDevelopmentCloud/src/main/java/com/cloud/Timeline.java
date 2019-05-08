package com.cloud;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.*;

/**
 * Servlet implementation class Timeline
 */
public class Timeline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Timeline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService userService = UserServiceFactory.getUserService();
			MessageEndpoint me = new MessageEndpoint();
			Integer nb_messages = 10;
			if ( request.getParameter("limit") != null) {
				nb_messages = Integer.parseInt(request.getParameter("limit"));
			}
			
			if(request.getParameter("tag") != null) {
				long t1 = System.currentTimeMillis();
				
				request.setAttribute("messages",me.getMessageHashtag(request.getParameter("tag"), nb_messages));
				
				long t2 = System.currentTimeMillis() - t1;
				request.setAttribute("tempsRecupMessage", String.valueOf(t2));
			}
			else if(userService.getCurrentUser() != null) {
				long t1 = System.currentTimeMillis();
				
				request.setAttribute("messages", me.getMessageFollow(userService.getCurrentUser().getNickname(), nb_messages));
				
				long t2 = System.currentTimeMillis() - t1;
				request.setAttribute("tempsRecupMessage", String.valueOf(t2));
			}
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/timeline.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("rechercheUser") != null) {
			response.sendRedirect("/findUser?pseudo=" + request.getParameter("valueRecherche"));
		}
		else if (request.getParameter("rechercheHashtag") != null) {
			response.sendRedirect("/timeline?tag=" + request.getParameter("valueRecherche"));
		}
		else if (request.getParameter("EnvoieMessage") != null) {
			
			UserService userService = UserServiceFactory.getUserService();
			String cleFichierUploade = "";
	        String urlImage = "";
	        
			long t1 = System.currentTimeMillis();
			
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	        ImagesService imagesService = ImagesServiceFactory.getImagesService(); // Récupération du service d'images
	        
	        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
	        List<BlobKey> blobKeys = blobs.get("photoFile");
	        
        	try {
				cleFichierUploade = blobKeys.get(0).getKeyString();
				urlImage = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKeys.get(0)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				cleFichierUploade = "";
				urlImage = "";
			}
			
			MessageEndpoint me = new MessageEndpoint();
			me.createMessage(userService.getCurrentUser().getNickname(), request.getParameter("msg"), cleFichierUploade, urlImage);
			
			long t2 = System.currentTimeMillis() - t1;
			response.sendRedirect("/timeline?tempsCreationMessage=" +  String.valueOf(t2));
		}
	}

}
