<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<%@ page isELIgnored="false" %>	
</head>
<body>

	
	 
	 <form:form id="invForm" method="POST" action="/uttaroncontent/invoice" modelAttribute="invoiceForm">
	   <table>
		    <tr>
		        <td><form:label path="invoiceId">Invoice Id</form:label></td>
		        <td><form:input path="invoiceId" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="productSerialNumber">Product Serial Number</form:label></td>
		        <td><form:input path="productSerialNumber" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="buyerName">Buyer Name</form:label></td>
		        <td><form:input path="buyerName" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="buyerNumber">Buyer Number</form:label></td>
		        <td><form:input path="buyerNumber" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="buyerAddress">Buyer Address</form:label></td>
		        <td><form:input path="buyerAddress" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="buyerPin">Buyer Pin</form:label></td>
		        <td><form:input path="buyerPin" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="productName">Product Name</form:label></td>
		        <td><form:input path="productName" /></td>
		    </tr>
		    <tr>
		        <td><form:label path="productType">Product Type</form:label></td>
		        <td><form:input path="productType" /></td>
		    </tr>		    
		    <tr>
		        <td colspan="2">
		             <input type="submit" value="Submit"/> 		            
		        </td>
		        <td colspan="2">
		            <input type="reset" value="Reset" />
		        </td> 		       
		    </tr>
		    
		</table>  
	</form:form>

</body>
</html>