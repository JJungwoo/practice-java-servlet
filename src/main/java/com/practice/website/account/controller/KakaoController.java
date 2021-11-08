package com.practice.website.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.website.account.domain.User;
import com.practice.website.account.service.UserService;
import com.practice.website.util.FileIOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import java.util.Properties;

@WebServlet(name = "KakaoController", value = "/oauth/kakao")
public class KakaoController extends HttpServlet {

    private static Logger logger;
    static final private String applicationPropertiesFilePath = "src/main/resources/application.properties";
    private String kakaoClientId;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();

        logger = LogManager.getLogger(KakaoController.class);

        userService = new UserService(getServletContext().getRealPath("."));

        String path = getServletContext().getRealPath(".").replaceAll("\\\\", "/");

        Properties properties = FileIOUtil.jdbcGetPropertise(path.substring(0, path.lastIndexOf("target")) + applicationPropertiesFilePath);

        kakaoClientId = properties.getProperty("kakao-client-id");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = kakaoAuthExcute(request, response);
        getIdKakaoAuthExcute(request, response, accessToken);
        response.sendRedirect("/");
    }

    /**
     * ex) curl -v -X POST "https://kapi.kakao.com/v2/user/me" \
     *         -H "Content-Type: application/x-www-form-urlencoded" \
     *         -H "Authorization: Bearer {ACCESS_TOKEN}" \
     *         --data-urlencode 'property_keys=["kakao_account.email"]'
     */
    public void getIdKakaoAuthExcute(HttpServletRequest request, HttpServletResponse response, String access_token) throws ServletException, IOException {

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

        logger.info("getIdkakaoAuthExcute 결과 : {} ", sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
        Map<String, Object> propertiesMap = (Map<String, Object>) map.get("properties");
        String userid = String.valueOf(propertiesMap.get("nickname"));
        String email = String.valueOf(map.get("id"));

        logger.info("kakao oauth api response userid {}, email {} ", userid, email);

        User user = null;
        Long id = null;

        try {
            user = userService.findByEmail(email);
            if (user == null) {
                userService.insert(User.builder()
                        .name(userid)
                        .email(email)
                        .build());
                id = userService.findByEmail(email).getNid();
                logger.info("kakao 신규 가입 성공 {} ", id);
            } else {
                id = user.getNid();
                logger.info("kakao 기존 유저 {} ", id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        if (session.getAttribute("userid") == null) {
            session.setAttribute("userid", userid);
            session.setAttribute("id", id);
            session.setAttribute("oauth", "kakao");
            session.setAttribute("accessToken", access_token);
            session.setMaxInactiveInterval(60 * 30);    // 30분
        }
    }


    public String kakaoAuthExcute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        String endpoint="https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);

        String bodyData="grant_type=authorization_code&";
        bodyData += "client_id=" + kakaoClientId + "&";
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

        logger.info("kakao oauth api response : {} ", sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
        String access_token = String.valueOf(map.get("access_token"));
        logger.info("access_token: {}", access_token);
        return access_token;
    }
}
