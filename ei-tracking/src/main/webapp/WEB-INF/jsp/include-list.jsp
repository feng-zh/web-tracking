<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<style type="text/css" title="currentStyle"> 
		@import "<%=request.getContextPath()%>/css/table.css";
		.ignore td {
			font-style: italic;
		}
		.new td {
			font-weight: bold;
		}
	</style>
	<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/jquery.dataTables.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/jquery.dataTables.columnFilter.js" ></script>
	<script type="text/javascript" charset="utf-8"> 
		$(document).ready(function() {
			$('#table').dataTable({"aaSorting": [[0,'desc']]}).columnFilter();
		} );
	</script>
<div id="list-result">
<table id="table" cellpadding="0" cellspacing="0" border="0" class="display">
<thead>
<tr>
<th>Time</th>
<th>From</th>
<th>QueryString</th>
<c:if test="${column_uid != false}"><th>Uid</th></c:if>
<c:if test="${column_remoteAddr != false}"><th>Client Access</th></c:if>
<th>User Agent</th>
</tr>
</thead>
<tbody>
<c:forEach var="entry" items="${list}" varStatus="status">
<tr class='${entry.ignore?"ignore":""} ${entry.returnUser?"":"new"}'>
<td><fmt:formatDate value="${entry.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td><c:out value="${entry.referFrom}" escapeXml="true"/></td><td><c:out value="${entry.queryString}" escapeXml="true"/></td>
<c:if test="${column_uid != false}"><td><c:out value="${entry.uid}" escapeXml="true"/></td>
</c:if><c:if test="${column_remoteAddr != false}"><td>${entry.clientAccess}</td>
</c:if><td>${entry.browserInfo} (${entry.osInfo})</td>
</tr></c:forEach>
</tbody>
<tfoot>
<tr>
<th>Time</th>
<th>From</th>
<th>QueryString</th>
<c:if test="${column_remoteAddr != false}"><th>Client IP</th></c:if>
<c:if test="${column_uid != false}"><th>Uid</th></c:if>
<th>User Agent</th>
</tr>
</tfoot>
</table>
</div>