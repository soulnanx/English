package com.example.renan.english.app;

import android.app.Application;
import android.os.AsyncTask;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.entity.PhraseOld;
import com.parse.Parse;
import com.parse.ParseCrashReporting;

import java.util.ArrayList;
import java.util.List;

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
        Parse.initialize(this, "04BFbyj72fzANpjeAGsMpplJ4H7mxFc7Pc0k477u", "t8NaJBnEMWt3yIoogNVmJ4uUUpUMca9iNNbkW6N0");
    }

    private void init() {
        this.tasks = new ArrayList<>();
    }

    private void initDatabase() {
        DatabaseSpec database = PersistenceConfig.registerSpec(3);
        database.match(MajorityNote.class);
        database.match(PhraseOld.class);
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
