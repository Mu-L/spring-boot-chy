package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LuckyBusUser {

//    @ExcelProperty(value= "userId")
    @ExcelProperty(value= "user_id")
    private Integer userId;

    @ExcelProperty(value= "username")
    private String username;

//    @ExcelProperty(value= "addAmount")
    @ExcelProperty(value= "加钱1%")
    private String addAmount;

}