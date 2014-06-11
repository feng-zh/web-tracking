<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="list-statistics">
<span>Total Count:</span> ${statistics.totalCount}; 
<span>Client IP Count:</span> ${statistics.uniqRemoteAddrCount}; 
<span>Total Visitor:</span> ${statistics.uniqUserCount}; 
<span>New Visitor:</span> ${statistics.newUserCount}; 
<br>
<ul>
<c:forEach var="osEntry" items="${statistics.osData}"><li>${osEntry.key}: ${osEntry.value}</li></c:forEach>
<c:forEach var="browserEntry" items="${statistics.browserData}"><li>${browserEntry.key}: ${browserEntry.value}</li></c:forEach> 
</ul>
</div>