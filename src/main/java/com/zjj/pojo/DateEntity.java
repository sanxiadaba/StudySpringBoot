package com.zjj.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DateEntity {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")   // 后端与前端映射的时间格式
    // 注意，这里只是代表前端的字符的时间可以映射到Date 并且当返回给前端时也会是这样的格式，在其它情况下里面存储的Date仍然是原格式（废话）
    // 额外使用的话需要再转换
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // String date = sdf.format(dateEntity.getDate());
    private Date date;
}
