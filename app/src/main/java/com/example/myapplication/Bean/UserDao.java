package com.example.myapplication.Bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDao {
    //SQLiteDatabase对象封装了所有SQLite的增删改查语句的操作方法，让开发者直接调用就行
    private SQLiteDatabase sqLiteDatabase;

    public UserDao(Context context) {
        //初始化刚刚写的userSQLiteHelper对象
        MyDataBaseSQLiteHelper myDataBaseSQLiteHelper = new MyDataBaseSQLiteHelper(context);
        //获取sqLiteDatabase对象
        sqLiteDatabase = myDataBaseSQLiteHelper.getWritableDatabase();
    }

    /**
     * 插入一条记录进入user表
     * insert into user("username","password") values("zhangsan","123456");
     */
    public boolean insertUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("user", null, values);
        return id > 0 ? true : false;
    }

    /**
     * 查询记录
     * select * from user where username = "zhangsan";
     */
    public UserBean querryUser(String username) {

        Cursor cursor = sqLiteDatabase.query("user", new String[]{"username","password"}, "username=?", new String[]{username}, null, null, null);

        UserBean userBean = new UserBean();
        while (cursor.moveToNext()) {
            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            Log.e("tag", userBean.getId() + "|" + userBean.getUsername() + "|" + userBean.getPassword());
        }
        return userBean;
    }

    public UserBean querryUser(String username, String password) {

        Cursor cursor = sqLiteDatabase.query("user", new String[]{"username", "password"}, "username=? and password=?", new String[]{username, password}, null, null, null);

        UserBean userBean = new UserBean();
        while (cursor.moveToNext()) {
            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            Log.e("tag", userBean.getId() + "|" + userBean.getUsername() + "|" + userBean.getPassword());
        }
        return userBean;
    }
}
