package com.example.hello3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Message msg;
    final int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_today = (TextView)findViewById(R.id.tv_today);
        String today = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(new Date());
        tv_today.setText(tv_today.getText()+today);
    }

    public void bOnClick(View v) {
        EditText et = (EditText)findViewById(R.id.number);
        String s = et.getText().toString();
        if(s.length()==0){
            Toast myToast = Toast.makeText(this.getApplicationContext(),"숫자를 올바르게 입력해주세요.", Toast.LENGTH_SHORT);
            myToast.show();
        }
        else {
            //권한 허가 요청하기
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!=PackageManager.PERMISSION_GRANTED){
                // 이 권한이 필요한 이유를 설명해야하는가?
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)){
                    //다이어로그 같은 것을 띄워서 사용자에세 해당 권한이 필요한 이유를 설명
                    //해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한 허가를 요청해야함.
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_SMS},
                            PERMISSION_ALL);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults){
        switch(requestCode){
            case PERMISSION_ALL:
                if(grantResults.length>0
                && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //권한 허가
                    readSMSMessage();
                } else {
                    //권한 거부
                    //사용자가 해당 권한 거부시 해주어야할 동작 수행
                    System.out.println("권한 거부");
                }
                return;


        }
    }

    //SMS문자 메시지를 읽어오는 코드. 전체 메시지를 읽어는 것으로 추정되며, 특정 번호에 대한 처리가 필요함.
    public int readSMSMessage(){
        Uri allMessage = Uri.parse("content://sms");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(allMessage,
                new String[]{"_id", "thread_id", "address", "person", "date", "body"},
                null, null, "date DESC");

        while(c.moveToNext()){
            msg = new Message();

            long messageId = c.getLong(0);
            msg.setMessageId(String.valueOf(messageId));

            long threadId = c.getLong(1);
            msg.setThreadId(String.valueOf(threadId));

            String address = c.getString(2);
            msg.setAddress(address);

            long contactId = c.getLong(3);
            msg.setContactId(String.valueOf(contactId));

            String contactId_string = String.valueOf(contactId);
            msg.setContactId_string(contactId_string);

            long timestamp = c.getLong(4);
            msg.setTimestamp(String.valueOf(timestamp));

            String body = c.getString(5);
            msg.setBody(body);

            System.out.println("MessageId : "+msg.getMessageId());
            System.out.println("ContactId : "+msg.getContactId());
            System.out.println("Address : "+msg.getAddress());
            System.out.println("body : "+msg.getBody());
        }
        return 0;
    }

}