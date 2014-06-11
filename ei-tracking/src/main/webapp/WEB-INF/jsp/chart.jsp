<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Entry Charts</title>
</head>
<body>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type='text/javascript'>
      google.load('visualization', '1', {'packages':['annotatedtimeline']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
    	function d(y,M,d,h,m){
    		return new Date(y,M-1,d,h,m);
    	}
        var data = new google.visualization.DataTable();
        data.addColumn('datetime', 'Date');
        <c:set var="seriesName" value="${seriesList.seriesName}"/>
<c:forEach var="nameItem" items="${seriesName}" >
        data.addColumn('number', '${nameItem}');
</c:forEach>
        data.addRows([
<c:forEach var="row" items="${seriesList.filledSeriesData}" varStatus="status"><c:if test="${status.index!=0}">,</c:if>
        [d(<fmt:formatDate value="${row.key}" pattern="yyyy,M,d,H,m"/>) <c:forEach var="nameItem" items="${seriesName}">, <c:choose><c:when test="${row.value[nameItem] !=null}">${row.value[nameItem]}</c:when><c:otherwise>0</c:otherwise></c:choose></c:forEach> ]</c:forEach>
        ]);

        var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
        chart.draw(data);
      }
    </script>
<div id='chart_div' style='width: 700px; height: 240px;'></div>
</body>
</html>
