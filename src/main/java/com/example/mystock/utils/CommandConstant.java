package com.example.mystock.utils;

public class CommandConstant {
    public static final String PROMPT_LAST = "请根据以上资讯输出对股价的预测。" +
            "输出形如：{\"short_term_predict\": \"\", \"reason_short\" : \"\", \"long_term_predict\": \"\", \"reason_long\":\"\" ,  \"risks_comment\": \"\"}";

    public static final String PROSPECTGOOD_PROMPT =
            "输出形如：{\"result\":[{\"url\" : \"\", \"title\" : \"\", \"date\" : \"\", \"content\": \"\", \"reason\": \"\"}], \"reason\": \"\"}。"+
            "无需任何markdown格式，仅输出json字符串。";

    public static final String PROSPECTBAD_PROMPT =
            "输出形如：{\"result\":[{\"url\" : \"\", \"title\" : \"\", \"date\" : \"\", \"content\": \"\", \"reason\": \"\"}], \"reason\": \"\"}。"+
                    "无需任何markdown格式，仅输出json字符串。";
}
