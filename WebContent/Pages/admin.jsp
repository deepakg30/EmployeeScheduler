<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Data</title>
<!-- Include one of jTable styles. -->
<link href="./Pages/css/lightcolor/blue/jtable.css" rel="stylesheet"
	type="text/css" />
<link href="./Pages/css/jquery-ui-1.10.3.custom.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="./Pages/css/timepicker.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<!-- Include jTable script file. -->
<script src="./Pages/js/jquery-1.8.2.js" type="text/javascript"></script>
<script src="./Pages/js/jquery-ui-1.10.3.custom.js"
	type="text/javascript"></script>
<script src="./Pages/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="./Pages/js/Datepair.js"></script>
<script src="./Pages/js/bootstrap-datepicker.js"></script>
<script src="./Pages/js/jquery.timepicker.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
$(function() {
	
	$('#datepairExample .date').datepicker({
		format: 'yyyy-m-dd',
        autoclose: true,
        startDate: '-3d'
	});
	var dateOnlyExampleEl = document.getElementById('datepairExample');
	var dateOnlyDatepair = new Datepair(dateOnlyExampleEl);
	});


function schedule() {
    	$ = jQuery;
    	$("#scheduleTable").show();
    	var formInput=$("#planform").serialize();
    	 $.getJSON('schedule',formInput,function(jsonResponse) {
    	var data = jsonResponse.schedule;
    	$("#PersonTableContainer").dataTable({
            "sPaginationType" : "full_numbers",
            "bRetrieve": false,
            "bDestroy": true,
            "aaData": data,
            "aoColumns" : [
		 { "mData": "date" },
		 { "mData": "e" },
        { "mData": "l" },
        { "mData": "d" },
        { "mData": "n" },
        { "mData": "dh" }
    ]
} ); 
    	 $("#usedDetail").show();
}); 
    	 };
    function getdayOff() {
    	 $("#usedDetail").hide();
    	 
    	$ = jQuery;
    	var usrid = document.getElementById('uid').value;
    	 $.getJSON('userDayOff',{userId:usrid},function(jsonResponse) {
    	var data = jsonResponse.dayOffList;
    	$("#dayoffTable").dataTable({
            "sPaginationType" : "full_numbers",
            "bRetrieve": false,
            "bDestroy": true,
            bFilter: false, bInfo: false,"bPaginate": false,
            "bLengthChange": false,
            "aaData": data,
            "aoColumns" : [
		 { "mData": "date" }
    ]
} ); 
});
    	 $("#dayoff").show();
    }
    
    
    function getUserDetails() {
    	$("#dayoff").hide();
    	$("#usedDetail").show();
    }
    function myreset() {
    	$ = jQuery;
    	$('#planform')[0].reset();
    	 $("#PersonTableContainer").dataTable().fnClearTable();
    	$("#scheduleTable").hide();
    	
    	}
</script>
<meta charset="UTF-8">
<title>Employee data</title>

<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<!-- Ionicons -->
<link
	href="//code.ionicframework.com/ionicons/1.5.2/css/ionicons.min.css"
	rel="stylesheet" type="text/css" />
<!-- Morris chart -->
<link href="./Pages/css/morris/morris.css" rel="stylesheet"
	type="text/css" />
<!-- jvectormap -->
<link href="./Pages/website/css/jvectormap/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- Date Picker -->
<link href="./Pages/css/datepicker/datepicker3.css" rel="stylesheet"
	type="text/css" />
<!-- Daterange picker -->
<link href="./Pages/css/daterangepicker/daterangepicker-bs3.css"
	rel="stylesheet" type="text/css" />
<!-- bootstrap wysihtml5 - text editor -->
<link
	href="./Pages/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"
	rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link href="./Pages/css/AdminLTE.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
</head>
<body class="skin-blue">
	<!-- header logo: style can be found in header.less -->
	<header class="header"> <a href="#" class="logo"> Job
		PLanner </a> <!-- Header Navbar: style can be found in header.less --> <nav
		class="navbar navbar-static-top" role="navigation"> <!-- Sidebar toggle button-->
	<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas"
		role="button"> <span class="sr-only">Toggle navigation</span> <span
		class="icon-bar"></span> <span class="icon-bar"></span> <span
		class="icon-bar"></span>
	</a>
	<div class="navbar-right">
		<ul class="nav navbar-nav">
			<!-- Messages: style can be found in dropdown.less-->
			<li class="dropdown messages-menu"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"> <i
					class="fa fa-envelope"></i>

			</a>
				<ul class="dropdown-menu">
					<li class="header">You have no messages</li>
					<li>
						<!-- inner menu: contains the actual data -->
						<ul class="menu">

							<li><a href="user.html">
									<div class="pull-left">
										<img src="website/img/avatar2.png" class="img-circle"
											alt="user image" />
									</div>
									<h4>
										Employee <small><i class="fa fa-clock-o"></i> 2 hours</small>
									</h4>

							</a></li>
							<li><a href="updatedb.jsp">
									<div class="pull-left">
										<img src="website/img/avatar.png" class="img-circle"
											alt="user image" />
									</div>
									<h4>
										Supervisor <small><i class="fa fa-clock-o"></i> Today</small>
									</h4>

							</a></li>


						</ul>
					</li>
					<li class="footer"><a href="#">See All Messages</a></li>
				</ul></li>

			<!-- User Account: style can be found in dropdown.less -->
			<li class="dropdown user user-menu"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"> <i
					class="glyphicon glyphicon-user"></i> <span>Admin <i
						class="caret"></i> <input type="hidden" id="uid" value=${user.uid}></span>
			</a>
				<ul class="dropdown-menu">
					<!-- User image -->
					<li class="user-header bg-light-blue"><img
						src="./Pages/img/avatar.png" class="img-circle" alt="User Image" />
						<p>Admin</p></li>
					<!-- Menu Body -->

					<!-- Menu Footer-->
					<li class="user-footer">
						<div class="pull-left">
							<a href="view_my_admin_profile_details.jsp"
								class="btn btn-default btn-flat">Profile</a>
						</div>
						<div class="pull-right">
							<a href="login.html" class="btn btn-default btn-flat">Sign
								out</a>
						</div>
					</li>
				</ul></li>
		</ul>
	</div>
	</nav> </header>
	</nav>
	</header>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="left-side sidebar-offcanvas"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="./Pages/img/avatar5.png" class="img-circle"
					alt="User Image" />
			</div>
			<div class="pull-left info">
				<p>Hello, Admin</p>

				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		<!-- search form -->
		<form action="#" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control"
					placeholder="Search..." /> <span class="input-group-btn">
					<button type='submit' name='search' id='search-btn'
						class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form>
		<!-- /.search form --> <!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="active"><a onclick="getUserDetails()"> <i
					class="fa fa-dashboard"></i> <span>Admin Dashboard</span>
			</a></li>
			<li><a href="calendar.html"> <i class="fa fa-calendar"></i>
					<span>Calendar</span>

			</a></li>
			<li><a onclick="getdayOff()"> <i class="fa fa-envelope"></i>
					<span>Day Off Requests</span>

			</a></li>
			<li class="treeview"><a href="vehiclerouting/loaded.jsp"> <i
					class="fa fa-folder"></i> <span>Schedules</span>
			</a></li>
		</ul>
		</section> <!-- /.sidebar --> </aside>
		<aside class="right-side"> <!-- Content Header (Page header) -->
		<section class="content-header">
		<h1>Employee Data</h1>
		<ol class="breadcrumb">
			<li><a href=""><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Table</li>
		</ol>
		</section> <section class="content">
		<div style="width: 100%; text-align: center;">
			<div class="col-sm-12 col-lg-12">
				<div class="row">
					<div class="">
						<s:form class="form-horizontal" role="form" id="planform"
							name="search">
							<div class="col-sm-6 col-lg-4">
								<div class="form-group">
									<label for="errorCode" class="col-md-4 control-label" style="margin-top:5px">Division</label>
									<div class="col-md-8">
										<select class="form-control" id="division"
											name="division" placeholder="Division">
									<option value="none">Select...</option>
									<option value="Catering">Catering</option>
									<option value="Concessions">Concessions</option>
									<option value="EMT">EMT</option>
									<option value="Law Enforcement">Law Enforcement</option>
									<option value="Media">Media</option>
									<option value="Operations">Operations</option>
									<option value="Parking">Parking</option>
									<option value="Retail">Retail</option>
									<option value="Security">Security</option>
									<option value="Ticket Sales">Ticket Sales</option>
									<option value="Ticket Taking">Ticket Taking</option>
											</select>
									</div>
								</div>
							</div>
							<div id="datepairExample">
							<div class="col-sm-6 col-lg-4">
								<div class="form-group">
									<label for="errorCode" class="col-md-4 control-label" style="margin-top:5px">Start Date</label>
									<div class="col-md-8">
										<input type="text" class="date start form-control" id="startDate"
											name="startDate" placeholder="Start Date">
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-lg-4">
								<div class="form-group">
									<label for="errorCode" class="col-md-4 control-label" style="margin-top:5px">End Date</label>
									<div class="col-md-8">
										<input type="text" class="date end form-control" id="endDate"
											name="endDate" placeholder="End Date">
									</div>
								</div>
							</div>
							</div>
						</s:form>
						<div class="row-sm-6 row-lg-4" style="margin-top:25px;">
						<div class="btn-group " style="margin-left: 15px;margin-top:25px;">

							<a href="#" class="btn btn-primary btn-md" style="margin: 0 2px;"
								id="search" onclick="schedule()">Schedule</a> <a href="#"
								class="btn btn-primary btn-md" style="margin: 0 2px;" id="reset"
								onclick="myreset()">RESET</a> 

						</div>
					</div>
					</div>
				</div>

			</div>
		</div>
<div id= "scheduleTable" style="display:none"><table id="PersonTableContainer" class="jqueryDataTable table table-striped table-bordered"
			style="font-size: 12px">
			<thead>
				<tr>
					
					<th>Date</th>
					<th>Early Shift</th>
					<th>Late Shift</th>
					<th>Day Shift</th>
					<th>Night Shift</th>
					<th>SupervisorDayShift</th>
				</tr>

			</thead>

			<tbody>
			</tbody>

		</table></div>
</div>

		</section>
</body>
</html>