package com.skyisbule.db.config;

import cn.hutool.core.io.file.FileReader;
import com.skyisbule.db.struct.RecordStruct;
import com.skyisbule.db.struct.TableStruct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by skyisbule on 2018/5/1.
 * 数据表定义
 */
public class tableDefinition {

    // table => 列名   table => 数据长度 table => 基础信息

    private static HashMap<String,ArrayList<String>>  table2Name    = new HashMap<>();
    private static HashMap<String,ArrayList<Integer>> table2DataLen = new HashMap<>();
    private static HashMap<String,TableStruct>        table2Info    = new HashMap<>();

    public void init(){
        String path = Environment.ROOT_DIRECTORY;
        File   dir  = new File(path);
        String[] fileList = dir.list();
        if (fileList!=null){
            for (String file : fileList){

                if (file.endsWith("stable")){
                    FileReader reader   = new FileReader(path+File.separator+file);
                    String[] contents   = reader.readString().split("&");
                    String   tableNames = contents[0];//第一行存列名
                    String   tableLen   = contents[1];//第二行存每列单位比特位数
                    String   tableInfo  = contents[2];//第三行存改表的基础信息
                    //处理文件名
                    ArrayList<String> list = new ArrayList<>();
                    for (String name : tableNames.split(";")){
                        list.add(name);
                    }
                    table2Name.put(file.split(".")[0],list);
                    //处理长度
                    ArrayList<Integer> tableLens = new ArrayList<>();
                    for (String len : tableLen.split(":")){
                        tableLens.add(Integer.parseInt(len));
                    }
                    table2DataLen.put(file.split(".")[0],tableLens);
                    //处理基础信息
                }

            }
        }
    }

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
