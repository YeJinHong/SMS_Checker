package com.example.hello3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

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

}