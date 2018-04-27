package com.skyisbule.db.executor;

import com.skyisbule.db.config.dataType;
import com.skyisbule.db.util.ByteUtil;
import com.skyisbule.db.struct.Column;
import com.skyisbule.db.struct.RecordStruct;

import java.util.ArrayList;

/**
 * Created by skyisbule on 2018/4/26.
 * 插入执行器
 */
public class InsertExecutor {

    public boolean doInsert(RecordStruct recordStruct){
        //定义写入数据
        byte[] data   = new byte[recordStruct.getRecordByteLenth() + recordStruct.getColumnNum()*4];
        byte[] header = this.headers(recordStruct);
        byte[] body   = this.body(recordStruct);
        //得到数据
        System.arraycopy(header,0,data,0,header.length);
        System.arraycopy(body,0,data,header.length,body.length);

        for (byte b : header){
            System.out.print(b);
        }
        System.out.println();
        for (byte b : body){
            System.out.print(b);
        }
        System.out.println();
        for (byte b : data){
            System.out.print(b);
        }

        return true;
    }

    private byte[] headers(RecordStruct r){
        //构造记录头
        byte[] header = new byte[r.getColumnNum()*4];
        byte[] temp;
        int i = 0;
        for (Column column : r.getData()){
            temp = ByteUtil.int2bytes(column.getNowLen());
            System.arraycopy(temp,0,header,i*4,4);
            i++;
        }
        return header;
    }

    private byte[] body(RecordStruct r){
        byte[] data = new byte[r.getRecordByteLenth()];
        int destPos = 0;
        for (Column column : r.getData()){
            byte[] full = new byte[column.getMaxLen()];
            byte[] temp = this.ColumnValue2Byte(column);
            System.arraycopy(temp,0,full,0,temp.length);
            System.arraycopy(full,0,data,destPos,column.getMaxLen());
            destPos += column.getMaxLen();
        }
        return data;
    }

    private byte[] ColumnValue2Byte(Column column){
        dataType type = column.getType();
        byte[] res;
        switch (type){
            case INT  : res = ByteUtil.int2bytes((Integer)column.getValue());break;
            case CHAR : res = column.getValue().toString().getBytes();break;
            default   : res = new byte[0];
        }
        return res;
    }


}
