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
						<li class="active"><a href="ontmapper">Ontology Mapper</a></li>
						<li><a href="deleteproject">Delete Project</a></li>
						<li><a href="exportdata">View Data</a></li>
						<li><a href="i2b2instance">Your i2b2 instance</a></li>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">Ontology Mapper</h1>
					<br>
					<div class="row">
						<div class="col-xs-4">

							<form method="GET" id="myform"
								action="<c:url value='ontmapper' />">
								<!-- <input type="text" class="form-control"  id='project_name' name='project_name' style="width:200px" placeholder="Project Name" required autofocus>  -->
								<select class="form-control" id="project_name"
									name='project_name' style="width: 200px" required autofocus>
									<option value="">Select Project</option>
									<c:if test="${not empty projects}">
										<c:forEach items="${projects}" var="listValue">
											<option value="${listValue.projectId}">${listValue.projectId}</option>
										</c:forEach>
									</c:if>
								</select> <br />

								<table style="width: 500px">
									<tr>
										<td style="width: 150px">Search for existing concept:</td>
										<td style="width: 250px"><input type="text"
											class="form-control" id='existing_concept'
											name='existing_concept' style="width: 200px"
											placeholder="Find Existing Concept" required autofocus></td>
										<td style="width: 100px"><a class="btn btn-default"
											id="existing_concept_search" role="button">Search</a></td>
									</tr>
								</table>

								<br />

								<table id="currentonttable" class="table"></table>

								<br />

								<table style="width: 500px">
									<tr>
										<td style="width: 150px">Snomed (1/10)</td>
										<td style="width: 250px"><input type="text"
											class="form-control" id='concept' name='concept'
											style="width: 200px" placeholder="Find Concept" required
											autofocus></td>
										<td style="width: 100px"><a class="btn btn-default"
											id="search" role="button">Search</a></td>
									</tr>
								</table>
								<br />

								<table id="onttable" class="table"></table>

								<input type="hidden" id="user" name="user" value="${user}" /> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input class="btn btn-primary"
									id="join" type="join" value="Create Link"> <br />
								<br /> <label for="message" id="message"
									style="color: red; font-weight: bold;"></label>
							</form>
							<br />

							<c:if test="${not empty success}">

								<c:if test="${success}">
   							uploaded filename ${filename} in new project ${added_project}<br />
								</c:if>

								<c:if test="${not success}">
   							${message}<br />
								</c:if>

							</c:if>

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

			function getParameterByName(name) {
				name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
				var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
						.exec(location.search);
				return results === null ? "" : decodeURIComponent(results[1]
						.replace(/\+/g, " "));
			}

			$(function() {
				'use strict';

				var p_project_name = getParameterByName('project_name');
				$("#project_name").val(p_project_name);

				var p_data_choice = getParameterByName('data_choice');
				$("#data_choice").val(p_data_choice);

				var p_views_per_page = getParameterByName('views_per_page');
				$("#views_per_page").val(p_views_per_page);

				$("#search").click(
								function() {

									if ($("#project_name option:selected").val() != "" && $("#concept").val().length > 2) {
											$.ajax({
													type : "GET",
													url : "http://"
															+ window.location.hostname
															+ ":"
															+ window.location.port
															+ "/i2b2UploaderWebapp/findconcept?term="
															+ $("#concept")
																	.val(),
													dataType : "json",
													data : {},
													contentType : "application/json; charset=utf-8",
													success : function(data) {

														$('#onttable tr').remove();
														//alert('data');
														var trHTML = '';

														trHTML += '<tr><td style="width:50px">select&nbsp;&nbsp;</td><td style="width:50px">code&nbsp;&nbsp;</td><td>term</td></tr>';

														$.each(data,
																		function(index) {
																			//alert(data[index].code + " " + data[index].term)

																			//$('<tr>').attr('id',i).
																			//append($('<td>').text(data[index].code)).
																			//append($('<td>').text(data[index].term)).appendTo('#onttable');

																			if (data[index].code != null
																					&& data[index].term != null) {
																				trHTML += '<tr><td><input type="radio" name="new_code" value="' + data[index].code + '"></td><td>'
																						+ data[index].code
																						+ '</td><td>'
																						+ data[index].term
																						+ '</td></tr>';
																			} else {
																				trHTML += '<tr><td  colspan="3">No records found</td></tr>';
																			}

																		});

														$('#message').text("");
														$('#onttable').append(trHTML);
													},
													failure : function(errMsg) {
														alert(errMsg);
													}
												});
									} else {
										$('#message').text("Please select project and ensure you type at least 3 characters");
									}

								});

				$("#existing_concept_search").click(
								function() {

									if ($("#project_name option:selected").val() != "" && $("#existing_concept").val().length > 2) {
										$.ajax({
													type : "GET",
													url : "http://"
															+ window.location.hostname
															+ ":"
															+ window.location.port
															+ "/i2b2UploaderWebapp/findexistingconcept?term="
															+ $(
																	"#existing_concept")
																	.val()
															+ "&project_name="
															+ $("#project_name")
																	.val(),
													dataType : "json",
													data : {},
													contentType : "application/json; charset=utf-8",
													success : function(data) {

														//alert(data);

														if (data != "") {
															$('#currentonttable tr').remove();
															var trHTML = '';

															trHTML += '<tr><td style="width:50px">select&nbsp;&nbsp;</td><td style="width:50px">code&nbsp;&nbsp;</td></tr>';

															$.each(data,
																			function(
																					index) {

																				if (data[index].code != null) {
																					trHTML += '<tr><td><input type="radio" name="existing_code" value="' + data[index].code + '"></td><td>'
																							+ data[index].code
																							+ '</td></tr>';
																				} else {
																					trHTML += '<tr><td  colspan="2">No concept found, please look at the third row of your spreadsheet or the i2b2 ontology tree.</td></tr>';
																				}

																			});

															$('#message').text("");
															$('#currentonttable').append(trHTML);
														} else {
															$('#message').text("nothing found for "+ $("#existing_concept").val());
														}

													},
													failure : function(errMsg) {
														alert(errMsg);
													}
												});
									} else {
										$('#message')
												.text(
														"Please select project and ensure you type at least 3 characters");
									}

								});

				$("#join").click(
								function() {

									if ($("#project_name option:selected").val() != ""
											&& $("input[name=existing_code]:checked").val() != null
											&& $("input[name=new_code]:checked").val() != null) {
										$.ajax({type : "GET",
													url : "http://"
															+ window.location.hostname
															+ ":"
															+ window.location.port
															+ "/i2b2UploaderWebapp/join?existing_code="
															+ $("input[name=existing_code]:checked").val()
															+ "&new_code="
															+ $("input[name=new_code]:checked").val()
															+ "&project_name="
															+ $("#project_name").val(),
													dataType : "json",
													data : {},
													contentType : "application/json; charset=utf-8",
													success : function(data) {

														//alert(data.message);
														//alert(data.error);

														$('#message').text(data.message);

														//$('#currentonttable tr').remove();
														//var trHTML = '';

														//trHTML += '<tr><td style="width:50px">select&nbsp;&nbsp;</td><td style="width:50px">code&nbsp;&nbsp;</td></tr>';

														//$.each(data, function(index) {        

														//trHTML += '<tr><td><input type="radio" name="existing_code" value="' + data[index].code + '"></td><td>' + data[index].code + '</td></tr>';

														//});

														//$('#currentonttable').append(trHTML);

													},
													failure : function(errMsg) {
														alert(errMsg);
													}
												});
									} else {
										$('#message').text("Please ensure radio buttons for both codes are clicked and project selected");
									}

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