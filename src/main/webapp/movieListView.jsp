<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>Title</title>

</head>
<body>
<!-- 영화 목록 나열 -->
<c:set var="cnt" value="0" />
<c:forEach items="${movieList}" var="movie">
    <c:set var="loopCnt" value="${cnt + 1}" />
    <li class="main-inner-section-divdivdiv-ul-li-movie">
        <a title="${movie.title}" href="/movie/${movie.id}">

            <div class="main-inner-section-divdivdiv-ul-li-div1-movie">
                <div class="main-inner-section-divdivdiv-ul-li-div1-div1-movie">
                    <img src="${movie.posterUrl}"
                         class="main-inner-section-divdivdiv-ul-li-div1-div1-image-movie ezcopuc1">
                </div>
                <div class="main-inner-section-divdivdiv-ul-li-div1-div2-movie">${loopCnt}</div>
            </div>

            <div class="main-inner-section-divdivdiv-ul-li-div2-movie">

                <div class="main-inner-section-divdivdiv-ul-li-div2-title-movie">${movie.title}</div>
                <div class="main-inner-section-divdivdiv-ul-li-div2-country-movie">${movie.openDate} ・ ${movie.nation}</div>
                <div class="main-inner-section-divdivdiv-ul-li-div2-rating-movie">
                    <span>평균</span>
                    <span>3.1</span>
                </div>
                <!-- <div class="css-hyqnp8-StyledContentBoxOfficeStats ebeya3l13">예매율 85% ・ 누적 관객 30만명</div>-->

            </div>
        </a>
    </li>
    <c:set var="cnt" value="${loopCnt}" />
</c:forEach>

</body>
</html>
