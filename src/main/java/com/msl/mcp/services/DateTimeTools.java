package com.msl.mcp.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DateTimeTools {


    //returnDirect = false 返回给大模型  returnDirect = true 直接返回给用户
    //resultConverter 自定义返回结果
    @Tool(description = "获取当前时区的日期及时间",returnDirect = false)
    public String getCurrentDateTime() {
        String string = LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
        System.out.println("当前时间 " + string);
        return string;
    }


    @Tool(description = "以当前格式ISO-8601根据参数设置闹钟")
    void setAlarm(String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("闹钟时间 " + alarmTime);
    }

}
