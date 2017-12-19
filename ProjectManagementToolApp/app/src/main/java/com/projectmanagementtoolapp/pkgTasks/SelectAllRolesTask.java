package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.projectmanagementtoolapp.pkgData.Database;

import java.sql.SQLException;

/**
 * Created by Figgu on 17.11.2017.
 */

public class SelectAllRolesTask extends AsyncTask<Object, Object, String> {
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    public SelectAllRolesTask(Activity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }
    @Override
    protected String doInBackground(Object... params) {
        Database db = Database.getInstance();

        try {
            db.getAllRoles();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPreExecute() {
        this.dialog.setTitle("Loading");
        this.dialog.setMessage("Wait while loading...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        this.dialog.dismiss();
    }
}
