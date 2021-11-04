<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="index.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="main-div">
    <header class="main-header">
        <nav>
            <div class="main-nav-div">
                <ul class="main-nav-ul">
                    <li class="main-logo-li">
                        <a href="index.jsp" class="main-text-logo-a">
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
                        <!--                        <button class="main-login-button">로그인</button>-->
                        <a href="account/login.html" class="main-login-button">로그인</a>
                    </li>
                    <li class="main-signup-li">
                        <!--                        <button class="main-signup-button">회원가입</button>-->
                        <a href="account/login.html" class="main-signup-button">회원가입</a>
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
    <section class="main-section">
        <div class="main-section-div">
            <section class="main-inner-section">
                <!-- 카테고리에 따른 영화 목록 나열 -->
                <div class="main-inner-section-div">
                    <div class="main-inner-section-div-title">
                        <p class="main-inner-section-div-p-title">영화목록1</p>
                    </div>

                    <div class="main-inner-section-div-movie">

                        <div class="main-inner-section-divdiv-movie">
                            <div class="main-inner-section-divdivdiv-movie">
                                <div class="main-inner-section-divdivdivdiv-movie">
                                    <div class="main-inner-section-divdivdivdivdiv-movie">
                                        <ul class="main-inner-section-divdivdiv-ul-movie">

                                            <!-- 영화 목록 나열 -->
                                            <li class="main-inner-section-divdivdiv-ul-li-movie">
                                                <a title="이터널스" href="/ko-KR/contents/m5r3G2E">

                                                    <div class="main-inner-section-divdivdiv-ul-li-div1-movie">
                                                        <div class="main-inner-section-divdivdiv-ul-li-div1-div1-movie">
                                                            <img src="https://an2-img.amz.wtchn.net/image/v2/5802f39e2dadd2eec855e26a0e59aea5.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNMU56TTFORE0xTnpreE16UXdNems0SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5LmJuZFBXclFhVFp4U3hrRzMxRTFXNmxRcXdjUm9ZaVRDX1c5WlhBMUNsTEE"
                                                                 class="main-inner-section-divdivdiv-ul-li-div1-div1-image-movie ezcopuc1">
                                                        </div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div1-div2-movie">1</div>
                                                    </div>

                                                    <div class="main-inner-section-divdivdiv-ul-li-div2-movie">

                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-title-movie">이터널스</div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-country-movie">2021 ・ 미국</div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-rating-movie">
                                                            <span>평균</span>
                                                            <span>3.1</span>
                                                        </div>
                                                        <!--                                                        <div class="css-hyqnp8-StyledContentBoxOfficeStats ebeya3l13">예매율 85% ・ 누적 관객 30만명</div>-->

                                                    </div>
                                                </a>
                                            </li>

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
                        <p class="main-inner-section-div-p-title">영화목록1</p>
                    </div>

                    <div class="main-inner-section-div-movie">

                        <div class="main-inner-section-divdiv-movie">
                            <div class="main-inner-section-divdivdiv-movie">
                                <div class="main-inner-section-divdivdivdiv-movie">
                                    <div class="main-inner-section-divdivdivdivdiv-movie">
                                        <ul class="main-inner-section-divdivdiv-ul-movie">

                                            <!-- 영화 목록 나열 -->
                                            <li class="main-inner-section-divdivdiv-ul-li-movie">
                                                <a title="이터널스" href="/ko-KR/contents/m5r3G2E">

                                                    <div class="main-inner-section-divdivdiv-ul-li-div1-movie">
                                                        <div class="main-inner-section-divdivdiv-ul-li-div1-div1-movie">
                                                            <img src="https://an2-img.amz.wtchn.net/image/v2/5802f39e2dadd2eec855e26a0e59aea5.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNMU56TTFORE0xTnpreE16UXdNems0SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5LmJuZFBXclFhVFp4U3hrRzMxRTFXNmxRcXdjUm9ZaVRDX1c5WlhBMUNsTEE"
                                                                 class="main-inner-section-divdivdiv-ul-li-div1-div1-image-movie ezcopuc1">
                                                        </div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div1-div2-movie">1</div>
                                                    </div>

                                                    <div class="main-inner-section-divdivdiv-ul-li-div2-movie">

                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-title-movie">이터널스</div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-country-movie">2021 ・ 미국</div>
                                                        <div class="main-inner-section-divdivdiv-ul-li-div2-rating-movie">
                                                            <span>평균</span>
                                                            <span>3.1</span>
                                                        </div>
                                                        <!--                                                        <div class="css-hyqnp8-StyledContentBoxOfficeStats ebeya3l13">예매율 85% ・ 누적 관객 30만명</div>-->

                                                    </div>

                                                </a>
                                            </li>

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