package com.skyisbule.db.config;

import com.skyisbule.db.struct.RecordStruct;

/**
 * Created by skyisbule on 2018/5/1.
 * 数据表定义
 */
public class tableDefinition {

    public static RecordStruct getRecordStruct(String table){
        //id age  长度均为4
        return new RecordStruct(8,2);
    }

    public static dataType getDataType(String table,int ColumnNum){
        return dataType.INT;
    }

    public static int getColumnBeginPos(String table,int ColumnNum){
        return 1;
    }

    public static String getColumnName(String table,int columnNum){
        return "test";
    }

}
