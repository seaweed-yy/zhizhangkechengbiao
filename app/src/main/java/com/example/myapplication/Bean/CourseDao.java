package com.example.myapplication.Bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CourseDao {
    //SQLiteDatabase对象封装了所有SQLite的增删改查语句的操作方法，让开发者直接调用就行
    private SQLiteDatabase sqLiteDatabase;

    public CourseDao(Context context) {
        //初始化刚刚写的userSQLiteHelper对象
        MyDataBaseSQLiteHelper myDataBaseSQLiteHelper = new MyDataBaseSQLiteHelper(context);
        //获取sqLiteDatabase对象
        sqLiteDatabase = myDataBaseSQLiteHelper.getWritableDatabase();
    }

    /**
     * 插入一条记录进入user表
     * insert into user("username","password") values("zhangsan","123456");
     */
    public boolean insertCourse(int rownum,int columnnum,String coursename) {
        ContentValues values = new ContentValues();
        values.put("rownum", rownum);
        values.put("columnnum", columnnum);
        values.put("coursename", coursename);

        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("course", null, values);
        return id > 0 ? true : false;
    }

    /**
     * 查询记录
     * select * from user where username = "zhangsan";
     */
    public String queryCourse(int rownum,int columnnum) {

        Cursor cursor = sqLiteDatabase.query("course", new String[]{"coursename"}, "rownum=? and columnnum=?", new String[]{String.valueOf(rownum),String.valueOf(columnnum)}, null, null, null);
        while (cursor.moveToNext()) {
            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            return cursor.getString(cursor.getColumnIndex("coursename"));
        }
        return "ERROR";
    }

    /**
     * 修改数据库表记录
     * update user set password = 123123 where username = zhangsan
     */
    public boolean updateCourse(int rownum, int columnnum,String coursename) {
        boolean flag;
        ContentValues values = new ContentValues();
        values.put("coursename", coursename);

        long id = sqLiteDatabase.update("course", values, "rownum = ? and columnnum = ? ", new String[]{String.valueOf(rownum),String.valueOf(columnnum)});
        flag = id > 0 ? true : false;
        return flag;
    }

    public boolean deleteCourse(int rownum, int columnnum) {
        long id = sqLiteDatabase.delete("course", "rownum = ? and columnnum = ? ", new String[]{String.valueOf(rownum),String.valueOf(columnnum)});
        return id > 0 ? true : false;
    }

}

