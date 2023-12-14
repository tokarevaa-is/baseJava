<%--
  Created by IntelliJ IDEA.
  User: aatokarev
  Date: 29.11.2023
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.tokarevaa.webapp.model.ContactType" %>
<%@ page import="com.tokarevaa.webapp.model.SectionType" %>
<%@ page import="com.tokarevaa.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.tokarevaa.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя *:</dt>
            <dd><input required="required" type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContacts(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSections(type)}"/>
            <jsp:useBean id="section" type="com.tokarevaa.webapp.model.Section"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                <textarea name='${type}' cols="80" rows="5">
<%----%>
                    <%=section%>
                </textarea>
                </c:when>
                <c:when test="${type==SectionType.QUALIFICATIONS || type==SectionType.ACHIEVEMENT}">
                <textarea name='${type}' cols="80" rows="5">
<%----%>
                    <%=String.join("\n", ((ListSection) section).getItems())%>
                </textarea>
                </c:when>
                <c:when test="${type==SectionType.EXPERIENCE || type==SectionType.EDUCATION}">
                    <%--                <textarea name='${type}' cols="80" rows="5">--%>

                    <%--                </textarea>--%>
                </c:when>
                <c:when test="">
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
