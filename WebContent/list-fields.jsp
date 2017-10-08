<%@ page import="java.util.*,com.anand.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

	<title>Output fields</title>
	
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

	<div  id="container">
		<div id="content">
			<table>
				<tr>
					<th>Field name</th>
					<th>Select</th>
				</tr>
			
			<c:forEach var="tempField" items="${fields }">
			<!-- for select -->
				<c:url var="templink" value="ContactServlet">
					<c:param name="hidden" value="select"/>
					<c:param name="fieldId" value="${tempField.fieldId}"/>
				</c:url>
				<tr>
					<td> ${tempField.fieldDesc} </td>
					<td> <a href="${templink}">select</a></td>
				</tr>
			</c:forEach>
		
			</table>
		</div>
	</div>


</body>

</html>