package com.example.mystock.service;

import cn.hutool.json.JSONObject;
import com.example.mystock.domain.dto.News;
import com.example.mystock.domain.dto.NewsResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.mystock.utils.CommandConstant.PROSPECTBAD_PROMPT;
import static com.example.mystock.utils.CommandConstant.PROSPECTGOOD_PROMPT;

@Slf4j
@Service
public class ProspectService {
    private final OkHttpClient client;

    public ProspectService() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时时间
                .readTimeout(60, TimeUnit.SECONDS)    // 读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)    // 写入超时时间
                .build();
    }

    public NewsResult positiveNews(String stock) throws IOException {
        // 创建JSON对象
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", "");
        jsonObject.addProperty("keyword", stock);
        JsonArray typeArray = new JsonArray();
        typeArray.add("cmsArticleWebOld");
        jsonObject.add("type", typeArray); // 直接添加JsonArray

        jsonObject.addProperty("client", "web");
        jsonObject.addProperty("clientType", "web");
        jsonObject.addProperty("clientVersion", "curr");

        JsonObject paramObject = new JsonObject();
        JsonObject nestedParamObject = new JsonObject();
        nestedParamObject.addProperty("searchScope", "default");
        nestedParamObject.addProperty("sort", "default");
        nestedParamObject.addProperty("pageIndex", 1);
        nestedParamObject.addProperty("pageSize", 20);
        nestedParamObject.addProperty("preTag", "");
        nestedParamObject.addProperty("postTag", "");
        paramObject.add("cmsArticleWebOld", nestedParamObject);
        jsonObject.add("param", paramObject);

        // 将JSON对象转换为字符串
        String jsonString = gson.toJson(jsonObject);

        // URL 编码 JSON 字符串
//        String encodedJsonString = URLEncoder.encode(jsonString, StandardCharsets.UTF_8);

        // 构建完整的URL
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("search-api-web.eastmoney.com")
                .addPathSegment("search")
                .addPathSegment("jsonp")
                .addQueryParameter("cb", "apisearch")
                .addQueryParameter("param", jsonString)
                .addQueryParameter("_", "1733658609092") // 时间戳
                .build();
//        System.out.println(url.toString());
//        String jsonString = "{\"uid\":\"\",\"keyword\":\"微软\",\"type\":[\"cmsArticleWebOld\"],\"client\":\"web\",\"clientType\":\"web\",\"clientVersion\":\"curr\",\"param\":{\"cmsArticleWebOld\":{\"searchScope\":\"default\",\"sort\":\"default\",\"pageIndex\":1,\"pageSize\":10,\"preTag\":\"<em>\",\"postTag\":\"</em>\"}}}";
//
//        // Convert JSON string to URL encoded string
//        String urlEncodedString = URLEncoder.encode(jsonString, StandardCharsets.UTF_8.toString());
//
//        String url = "https://search-api-web.eastmoney.com/search/jsonp?cb=apisearch&param=" + urlEncodedString + "&_=1733658609092";

        Request request = new Request.Builder()
                .url(url)
                .build();
        String responseBody = "";
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            responseBody = response.body().string();
//            System.out.println(responseBody);
            log.info(responseBody);
        }catch (Exception e){
            e.printStackTrace();
        }
        int startIndex = responseBody.indexOf("(") + 1;
        int endIndex = responseBody.lastIndexOf(")");
        String jsonPart = responseBody.substring(startIndex, endIndex);

        // 解析 JSON 字符串
        JsonObject jsonObject2 = JsonParser.parseString(jsonPart).getAsJsonObject();

        // 获取 result 部分
        JsonObject resultObject = jsonObject2.getAsJsonObject("result");

        // 将 result 转换为 JSON 字符串
        String resultNewsString = resultObject.toString();

//        System.out.println(resultNewsString);
        StringBuffer sb = new StringBuffer();
        sb.append(resultNewsString);
        sb.append("根据以上资讯，请输出对").append(stock).append("公司股价有利的利好资讯。");
        sb.append(PROSPECTGOOD_PROMPT);
        String prompt = sb.toString();
        JSONObject json = new JSONObject();
        json.putOnce("model", "qwen2.5:14b");
        json.putOnce("stream", false);
        json.putOnce("prompt", prompt);
        String command = json.toString();

        // 创建请求体
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(command, JSON);
        log.info("command: " + command);
        // 构建请求
        Request request2 = new Request.Builder()
                .url("http://localhost:1230/ollama/api/generate")
                .addHeader("Authorization", "Bearer sk-5e3f9ba55c6340679d1998e1375b8190")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        String reqResult = null;
        // 发送请求并处理响应
        try (Response response = client.newCall(request2).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            // 打印响应结果
            reqResult = response.body().string();
//            log.info("reqResult: " + reqResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析response字段
        JSONObject jo = new JSONObject(reqResult);
        String response = jo.getStr("response");
        log.info("response: " + response);
        Gson gson2 = new Gson();
        Type type = new TypeToken<NewsResult>() {}.getType();
        NewsResult newsResult = gson2.fromJson(response, type);
        return newsResult;
    }

    public NewsResult negativeNews(String stock) {
        // 创建JSON对象
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", "");
        jsonObject.addProperty("keyword", stock);
        JsonArray typeArray = new JsonArray();
        typeArray.add("cmsArticleWebOld");
        jsonObject.add("type", typeArray); // 直接添加JsonArray

        jsonObject.addProperty("client", "web");
        jsonObject.addProperty("clientType", "web");
        jsonObject.addProperty("clientVersion", "curr");

        JsonObject paramObject = new JsonObject();
        JsonObject nestedParamObject = new JsonObject();
        nestedParamObject.addProperty("searchScope", "default");
        nestedParamObject.addProperty("sort", "default");
        nestedParamObject.addProperty("pageIndex", 1);
        nestedParamObject.addProperty("pageSize", 20);
        nestedParamObject.addProperty("preTag", "");
        nestedParamObject.addProperty("postTag", "");
        paramObject.add("cmsArticleWebOld", nestedParamObject);
        jsonObject.add("param", paramObject);

        // 将JSON对象转换为字符串
        String jsonString = gson.toJson(jsonObject);

        // URL 编码 JSON 字符串
//        String encodedJsonString = URLEncoder.encode(jsonString, StandardCharsets.UTF_8);

        // 构建完整的URL
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("search-api-web.eastmoney.com")
                .addPathSegment("search")
                .addPathSegment("jsonp")
                .addQueryParameter("cb", "apisearch")
                .addQueryParameter("param", jsonString)
                .addQueryParameter("_", "1733658609092") // 时间戳
                .build();
//        System.out.println(url.toString());
//        String jsonString = "{\"uid\":\"\",\"keyword\":\"微软\",\"type\":[\"cmsArticleWebOld\"],\"client\":\"web\",\"clientType\":\"web\",\"clientVersion\":\"curr\",\"param\":{\"cmsArticleWebOld\":{\"searchScope\":\"default\",\"sort\":\"default\",\"pageIndex\":1,\"pageSize\":10,\"preTag\":\"<em>\",\"postTag\":\"</em>\"}}}";
//
//        // Convert JSON string to URL encoded string
//        String urlEncodedString = URLEncoder.encode(jsonString, StandardCharsets.UTF_8.toString());
//
//        String url = "https://search-api-web.eastmoney.com/search/jsonp?cb=apisearch&param=" + urlEncodedString + "&_=1733658609092";

        Request request = new Request.Builder()
                .url(url)
                .build();
        String responseBody = "";
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            responseBody = response.body().string();
//            System.out.println(responseBody);
            log.info(responseBody);
        }catch (Exception e){
            e.printStackTrace();
        }
        int startIndex = responseBody.indexOf("(") + 1;
        int endIndex = responseBody.lastIndexOf(")");
        String jsonPart = responseBody.substring(startIndex, endIndex);

        // 解析 JSON 字符串
        JsonObject jsonObject2 = JsonParser.parseString(jsonPart).getAsJsonObject();

        // 获取 result 部分
        JsonObject resultObject = jsonObject2.getAsJsonObject("result");

        // 将 result 转换为 JSON 字符串
        String resultNewsString = resultObject.toString();

//        System.out.println(resultNewsString);
        StringBuffer sb = new StringBuffer();
        sb.append(resultNewsString);
        sb.append("根据以上资讯，请输出对").append(stock).append("公司股价不利的利空资讯。");
        sb.append(PROSPECTBAD_PROMPT);
        String prompt = sb.toString();
        JSONObject json = new JSONObject();
        json.putOnce("model", "qwen2.5:14b");
        json.putOnce("stream", false);
        json.putOnce("prompt", prompt);
        String command = json.toString();

        // 创建请求体
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(command, JSON);
        log.info("command: " + command);
        // 构建请求
        Request request2 = new Request.Builder()
                .url("http://localhost:1230/ollama/api/generate")
                .addHeader("Authorization", "Bearer sk-5e3f9ba55c6340679d1998e1375b8190")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        String reqResult = null;
        // 发送请求并处理响应
        try (Response response = client.newCall(request2).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            // 打印响应结果
            reqResult = response.body().string();
//            log.info("reqResult: " + reqResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析response字段
        JSONObject jo = new JSONObject(reqResult);
        String response = jo.getStr("response");
        log.info("response: " + response);
        Gson gson2 = new Gson();
        Type type = new TypeToken<NewsResult>() {}.getType();
        NewsResult newsResult = gson2.fromJson(response, type);
        return newsResult;
    }
}
