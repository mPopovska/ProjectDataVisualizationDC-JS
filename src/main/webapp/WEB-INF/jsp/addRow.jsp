<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Matea
  Date: 24.08.2016
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <title>Add Row</title>
    <spring:url value="/resources/css/bootstrap.min.css" var="mainCss" />
    <spring:url value="/resources/js/jquery.js" var="jqueryJs" />
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs" />

    <link href="${mainCss}" rel="stylesheet" />
    <script src="${jqueryJs}"></script>
    <script src="${mainJs}"></script>
</head>
<body>
<div class="container-fluid">
<h1>Add row</h1>
<form action="addWorkLog" method="post">
    <p>Project</p>
    <input type="text" name="project"/><br/>
    <p>Date</p>
    <input type="date" name="date"/><br/>
    <p>Tags</p>
    <input type="text" name="tags"/><br/>
    <p>Description</p>
    <input type="text" name="desc"/><br/>
    <p>Hours</p>
    <input type="number" name="hours"/><br/>
    <p>Holiday</p>
    <input type="checkbox" name="isHoliday"/><br/>
    <input type="submit" value="submit" class="btn btn-primary"/>
</form>
</div>
</body>
</html>
