package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.projectmanagementtoolapp.pkgActivities.EditProjectActivity;
import com.projectmanagementtoolapp.pkgActivities.MainActivity;
import com.projectmanagementtoolapp.pkgActivities.ShowSprintsActivity;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinprojectwithrole;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alexk on 28.01.2018.
 */

public class GetUsersOfProjectTask extends AsyncTask<Object, Object, String> {
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    private Database db = Database.getInstance();

    private String responseStr = null;

    public GetUsersOfProjectTask(Activity activity) {
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

        Request request = new Request.Builder()
                .url(db.url + (String) params[0] + "/" + (Integer) params[1])
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
        Type type = new TypeToken<ArrayList<Userisinprojectwithrole>>() {}.getType();
        System.out.println("json: " + responseStr);
        ArrayList<Userisinprojectwithrole> users = gson.fromJson(responseStr, type);
        System.out.println(users);


        if (activity.getClass() == EditProjectActivity.class) {
            if (users.size() > 1) {
                ((EditProjectActivity) activity).fillUsers(users);
            }

            ((EditProjectActivity) activity).fillGUI();
        } else if (activity.getClass() == ShowSprintsActivity.class) {
            ((ShowSprintsActivity) activity).setUsersFromTask(users);
        }
    }
}
