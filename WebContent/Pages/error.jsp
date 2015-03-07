<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="./Pages/js/jquery.js"></script>
<script src="./Pages/js/jquery.min.js"></script>
<script src="./Pages/js/bootstrap.min.js"></script>
<script src="./Pages/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});

	function mysearch() {
		$ = jQuery;
		var formInput = $("#errorform").serialize();
		$
				.getJSON(
						'errorSearch',
						formInput,
						function(jsonResponse) {
							var PfJsonData = jsonResponse.aaData;

							$(".jqueryDataTable")
									.dataTable(
											{
												"sPaginationType" : "full_numbers",
												"bRetrieve" : false,
												"bDestroy" : true,
												"aaData" : PfJsonData,

												"aoColumns" : [
														{
															"mData" : "errorId",
															"render" : function(
																	data) {
																return '<input type="checkbox" name="ck" value= "'+data +'">';
															},
															className : "dt-body-center"
														},
														{
															"mData" : "errorCode"
														},
														{
															"mData" : "count",
															"render" : function(
																	data) {
																return '<input type="text" class="count hide" value= "'+data +'">';
															},
															className : "dt-body-center hide"
														},
														{
															"mData" : "errorMessage"
														},
														  { "mData": "serviceName" },
												             { "mData": "operationName" },
														{
															"mData" : "time",
															"render" : function(
																	data) {
																return '<input type="text" class="time hide" value= "'+data +'">';
															},
															className : "dt-body-center hide"
														} ]
											});
						});
		$("#searchTable").show();
	}

	function myreset() {
		$ = jQuery;
		$('#errorform')[0].reset();
		$("#searchTable").hide();
	}

	function mygraph2() {
		var selected = '';
		var datax = new google.visualization.DataTable();
		datax.addColumn('number', 'Time');
		var carray = [];
		var tarray = [];
		var r =$("#startTime").val();
		$("input[name='ck']:checked").each(function() {
		datax.addColumn('number', 'Count');

		var count = ($(".count", $(this).parent().parent()).val());
		carray[carray.length] = count.split(',').map(function(n) {
		return Number(n);
		});
		tarray[tarray.length] =count.split(',').map(function(n) {
		return Number(r++);
		});
		});
		alert(carray);
		alert(tarray);
		var rows = [];
		for (var i = 0; i < carray[0].length; i++) {
		var t = [];
		t[0] = tarray[0][i]
		for (var j = 1; j < carray.length + 1; j++) {
		t[j] = carray[j - 1][i];
		}

		rows[i] = t;
		}

		for (var i = 0; i < rows.length; i++) {
		datax.addRow(rows[i]);
		}

		var options = {
		title : 'Error Trends'
		};

		var chart = new google.visualization.LineChart(document
		.getElementById('chart_div'));

		chart.draw(datax, options);

		}
$(function() {
$( "#datepicker" ).datepicker();
});

</script>
<link rel="stylesheet" href="./Pages/css/bootstrap.min.css">
<link rel="stylesheet" href="./Pages/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="./Pages/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
</head>
<body>

	<div class="container">
		<nav role="navigation" class="navbar navbar-inverse">
		<div class="navbar-header">

			<button type="button" data-target="#navbarCollapse"
				data-toggle="collapse" class="navbar-toggle">

				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>

			</button>

			<a href="#" class="navbar-brand">MYAPP</a>

		</div>

		<!-- Collection of nav links, forms, and other content for toggling -->

		<div id="navbarCollapse" class="collapse navbar-collapse">

			<ul class="nav navbar-nav">

				<li><a href="<s:url action="welcome"/>">Home</a></li>

				<li><a href="<s:url action="error"/>">Reflow</a></li>

				<li class="active"><a href="<s:url action="error"/>">ErrorTrend</a></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a data-toggle="dropdown"
					class="dropdown-toggle" href="#">User <b class="caret"></b></a>

					<ul role="menu" class="dropdown-menu">

						<li><a href="#">Profile</a></li>

						<li><a href="<s:url action="logout"/>">Logout</a></li>

					</ul></li>
			</ul>

		</div>

		</nav>



		<div class="row">
			<div class="span2">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="<s:url action="welcome"/>">Home</a></li>
					<li><a href="<s:url action="error"/>">Reflow</a></li>
					<li class="active"><a href="#">ErrorTrend</a></li>
					<li><a href="#">Link3</a></li>

				</ul>
			</div>

			<div class="span9 well">
				<s:form class="form-horizontal" role="form" id="errorform"
					name="error">
					<div class="col-sm-6 col-lg-4">
							<div class="form-group">
								<label for="serviceNumber" class="col-md-4 control-label">Service
									Name:</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="serviceNumber" name="serviceName"
										placeholder="Service Number">
								</div>
							</div>
						</div>
					<div class="col-sm-6 col-lg-4">
							<div class="form-group">
								<label for="errorCode" class="col-md-4 control-label">Error
									Code:</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="errorCode" name="errorCode"
										placeholder="Error Code">
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-4">
							<div class="form-group">
								<label for="operationName" class="col-md-4 control-label">Operation
									Name:</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="operationName" name="operationName"
										placeholder="Operation Name">
								</div>
							</div>
						</div>
					<div class="col-sm-6 col-lg-4">
							<div class="form-group">
								<label for="Date" class="col-md-4 control-label">
									Date:</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="datepicker" name="date"
										placeholder="Date">
								</div>
							</div>
						</div>
					<div class="col-sm-6 col-lg-4">
						<div class="form-group">
						<label for="operationName" class="col-md-4 control-label">
									Start Time:</label>
							<div class="col-md-8">

								<select class="form-control" id="startTime" name="sendTime"
									placeholder="START TIME">
									<option value="1">01:00</option>
									<option value="2">02:00</option>
									<option value="3">03:00</option>
									<option value="4">04:00</option>
									<option value="5">05:00</option>
									<option value="6">06:00</option>
									<option value="7">07:00</option>
									<option value="8">08:00</option>
									<option value="9">09:00</option>
									<option value="10">10:00</option>
									<option value="11">11:00</option>
									<option value="12">12:00</option>
									<option value="13">13:00</option>
									<option value="14">14:00</option>
									<option value="15">15:00</option>
									<option value="16">16:00</option>
									<option value="17">17:00</option>
									<option value="18">18:00</option>
									<option value="19">19:00</option>
									<option value="20">20:00</option>
									<option value="21">21:00</option>
									<option value="22">22:00</option>
									<option value="23">23:00</option>
									<option value="24">24:00</option>
								</select>
							</div>
						</div>
					</div> 
					
					<div class="col-sm-6 col-lg-4">
						<div class="form-group">
						<label for="operationName" class="col-md-4 control-label">
									End Time:</label>
							<div class="col-md-8">
								<select class="form-control" id="endTime" name="receivedTime"
									placeholder="END TIME">
									<option value="1">01:00</option>
									<option value="2">02:00</option>
									<option value="3">03:00</option>
									<option value="4">04:00</option>
									<option value="5">05:00</option>
									<option value="6">06:00</option>
									<option value="7">07:00</option>
									<option value="8">08:00</option>
									<option value="9">09:00</option>
									<option value="10">10:00</option>
									<option value="11">11:00</option>
									<option value="12">12:00</option>
									<option value="13">13:00</option>
									<option value="14">14:00</option>
									<option value="15">15:00</option>
									<option value="16">16:00</option>
									<option value="17">17:00</option>
									<option value="18">18:00</option>
									<option value="19">19:00</option>
									<option value="20">20:00</option>
									<option value="21">21:00</option>
									<option value="22">22:00</option>
									<option value="23">23:00</option>
									<option value="24">24:00</option>
								</select>
							</div>
						</div>
					</div>




					<div class="row-sm-6 row-lg-4">
						<div class="btn-group " style="margin-left: 15px;">

							<a href="#" class="btn btn-primary btn-sm" style="margin: 0 2px;"
								id="search" onclick="mysearch()">SEARCH</a> <a href="#"
								class="btn btn-primary btn-sm" style="margin: 0 2px;" id="reset"
								onclick="myreset()">RESET</a> <a href="#"
								class="btn btn-primary btn-sm" style="margin: 0 2px;" id="graph"
								onclick="mygraph2()">GRAPH</a>

						</div>
					</div>

				</s:form>

			</div>
			<div class="span4 ">
				<div id="chart_div" style="width: 500px; height: 150px;"></div>
			</div>
		</div>


	</div>
	<div class="container" style="display: none" id="searchTable">


		<table class="jqueryDataTable table table-striped table-bordered"
			style="font-size: 12px">
			<thead>
				<tr>
					<th>Select</th>
					<th>Error Code</th>
					<th class="hide">Count</th>
					<th>Error Message</th>
					<th>Service Name</th>
					<th>Operation Name</th>
					<th class="hide">Time</th>
				</tr>

			</thead>

			<tbody>
			</tbody>

		</table>
	</div>
</body>
</html>