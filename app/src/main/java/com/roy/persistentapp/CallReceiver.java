package com.roy.persistentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.roy.persistentapp.RoomComponents.PhoneCall;
import com.roy.persistentapp.RoomComponents.PhoneCallDao;
import com.roy.persistentapp.RoomComponents.PhoneCallDatabase;
import com.roy.persistentapp.RoomComponents.PhoneCallRepository;

import java.util.Date;

//TODO: if roomdb getting closed
public class CallReceiver extends BroadcastReceiver {
    /**
     * 0 -> no call received
     * 1 -> call received
     * 2 -> call over
     */
    private static int callReceivedFLag = 0;
    private static Date mCallreceiveDate;
    private static long mCallDuration;
    private PhoneCall call = null;

    private static final String TAG = "CallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        PhoneCallDatabase database = PhoneCallDatabase.getInstance(context);
        PhoneCallDao dao = database.phoneCallDao();
        PhoneCallRepository phoneCallRepo = new PhoneCallRepository(context);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("CallReceiver", "phoneState: " + state);
        Toast.makeText(context, state, Toast.LENGTH_SHORT).show();

        Log.i(TAG, "receiveFlag: " + callReceivedFLag);
        if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) { // when received
            callReceivedFLag = 1;
            mCallreceiveDate = new Date();
            Log.d("CallReceiver", "date: " + mCallreceiveDate.toString());
        } else if (callReceivedFLag == 1 && TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            mCallDuration = new Date().getTime() - mCallreceiveDate.getTime();
            Log.i(TAG, "duration: " + mCallDuration);

            call = new PhoneCall(Long.toString(mCallDuration),
                    mCallreceiveDate.toString());

            callReceivedFLag = 2;
        }
//        else if (state == null){
//            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//            String date = new Date().toString();
//            call = new PhoneCall(number, date);
//        }

        if (call != null && callReceivedFLag == 2) {
//            PhoneCall finalCall = call;
            Log.d(TAG, "Data inserted: " + call.toString());
            phoneCallRepo.insert(call);
            Toast.makeText(context, "Data inserted!", Toast.LENGTH_SHORT).show();
            callReceivedFLag = 0;

//            PhoneCallDatabase.executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Log.i(TAG, "inserted to db");
//                    dao.insert(finalCall);
//                }
//            });
        }
    }
}