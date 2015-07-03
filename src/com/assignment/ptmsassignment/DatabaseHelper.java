package com.assignment.ptmsassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sunny on 7/1/2015.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int version = 1;
    String sql;

    public DatabaseHelper(Context context, String name) {
        super(context, name, null, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table Technician
        sql = "CREATE TABLE Technician" +
                "(staffNo text PRIMARY KEY, " +
                "staffLogin text, staffPswd text, staffName text);";
        db.execSQL(sql);

        sql = "INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName)" +
                " VALUES ('1001', 'login1001', 'pass1001', 'Jacky Wong');";
        db.execSQL(sql);

        sql = "INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName)" +
                " VALUES ('1002', 'login1002', 'pass1002', 'Kevin Leung');";
        db.execSQL(sql);

        sql = "INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName)" +
                " VALUES ('1003', 'login1003', 'pass1003', 'Flora Chan');";
        db.execSQL(sql);

        sql = "INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName)" +
                " VALUES ('1004', 'login1004', 'pass1004', 'Alan Lam');";
        db.execSQL(sql);


        //create table Product
        sql = "CREATE TABLE Product" +
                "(prodNo text PRIMARY KEY, prodName text, prodPrice int);";
        db.execSQL(sql);

        sql = "INSERT INTO Product(prodNo, prodName, prodPrice) " +
                "VALUES ('CN1008', 'Canon Power Photocopier', 4889);";
        db.execSQL(sql);

        sql = "INSERT INTO Product(prodNo, prodName, prodPrice) " +
                "VALUES ('CN2186', 'Canon Inket Printer', 1635);";
        db.execSQL(sql);

        sql = "INSERT INTO Product(prodNo, prodName, prodPrice) " +
                "VALUES ('HP1022', 'HP LaserJet 1022 Printer', 2500);";
        db.execSQL(sql);

        sql = "INSERT INTO Product(prodNo, prodName, prodPrice) " +
                "VALUES ('HP2055', 'HP LaserJet Colour Printer', 3500);";
        db.execSQL(sql);


        //create table Company
        sql = "CREATE TABLE Company(comNo text PRIMARY KEY" +
                ", comName text, comTel text, comAddr text);";
        db.execSQL(sql);

        sql = "INSERT INTO Company(comNo, comName, comTel, comAddr) " +
                "VALUES ('2001', 'Royal Pacific Hotel', " +
                "'27368818', 'Royal Pacific Hotel & Towers," +
                " China Hong Kong City, 33 Canton Road, Tsim Sha Tsui');";
        db.execSQL(sql);

        sql = "INSERT INTO Company(comNo, comName, comTel, comAddr) " +
                "VALUES ('2002', 'Hang Seng Bank Ltd', " +
                "'28220228', '83 Des Voeux Rd C, Central District');";
        db.execSQL(sql);

        sql = "INSERT INTO Company(comNo, comName, comTel, comAddr) VALUES" +
                " ('2003', 'American Express Bank Ltd', " +
                "'22771010', 'One Exchange Square, Central District');";
        db.execSQL(sql);


        //create table Purchase
        sql = "CREATE TABLE Purchase(serialNo text PRIMARY KEY," +
                " purchaseDate text ,prodNo text, comNo text);";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('34738783298', '20/4/2014', 'HP1022', '2003');";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('38473878893', '15/8/2013', 'CN2186', '2002');";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('42389489993', '18/12/2014', 'CN1008', '2003');";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('48947347894', '21/2/2012', 'HP1022', '2002');";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('89347827894', '17/1/2013', 'HP2055', '2002');";
        db.execSQL(sql);

        sql = "INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) VALUES ('46917347228', '17/1/2013', 'HP1022', '2001');";
        db.execSQL(sql);

        System.out.println("Database created");
    }


}
