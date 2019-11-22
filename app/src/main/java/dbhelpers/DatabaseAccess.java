package dbhelpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dictionary.MainActivity;

import java.util.ArrayList;
import java.util.List;

import model.Word;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static String databaseName;
    private DatabaseAccess(Context context,String databaseName){
        this.openHelper = new DatabaseOpenHelper(context,databaseName + ".db");
        DatabaseAccess.databaseName = databaseName;
    }
    public static DatabaseAccess getInstance(Context context,String databaseName){
        if(instance == null){
            instance = new DatabaseAccess(context,databaseName);
        } else {
            if(!DatabaseAccess.databaseName.equals(databaseName)) {
                instance = new DatabaseAccess(context,databaseName);
            }
        }
        return instance;
    }

    public ArrayList<String> getWRds(){
        openDB();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + databaseName  ,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        closeDB();
        return list;
    }
    public ArrayList<Word> getWordsOffset(int id, int offset){
        openDB();
        ArrayList<Word> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + databaseName   +
                " WHERE id >= "+ id + " limit " + offset,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(new Word(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2)))  ;
            cursor.moveToNext();
        }
        cursor.close();
        closeDB();
        return list;
    }

    public Word getWordsById(int id){
        openDB();
        Word word = new Word();
        Cursor cursor = database.rawQuery("Select * from " + databaseName   +
                " where id = " + id,null);
        cursor.moveToFirst();
        word.setId(cursor.getInt(0));
        word.setName(cursor.getString(1));
        word.setContent(cursor.getString(2));
        word.insertScriptForHref();
        cursor.close();
        closeDB();
        return word;
    }

    public String getDefinition(String word){
        String definition = " ";
        Cursor cursor = database.rawQuery("Select * from anh_viet " +
                "where word = '" + word +"'",null);
        cursor.moveToFirst();
        definition = cursor.getString(2);
        cursor.close();
        return definition;
    }

    public void openDB(){
        this.database = openHelper.getWritableDatabase();
    }

    public void closeDB(){
        if(database!=null){
            this.database.close();
        }
    }

    public ArrayList<String> getWordsStartWith(String str) {
        ArrayList<String> list = new ArrayList<>();
        try{
            openDB();
            Cursor cursor = database.rawQuery("select * from " + databaseName   +
                    " WHERE word like "+"'" + str + "%'" + " limit 30",null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                list.add(cursor.getString(1));
                cursor.moveToNext();
            }
            cursor.close();
            closeDB();
        }catch (IllegalStateException err) {
            err.printStackTrace();
        }
        return list;
    }

    public Word getWordByName(String word) {
        try {
            openDB();
            Cursor cursor = database.rawQuery("select * from " + databaseName
                    + " where word = " +  "\"" + word.trim() + "\"",null);
            cursor.moveToFirst();
            Word word1 = new Word(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2));
            cursor.close();
            closeDB();
            return  word1;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    public ArrayList<Word> getAllWords() {
        openDB();
        ArrayList<Word> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + databaseName,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(new Word(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDB();
        return list;
    }

    public List<Word> getAllFavoritesWord() {
        openDB();
        ArrayList<Word> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + databaseName + " a inner join "
                + MainActivity.FAVORITE + " b on a.id=b.id",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(new Word(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDB();
        return list;
    }

    public boolean isLiked(int wordId) {
        openDB();
        boolean result = false;
        Cursor cursor = database.rawQuery("select * from " + MainActivity.FAVORITE
                + " where id = " + wordId ,null);
        if(cursor.getCount()==1) {
            result = true;
        }
        cursor.close();
        closeDB();
        return result;
    }

    public boolean addIntoFavorite(int wordId) {
        boolean result = false;
        try {
            openDB();
            ContentValues value = new ContentValues();
            value.put("id",wordId);
            database.insert(MainActivity.FAVORITE,null,value);
            closeDB();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return  result;
        }
    }
}
