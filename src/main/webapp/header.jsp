<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>Title</title>
    <link href="${contextPath}/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<header class="main-header">
    <nav>
        <div class="main-nav-div">
            <ul class="main-nav-ul">
                <li class="main-logo-li">
                    <a href="${contextPath}/index.jsp" class="main-text-logo-a">
                        <h1>MovieRating</h1>
                    </a>
                    <%--                        <svg class="main-logo-svg">MovieRating</svg>--%>
                </li>

                <li class="main-nav-div-li">
                    <div class="main-nav-SearchContainer">
                        <div class="main-nav-SearchContainer-div">
                            <form action="#">
                                <label class="main-nav-SearchContainer-div-label">
                                    <input autocomplete="off" placeholder="콘텐츠, 인물, 컬렉션, 유저를 검색해보세요."
                                           type="text" name="searchKeyword" class="main-nav-SearchContainer-div-label-input" value="">
                                    <div value="false" class="main-nav-SearchContainer-div-label-div">
                                            <span aria-label="clear" role="button" class="main-nav-SearchContainer-div-label-div-span">
                                            </span>
                                    </div>
                                </label>
                            </form>
                        </div>
                    </div>
                </li>

                <%
                    String user = Optional.ofNullable(session.getAttribute("userid")).map(Object::toString).orElse(null);
                    if (user == null) {
                %>
                <li class="main-login-li">
                    <%--                        <button class="main-login-button" onclick="move()">로그인</button>--%>
                    <a href="${contextPath}/account/login.html" class="main-login-button">로그인</a>
                </li>
                <li class="main-signup-li">
                    <!--                        <button class="main-signup-button">회원가입</button>-->
                    <a href="${contextPath}/account/signup.html" class="main-signup-button">회원가입</a>
                </li>
                <%
                } else {
                %>
                <h1><%=user%>님 환영합니다.</h1>
                <form action="/logout" method="get">
                    <button type="submit">로그아웃</button>
                </form>
                <%
                    }
                %>

            </ul>
        </div>
    </nav>
</header>
</body>
</html>
