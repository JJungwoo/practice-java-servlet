package com.practice.website.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.website.util.FileIOUtil;

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

    static final private String applicationPropertiesFilePath = "src/main/resources/application.properties";
    private String naverClientId;

    @Override
    public void init() throws ServletException {
        super.init();

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

        System.out.println("<response result>");
        System.out.println(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody.toString(), Map.class);
        Map<String, Object> respMap = (Map<String, Object>) map.get("response");
        String userid = String.valueOf(respMap.get("id"));

        System.out.println("email : " + String.valueOf(respMap.get("email")));

        HttpSession session = request.getSession(true);
        if (session.getAttribute("userid") == null) {
            session.setAttribute("userid", userid);
            session.setAttribute("oauth", "naver");
            session.setAttribute("accessToken", access_token);
        }
        System.out.println("userid : " + userid + ",  session : " + String.valueOf(session.getAttribute("userid")));
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
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
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
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
