package com.zjj.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

// 格式化Date 输出String
public class FormatDate {
    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
