<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/xhtml"
	  xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="mainCss" />
	<spring:url value="/resources/js/jquery.js" var="jqueryJs" />
	<spring:url value="/resources/js/bootstrap.min.js" var="mainJs" />

	<link href="${mainCss}" rel="stylesheet" />
	<script src="${jqueryJs}"></script>
	<script src="${mainJs}"></script>
</head>
<body>
<div class="container-fluid">
	<div th:if="${message}">
		<h2 th:text="${message}"/>
	</div>

	<div>
		<form method="POST" enctype="multipart/form-data" action="/">
			<table>
				<tr><td>File to upload:</td><td class="btn btn-primary"><input type="file" name="file" /></td></tr>
				<tr><td></td><td><input type="submit" value="Upload" class="btn btn-primary"/></td></tr>
			</table>
		</form>
	</div>

	<div>
		<ul>
			<li th:each="file : ${files}">
				<a th:href="${file.href}" th:text="${file.rel}" />
			</li>
		</ul>
	</div>
</div>
</body>
</html>