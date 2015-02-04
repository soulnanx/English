//package com.example.renan.english.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
///**
// * Created by renan on 12/10/14.
// */
//public class LoadingTaskUtil {
//
//    public static void addLoading(Context context){
//        View loading = ((Activity)context).findViewById(R.id.progressBar);
//
//        if (loading == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//            loading = inflater.inflate( R.layout.loading, null );
//            getRootView(context).addView(loading);
//        }
//    }
//
//    public static void removeLoading(Context context){
//        View loading = ((Activity)context).findViewById(R.id.progressBar);
//
//        if (loading != null){
//            getRootView(context).removeView(loading);
//        }
//
//    }
//
//    private static ViewGroup getRootView(Context context){
//        return (ViewGroup)((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
//    }
//}
