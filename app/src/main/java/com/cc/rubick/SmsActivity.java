package com.cc.rubick;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.cc.rubick.model.RecordEntity;
import com.cc.rubick.model.Sms;
import com.cc.rubick.util.MyDate;
import com.cc.rubick.util.MyRestTemplate;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsActivity extends AppCompatActivity {
    private Context context = null;
    private MyDate myDate = null;
    ContentResolver resolver = null;
    private String[] columns = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    MyRestTemplate restTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        myDate = (MyDate) this.getApplication();

        resolver = getContentResolver();
        restTemplate = new MyRestTemplate(this);

        setContentView(R.layout.activity_sms);
        AsyncTask<Integer, Integer, Integer> execute = new SmsTask().execute();
        new GeocodeingTask().execute();


    }

    private class SmsTask extends AsyncTask<Integer, Integer, Integer> {
        List<List<Sms>> list = new ArrayList<List<Sms>>();

        @Override
        protected Integer doInBackground(Integer... params) {
            final String SMS_URI_ALL = "content://sms/";
            List<Sms> smss = new ArrayList<Sms>();

            ContentResolver cr = context.getContentResolver();
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, null, null, null, "date desc");
            TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String ownerMobile = mTelephonyMgr.getLine1Number();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (cur.moveToNext()) {
                if (smss.size() < 30) {
                    Sms s = new Sms();
                    s.setSmsIds(cur.getInt(cur.getColumnIndex(android.provider.Telephony.Sms._ID)));
                    s.setAddress(cur.getString(cur.getColumnIndex(android.provider.Telephony.Sms.ADDRESS)));
                    s.setAddressMobile(s.getAddress().substring(3, s.getAddress().length()));
                    s.setBody(cur.getString(cur.getColumnIndex(android.provider.Telephony.Sms.BODY)));
                    Date date = new Date(cur.getLong(cur.getColumnIndex(android.provider.Telephony.Sms.DATE)));
                    s.setDate(dateFormat.format(date));
                    s.setPerson(cur.getString(cur.getColumnIndex(android.provider.Telephony.Sms.PERSON)));
                    s.setProtocol(cur.getString(cur.getColumnIndex(android.provider.Telephony.Sms.PROTOCOL)));
                    s.setRead(cur.getInt(cur.getColumnIndex(android.provider.Telephony.Sms.READ)));
                    s.setServiceCenter(cur.getString(cur.getColumnIndex(android.provider.Telephony.Sms.SERVICE_CENTER)));
                    s.setStatus(cur.getInt(cur.getColumnIndex(android.provider.Telephony.Sms.STATUS)));
                    s.setThreadId(cur.getInt(cur.getColumnIndex(android.provider.Telephony.Sms.THREAD_ID)));
                    s.setType(cur.getInt(cur.getColumnIndex(android.provider.Telephony.Sms.TYPE)));
                    s.setOwnerMobile(ownerMobile);//电话
                    Log.v("body", s.getBody());

                    smss.add(s);
                } else {
                    list.add(smss);
                    smss = new ArrayList<Sms>();
                }
            }
            Log.v("长度       为", smss.size() + "");

            for (List<Sms> smsList : list) {

                JsonObject jsonObject = restTemplate.post("pugna/sms/upload", smsList);
            }


            return list.size();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Toast.makeText(getApplicationContext(), "同步数据" + list.size() + "次，每次30条",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String getQueryData() {
        StringBuilder sb = new StringBuilder();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(columns[0]);//获取Id值的索引
            int disPlayNameIndex = cursor.getColumnIndex(columns[1]);//获取姓名索引
            String disPlayName = cursor.getString(disPlayNameIndex);
            int id = cursor.getInt(idIndex);
            Cursor phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, columns[3] + "=" + id, null, null);
            while (phone.moveToNext()) {
                int phoneNumberIndex = phone.getColumnIndex(columns[2]);
                String phoneNumber = phone.getString(phoneNumberIndex);
                sb.append(disPlayName + ":" + phoneNumber + "\n");
            }


        }
        cursor.close();
        return sb.toString();

    }

    private class GeocodeingTask extends AsyncTask<Integer, Integer, Integer> {
        String result = "";
        int size = 0;


        @Override
        protected Integer doInBackground(Integer... params) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            }
            TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String ownerMobile = mTelephonyMgr.getLine1Number();
            if (ownerMobile == null || ownerMobile.isEmpty()) {
                ownerMobile="18613868034";
            }


            JsonObject jsonObject = restTemplate.get("pugna/callRecords/list?page.showCount=1");

            String dateWhere = jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("date").getAsString();
            Log.v("dateWhere", dateWhere);
            String where = "";
            try {
                where = where + dateFormat.parse(dateWhere.substring(0, dateWhere.length() - 2)).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.DATE + " < ?", new String[]{where}, CallLog.Calls.DEFAULT_SORT_ORDER);

            List<RecordEntity> recordEntities = new ArrayList<>();

            while (cursor.moveToNext()) {
                RecordEntity record = new RecordEntity();
                record.setCallId(cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID)));
                record.setName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                record.setNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                record.setType(cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)));
                Date date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                record.setDate(dateFormat.format(date));
                record.setDuration(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION)));
                record.setCallNew(cursor.getInt(cursor.getColumnIndex(CallLog.Calls.NEW)));
                record.setOwnerMobile(ownerMobile.substring(3, ownerMobile.length()));//本机号码
                record.setIsRead((cursor.getInt(cursor.getColumnIndex(CallLog.Calls.IS_READ))));
                record.setCachedPhotoUri((cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI))));
                //record.setCallCount((cursor.getInt(cursor.getColumnIndex(CallLog.Calls._COUNT))));
                recordEntities.add(record);
            }

            size = recordEntities.size();
            if (size == 0) {
                return 0;
            }

            JsonObject post = restTemplate.post("pugna/callRecords/uploadRecord", recordEntities);
            result = post.get("message").getAsString();


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (result.equals("成功")) {
                Toast.makeText(getApplicationContext(), "同步通话记录" + size + "条",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_SHORT).show();
            }

        }
    }


}
