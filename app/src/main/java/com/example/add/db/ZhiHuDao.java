package com.example.add.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.add.bean.ZhiHuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 15/9/10.
 */
public class ZhiHuDao {
    private ZhiHuHelper dbHelper;
    public ZhiHuDao(Context context){
        dbHelper = new ZhiHuHelper(context);
    }
    public void add(ZhiHuBean.Stories newsItem)
    {
        String sql = "insert into "+ZhiHuHelper.TABLE_NAME+" (image,title,time) values(?,?,?) ;";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[] {newsItem.getImages()==null?"abc":newsItem.getImages()[0], newsItem.getTitle(),newsItem.getTime()});
        db.close();
    }

//    public void insertCache(Cache cache) {
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.execSQL("insert into "
//                        + TABLE_NAME
//                        + " (request, response, time) values(?,?,?)",
//                new Object[]{cache.getRequest(), cache.getResponse(), cache.getTime()});
//        db.close();
//    }
//    public void deleteAll(int newsType)
//    {
//        String sql = "delete from tb_newsItem where newstype = ?";
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL(sql, new Object[] { newsType });
//        db.close();
//    }

    public void add(List<ZhiHuBean.Stories> newsItems)
    {
        for (ZhiHuBean.Stories newsItem : newsItems)
        {
            add(newsItem);
        }
    }

    public List<ZhiHuBean.Stories> listAll()
    {

        // 0 -9 , 10 - 19 ,
        List<ZhiHuBean.Stories> newsItems = new ArrayList<ZhiHuBean.Stories>();
        try
        {
            String sql = "select * from  "+ZhiHuHelper.TABLE_NAME;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(sql,null);

            ZhiHuBean.Stories newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new ZhiHuBean().new Stories();

                String image = c.getString(c.getColumnIndex("image"));
                String title = c.getString(c.getColumnIndex("title"));
                String time = c.getString(c.getColumnIndex("time"));

                newsItem.setImages(new String[]{image});
                newsItem.setTitle(title);
                newsItem.setTime(time);

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;

    }

    public ZhiHuBean.Stories selectOneByTitle(ZhiHuBean.Stories stor)
    {

        // 0 -9 , 10 - 19 ,
        ZhiHuBean.Stories newsItems = null;

            String sql = "select * from  "+ZhiHuHelper.TABLE_NAME +" where title = ?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(sql,new String[]{stor.getTitle()});
            while (c.moveToNext())
            {
                newsItems = new ZhiHuBean().new Stories();


                String image = c.getString(c.getColumnIndex("image"));
                String title = c.getString(c.getColumnIndex("title"));
                String time = c.getString(c.getColumnIndex("time"));

                newsItems.setImages(new String[]{image});
                newsItems.setTitle(title);
                newsItems.setTime(time);

            }

        return newsItems;

    }

    public List<ZhiHuBean.Stories> list(int currentPage)
    {

        // 0 -9 , 10 - 19 ,
        List<ZhiHuBean.Stories> newsItems = new ArrayList<ZhiHuBean.Stories>();
        try
        {
            int offset = 10 * (currentPage - 1);
            String sql = "select * from  "+ZhiHuHelper.TABLE_NAME+" where limit ?,? ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(sql, new String[] { offset + "", "" + (offset + 10) });

            ZhiHuBean.Stories newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new ZhiHuBean().new Stories();


                String image = c.getString(0);
                String title = c.getString(1);
                String time = c.getString(2);

                newsItem.setImages(new String[]{image});
                newsItem.setTitle(title);
                newsItem.setTime(time);

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;

    }
}
