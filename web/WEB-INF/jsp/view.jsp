<%--
  Created by IntelliJ IDEA.
  User: aatokarev
  Date: 29.11.2023
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ page import="com.tokarevaa.webapp.model.ContactType" %>--%>
<%@ page import="com.tokarevaa.webapp.model.SectionType" %>
<%@ page import="com.tokarevaa.webapp.model.TextSection" %>
<%@ page import="com.tokarevaa.webapp.model.ListSection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>${resume.fullName}
        <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a>
        <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a>
    </h2>
    <p>
    <h3>Контакты:</h3>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.tokarevaa.webapp.model.ContactType, java.lang.String>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
    </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="<%=resume.getSections()%>">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.tokarevaa.webapp.model.SectionType, com.tokarevaa.webapp.model.Section>"/>
        <c:if test="${sectionEntry.value!='' && sectionEntry.value!='[]'}">
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="typeText" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section"
                         type="com.tokarevaa.webapp.model.Section"/>
    <h3><%=sectionEntry.getKey().getTitle()%>
    </h3>
    <c:choose>
        <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
            <%=((TextSection) section).getContent()%>
        </c:when>
        <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATIONS}">
            <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                <li>${item}</li>
            </c:forEach>
        </c:when>
        <c:when test="${type==SectionType.EXPERIENCE || type==SectionType.EDUCATION}">
        </c:when>
    </c:choose>
    </c:if>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>