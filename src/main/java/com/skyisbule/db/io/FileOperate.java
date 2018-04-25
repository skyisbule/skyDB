package com.skyisbule.db.io;

/**
 * Created by skyisbule on 2018/4/25.
 * 文件操作接口
 */
public interface FileOperate {

    public byte[] read(String fileName,int offset,int limit);

    public boolean write(String fileName,int offset,int limit,byte[] data);

}
