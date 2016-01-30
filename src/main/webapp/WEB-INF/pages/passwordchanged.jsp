<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Brisskit Portal - Change Password Submit</title>
<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/dist/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- login CSS -->
<link href="<c:url value="/resources/dist/css/login.css" />"
	rel="stylesheet">
</head>
<body>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<h1 class="text-center login-title">
		Password changed. Please <a href="<c:url value='/login' />">login</a>
		again
	</h1>

</body>
</html>
