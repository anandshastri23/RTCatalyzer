<%@ page import="java.util.*,com.anand.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

	<title>Input fields</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	
	
</head>


<body>
	
	<div id="wrapper">
		<div id="header">
			<h2>RT Catalyzer</h2>
		</div>
	</div>
	<br/>

	<div  id="container">
		<form action="/RTCatalyzer/ContactServlet">	
			<table>
				<tr>
					<th>Input Field </th>
					<th>Value</th>
					<th> Rule# </th>
					<th>Output</th>
					<th>View rule</th>
				</tr>
			 <%! int i = 1; %>
			 <%! List<Integer> list = new ArrayList<Integer>(); %>
			 
			<c:forEach var="tempField" items="${fields }">
			<!-- for select -->
				<c:url var="templink" value="ContactServlet">
					<c:param name="hidden" value="select"/>
					<c:param name="fieldId" value="${tempField.fieldId}"/>

				</c:url>
				<tr>
					<td> <label>${tempField.fieldDesc}</label> </td>
					<td> <input type="text" name="fieldvalue" > </td>
					<td><%= i %> <% i++; %></td> 
					<td> <label>${tempField.fieldDesc}</label> </td>
					<td> <input type="button" name="view rule" ></td>
					
				</tr>
			</c:forEach>
		
			</table>
			<hr/>
			<input type="hidden" name="hidden" value="calculate">
			<input type="submit" value="submit">
			</form>
		
		</div>



</body>

</html>