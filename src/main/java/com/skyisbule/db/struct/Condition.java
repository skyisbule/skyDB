package com.skyisbule.db.struct;

import com.skyisbule.db.config.conditionType;
import com.skyisbule.db.config.selectType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by skyisbule on 2018/5/1.
 * 查询条件定义
 */
@Getter
@Setter
public class Condition {

    /**
     * 查询条件
     */
    class filter{
        String        key;
        conditionType conditionType;
        String        value;
    }

    String            tableName;
    ArrayList<filter> condition;
    selectType        selectType;
    ArrayList<String> columnName;

}
