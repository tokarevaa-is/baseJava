<%--
  Created by IntelliJ IDEA.
  User: aatokarev
  Date: 29.11.2023
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.tokarevaa.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.tokarevaa.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>

</body>
</html>
