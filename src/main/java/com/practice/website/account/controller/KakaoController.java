package com.practice.website.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.Map;

@WebServlet(name = "KakaoController", value = "/oauth/kakao")
public class KakaoController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // http://localhost:8000/blog/user?cmd=join
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>kakao login</h1>");
        out.println("</body></html>");
        String accessToken = kakaoAuthExcute(request, response);
        getIdkakaoAuthExcute(request, response, accessToken);
        response.sendRedirect("/");
    }

    /**
     * ex) curl -v -X POST "https://kapi.kakao.com/v2/user/me" \
     *         -H "Content-Type: application/x-www-form-urlencoded" \
     *         -H "Authorization: Bearer {ACCESS_TOKEN}" \
     *         --data-urlencode 'property_keys=["kakao_account.email"]'
     */
    public void getIdkakaoAuthExcute(HttpServletRequest request, HttpServletResponse response, String access_token) throws ServletException, IOException {

        System.out.println("getIdkakaoAuthExcute");

        String endpoint="https://kapi.kakao.com/v2/user/me";
        URL url = new URL(endpoint);

        String Authorization = "Bearer " + access_token;

        //Stream 연결
        HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();

        //http header 값 넣기
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", Authorization);
        conn.setDoOutput(true);

        //request 하기
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        bw.flush();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );

        String input = "";
        StringBuilder sb = new StringBuilder();
        while((input=br.readLine())!=null){
            sb.append(input);
        }

        System.out.println("getIdkakaoAuthExcute 결과 : " + sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
        String userid = String.valueOf(map.get("id"));

        HttpSession session = request.getSession(true);
        if (session.getAttribute("userid") == null) {
            session.setAttribute("userid", userid);
        }
        System.out.println("userid : " + userid + ",  session : " + String.valueOf(session.getAttribute("userid")));
    }


    public String kakaoAuthExcute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        String endpoint="https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);

        String bodyData="grant_type=authorization_code&";
        bodyData += "client_id=5ba7121063da70f1a1efd0bd8a96f162&";
        bodyData += "redirect_uri=http://localhost:9999/oauth/kakao?cmd=callback&";
        bodyData += "code="+code;

        //Stream 연결
        HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();

        //http header 값 넣기
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);

        //request 하기
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        bw.write(bodyData);
        bw.flush();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );

        String input = "";
        StringBuilder sb = new StringBuilder();
        while((input=br.readLine())!=null){
            sb.append(input);
        }

        System.out.println(sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
        String access_token = String.valueOf(map.get("access_token"));
        System.out.println("access_token:"+access_token);
        return access_token;
    }
}