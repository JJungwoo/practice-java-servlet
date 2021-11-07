<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <title>MovieRating</title>
    <link href="${contextPath}/index.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="main-div">
    <%@ include file="header.jsp"%>

    <section class="main-section">
        <div class="main-section-div">
            <section class="main-inner-section">
                <!-- 카테고리에 따른 영화 목록 나열 -->
                <div class="main-inner-section-div">
                    <div class="main-inner-section-div-title">
                        <p class="main-inner-section-div-p-title">박스오피스 순위</p>
                    </div>

                    <div class="main-inner-section-div-movie">
                        <div class="main-inner-section-divdiv-movie">
                            <div class="main-inner-section-divdivdiv-movie">
                                <div class="main-inner-section-divdivdivdiv-movie">
                                    <div class="main-inner-section-divdivdivdivdiv-movie">
                                        <ul class="main-inner-section-divdivdiv-ul-movie">

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


                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div direction="left" class="left"></div>
                            <div direction="right" class="right"></div>
                        </div>
                    </div>
                </div>

<%--                --%>

                <div class="main-inner-section-div">
                    <div class="main-inner-section-div-title">
                        <p class="main-inner-section-div-p-title">평균별점이 높은 작품</p>
                    </div>

                    <div class="main-inner-section-div-movie">

                        <div class="main-inner-section-divdiv-movie">
                            <div class="main-inner-section-divdivdiv-movie">
                                <div class="main-inner-section-divdivdivdiv-movie">
                                    <div class="main-inner-section-divdivdivdivdiv-movie">
                                        <ul class="main-inner-section-divdivdiv-ul-movie">

                                            <!-- 영화 목록 나열 -->


                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



            </section>
        </div>
    </section>
</div>

</body>
</html>