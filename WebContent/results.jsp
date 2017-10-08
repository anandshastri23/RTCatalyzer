<%@ page import="java.util.*,com.anand.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

	<title>Input fields</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: center;    
}
</style>
</head>

<body>
	
	<div id="wrapper">
		<div id="header">
			<h2>RT Catalyzer</h2>
		</div>
	</div>
	<br/>


 <%! int i = 1; %>
	<div  id="container">	
			<table>
				<tr>
					<th>Input Field </th>
					<th>Value</th>
				</tr>	

			<c:forEach var="tempField" items="${fullResults }">

			<!-- for select -->
				<c:url var="templink" value="ContactServlet">
					<c:param name="hidden" value="select"/>

				</c:url>
				<tr>
					<td> <label>${tempField.fieldDesc}</label> </td>
					<td> <label>${tempField.fieldValue}</label></td>
			</c:forEach>
			</table>
			
			<br>
			<br>
			<table>
				<tr>
					<th> Rule# </th>
					<th>Output</th>
					<th>Select</th>
				</tr>

				<c:forEach var="tempField" items="${fullResults }">
				<!-- for select -->
				<c:url var="templink" value="ContactServlet">
					<c:param name="hidden" value="select"/>

				</c:url>
					
				<tr>
					
					<td><%= i %> <% i++; %></td> 
					<td><label>${tempField.result}</label></td>
					<td> <input type="button" name="view rule" value ="View rule"></td>
				
				</tr>
			</c:forEach>

			</table>
			
			<hr/>

		
		</div>



</body>

</html>