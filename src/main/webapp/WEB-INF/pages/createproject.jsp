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
						<li><a href="np_uploadxls">Upload XLS - New Project</a></li>
						<li><a href="ex_uploadxls">Upload XLS - Existing Project</a></li>
						<li><a href="ontmapper">Ontology Mapper</a></li>
						<li><a href="deleteproject">Delete Project</a></li>
						<li><a href="exportdata">View Data</a></li>
						<li><a href="i2b2instance">Your i2b2 instance</a></li>						
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

					<h1 class="page-header">Create Project</h1>
					Enter project name to create : <br> <br>

					<form class="form-signin" name='createProjectForm'
						action="<c:url value='/createprojectsubmit' />" method='GET'>

						<!-- <input type="text" class="form-control"  name='projectid' placeholder="Project ID" style="width:200px" required autofocus>  -->
						<input type="text" class="form-control" name='projectname'
							placeholder="Project Name" style="width: 200px" required
							autofocus>

						<button class="btn btn-primary" type="submit" value="submit"
							style="width: 200px">Create Project</button>
					</form>

					<br> <br>
					<!-- The global progress bar -->
					<div id="progress" class="progress">
						<div class="progress-bar progress-bar-success"></div>
					</div>
					<!-- The container for the uploaded files -->
					<div id="files" class="files"></div>


				</div>
			</div>
		</div>

		<!-- Bootstrap core JavaScript ================================================== -->
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
			$(function() {
				'use strict';

				$("#progress").hide();
				$('#projects').change(function() {
					//alert($(this).val());
				});

				// Change this to the location of your server-side upload handler:
				var url = window.location.hostname === 'blueimp.github.io' ? '//jquery-file-upload.appspot.com/'
						: 'file';
				$('#fileupload').fileupload(
						{
							formData : {
								param1 : $('#projects').val(),
								param2 : "value2",
								param3 : "yasseshaikh"
							},
							url : url,
							dataType : 'json',
							acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
							maxFileSize : 5000000, // 5 MB
							done : function(e, data) {
								//alert(data.files[0].size);
								$('<p/>').text(data.files[0].name).appendTo(
										'#files');
								/* $.each(data.result.files, function (index, file) {
								     $('<p/>').text(file.name).appendTo('#files');
								 });*/
							},
							stop : function(e, data) {
								//$('.upload').hide();
								//$( "#progress" ).hide();
							},
							progressall : function(e, data) {
								$("#progress").show();
								var progress = parseInt(data.loaded
										/ data.total * 100, 10);
								$('#progress .progress-bar').css('width',
										progress + '%');
							}
						}).prop('disabled', !$.support.fileInput).parent()
						.addClass($.support.fileInput ? undefined : 'disabled')
						.bind('fileuploadsubmit', function(e, data) {
							$("#progress").show();
							var input = $('#projects');
							data.formData = {
								param1 : input.val()
							};
						});
			});
		</script>

		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</sec:authorize>
</body>
</html>