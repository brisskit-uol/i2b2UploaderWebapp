<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../favicon.ico">

<title>Brisskit Portal</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/dist/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/dist/css/dashboard.css" />"
	rel="stylesheet">

<!-- Generic page styles -->
<!--    <link rel="stylesheet" href="../dist/css/style.css"> -->
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<!--    <link rel="stylesheet" href="../dist/css/jquery.fileupload.css"> -->

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<!--<script src="../assets/js/ie-emulation-modes-warning.js"></script>-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<sec:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />

		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Brisskit Portal</a>
				</div>
				<div class="navbar-header">
					<img src="<c:url value="/resources/images/logo.png" />"
						alt="Jisc Logo" style="border: 5px solid black"> &nbsp; <img
						src="<c:url value="/resources/images/brisskit-logo-small.png" />"
						alt="Brisskit Logo" style="border: 5px solid black">
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">Dashboard</a></li>
						<li><a href="settings">Settings</a></li>

						<script>
							function formSubmit() {
								document.getElementById("logoutForm").submit();
							}
						</script>
						<c:if test="${pageContext.request.userPrincipal.name != null}">
							<li><a href="javascript:formSubmit()">Logout</a></li>
						</c:if>

						<li><a
							href="http://wiki.brisskit.le.ac.uk/wiki/I2b2_Uploader_Portal_Guide"
							target="_blank">Help</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li><a href="np_uploadxls">Upload XLS - New Project</a></li>
						<li><a href="ex_uploadxls">Upload XLS - Existing Project</a></li>
						<li><a href="ontmapper">Ontology Mapper</a></li>
						<li><a href="deleteproject">Delete Project</a></li>
						<li><a href="exportdata">View Data</a></li>
						<li><a href="i2b2instance">Your i2b2 instance</a></li>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">New User</h1>
					Create New User <br> <br>
					<div class="row">
						<div class="col-xs-4">
							<form id="cu" class="form" name='createUserForm'
								action="<c:url value='createuser' />" method='POST'
								autocomplete="off">

								<input type="text" class="form-control" autocomplete='off'
									id='email' name='email' placeholder="New User Email" required
									autofocus> <input type="password" class="form-control"
									autocomplete='off' id='password1' name='password1'
									placeholder="User Password" required autofocus> <input
									type="password" class="form-control" autocomplete='off'
									id='password2' name='password2'
									placeholder="User Password Again" required autofocus> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button class="btn btn-lg btn-primary btn-block" type="submit"
									value="submit">Submit</button>
							</form>

							<!--  
		<c:if test="${not empty success}">
			<div>${success}</div>
		</c:if>
-->
							<c:if test="${not empty username}">
								<br>
								<div>Successfully created account for ${username}</div>
							</c:if>

						</div>
						<div class="col-xs-4"></div>
					</div>
				</div>
			</div>

			<!-- Bootstrap core JavaScript ================================================== -->
			<!-- Placed at the end of the document so the pages load faster -->
			<script
				src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
			<script src="<c:url value="/resources/dist/js/bootstrap.min.js" />"></script>
			<!-- <script src="<c:url value="/resources/assets/js/docs.min.js" />"></script>-->
			<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
			<script
				src="<c:url value="/resources/assets/js/ie10-viewport-bug-workaround.js" />"></script>

			<script
				src="<c:url value="/resources/dist/js/vendor/jquery.ui.widget.js" />"></script>
			<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
			<script
				src="<c:url value="/resources/dist/js/jquery.iframe-transport.js" />"></script>
			<!-- The basic File Upload plugin -->
			<script
				src="<c:url value="/resources/dist/js/jquery.fileupload.js" />"></script>
			<script src="<c:url value="/resources/dist/js/jquery.validate.js" />"></script>

			<script>
				/*jslint unparam: true */
				/*global window, $ */
				$(function() {
					'use strict';
					// Change this to the location of your server-side upload handler:
					var url = window.location.hostname === 'blueimp.github.io' ? '//jquery-file-upload.appspot.com/'
							: 'server/php/';
					$('#fileupload').fileupload(
							{
								url : url,
								dataType : 'json',
								done : function(e, data) {
									$.each(data.result.files, function(index,
											file) {
										$('<p/>').text(file.name).appendTo(
												'#files');
									});
								},
								progressall : function(e, data) {
									var progress = parseInt(data.loaded
											/ data.total * 100, 10);
									$('#progress .progress-bar').css('width',
											progress + '%');
								}
							}).prop('disabled', !$.support.fileInput).parent()
							.addClass(
									$.support.fileInput ? undefined
											: 'disabled');
				});

				$(document)
						.ready(
								function() {

									jQuery.validator
											.addMethod(
													'passwordMatch',
													function(value, element) {
														// The two password inputs
														var password = $(
																"#password1")
																.val();
														var confirmPassword = $(
																"#password2")
																.val();

														// Check for equality with the password inputs
														if (password != confirmPassword) {
															return false;
														} else {
															return true;
														}

													},
													"Your Passwords Must Match");

									$("#cu")
											.validate(
													{
														rules : {
															email : {
																required : true,
																email : true
															},
															password1 : {
																required : true,
																minlength : 8
															},
															password2 : {
																required : true,
																passwordMatch : true,
																minlength : 8
															}
														},
														messages : {
															email : {
																required : "We need your email address to contact you",
																email : "email must be in the format of name@domain.com"
															},
															password1 : {
																required : "What is your password?",
																minlength : "Your password must contain more than 7 characters"
															},
															password2 : {
																required : "You must confirm your password",
																minlength : "Your password must contain more than 7 characters",
																passwordMatch : "Your Passwords Must Match" // custom message for mismatched passwords
															}

														}

													}

											);
								});
			</script>

			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
	</sec:authorize>
</body>
</html>