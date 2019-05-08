<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cloud.Message" %>
<%@ page import="com.google.appengine.api.blobstore.*" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<%@include file="header.jsp" %>


<section class="features" id="features">
    <div class="container">
      <div class="section-heading text-center">
        <h2>Timeline</h2>
        <hr>
      </div>
      <div class="row">
      
      <form method="post">
      	<p>
      		<label>Recherche users : <input type="text" name="valueRecherche"/></label>
		</p>
		<input type="hidden" name="rechercheUser" value="rechercheUser">
		<input type="submit" />
      </form>
      
      <form method="post">
      	<p>
      		<label>Recherche hashtag : <input type="text" name="valueRecherche" /></label>
		</p>
		<input type="hidden" name="rechercheHashtag" value="rechercheHashtag">
		<input type="submit" />
      </form>
      
      <section>
      <% if(request.getParameter("tempsCreationMessage") != null) {%>
    	  <p>Temps creation message : <%= request.getParameter("tempsCreationMessage") %> ms </p>
      <%}%>
      <form action="<%= blobstoreService.createUploadUrl("/timeline") %>" method="post" enctype="multipart/form-data">
			<p>
				<label>Message : <textarea type="text" name="msg"> </textarea></label>
			</p>
			<p>
				<label>Image : <input type="file" name="photoFile" /></label>
			</p>
			<p>
				<input type="submit" />
			</p>
			<input type="hidden" name="EnvoieMessage" value="envoie">
		</form>
      </section>
      
      <section>
      <p>Temps recuperation : <%= request.getAttribute("tempsRecupMessage") %> ms </p>
		<%
				List<Message> listMessage = (List<Message>) request.getAttribute("messages");
				for (Message m : listMessage) {
			%>
			<hr>
			<p>
				
				<p><%=m.getDate() %></p>
				<p><%=m.getContenu() %></p>
				<% if(m.getUrlImage() != null){ %>
				<img src="<%= m.getUrlImage() %>"/>
				<% } %>
			</p>
			<%
				}
			%>
		</section>
		
      </div>
    </div>
  </div>
</section>

<%@include file="footer.html" %>