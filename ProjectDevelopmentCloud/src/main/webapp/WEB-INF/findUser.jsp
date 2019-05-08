<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.List" %>
<%@include file="header.jsp" %>

	<h1>Recherche</h1>
	<section>
		<form method="post">
			<p>
				<label>Pseudo : <input type="text" name="valueRecherche" /></label>
			</p>
				<input type="hidden" name="rechercheUser" value="rechercheUser" />
				<input type="submit" />
		</form>
		<%if (userService.getCurrentUser() != null && request.getParameter("nomAjout") != null) { %>
				<h4><%= request.getAttribute("nomAjout") %> suivi !</h4>
		<%}
	   if(request.getAttribute("users") != null){ 
	%>
	</section>
	<section>
		<h2>User</h2>
		<%
				List<Entity> users = (List<Entity>) request.getAttribute("users");
				for (Entity u : users) {
			%>
			<p>
				<strong><%= u.getProperty("pseudo") %></strong>
				<%
				if(userService.getCurrentUser() != null ) {%>
					<form method="post">
					<input type="hidden" name="userFollow" value="<%=u.getProperty("pseudo")%>">
					<button type="submit">Suivre</button>
					</form>
			<% } %>
			</p>
			<%
				}
	}
			%>
	</section>
			
<%@include file="footer.html" %>