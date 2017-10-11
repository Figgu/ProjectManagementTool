package com.projectmanagementtoolapp.pkgTasks;

import android.os.AsyncTask;

import com.projectmanagementtoolapp.pkgData.Database;

import java.sql.SQLException;

/**
 * Created by alexk on 10.10.2017.
 */

public class OpenConnectionTask extends AsyncTask<Object, Object, String> {
    @Override
    protected String doInBackground(Object[] params) {
        Database db = Database.getInstance();
        try {
            db.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
