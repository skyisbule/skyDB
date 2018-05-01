package com.skyisbule.db.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


/**
 * Created by skyisbule on 2018/4/26.
 * 定义一种表结构
 */
@Getter
@Setter
public class RecordStruct {

    //表名字
    String tableName;
    //记录的长度
    int recordByteLenth;
    //记录的个数
    int columnNum;
    //存储各列的值
    ArrayList<Column> data;

    public RecordStruct(int recordByteLenth, int columnNum, ArrayList<Column> data){
        this.recordByteLenth = recordByteLenth;
        this.columnNum       = columnNum;
        this.data            = data;
    }

    public RecordStruct(int recordByteLenth, int columnNum){
        this.recordByteLenth = recordByteLenth;
        this.columnNum       = columnNum;
    }


}
