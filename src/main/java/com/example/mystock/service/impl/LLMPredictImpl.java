package com.example.mystock.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mystock.domain.dto.Predict;
import com.example.mystock.entity.NewsData;
import com.example.mystock.mapper.NewsDataMapper;
import com.example.mystock.service.ILLMPredictService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.mystock.utils.CommandConstant.PROMPT_LAST;

@Slf4j
@Service
public class LLMPredictImpl extends ServiceImpl<NewsDataMapper, NewsData> implements ILLMPredictService {
    @Resource
    private OkHttpClient client;
    @Override
    public Predict predict(String stock) {
        //如果在news表中查询不到本股票资讯，则返回空
        List<NewsData> result = this.lambdaQuery().eq(NewsData::getStock, stock).list();
        if (result.isEmpty()) {
            return null;
        }
        //找出近七天的所有资讯
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<NewsData> recentNews = this.lambdaQuery()
                .eq(NewsData::getStock, stock)
//                .gt(NewsData::getNewstime, oneWeekAgo)
                .list();
        StringBuffer newsText = new StringBuffer();
        for (NewsData news : recentNews) {
            newsText.append(news.getStock());
            newsText.append(news.getNewstime()).append("资讯:");
            newsText.append(news.getTitle()).append("\n");
        }
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
        JSONObject jsonObject = new JSONObject(reqResult);
        String response = jsonObject.getStr("response");
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
