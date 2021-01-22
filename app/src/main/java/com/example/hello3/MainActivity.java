package com.example.hello3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
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

        }
    }

    //SMS문자 메시지를 읽어오는 코드. 전체 메시지를 읽어오는 것으로 추정되며, 특정 번호에 대한 처리가 필요함.
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

        }
        return 0;
    }

}