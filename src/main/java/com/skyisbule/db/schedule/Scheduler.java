package com.skyisbule.db.schedule;

/**
 * Created by skyisbule on 2018/4/26.
 * 核心调度器
 */
public class Scheduler {

    private Scheduler(){

    }

    private static Scheduler s = new Scheduler();

    public Scheduler getInstance(){
        return s;
    }



}
