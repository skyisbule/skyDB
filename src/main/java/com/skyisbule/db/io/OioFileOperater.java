package com.skyisbule.db.io;

import com.skyisbule.db.config.Environment;

import java.io.*;

/**
 * Created by skyisbule on 2018/4/25.
 * Oio的文件操作
 * 用于小文件
 */
public class OioFileOperater implements FileOperate{

    private String filePath = Environment.ROOT_DIRECTORY;
    private RandomAccessFile file;

    public OioFileOperater(String fileName){
        try {
            file = new RandomAccessFile(filePath+File.pathSeparator+fileName, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] read(String fileName, int offset, int len) {
        byte[] data = new byte[len];
        try{
            file.seek(offset);
            file.read(data);
            return data;
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }

    }

    public boolean write(String fileName, int offset, int limit, byte[] data) {
        try
        {
            file.seek(offset);
            file.write(data);
            file.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public int getLen() throws IOException {
        return (int)this.file.length();
    }

    private void close() throws IOException {
        this.file.close();
    }
}
