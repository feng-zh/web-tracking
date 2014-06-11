<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title><decorator:title /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="//code.jquery.com/jquery-1.10.1.min.js"></script>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet" media="screen" />
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<script>
var _context = "<%= request.getContextPath()%>"
</script>
<decorator:head />
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand active" href="list.do">EI Tracking</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a href="list.do">List</a></li>
						<li><a href="chart.do">Chart</a></li>
						<li><a href="uid.do">Current Uid</a></li>
						<li><a href="ip.do">Current IP</a></li>
						<li><a href="ignoreme.do">Ignore Me</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
	<decorator:body />
	</div>
	<div id="tracking" style="display: none;">
	<img src="<%= request.getContextPath()%>/entry.do">
	</div>
</body>
</html>