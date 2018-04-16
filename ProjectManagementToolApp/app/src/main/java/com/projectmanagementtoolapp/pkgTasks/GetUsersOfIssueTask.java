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
 * Created by alexk on 17.04.2018.
 */

public class GetUsersOfIssueTask extends AsyncTask<Object, Object, String> {

    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    private Database db = Database.getInstance();

    private String responseStr = null;

    public GetUsersOfIssueTask(Activity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Object... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        String requestStr = gson.toJson((Integer) params[1]);
        System.out.println(requestStr);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf((Integer) params[1]));
        System.out.println(jsonObject.toString());

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url(db.url + (String) params[0])
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseStr = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        return responseStr;
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setTitle("Loading");
        this.dialog.setMessage("Wait while loading...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        this.dialog.dismiss();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        System.out.println("json: " + responseStr);
        ArrayList<User> users = gson.fromJson(responseStr, type);
        System.out.println(users);
    }
}
