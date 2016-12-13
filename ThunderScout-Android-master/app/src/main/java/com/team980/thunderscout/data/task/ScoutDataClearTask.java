package com.team980.thunderscout.data.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.widget.Toast;

import com.team980.thunderscout.data.ServerDataContract.ScoutDataTable;
import com.team980.thunderscout.data.ServerDataDbHelper;
import com.team980.thunderscout.info.LocalDataAdapter;

public class ScoutDataClearTask extends AsyncTask<Void, Integer, Void> {

    private LocalDataAdapter viewAdapter;
    private Context context;

    public ScoutDataClearTask(LocalDataAdapter adapter, Context context) {
        viewAdapter = adapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        viewAdapter.clearData();
    }

    @Override
    public Void doInBackground(Void... params) {

        SQLiteDatabase db = new ServerDataDbHelper(context).getWritableDatabase();

        int rowsDeleted;

        try {
            rowsDeleted = db.delete(ScoutDataTable.TABLE_NAME, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }

        publishProgress(rowsDeleted);

        db.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer[] values) {
        //Runs on UI thread when publishProgress() is called
        Toast.makeText(context, values[0] + " rows deleted from DB", Toast.LENGTH_LONG).show();

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void o) {
        //Runs on UI thread after execution

        super.onPostExecute(o);
    }

}
