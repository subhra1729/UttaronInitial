<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
	</head>
	<body>
		<h1> Error Page</h1>
		<p>Application has encountered error . Please contact support</p>
		<!-- 
			Failed Url: ${url}
			Exception Message: ${exception.message}
			<c:forEach items="${exception.stackTrace}" var="ste">	
				${ste}
			</c:forEach>
		 -->
	</body>
</html>