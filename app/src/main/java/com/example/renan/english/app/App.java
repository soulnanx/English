package com.example.renan.english.app;

import android.app.Application;
import android.os.AsyncTask;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.R;
import com.example.renan.english.entity.Dialog;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.entity.Message;
import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.entity.PhraseOld;
import com.example.renan.english.entity.User;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application{

    private List<AsyncTask<?, ?, ?>> tasks;
    public SqlAdapter adapter;
    public Map<Note, List<Phrase>> cacheList = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initDatabase();
        initParse();
    }

    private void initParse(){
//        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Phrase.class);
        ParseObject.registerSubclass(Note.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Dialog.class);
        ParseObject.registerSubclass(User.class);

        Parse.initialize(
                this,
                getResources().getString(R.string.parse_app_id),
                getResources().getString(R.string.parse_client_key)
        );

        ParseFacebookUtils.initialize(getResources().getString(R.string.facebook_app_id));

    }

    private void init() {
        this.tasks = new ArrayList<>();
    }

    private void initDatabase() {
        DatabaseSpec database = PersistenceConfig.registerSpec(4);
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
