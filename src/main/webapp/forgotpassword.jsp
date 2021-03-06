<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
   <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Brisskit Portal - Forgot Password</title>
      <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/dist/css/bootstrap.min.css" />" rel="stylesheet">

    
    
    <!-- login CSS -->
    <link href="<c:url value="/resources/dist/css/login.css" />" rel="stylesheet">
 </head>
   <body>
   <h1 class="text-center login-title">
To reset your password, enter the email address you use to sign in to the i2b2 portal. 
</h1>
<form class="form-signin" name='loginForgotForm' action="<c:url value='/fp' />" method='POST'>
                
<input type="text" class="form-control"  name='username' id='username' placeholder="Email" required autofocus>
<button class="btn btn-lg btn-primary btn-block" type="submit" value="submit">
                    Submit</button>
</form>
</body>
</html>                