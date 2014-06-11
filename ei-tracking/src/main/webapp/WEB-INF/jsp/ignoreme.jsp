<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Set/View Ignore Me</title>
</head>
<body>
<h2>Ignore Me Status</h2>
<hr />
<div id="form"><form:form method="post" >
	<c:if test="${submit!=null}"><span style="background-color: #cccccc; font-style: italic;">Your setting is applied.</span></c:if>
	<p><c:if test="${ignoremeSet}">
		<b>You are enable "Ignore Me" function, this is most for EI team
		members.</b>
	</c:if> <c:if test="${!ignoremeSet}">Your tracking are all logged for analysis purpose.</c:if>
	</p>
	<p><label>Change "Ignore Me":</label> <input type="radio"
		id="enableIgnoreMe" name="ignoremeSet" value="true" ${ignoremeSet?"checked":""}/>
	<label for="enableIgnoreMe">Enable</label> <input type="radio"
		id="disableIgnoreMe" name="ignoremeSet" value="false" ${ignoremeSet?"":"checked"}/>
	<label for="disableIgnoreMe">Disable</label> <input type="submit"
		name="submit" value="Submit" /></p>
</form:form></div>
</body>
</html>
