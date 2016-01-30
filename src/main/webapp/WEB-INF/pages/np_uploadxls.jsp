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
<link rel="stylesheet"
	href="<c:url value="/resources/dist/css/style.css" />">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet"
	href="<c:url value="/resources/dist/css/jquery.fileupload.css" />">


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
	<!-- <h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
    -->
	<sec:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->
		<!-- <c:url value="/j_spring_security_logout" var="logoutUrl" /> -->

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
						<li><a href="welcome">Dashboard</a></li>
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
						<li class="active"><a href="np_uploadxls">Upload XLS -
								New Project</a></li>
						<li><a href="ex_uploadxls">Upload XLS - Existing Project</a></li>
						<li><a href="ontmapper">Ontology Mapper</a></li>
						<li><a href="deleteproject">Delete Project</a></li>
						<li><a href="exportdata">View Data</a></li>
						<li><a href="i2b2instance">Your i2b2 instance</a></li>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">Upload XLS - New Project</h1>
					Please enter the name of project you wish to create : <br>
					<br>
					<div class="row">
						<div class="col-xs-4">

							<form method="POST" id="uploadFile"
								action="<c:url value='uploadFile' />?${_csrf.parameterName}=${_csrf.token}"
								enctype="multipart/form-data">
								<input type="text" class="form-control" maxlength="15"
									id='project_name' name='project_name' style="width: 200px"
									placeholder="Project Name" required autofocus> <input
									type="hidden" id="uploadtype" name="uploadtype"
									value="${uploadtype}" /> <input type="hidden" id="user"
									name="user" value="${user}" /> <br /> File to upload: <input
									type="file" name="file" id="file" class="btn btn-primary">
								<br />  Brisskit, University of Leicester, Jisc accepts no liability for loss or theft of sensitive data submitted through these media and does not recommend the transfer of sensitive data via these methods.
								<br /> <input type="checkbox" id="disclaimer" value="Agree"> Tick to accept 
								<br /> <br /> 
									<input class="btn btn-primary" id="upload" value="Upload" style="width: 100px"> Press here to upload the file! <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
							<br />

							<!-- 		
					<c:if test="${not empty success}"> 
					
					    <c:if test="${success}">
   							<label style="color: red;font-weight: bold;">${message}</label><br />
						</c:if>
						
						<c:if test="${not success}">
   							<label style="color: red;font-weight: bold;">${message}</label><br />
						</c:if>
											
  					</c:if>	
  					 -->

							<label for="message" id="message"
								style="color: red; font-weight: bold;">${message}</label>

							<!--  			
					<c:if test="${not empty added_project}">
  						added project ${added_project} <br />
  					</c:if>	
  					
  					<c:if test="${not empty success}"> 
  						success ${success} <br />
  					</c:if>	
  					
  					<c:if test="${not empty user}"> 
  						user ${user} <br />
  					</c:if>	
  					
  					<c:if test="${not empty filename}"> 
  						filename ${filename} <br />
  					</c:if>	
  					
  					<c:if test="${not empty filesize}"> 
  						filesize ${filesize} <br />
  					</c:if>	
  					-->

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>  -->

		<script src="<c:url value="/resources/dist/js/jquery-2.1.3.js" />"></script>

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
		<script>
			/*jslint unparam: true */
			/*global window, $ */

			function checkMaxLength(text, max) {
				return (text.length >= max);
			}

			$("#project_name").bind('keyup', function(e) {

				var myStr = $(this).val()
				myStr = myStr.toLowerCase();
				//myStr=myStr.replace(/[^a-zA-Z0-9 ]+/g,"");
				myStr = myStr.replace(/\s+/g, "_");

				$("#project_name").val((myStr));

				if ($("#project_name").val() != "") {
					$("#fileupload-span").show();
				}
				if ($("#project_name").val() == "") {
					$("#fileupload-span").hide();
				}

			});

			$("#project_name").bind(
					'keypress',
					function(event) {
						var regex = new RegExp("^[a-zA-Z0-9\b]+$");
						var key = String
								.fromCharCode(!event.charCode ? event.which
										: event.charCode);
						if (!regex.test(key)) {
							event.preventDefault();
							return false;
						}
						/*if (checkMaxLength ($("#project_name").val(), 15)) {
							event.preventDefault();
							event.stopPropagation();
						}*/
					});

			$("#upload")
					.click(
							function() {

								var ck = $('#disclaimer').prop('checked');

								if (ck == true)
								{
								//alert('x');

								//alert($("#file").val());
								if ($("#file").val() != ''
										&& $("#project_name").val() != '') {
									if ($.isNumeric($("#project_name").val()
											.substring(0, 1))) {
										$('#message')
												.text(
														"First character of project name cannot be a number");
									} else {
										$("#uploadFile").submit();
										$('#message').text("");
									}
								} else {
									$('#message')
											.text(
													"Enter project name and select file");
								}
								}
								else
								{
									$('#message').text("Please check the disclaimer box.");
								}
							});

			$(function() {
				'use strict';

			});
		</script>

		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

	</sec:authorize>
</body>
</html>