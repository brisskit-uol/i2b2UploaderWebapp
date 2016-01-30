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
	<!-- <h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
    -->
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
						<li><a href="welcome">Dashboard</a></li>
						<!-- <li><a href="createnewuser">Create New User</a></li>
            <li><a href="changepassword">Change Password</a></li>  -->
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
					<h1 class="page-header">Dashboard</h1>
					Welcome to the dashboard, please select item from menu on the left
					<br> <br>

					<!-- 
         <li><a href="<c:url value="/resources/spreadsheets/EG1-laheart.xlsx" />">EG1-laheart.xlsx</a></li>
         <li><a href="<c:url value="/resources/spreadsheets/pharma_large.xls" />">pharma_large.xls</a></li>
         <li><a href="<c:url value="/resources/spreadsheets/project1_basic.xls" />">project1_basic.xls</a></li>
         <li><a href="<c:url value="/resources/spreadsheets/project2_1_optional_fields.xls" />">project2_1_optional_fields.xls</a></li>
         <li><a href="<c:url value="/resources/spreadsheets/project2_2_new_col_new_patient.xls" />">project2_2_new_col_new_patient.xls</a></li>
         <li><a href="<c:url value="/resources/spreadsheets/project2_3_new_col_same_patients.xls" />">project2_3_new_col_same_patients.xls</a></li>
 -->

					The i2b2 uploader aims to upload a series of observable facts from
					a spreadsheet to i2b2. <br>
					<br> A comprehensive guide is available if you click on 'help'
					in the right hand corner above. <br>
					<br> Please follow these simple rules to upload : <br>
					<br>
					<li>Spreadsheets must be in the format .xls or .xlsx</li>
					<li>It is mandatory that the first column in the spreadsheet
						is 'ID' and the two fields below it are set to null. The ID column
						should contain patient identifiers.</li>
					<li>The 1st row of the spreadsheet contains column headings
						which represent short codes of the facts being collected for the
						patients.</li>
					<li>The 2nd row of the spreadsheet contains tool tips which
						are shown in the i2b2 client, within the ontology.</li>
					<li>The 3rd row of the spreadsheet contains i2b2 ontology
						codes. It also contain units in square brackets [] which represent
						the unit of measure. If the unit is known, then for example you
						can use [kgs], [cms] etc. For text you have to use [text], if the
						square brackets are omited, the fact is treated as an enumeration.</li>

					<br> Here are some example spreadsheets for you to try : <br>
					<br>

					<table class="table table-striped">
						<tr>
							<td>File</td>
							<td>Project</td>
							<td>Description</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/project1_basic.xls" />">project1_basic.xls</a></td>
							<td>project1</td>
							<td>This is the most simplest spreadsheet. It only has a
								single tab 'Data' and it uploads data for 5 patients. It has a
								mixture of numerical, textual, date and enumerated values. Note
								that dates have to be in the format YYYY-MM-DD. To better
								understand the upload to i2b2, upload this spreadsheet into i2b2
								and examine the spreadsheets first 3 rows whilst using i2b2.</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/project2_1_optional_fields.xls" />">project2_1_optional_fields.xls</a></td>
							<td>project2</td>
							<td>This spreadsheet contains 5 patients and has 2 aditional
								optional tabs 'Lookup' and 'Breakdowns'. The 'Lookup' tab allows
								the mapping of codes in patient rows to meaningful names,
								suppose you have numerical codes 1,2,3 signifying heart, lung,
								brain. These can be mapped in the 'Lookup' tab. The 'Breakdowns'
								tab maps available breaks downs in i2b2 which are Age, Gender,
								Race and Vital Status to columns in the 'Data' tab. For example
								in this spreadsheet in the 'breakdowns' tab Vital Status is
								mapped to the column Death in the 'Data' tab. This spreadsheet
								also contains 5 optional columns which are OBS_START_DATE, AGE,
								GENDER, RACE and DEATH. OBS_START_DATE is a special column which
								marks the start date of all facts in the particular row. AGE,
								GENDER, RACE and DEATH are needed for i2b2 breakdowns.</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/project2_2_new_col_new_patient.xls" />">project2_2_new_col_new_patient.xls</a></td>
							<td>project2</td>
							<td>This spreadsheet should be added to the previously
								created project. It contains 5 new patients and a new txt fact
								column.</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/project2_3_new_col_same_patients.xls" />">project2_3_new_col_same_patients.xls</a></td>
							<td>project2</td>
							<td>This spreadsheet should be added to the previously
								created project. It contains existing 5 new patients and a new
								text fact column.</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/EG1-laheart.xlsx" />">EG1-laheart.xlsx</a></td>
							<td>laheart</td>
							<td>This spreadsheet contains 200 patients and has 2
								aditional optional tabs 'Lookup' and 'Breakdowns'. It also maps
								2 breakdowns to the optional columns AGE_1950 and DEATH.</td>
						</tr>
						<tr>
							<td><a
								href="<c:url value="/resources/spreadsheets/pharma_large.xls" />">pharma_large.xls</a></td>
							<td>pharma</td>
							<td>This spreadsheet contains 4997 patients and it places
								default values in the optional columns AGE, GENDER, RACE and
								DEATH.</td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<!-- Bootstrap core JavaScript================================================== -->
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
								$.each(data.result.files,
										function(index, file) {
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
						.addClass($.support.fileInput ? undefined : 'disabled');
			});
		</script>
		
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

	</sec:authorize>
</body>
</html>