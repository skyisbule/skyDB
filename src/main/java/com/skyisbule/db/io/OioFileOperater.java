package com.skyisbule.db.io;

import com.skyisbule.db.config.Environment;

import java.io.*;

/**
 * Created by skyisbule on 2018/4/25.
 * Oio的文件操作
 * 用于小文件
 */
public class OioFileOperater implements FileOperate {

    private String filePath = Environment.ROOT_DIRECTORY;

    public byte[] read(String fileName, int offset, int limit) {
        byte[] data = new byte[limit - offset];
        try{
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath+File.pathSeparator+fileName, "r");
            randomAccessFile.seek(offset);//将文件流的位置移动到pos字节处
            randomAccessFile.read(data);
            return data;
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }

    }

    public boolean write(String fileName, int offset, int limit, byte[] data) {
        try
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath+File.pathSeparator+fileName, "rw");
            randomAccessFile.seek(offset);//将文件流的位置移动到pos字节处
            randomAccessFile.write(data);
            randomAccessFile.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
