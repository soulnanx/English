package com.example.renan.english.app;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.dao.MajorityNoteDAO;
import com.example.renan.english.dao.PhraseDAO;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.entity.MajorityNote;
import com.parse.Parse;
import com.parse.ParseCrashReporting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renan on 12/11/14.
 */
public class App extends Application{

    private List<AsyncTask<?, ?, ?>> tasks;
    public SqlAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initDatabase();
    }

    private void initParse(){
        ParseCrashReporting.enable(this);

//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }

    private void init() {
        this.tasks = new ArrayList<>();
    }

    private void initDatabase() {
        DatabaseSpec database = PersistenceConfig.registerSpec(3);
        database.match(MajorityNote.class);
        database.match(Phrase.class);
        adapter = Persistence.getAdapter(this);
    }

    public void registerTask(AsyncTask task){
        if(task != null){

            if (this.tasks.contains(task)){
                for(AsyncTask taskInList : this.tasks){
                    if (taskInList.equals(task)){
                        taskInList.cancel(true);
                        this.tasks.remove(taskInList);
                    }
                }
            }

            this.tasks.add(task);
        }
    }

    public void unregisterTask(AsyncTask task){
        if(task != null){
            task.cancel(true);
            this.tasks.remove(task);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterAllTasks();
    }

    private void unregisterAllTasks() {
        for(AsyncTask task : this.tasks){
            task.cancel(true);
            this.tasks.remove(task);
        }
    }

}
