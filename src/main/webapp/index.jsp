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
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script>

        function LoadingWithMask(gif) {
            //화면의 높이와 너비를 구합니다.
            var maskHeight = $(document).height();
            var maskWidth  = window.document.body.clientWidth;

            //화면에 출력할 마스크를 설정해줍니다.
            var mask       = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
            var loadingImg = '';

            loadingImg += " <img src='"+ gif + "' style='position: absolute; display: block; margin: 0px auto;'/>";

            //화면에 레이어 추가
            $('body')
                .append(mask)

            //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
            $('#mask').css({
                'width' : maskWidth,
                'height': maskHeight,
                'opacity' : '0.3'
            });

            //마스크 표시
            $('#mask').show();

            //로딩중 이미지 표시
            $('#loadingImg').append(loadingImg);
            $('#loadingImg').show();
        }

        function closeLoadingWithMask() {
            $('#mask, #loadingImg').hide();
            $('#mask, #loadingImg').empty();
        }

        $(document).ready(function() {
            LoadingWithMask('${contextPath}/src/main/webapp/resources/images/loadingImg.gif');
            $.ajax({
                type:'GET',
                url:"http://localhost:9999/collection/19",
                dataType:'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    // alert('success:'+data);
                    $('#movieList1').html(data);
                },
                error: function(request,status,error){
                    $('#movieList1').html(request.responseText);
                    // alert('fail code:'+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); //에러 상태에 대한 세부사항 출력
                }
            });

            $.ajax({
                type:'GET',
                url:"http://localhost:9999/collection/25",
                dataType:'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    // alert('success:'+data);
                    $('#movieList2').html(data);
                },
                error: function(request,status,error){
                    $('#movieList2').html(request.responseText);
                    // alert('fail code:'+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); //에러 상태에 대한 세부사항 출력
                }
            });
            closeLoadingWithMask();
        });
    </script>
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

                                            <div id="movieList1">

                                            </div>

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
                                            <div id="movieList2">

                                            </div>

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