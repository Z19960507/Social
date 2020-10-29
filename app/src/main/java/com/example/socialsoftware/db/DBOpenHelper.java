package com.example.socialsoftware.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.socialsoftware.model.FakeUser;
import com.example.socialsoftware.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlecurl 2018/6/24
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 声明一个数据库变量db
     * 这个数据库即SQL Lite
     * 是Android SDK中自带的数据库
     */
    private SQLiteDatabase db;
    private Context mContext;

    /**
     * 构造方法，参数为上下文context，上下文就是这个类所在包的路径
     * 调用父类构造器，super(context,"db_test",null,1); 指明上下文，数据库名，工厂默认空值，版本号默认从1开始
     * db = getReadableDatabase(); 与之相似的还有一句getWritableDatabase(),可以Ctrl+单击getReadableDatabase()
     */
    public DBOpenHelper(Context context) {
        super(context, "db_test", null, 1);
        db = getReadableDatabase();
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +//如果user不存在的存在的话
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE," +
                "password TEXT," +
                "code TEXT," +
                "real_name TEXT," +
                "real_name_jp TEXT," +
                "gender TEXT," +
                "date_of_birth TEXT," +
                "nationality TEXT," +
                "postcode TEXT," +
                "address TEXT," +
                "phone_number TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS user_association(" +//如果 user_association 不存在的存在的话
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user1 TEXT ," +
                "user2 TEXT)");

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
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        initData(db);
    }

    private void initData(SQLiteDatabase db){
        db.execSQL("REPLACE INTO user(`name`,`password`,`code`,`real_name`,`real_name_jp`) VALUES ('admin666','admin666','123456','admin666','admin666')");
        db.execSQL("REPLACE INTO user(`name`,`password`,`code`,`real_name`,`real_name_jp`) VALUES ('admin777','admin777','123456','admin777','admin777')");
        db.execSQL("REPLACE INTO user(`name`,`password`,`code`,`real_name`,`real_name_jp`) VALUES ('admin888','admin888','123456','admin888','admin888')");
        db.execSQL("REPLACE INTO user(`name`,`password`,`code`,`real_name`,`real_name_jp`) VALUES ('admin999','admin999','123456','admin999','admin999')");
        db.execSQL("REPLACE INTO user_association(`user1`,`user2`) VALUES ('admin888','admin666')");
        db.execSQL("REPLACE INTO user_association(`user1`,`user2`) VALUES ('admin888','admin777')");
        db.execSQL("REPLACE INTO user_association(`user1`,`user2`) VALUES ('admin999','admin888')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
    public void addName(String name) {
        db.execSQL("INSERT INTO user (name) VALUES(?)", new Object[]{name});
    }

    public void addPassword(String password) {
        db.execSQL("INSERT INTO user (password) VALUES(?)", new Object[]{password});
    }

    public void addPhonecode(String phonecode) {
        db.execSQL("INSERT INTO user (phonecode) VALUES(?)", new Object[]{phonecode});
    }

    public void delete(String name, String password) {
        db.execSQL("DELETE FROM user WHERE name = AND password =" + name + password);
    }

    public void update(String password) {
        db.execSQL("UPDATE user SET password = ?", new Object[]{password});
    }

    public void updateRealName(String realName) {

        db.execSQL("UPDATE user SET real_name = ?", new Object[]{realName});
    }

    public FakeUser GetFakeUserByFriend(String friend) {
        if (TextUtils.isEmpty(friend)) {
            return null;
        }
        Cursor cursor = db.rawQuery(
                "SELECT friend FROM user WHERE name = ?",
                new String[]{friend});

        FakeUser fakeUser = new FakeUser();

        if (cursor.moveToFirst()) {
            String friendName = cursor.getString(cursor.getColumnIndex("friend"));

            fakeUser.setFriend(friendName);
        }
        return fakeUser;
    }

    // List 的 泛型 是 User
    public List<User> getFriendListByName(String account){
        List<User> list = new ArrayList<>();
        Cursor cursor2 = db.rawQuery(
                "SELECT user2 AS friendId" +
                " FROM user_association" +
                " WHERE user1 = '"+account+"'" +
                " UNION" +
                " SELECT user1 AS friendId" +
                " FROM user_association" +
                " WHERE user2 = '"+account+"'", null);
        while (cursor2.moveToNext()) {
            String friendId = cursor2.getString(cursor2.getColumnIndex("friendId"));
            User user = getUserByName(friendId);
            list.add(user);
        }
        cursor2.close();
        return list;
    }
    
    
    public int updateFriend(FakeUser fakeUser, String account) {

        ContentValues cv = new ContentValues();

        cv.put("friend",fakeUser.getFriend());
        String[] friend = {account};

        int res = db.update("Friend",cv,"name = ?", friend);
        return res;
    }

    //从 user 表里查询账号为 account 的 real_name,User為返回值，getUserByName為方法名稱
    public User getUserByName(String account) {
        if (TextUtils.isEmpty(account)){
            return null;
        }
        Cursor cursor = db.rawQuery(
                "SELECT real_name,real_name_jp," +
                        "gender,date_of_birth,nationality," +
                        "postcode,address,phone_number " +
                        "FROM user " +
                        "WHERE name = ?",
                new String[]{account});
        User user = new User();
        //游標搜取
        if (cursor.moveToFirst()) {
            String realNameInDb = cursor.getString(cursor.getColumnIndex("real_name"));
            String realNameJpInDb = cursor.getString(cursor.getColumnIndex("real_name_jp"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String date_of_birth = cursor.getString(cursor.getColumnIndex("date_of_birth"));
            String nationality = cursor.getString(cursor.getColumnIndex("nationality"));
            String postcode = cursor.getString(cursor.getColumnIndex("postcode"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String phone_number = cursor.getString(cursor.getColumnIndex("phone_number"));

            user.setName(account);
            user.setReal_name(realNameInDb);
            user.setReal_name_jp(realNameJpInDb);
            user.setGender(gender);
            user.setDate_of_birth(date_of_birth);
            user.setNationality(nationality);
            user.setPostcode(postcode);
            user.setAddress(address);
            user.setPhone_number(phone_number);
        }

        cursor.close();
        return user;
    }


    //
    public int updateAll(User user, String account) {
        /*
        db.execSQL("UPDATE user " +
                        "SET " +
                        "real_name = ?," +
                        "real_name_jp = ?," +
                        "gender = ?," +
                        "date_of_birth = ?," +
                        "nationality = ?," +
                        "postcode = ?," +
                        "address = ?," +
                        "phone_number = ?" +
                        "WHERE name = ?",
                new Object[]{
                        user.getReal_name(), user.getReal_name_jp(),
                        user.getGender(), user.getDate_of_birth(),
                        user.getNationality(), user.getPostcode(),
                        user.getAddress(), user.getPhone_number(),
                        account
                }
        );
        */

        ContentValues cv = new ContentValues();
        cv.put("real_name", user.getReal_name());
        cv.put("real_name_jp", user.getReal_name_jp());
        cv.put("gender",user.getGender());
        cv.put("date_of_birth",user.getDate_of_birth());
        cv.put("nationality",user.getNationality());
        cv.put("postcode",user.getPostcode());
        cv.put("address",user.getAddress());
        cv.put("phone_number",user.getPhone_number());
        String[] args = {account};
        int res = db.update("user",cv,"name = ?", args);
        return res;
    }

    /*
    获取当前用户所有好友id
     */
//    public List<String> getAllFriendID(String name) {
//        List<String> list = new ArrayList<>();
//        Cursor cursor2 = db.rawQuery("SELECT user2 AS friendId" +
//                "FROM user_association" +
//                "WHERE user1 = 'u001'" +
//                "" +
//                "UNION" +
//                "" +
//                "SELECT user1 AS friendId" +
//                "FROM user_association" +
//                "WHERE user2 = 'u001'", new String[]{name});
//        while (cursor2.moveToNext()) {
//            String realNameInDb = cursor2.getString(cursor2.getColumnIndex("friendId"));
//            list.add(realNameInDb);
//        }
//        return list;
//    }


    public void submit(String name, String password, String code) {
        db.execSQL("INSERT INTO user (name,password,code) VALUES(?,?,?)", new Object[]{name, password, code});
    }

    public void msubmit(String age, String fname, String lname) {
        db.execSQL("INSERT INTO mine (age,fname,lname) VALUES(?,?,?)", new Object[]{age, fname, lname});
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
