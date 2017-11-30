package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by alexk on 30.11.2017.
 */

public class InsertUserInProject extends AsyncTask<Object, Object, String> {
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    public InsertUserInProject(Activity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Object... params) {
        Database db = Database.getInstance();

        try {
            db.insertUserInProject(((User) params[0]).getUserID(), (int) params[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
    }
}
