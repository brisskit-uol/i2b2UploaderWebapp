<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Brisskit Portal</title>
<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/dist/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- login CSS -->
<link href="<c:url value="/resources/dist/css/login.css" />"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">Sign in to continue to i2b2
					Portal</h1>
				<div class="account-wall">
					<!--  <img class="profile-img" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120" alt=""> -->
					<img class="profile-img"
						src="<c:url value="/resources/images/login_bg.jpg" />" alt="">

					<c:if test="${not empty error}">
						<div class="text-center">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="text-center">${msg}</div>
					</c:if>

					<form class="form-signin" name='loginForm'
						action="<c:url value='/j_spring_security_check' />" method='POST'>

						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" /> <input type="text"
							class="form-control" name='username' placeholder="Email" required
							autofocus> <input type="password" class="form-control"
							name='password' placeholder="Password" required>

						<button class="btn btn-lg btn-primary btn-block" type="submit"
							value="submit">Sign in</button>
						<label class="checkbox pull-left"> <input type="checkbox"
							value="remember-me"> Remember me
						</label>
						<!-- <a href="#" class="pull-right need-help">Need help? </a>  -->
						<span class="clearfix"></span>
					</form>
				</div>
				<a href="forgotpassword.jsp" class="text-center new-account">Forgot
					Password</a>
			</div>
		</div>
	</div>
</body>
</html>