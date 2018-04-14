package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.projectmanagementtoolapp.pkgActivities.MainActivity;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alexk on 24.01.2018.
 */

public class GetUserByNameTask extends AsyncTask<Object, Object, User> {
    private Database db = Database.getInstance();
    private String responseStr = null;

    @Override
    protected User doInBackground(Object... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        boolean retValue = true;

        Request request = new Request.Builder()
                .url(db.url + (String) params[0] + "/" + (String) params[1])
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            responseStr = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (response.code() == 200) {
            Gson gson = new Gson();
            return gson.fromJson(responseStr, User.class);
        } else {
            return null;
        }
    }
}