package com.example.socialsoftware;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Created by littlecurl 2018/6/24
 */

class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 声明一个数据库变量db
     * 这个数据库即SQL Lite
     * 是Android SDK中自带的数据库
     */
    private SQLiteDatabase db;

    /**
     * 构造方法，参数为上下文context，上下文就是这个类所在包的路径
     * 调用父类构造器，super(context,"db_test",null,1); 指明上下文，数据库名，工厂默认空值，版本号默认从1开始
     * db = getReadableDatabase(); 与之相似的还有一句getWritableDatabase(),可以Ctrl+单击getReadableDatabase()
     */
    public DBOpenHelper(Context context){
        super(context,"db_test",null,1);
        db = getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +//如果user不存在的存在的话
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "code TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS char(" +//如果user不存在的存在的话
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "code TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mine(" +//如果user不存在的存在的话
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "age TEXT," +
                "fname TEXT," +
                "lname TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    /**
     * 自定义的增删改查方法
     * 这些方法，
     * add()
     * delete()
     * update()
     * getAllData()
     */
    public void addName(String name){
        db.execSQL("INSERT INTO user (name) VALUES(?)",new Object[]{name});
    }
    public void addPassword(String password){
        db.execSQL("INSERT INTO user (password) VALUES(?)",new Object[]{password});
    }
    public void addPhonecode(String phonecode){
        db.execSQL("INSERT INTO user (phonecode) VALUES(?)",new Object[]{phonecode});
    }
    public void delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    public void submit(String name, String password,String code) {
        db.execSQL("INSERT INTO user (name,password,code) VALUES(?,?,?)", new Object[]{name, password,code});
    }

    public void msubmit(String age, String fname,String lname) {
        db.execSQL("INSERT INTO mine (age,fname,lname) VALUES(?,?,?)", new Object[]{age, fname,lname});
    }


    public User selectUserByName(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            String nameInDb = cursor.getString(cursor.getColumnIndex("name"));
            String passwordInDb = cursor.getString(cursor.getColumnIndex("password"));
            String codeInDb = cursor.getString(cursor.getColumnIndex("code"));

            return new User(nameInDb, passwordInDb, codeInDb);
        }
        return null;
    }

//获取User数组
//定义了一个ArrayList类的list(列表),
//     /*
//     * 下面重点说一下查询表user全部内容的方法
//     * 我们查询出来的内容，需要有个容器存放，以供使用，
//     * 所以定义了一个ArrayList类的list
//     * 有了容器，接下来就该从表中查询数据，
//     * 这里使用游标Cursor
//     * 如果需要用Cursor的话，第一个参数："表名"，中间5个：null，
//     * 最后是查询出来内容的排序方式："name DESC"
//     * 游标定义好了，接下来写一个while循环，让游标从表头游到表尾
//     * 在游的过程中把游出来的数据存放到list容器中
//     * @return
//     */
    public ArrayList<User> getAllData() {
//泛型<User>
        ArrayList<User> list = new ArrayList<User>();
        //游标只在一整行，Cursor=查询，游标最开始在第一行（默认）movetonext，放在List里=add，！！！把表里的所有需要的数据都取出来
        Cursor cursor = db.query("user", null, null, null, null, null, "name DESC");
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name, password));
        }
        // 游标用完需要关闭
        cursor.close();
        return list;
    }


}
