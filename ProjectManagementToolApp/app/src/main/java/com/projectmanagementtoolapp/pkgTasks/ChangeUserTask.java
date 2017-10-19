package com.projectmanagementtoolapp.pkgTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.projectmanagementtoolapp.pkgData.Database;

import java.sql.SQLException;

/**
 * Created by Figgu on 19.10.2017.
 */

public class ChangeUserTask extends AsyncTask<Object, Object, String>
{
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;

    public ChangeUserTask(Activity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Object... params) {
        Database db = Database.getInstance();
        try {
            db.changeUser((String) params[0], (String) params[1], (String) params[2], (String) params[3]);
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
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        this.dialog.dismiss();
    }



}