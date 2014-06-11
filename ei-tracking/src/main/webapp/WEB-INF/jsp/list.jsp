<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Entry List</title>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/javascript/jquery.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.8/jquery-ui.min.js"></script> 
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/javascript/jquery.ui.datetime.min.js"></script>
	<link rel="stylesheet" type="text/css" media="screen" href="http://hotlink.jquery.com/jqueryui/themes/base/jquery.ui.all.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.8/themes/flick/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/css/jquery.ui.datetime.css" />
	<script type="text/javascript" charset="utf-8"> 
	$(function(){
		$('#startDate').datetime();
		$('#endDate').datetime();
	});
	</script>  
</head>
<body>
<div id="summary">
<div id="search" style="float: left;">
<form:form method="post">
	<table>
	<tr>
	<td>
	<label for="startDate">Start Date</label><form:input path="startDate"/>
	</td>
	<td>
	<label for="endDate">End Date</label><form:input path="endDate"/>
	</td>
	<td>
	<form:checkbox path="includeIgnore" label="All Included"/>
	</td>
	</tr>
<!-- 	
	<tr>
	<td>
	<label for="pageString">Page</label><form:input path="pageString"/>
	</td>
	<td>
	<label for="remoteAddr">Address</label><form:input path="remoteAddr"/>
	</td>
	<td>
	</td>
	</tr>
	</table>
-->	
	<input type="submit" value="Query" />
</form:form>
</div>
<div style="float: right;">
<%@include file="include-statistics.jsp" %>
</div>
</div>
<%@include file="include-list.jsp" %>
</body>
</html>
