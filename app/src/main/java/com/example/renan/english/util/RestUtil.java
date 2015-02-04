package com.example.renan.english.util;

import android.support.annotation.NonNull;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestUtil {

    private static final int TIME_OUT = 120000;

    //    Headers
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    //    STATUS CODE
    public static final int OK = 200;
    public static final int NO_CONTENT = 204;
    public static final int NOT_FOUND = 404;

	public static HttpResponse post(@NonNull String path, Map<String, String> headers, String json) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		httpPost.setParams(setTimeout(TIME_OUT));

        if(headers != null && !headers.isEmpty()){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        if (!json.isEmpty()){
            httpPost.setEntity(new StringEntity(json));
        }

        return httpclient.execute(httpPost);
	}

	public static HttpResponse get(@NonNull String path, Map<String, String> headers) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(path);
        httpGet.setParams(setTimeout(TIME_OUT));

        if(headers != null && !headers.isEmpty()){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return httpclient.execute(httpGet);
	}

    public static HttpResponse put(String path, Map<String, String> params,
                                           List<HttpEntity> httpEntity, String authorization) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(path);
        httpPut.setParams(setTimeout(TIME_OUT));
        if (authorization != null) {
            if (!authorization.equals("")) {
                httpPut.addHeader("Authorization", "Token " + authorization);
            }
        }
        if (httpEntity != null) {
            httpPut.addHeader("Content-Type", "application/json");
            for(HttpEntity entity : httpEntity){
                httpPut.setEntity(entity);
            }
        }
        if(params != null){
            httpPut.addHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for(Map.Entry<String, String> item : params.entrySet()){
                nameValuePairs.add(new BasicNameValuePair(item.getKey(), item.getValue()));
            }
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        return httpclient.execute(httpPut);
    }

    public static HttpParams setTimeout(int tempo) {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, tempo);
        HttpConnectionParams.setSoTimeout(httpParameters, tempo);
        return httpParameters;
    }

}