package com.skyisbule.db.executor;

import com.skyisbule.db.config.dataType;
import com.skyisbule.db.util.ByteUtil;
import com.skyisbule.db.struct.Column;
import com.skyisbule.db.struct.RecordStruct;


/**
 * Created by skyisbule on 2018/4/26.
 * 插入执行器
 */
public class InsertExecutor {

    /**
     * 插入构造器，构造记录单元，递交给调度器，由调度器执行操作。
     * @param recordStruct 记录单元。
     * @return
     */
    public boolean doInsert(RecordStruct recordStruct){
        //定义写入数据 三大块 时间戳、记录位置、记录内容
        byte[] data   = new byte[recordStruct.getRecordByteLenth() + recordStruct.getColumnNum()*4 + 4];
        byte[] header = this.headers(recordStruct);
        byte[] body   = this.body(recordStruct);
        //得到数据
        System.arraycopy(header,0,data,0,header.length);
        System.arraycopy(body,0,data,header.length,body.length);

        return true;
    }

    /**
     * 构造信息头：
     * 1.获取每个记录单元的长度，转为4byte int
     * 2.逐个写入header
     * @param r
     * @return
     */
    private byte[] headers(RecordStruct r){
        //构造记录头
        byte[] header = new byte[r.getColumnNum()*4 + 4];
        byte[] temp;
        //写入时间戳
        int time = (int)System.currentTimeMillis();
        System.arraycopy(ByteUtil.int2bytes(time),0,header,0,4);
        int i = 1;
        for (Column column : r.getData()){
            temp = ByteUtil.int2bytes(column.getNowLen());
            System.arraycopy(temp,0,header,i*4,4);
            i++;
        }
        return header;
    }

    /**
     * 构造记录体，将定长字段逐个转为byte[]  空白的部分补零。
     * @param r
     * @return
     */
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
