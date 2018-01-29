package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.projectmanagementtoolapp.pkgActivities.AddRoleActivity;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgData.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Figgu on 17.11.2017.
 */

public class DeleteRoleTask extends AsyncTask<Object, Object, String> {

    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    private Database db = Database.getInstance();
    private String responseStr = null;
    private Response response = null;

    public DeleteRoleTask(Activity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Object... params) {
        ArrayList<Role> roles = (ArrayList<Role>) params[1];

        for (Role role : roles) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(db.url + (String) params[0] + "/" + role.getRoleid())
                    .delete()
                    .build();

            try {
                response = client.newCall(request).execute();
                responseStr = response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
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
        ((AddRoleActivity) activity).afterDelete();
        ((AddRoleActivity) activity).printSnackbar(responseStr);
        ((AddRoleActivity) activity).getAllRoles();
    }
}
