<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList, Model.Beans.*"%>
	
<jsp:include page="header.jsp" >
<jsp:param name="pageName" value="Amministrazione"/>
</jsp:include>
<jsp:include page="navbar.jsp" />


	<section style="margin-left: 6px;text-align:center;">
<% ProductBean prodotto=(ProductBean) request.getAttribute("prodotto");
String operazione="Aggiungi";
if (request.getParameter("Modifica")!=null || request.getParameter("Aggiungi")!=null)
  operazione="Modifica";
				%>
		<h1><%=operazione %> prodotto</h1>
		<h5><%=request.getAttribute("notifica")==null ?"":request.getAttribute("notifica") %></h5>
		<% if (request.getParameter("Rimuovi")==null && prodotto!=null){ 
%>
	<div>Stai agendo sul prodotto <b><%=prodotto.getTitolo() %></b> con ID <b><%=prodotto.getIdProdotto() %></b></div><br>
<% } %>
		
		
		<form action="AdminProdotto" method="post" onsubmit="return Checkfiles()" enctype="multipart/form-data" accept-charset="utf-8"  >
				<% if (request.getParameter("Rimuovi")!=null){ %>
				<label>Titolo:</label>
				<input style="padding:10px;font-size:16px;" type="text" name="titolo" required><br><br>
				<label>Categorie:</label>
				<% ArrayList<CategoryBean> categorie=( ArrayList<CategoryBean>) request.getAttribute("categorie");
				for(CategoryBean categoria:categorie){
				%>
					<br><br><input type="checkbox" name="categorie" value="<%=categoria.getNome() %>" ><label><%=categoria.getNome() %></label>
				<% } %><br><br>
				<label>Immagini</label>
  				<input value="Carica immagini..." type="file" name="file" accept="image/png,image/jpg,image/jpeg" id="filess" multiple="multiple" /><br>


				<br><label>Descrizione:</label><br><br>
				<textarea name="descrizione" placeholder="Descrivi il prodotto..." required style="font-size:16px;padding: 8px; width:500px; height:100px;"></textarea><br><br>
				<label>Prezzo:</label>
				<input style="width:50px;" type="number" min="0.00" max="10000.00" step="0.01" name="prezzo" required><br><br>
				<label>Data pubblicazione:</label>
				<input type="date" name="dataPubbl"required><br><br>
				<label>Percentuale di sconto:</label>
				<input style="width:50px;" type="number" min="0.00" max="100.00" step="0.01" name="percSconto" value="0" required><br><br>
				<label>Quantità:</label>
				<input style="width:50px;" type="number" min="0" max="1000000" step="1" name="qnt" required><br><br>
				
				<input class="btn" id=modButton" type="submit" name="Aggiungi" value="Aggiungi"><br><br>
				<% } else { %>
				<input type="hidden" name="id" <%if (prodotto!=null) {%>value="<%=prodotto.getIdProdotto() %>" <% } %> >
				<label>Titolo:</label>
				<input style="padding:10px;font-size:16px;" type="text" name="titolo" value="<%=prodotto!=null ?prodotto.getTitolo():""%>" required><br><br>
				<label>Categorie:</label>
			<%
				ArrayList<CategoryBean> categorie = (ArrayList<CategoryBean>) request.getAttribute("categorie");
				ArrayList<CategoryBean> categorieProdotto=null;
				if(prodotto!=null)	
					categorieProdotto = prodotto.getCategorie();
				for (CategoryBean categoria : categorie) {
					String checked = "";
					if (categorieProdotto != null) {
						for (CategoryBean categoriaProdotto : categorieProdotto) {
							if (categoria.getNome().equals(categoriaProdotto.getNome()))
								checked = "checked";

						}
					}
					
			%>
		<br><br><input type="checkbox" name="categorie" value="<%=categoria.getNome() %>"  <%=checked %> ><label><%=categoria.getNome() %></label>
			<% } %><br><br>


				<% if(prodotto!=null){
					ArrayList<ImageBean> immagini=(ArrayList<ImageBean>) prodotto.getImmagini();
					if(immagini!=null){%>
					
					<section style="display:flex;align-items:center;flex-wrap:wrap;justify-content:center;">
						<%for(ImageBean immagine:immagini){%>
						
							<input type="hidden" name="deleteImages" id="deleteImages" value="">
 							<img class="imageId" src="<%=immagine.getPathname() %>" width="150px" style="margin:10px;" />
				
				<%} %>
					</section>
					<%} }%><br><br>
				<label>Immagini </label>
 				<input type="file" name="file" accept="image/png,image/jpg,image/jpeg" id="filess" multiple="multiple"/><br>

				<br><label>Descrizione:</label><br><br>
				<%
				String descrizione="";
				if (prodotto!=null && prodotto.getDescrizione()!=null) 
					descrizione=prodotto.getDescrizione();
				%>
				<textarea name="descrizione"  required style="font-size:16px;padding: 8px; width:250px; height:100px;"><%=descrizione%></textarea><br><br>
				<label>Prezzo:</label>
				<input style="width:50px;" type="number" min="0.00" max="10000.00" step="0.01" value="<%=prodotto!=null?prodotto.getPrezzo():""%>"name="prezzo" required><br><br>
				<label>Data pubblicazione:</label>
				<input type="date" value="<%=prodotto!=null?prodotto.getDataPubblicazione():""%>" name="dataPubbl" required><br><br>
				<label>Percentuale di sconto:</label>
				<input style="width:50px;" type="number" min="0" max="100" step="1" name="percSconto" value="<%=prodotto!=null?prodotto.getPercSconto():""%>" value="0" required><br><br>
				<label>Quantità:</label>
				<input style="width:50px;" type="number" min="0" max="1000000" step="1" name="qnt" value="<%=prodotto!=null?prodotto.getQuantità():""%>" required><br><br>
					
					<%if (prodotto!=null) { %>
					
						<input class="btn" id="modButton" type="submit" name="Modifica" value="Modifica">
						<input class="btn" type="submit" name="Rimuovi" value="Rimuovi">
	
					<% } else {%>
				
						<input class="btn" id="modButton" type="submit" name="Aggiungi" value="Aggiungi">
						
					<% } %>	
				<% } %>
			</form>
			
	</section>
	
	<script>
    $('.imageId').click(function () {
    	if($(this).prev().attr("value")==""){
    		$(this).css({"filter":"brightness(30%)"});
    		$(this).prev().attr("value",$(this).attr("src"));
    	}
    	else {
    	$(this).css({"filter":"brightness(100%)"});
		$(this).prev().attr("value","");
    	}
    });
    
    
    function Checkfiles() {
		var errors = 0;
		var files = document.getElementById('filess').files;
		for (var i=0;i<files.length;i++) {
			var filename = files[i].name;
			var ext = filename.substring(filename.lastIndexOf(".") + 1);
			if (ext != "jpg" && ext != "jpeg" && ext != "png") {
				alert("Carica solo file jpg, jpeg oppure png");
				return false;
			}

		}
		return true;

	}
    

</script>

<jsp:include page="footer.jsp" />

