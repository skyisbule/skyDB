package com.skyisbule.db.util;

import java.util.List;

/**
 * Created by skyisbule on 2018/4/26.
 * 定义一种表结构
 */
public class TableStruct {

    //记录的长度
    int recordLenth;
    //记录的个数
    int recordNum;
    //存储各列的值
    List<Column> data;

    TableStruct(int recordLenth,int recordNum,List<Column> data){
        this.recordLenth = recordLenth;
        this.recordNum   = recordNum;
        this.data        = data;
    }



}
