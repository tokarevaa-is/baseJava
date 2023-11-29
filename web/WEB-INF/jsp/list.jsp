<%--
  Created by IntelliJ IDEA.
  User: aatokarev
  Date: 27.11.2023
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.tokarevaa.webapp.model.Resume" %>
<%@ page import="com.tokarevaa.webapp.model.ContactType" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.tokarevaa.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContacts(ContactType.EMAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>