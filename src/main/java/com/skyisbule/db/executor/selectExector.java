package com.skyisbule.db.executor;

import com.skyisbule.db.config.dataType;
import com.skyisbule.db.config.selectType;
import com.skyisbule.db.config.tableDefinition;
import com.skyisbule.db.io.OioFileOperater;
import com.skyisbule.db.result.Result;
import com.skyisbule.db.struct.Column;
import com.skyisbule.db.struct.Condition;
import com.skyisbule.db.struct.RecordStruct;
import com.skyisbule.db.util.ByteUtil;
import com.sun.org.apache.regexp.internal.RE;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by skyisbule on 2018/4/28.
 * 查询执行器
 */
public class selectExector {

    private static final int READ_COLUMN_NUM = 8;

    public LinkedList<Column> doSelect(Condition condition) throws IOException {
        selectType type        = condition.getSelectType();
        LinkedList<Column> res = new LinkedList<>();
        switch (type){
            case ALL   :
                res = selectAll(condition);
                break;
            case single:break;
            case many  :break;
            default:
        }
        return res;
    }

    private LinkedList<Column> selectAll(Condition condition) throws IOException {
        String tableName = condition.getTableName();
        RecordStruct struct = tableDefinition.getRecordStruct(tableName);
        int columnNum = struct.getColumnNum();
        int recordLen = struct.getRecordByteLenth();
        int singleReadLen = (columnNum+1)*4 + recordLen;
        OioFileOperater operater = new OioFileOperater(tableName);
        int totalRecordNum = operater.getLen()/singleReadLen;

        LinkedList<Column> result = new LinkedList<Column>();
        int couldRead = totalRecordNum;
        int blocknum  = 0;
        while(true){
            if (couldRead > READ_COLUMN_NUM){
                byte[] data;
                data = operater.read(tableName,blocknum*singleReadLen*READ_COLUMN_NUM,READ_COLUMN_NUM * singleReadLen);
                blocknum++;
                couldRead -= READ_COLUMN_NUM;
                byteToColumn(tableName,result,data,READ_COLUMN_NUM,columnNum,singleReadLen);
            }else{
                byte[] res;
                res = operater.read(tableName,blocknum*singleReadLen*READ_COLUMN_NUM,couldRead * singleReadLen);
                byteToColumn(tableName,result,res,READ_COLUMN_NUM,columnNum,singleReadLen);
            }
        }

        return result;

    }

    private void byteToColumn(String table,LinkedList<Column> res,byte[] data,int delNum,int columnNum,int singleLen){
        for (int i = 0 ;i < delNum ; i++){
            byte[] column = new byte[singleLen];
            //拿到一条数据的byte[]
            System.arraycopy(data,i*singleLen,column,0,singleLen);
            Column columnTemp = new Column();
            for (int j = 0 ;j < columnNum ; j++){
                dataType type = tableDefinition.getDataType(table,j);
                switch (type){
                    case INT :
                        byte[] intTemp = new byte[4];
                        int pos        = tableDefinition.getColumnBeginPos(table,j);
                        System.arraycopy(column,pos,intTemp,0,4);
                        Integer columnRes = ByteUtil.bytes2int(intTemp);
                        columnTemp.setValue(columnRes);
                        columnTemp.setColumnName(tableDefinition.getColumnName(table,j));
                        res.add(columnTemp);
                        break;
                    case CHAR :
                        byte[] getRealLen   = new byte[4];
                        System.arraycopy(column,4*(j+1),getRealLen,0,4);
                        int realLen = ByteUtil.bytes2int(getRealLen);
                        byte[] charTemp = new byte[realLen];
                        int charPos     = tableDefinition.getColumnBeginPos(table,j);
                        System.arraycopy(column,charPos,charTemp,0,realLen);
                        String charRes  = new String(charTemp);
                        columnTemp.setValue(charRes);
                        columnTemp.setColumnName(tableDefinition.getColumnName(table,j));
                        res.add(columnTemp);
                        break;
                }
            }
        }
    }

}
