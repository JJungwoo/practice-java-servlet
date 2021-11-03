<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<%
    String user = Optional.ofNullable(session.getAttribute("userid")).map(Object::toString).orElse(null);
    if (user == null) {
    %>
    <a href="account/login.html">로그인</a> <br>
    <a href="account/signup.html">회원가입</a> <br>
    <%
    } else {
    %>
        <h1><%=user%>님 환영합니다.</h1> <br>
        <form action="/logout" method="get">
            <button type="submit">로그아웃</button>
        </form>
    <%
    }
%>
</body>
</html>