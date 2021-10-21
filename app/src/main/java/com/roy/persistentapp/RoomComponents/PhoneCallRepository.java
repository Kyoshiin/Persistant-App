package com.roy.persistentapp.RoomComponents;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * For implementing DAO methods
 */
public class PhoneCallRepository {
    private PhoneCallDao dao;

    public PhoneCallRepository(Context context) {
        PhoneCallDatabase database = PhoneCallDatabase.getInstance(context);
        dao = database.phoneCallDao();
    }

    public LiveData<List<PhoneCall>> getLiveData() {
        return dao.getAll();
    }

    /**
     * DB operations to be done on backgrnd thread
     * as room not allow on main thread
     */
    public void insert(PhoneCall phoneCall){
        new InsertNoteAsyncTask(dao).execute(phoneCall);
    }

    private class InsertNoteAsyncTask extends AsyncTask<PhoneCall, Void, Void> {
        private PhoneCallDao phoneCallDao;
        public InsertNoteAsyncTask(PhoneCallDao dao) {
            this.phoneCallDao=dao;
        }

        @Override
        protected Void doInBackground(PhoneCall... phoneCalls) {
            phoneCallDao.insert(phoneCalls[0]);
            return null;
        }
    }
}
