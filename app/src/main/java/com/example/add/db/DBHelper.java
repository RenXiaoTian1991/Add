package com.example.add.db;

import android.content.Context;

import com.example.add.utils.AppUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by myself on 15/8/14.
 */
public class DBHelper implements DbUtils.DbUpgradeListener {

    private static DBHelper dbHelper;
    private String bdName = "dbtest";
    private int VERSION;
    private DbUtils mDBClient;
    @Override
    public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {

    }

    private DBHelper(Context context){
        VERSION = AppUtils.getVersionCode(context);
        mDBClient = DbUtils.create(context, bdName, VERSION, this);
        mDBClient.configAllowTransaction(true);
        mDBClient.configDebug(true);
    }

    public static DBHelper getInstance(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public synchronized boolean insert(Object entity){
        try {
            mDBClient.save(entity);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean insertAll(List<?> list){
        try {
            mDBClient.saveAll(list);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean deleteData(Object object){
        try {
            mDBClient.delete(object);
        } catch (DbException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    public synchronized boolean deleteOne(Class<?> cla,String colum,String value){
        try {
            mDBClient.delete(cla, WhereBuilder.b(colum, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean deleteAll(Class<?> cla){
        try {
            mDBClient.deleteAll(cla);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public synchronized  Object selectOne(Class<?> cla,int id){
        Object obj;
        try {
            obj = mDBClient.findFirst(Selector.from(cla).where(WhereBuilder.b("id", "=", id)));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    public synchronized List<?> selectAll(Class<?> cla){
        List<?> list= null;
        try {
            list = mDBClient.findAll(Selector.from(cla));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public synchronized <T> List<T> search(Class<T> entity) {
        try {
            return mDBClient.findAll(Selector.from(entity));
        } catch (Exception e) {
            if (e != null)
                e.printStackTrace();
        }
        return null;
    }
}
