<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<html>
<head>
    <title>영화 상세정보</title>
</head>
<body>
<header>
<%@ include file="header.jsp"%>
</header>
<section>
<h1 align="center">영화 상세정보</h1>

<div>
    <!--포스터 이미지 출력-->
    <p><img src="${movie.posterUrl}" alt="${movie.posterUrl}" /></p>
</div>
<div>
    <h2>${movie.title}</h2>
    <input type="hidden" value="${movie.id}">
    <p>${movie.openDate} 개봉 / ${movie.genre} / ${movie.nation}</p>
    <p>원제 ${movie.originName} / 상영시간 ${movie.runTime}분</p>
    <h3>평균별점 ⭐️ ${avgScore} (${ratingCount}명 평가)</h3>
</div>
<div>

<%--    <%--%>
<%--        String user = Optional.ofNullable(session.getAttribute("userid")).map(Object::toString).orElse(null);--%>
<%--        if (user == null) {--%>
<%--    %>--%>
<%--        &lt;%&ndash; 비회원일 경우 &ndash;%&gt;--%>

<%--    <%--%>
<%--    } else {--%>
<%--    %>--%>
<%--        &lt;%&ndash; 회원일 경우 &ndash;%&gt;--%>

<%--        &lt;%&ndash; 별점 등록한 경우 &ndash;%&gt;--%>
<%--    <%--%>
<%--        }--%>
<%--    %>--%>


<%--    <h3>별점 입력</h3>--%>
<%--    <form action="${contextPath}/rating/form" method="post">--%>
<%--        <input type="hidden" name="movieid" value="${movie.movieNo}">--%>
<%--        <input type="hidden" name="name" value="${movie.name}">--%>
<%--        <input type="submit" value="별점 평가하기">--%>
<%--    </form>--%>

</div>
<div>
    <h3>상세 정보</h3>
    <p>${movie.detail}</p>
</div>
<div>
    <h3>출연/제작</h3>
    <div id="casting">
        <%-- <a href="/casting?movieid=${movie.movieNo}">캐스팅 목록</a> --%>
    </div>
</div>
<div>
    <h3>코멘트</h3>
    <p><!-- 코멘트 영역 --></p>
</div>
<div>
    <h3>컬렉션</h3>
    <p><!-- 컬렉션 영역 --></p>
</div>
<div>
    <p><button type="button" onclick="window.history.back();">뒤로가기</button></p>
</div>
</section>
</body>
</html>