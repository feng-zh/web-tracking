<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Current User's Entry List</title>
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
<c:if test="${error != null}" >
<h2>Error</h2>
<hr/>
<p>
<b>${error}</b>
</p>
</c:if>
<c:if test="${error == null}" >
<div id="summary">
<div id="search" style="float: left;">
<form:form method="post" >
	<table>
	<tr>
	<td>
	<label for="startDate">Start Date</label><form:input path="startDate"/>
	</td>
	<td>
	<label for="endDate">End Date</label><form:input path="endDate"/>
	</td>
	</tr>
	<tr>
	<td>
	<c:if test="${mode == 'uid'}">
	<c:set var="column_uid" value="false"/>
	<c:set var="column_returnUser" value="false"/>
	<label for="uid">Unique Id</label>
	<c:if test="${uid == null}">
	No record
	</c:if>
	<c:if test="${uid != null }">
	<b>${uid}</b>
	</c:if>
	</c:if>
	<c:if test="${mode == 'ip'}">
	<c:set var="column_remoteAddr" value="false"/>
	<label for="uid">Remote Address</label>
	<c:if test="${remoteAddr == null}">
	No record
	</c:if>
	<c:if test="${remoteAddr != null }">
	<b>${remoteAddr}</b>
	</c:if>
	</c:if>
	</td>
	<td>
	<input type="submit" value="Query" />
	</td>
	</tr>
	</table>
</form:form>
</div>
<div style="float: right;">
<%@include file="include-statistics.jsp" %>
</div>
</div>
<%@include file="include-list.jsp" %>
</c:if>
</body>
</html>
