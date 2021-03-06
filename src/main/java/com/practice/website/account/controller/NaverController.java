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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet(name = "NaverController", value = "/oauth/naver")
public class NaverController extends HttpServlet {

    private static Logger logger;
    private static final String applicationPropertiesFilePath = "src/main/resources/application.properties";
    private String naverClientId;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();

        logger = LogManager.getLogger(NaverController .class);

        userService = new UserService(getServletContext().getRealPath("."));

        String path = getServletContext().getRealPath(".").replaceAll("\\\\", "/");

        Properties properties = FileIOUtil.jdbcGetPropertise(path.substring(0, path.lastIndexOf("target")) + applicationPropertiesFilePath);

        naverClientId = properties.getProperty("naver-client-id");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = naverAuthExcute(request, response);
        getIdNaverAuthExcute(request, response, accessToken);
        response.sendRedirect("/");
    }

    public void getIdNaverAuthExcute(HttpServletRequest request, HttpServletResponse response, String access_token) throws ServletException, IOException {

        String header = "Bearer " + access_token;

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);

        logger.info("user info api response {}", responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody.toString(), Map.class);
        Map<String, Object> respMap = (Map<String, Object>) map.get("response");
        String userid = String.valueOf(respMap.get("nickname"));
        String email = String.valueOf(respMap.get("email"));

        logger.info("naver oauth api response userid {}, email {} ", userid, email);

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
            } else {
                id = user.getNid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        if (session.getAttribute("userid") == null) {
            session.setAttribute("userid", userid);
            session.setAttribute("id", id);
            session.setAttribute("oauth", "naver");
            session.setAttribute("accessToken", access_token);
            session.setMaxInactiveInterval(60 * 30);    // 30???
        }
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // ?????? ??????
                return readBody(con.getInputStream());
            } else { // ?????? ??????
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ?????? ??????", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL??? ?????????????????????. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("????????? ??????????????????. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ????????? ??????????????????.", e);
        }
    }

    /**
     * https://nid.naver.com/oauth2.0/token?
     * grant_type=authorization_code&
     * client_id={client_id}&
     * client_secret={client_secret}&
     * code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg
     *
     */
    public String naverAuthExcute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        String endpoint="https://nid.naver.com/oauth2.0/token";
        URL url = new URL(endpoint);

        String bodyData="grant_type=authorization_code&";
        bodyData += "client_id=" + naverClientId + "&";
        bodyData += "code="+code;

        //Stream ??????
        HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();

        //http header ??? ??????
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);

        //request ??????
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

        logger.info("naver oauth api response {}", sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
        String access_token = String.valueOf(map.get("access_token"));
        logger.info("access_token: {} ", access_token);
        return access_token;
    }
}
