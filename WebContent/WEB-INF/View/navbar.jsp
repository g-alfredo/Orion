<div id="nav-buttons" >
	<ul style="display: flex; justify-content: space-around;">
		<li id="cartArea"><a href="Cart">Carrello</a></li>
        <li id="wishlistArea"><a href="WishList">Lista desideri</a></li>
		<li><a href="OnSale">Offerte</a></li>
					
		<li class="dropdown1">
    		<a class="dropbtn1">Categorie</a>
    			<div class="dropdown-content1">
    				<%@ page import="java.util.ArrayList, Model.Beans.*, Model.DAO.*"%>
					<% ArrayList<CategoryBean> categorie=(ArrayList<CategoryBean>) request.getAttribute("categorie");
					   if(categorie!=null){
					   		for(CategoryBean categoria : categorie) { %>
									<a href="Categoria?categoria=<%=categoria.getNome()%>" > <%=categoria.getNome()%></a>
					<% } }%>
								
    			</div>
  		</li>
  	</ul>
  	<div id="genre-nav-buttons" style="display:none">		
	<ul></ul>
	</div>
</div>