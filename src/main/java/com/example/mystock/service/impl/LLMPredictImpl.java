package com.example.mystock.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mystock.domain.dto.Predict;
import com.example.mystock.entity.NewsData;
import com.example.mystock.mapper.NewsDataMapper;
import com.example.mystock.service.ILLMPredictService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.mystock.utils.CommandConstant.PROMPT_LAST;

@Slf4j
@Service
public class LLMPredictImpl extends ServiceImpl<NewsDataMapper, NewsData> implements ILLMPredictService {

    private OkHttpClient client;

    public LLMPredictImpl() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时时间
                .readTimeout(60, TimeUnit.SECONDS)    // 读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)    // 写入超时时间
                .build();
    }
    @Override
    public Predict predict(String stock) {
//        //如果在news表中查询不到本股票资讯，则返回空
//        List<NewsData> result = this.lambdaQuery().eq(NewsData::getStock, stock).list();
//        if (result.isEmpty()) {
//            return null;
//        }
//        //找出近七天的所有资讯
//        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
//        List<NewsData> recentNews = this.lambdaQuery()
//                .eq(NewsData::getStock, stock)
////                .gt(NewsData::getNewstime, oneWeekAgo)
//                .list();
//        StringBuffer newsText = new StringBuffer();
//        for (NewsData news : recentNews) {
//            newsText.append(news.getStock());
//            newsText.append(news.getNewstime()).append("资讯:");
//            newsText.append(news.getTitle()).append("\n");
//        }

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
        nestedParamObject.addProperty("pageSize", 15);
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
        System.out.println(url.toString());
//        String jsonString = "{\"uid\":\"\",\"keyword\":\"微软\",\"type\":[\"cmsArticleWebOld\"],\"client\":\"web\",\"clientType\":\"web\",\"clientVersion\":\"curr\",\"param\":{\"cmsArticleWebOld\":{\"searchScope\":\"default\",\"sort\":\"default\",\"pageIndex\":1,\"pageSize\":10,\"preTag\":\"<em>\",\"postTag\":\"</em>\"}}}";
//
//        // Convert JSON string to URL encoded string
//        String urlEncodedString = URLEncoder.encode(jsonString, StandardCharsets.UTF_8.toString());
//
//        String url = "https://search-api-web.eastmoney.com/search/jsonp?cb=apisearch&param=" + urlEncodedString + "&_=1733658609092";

        Request request1 = new Request.Builder()
                .url(url)
                .build();
        String responseBody = "";
        try (Response response = client.newCall(request1).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            responseBody = response.body().string();
            System.out.println(responseBody);
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

        StringBuffer newsText = new StringBuffer(resultNewsString);

        newsText.append(PROMPT_LAST);
        //构造command
        String prompt = newsText.toString();
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
        Request request = new Request.Builder()
                .url("http://localhost:1230/ollama/api/generate")
                .addHeader("Authorization", "Bearer sk-5e3f9ba55c6340679d1998e1375b8190")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        String reqResult = null;
        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            // 打印响应结果
            reqResult = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析response字段
        JSONObject jsonObject1 = new JSONObject(reqResult);
        String response = jsonObject1.getStr("response");
        log.info("response: " + response);
        //填入predict中并返回
        Predict predict = new Predict();
        JSONObject pridctJson = new JSONObject(response);
        predict.setShort_term_predict(pridctJson.getStr("short_term_predict"));
        predict.setReason_short(pridctJson.getStr("reason_short"));
        predict.setLong_term_predict(pridctJson.getStr("long_term_predict"));
        predict.setReason_long(pridctJson.getStr("reason_long"));
        predict.setRisks_comment(pridctJson.getStr("risks_comment"));
        return predict;
    }
}
