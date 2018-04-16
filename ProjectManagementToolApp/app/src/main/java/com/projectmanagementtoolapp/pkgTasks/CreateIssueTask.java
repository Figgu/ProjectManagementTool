package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.projectmanagementtoolapp.pkgActivities.AddIssueActivity;
import com.projectmanagementtoolapp.pkgActivities.AddProjectActivity;
import com.projectmanagementtoolapp.pkgActivities.ShowIssuesActivity;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Issue;
import com.projectmanagementtoolapp.pkgData.Project;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alexk on 16.04.2018.
 */

public class CreateIssueTask extends AsyncTask<Object, Object, String> {
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    private Database db = Database.getInstance();

    private String responseStr = null;
    private Response response = null;

    public CreateIssueTask(Activity activity) {
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
        Issue issue = (Issue) params[1];
        String requestStr = gson.toJson(issue);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr);

        Request request = new Request.Builder()
                .url(db.url + (String) params[0])
                .post(body)
                .build();

        try {
            response = client.newCall(request).execute();
            responseStr = response.body().string();
        }catch (Exception e) {
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

        if (response.code() != 200) {
            ((AddIssueActivity) activity).makeSnackbar(responseStr);
        } else {
            ((AddIssueActivity) activity).addUsers(responseStr);
        }
    }
}
