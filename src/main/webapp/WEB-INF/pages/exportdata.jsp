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
						<li class="active"><a href="exportdata">View Data</a></li>
						<li><a href="i2b2instance">Your i2b2 instance</a></li>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">View Data</h1>
					<br>
					<div class="row">
						<div class="col-xs-4">

							<form method="GET" id="myform"
								action="<c:url value='exportdata' />">
								<!-- <input type="text" class="form-control"  id='project_name' name='project_name' style="width:200px" placeholder="Project Name" required autofocus>  -->
								<select class="form-control" id="project_name"
									name='project_name' style="width: 200px" required autofocus>
									<option value="">Select Project</option>
									<c:if test="${not empty projects}">
										<c:forEach items="${projects}" var="listValue">
											<option value="${listValue.projectId}">${listValue.projectId}</option>
										</c:forEach>
									</c:if>
								</select> <br /> <select class="form-control" id="data_choice"
									name='data_choice' style="width: 200px" required>
									<option value="">Select Data</option>
									<option value="patients">Patients</option>
									<option value="observations">Observations</option>
									<option value="concepts">Concepts</option>
									<option value="ontology">Ontology</option>
									<option value="mappings">Mappings</option>
								</select> <br /> <select class="form-control" id="views_per_page"
									name='views_per_page' style="width: 200px" required>
									<option value="">Views Per Page</option>
									<option value="1000">1000</option>
								</select> <input type="hidden" id="next_view" name="next_view"
									value="${next_view}" /> <input type="hidden" id="totalcount"
									name="totalcount" value="${totalcount}" /> <input
									type="hidden" id="uploadtype" name="uploadtype"
									value="${uploadtype}" /> <input type="hidden" id="user"
									name="user" value="${user}" /> <br /> <input
									class="btn btn-primary" id="Go" type="submit" value="Go">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
							<br />


							<c:if test="${data_choice == 'observations'}">
								<c:if test="${not empty object}">
									<div id="info">${message}</div>
									<div id="example_paginate">
										<a id="previous" class="paginate_button previous disabled">Previous</a>
										<span> <a id="next" class="paginate_button next">Next</a>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>patient_ide</th>
												<th>encounter_num</th>
												<th>patient_num</th>
												<th>concept_cd</th>
												<th>valtype_cd</th>
												<th>tval_char</th>
												<th>nval_num</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${object}" var="element">
												<tr>

													<td>${element.patient_ide}</td>
													<td>${element.encounter_num}</td>
													<td>${element.patient_num}</td>
													<td>${element.concept_cd}</td>
													<td>${element.valtype_cd}</td>
													<td>${element.tval_char}</td>
													<td>${element.nval_num}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:if>

							<c:if test="${data_choice == 'patients'}">
								<c:if test="${not empty object}">
									<div id="info">${message}</div>
									<div id="example_paginate">
										<a id="previous" class="paginate_button previous disabled">Previous</a>
										<span> <a id="next" class="paginate_button next">Next</a>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>patient_ide</th>
												<th>patient_num</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${object}" var="element">
												<tr>
													<td>${element.patient_ide}</td>
													<td>${element.patient_num}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:if>

							<c:if test="${data_choice == 'concepts'}">
								<c:if test="${not empty object}">
									<div id="info">${message}</div>
									<div id="example_paginate">
										<a id="previous" class="paginate_button previous disabled">Previous</a>
										<span> <a id="next" class="paginate_button next">Next</a>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>concept_path</th>
												<th>concept_cd</th>
												<th>name_char</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${object}" var="element">
												<tr>
													<td>${element.concept_path}</td>
													<td>${element.concept_cd}</td>
													<td>${element.name_char}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:if>

							<c:if test="${data_choice == 'ontology'}">
								<c:if test="${not empty object}">
									<div id="info">${message}</div>
									<div id="example_paginate">
										<a id="previous" class="paginate_button previous disabled">Previous</a>
										<span> <a id="next" class="paginate_button next">Next</a>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>c_hlevel</th>
												<th>c_fullname</th>
												<th>c_name</th>
												<th>c_synonym_cd</th>
												<th>c_visualattributes</th>
												<th>c_totalnum</th>
												<th>c_basecode</th>
												<th>c_metadataxml</th>
												<th>c_facttablecolumn</th>
												<th>c_tablename</th>
												<th>c_columnname</th>
												<th>c_columndatatype</th>
												<th>c_operator</th>
												<th>c_dimcode</th>
												<th>c_comment</th>
												<th>c_tooltip</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${object}" var="element">
												<tr>
													<td>${element.c_hlevel}</td>
													<td>${element.c_fullname}</td>
													<td>${element.c_name}</td>
													<td>${element.c_synonym_cd}</td>
													<td>${element.c_visualattributes}</td>
													<td>${element.c_totalnum}</td>
													<td>${element.c_basecode}</td>
													<td>${element.c_metadataxml}</td>
													<td>${element.c_facttablecolumn}</td>
													<td>${element.c_tablename}</td>
													<td>${element.c_columnname}</td>
													<td>${element.c_columndatatype}</td>
													<td>${element.c_operator}</td>
													<td>${element.c_dimcode}</td>
													<td>${element.c_comment}</td>
													<td>${element.c_tooltip}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:if>

							<c:if test="${data_choice == 'mappings'}">
								<c:if test="${not empty object}">
									<div id="info">${message}</div>
									<div id="example_paginate">
										<a id="previous" class="paginate_button previous disabled">Previous</a>
										<span> <a id="next" class="paginate_button next">Next</a>
									</div>
									<table class="table table-bordered" style="width: 500px">
										<thead>
											<tr>
												<th>current_code</th>
												<th></th>
												<th>new_code</th>
												<th>new_code_desc</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${object}" var="element">
												<tr>
													<td>${element.current_code}</td>
													<td><img
														src="<c:url value="/resources/images/arrow.png" />"
														alt="Arrow"></td>
													<td>${element.new_code}</td>
													<td>${element.new_code_desc}</td>
													<td><input class="btn btn-success"
														id="${element.new_code}" type="button" value="delete">
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:if>
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

				$("#previous")
						.click(
								function() {

									if (parseInt($("#next_view").val()) > parseInt(p_views_per_page)) {
										$("#next_view")
												.val(
														parseInt($("#next_view")
																.val())
																- parseInt(p_views_per_page));
										$("#myform").submit();
									} else {
										$("#next_view").val("1");
										$("#myform").submit();
									}

								});
				$("#next")
						.click(
								function() {
									if ((parseInt($("#next_view").val()) + parseInt(p_views_per_page)) < (parseInt($(
											"#totalcount").val()))) {
										$("#next_view")
												.val(
														parseInt($("#next_view")
																.val())
																+ parseInt(p_views_per_page));
										$("#myform").submit();
									}
								});

				$("#Go").click(function() {
					$("#next_view").val("1");
					$("#myform").submit();
				});

				$(".btn")
						.click(
								function() {

									if ($(this).attr('id') != 'Go') {

										//$(this).remove(); 	            
										//alert( $(this).attr('id'));

										$
												.ajax({
													type : "GET",
													url : "http://"
															+ window.location.hostname
															+ ":"
															+ window.location.port
															+ "/i2b2UploaderWebapp/deleteconcept?new_code="
															+ $(this)
																	.attr('id')
															+ "&project_name="
															+ $("#project_name")
																	.val(),
													dataType : "json",
													data : {},
													contentType : "application/json; charset=utf-8",
													success : function(data) {
														$("#myform").submit();
													},
													failure : function(errMsg) {
														alert(errMsg);
													}
												});

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