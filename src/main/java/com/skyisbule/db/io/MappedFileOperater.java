package com.skyisbule.db.io;

import com.skyisbule.db.config.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by skyisbule on 2018/4/25.
 * 使用mappedbytebuffer实现文件操作
 * 用于大文件
 */
public class MappedFileOperater implements FileOperate {

    private String filePath = Environment.ROOT_DIRECTORY;
    private File file;
    private byte[] data ;


    public byte[] read(String fileName,int offset,int limit){
        try {
            file = new File(filePath+"/"+fileName);
            data = new byte[limit - offset];
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, offset,limit);
            for (; offset < limit; offset++) {
                byte b = mappedByteBuffer.get();
                System.out.println("read:"+(int)b);
                data[offset] = b;
            }
            return data;
        }catch(IOException e){
                System.out.println(e.getStackTrace());
            }
        return new byte[0];
    }


    public boolean write(String fileName,int offset,int limit,byte[] data){
        file = new File(filePath+"/"+fileName);
        data = new byte[limit - offset];
        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "rw")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_WRITE, offset,limit);
            mappedByteBuffer.put(data,offset,limit-offset);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
