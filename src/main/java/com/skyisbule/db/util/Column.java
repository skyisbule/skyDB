package com.skyisbule.db.util;

import com.skyisbule.db.config.dataType;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by skyisbule on 2018/4/26.
 * 定义列的结构
 */
@Setter
@Getter
public class Column {

    String   columnName;
    Object   value;
    Integer  maxLen;
    dataType type;

}
