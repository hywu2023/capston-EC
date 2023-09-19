package kr.co.company.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button bt1 = findViewById(R.id.button1);
        Button bt2 = findViewById(R.id.button2);
        Button bt3 = findViewById(R.id.button3);
        Button bt4 = findViewById(R.id.button4);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "비밀번호 찾기", Toast.LENGTH_SHORT).show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "폰트설정", Toast.LENGTH_SHORT).show();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fa =FirebaseAuth.getInstance();
                if(fa.getCurrentUser() == null) {
                    Log.d("hihi", "로그인 사용자 없음");
                }else{
                    Log.d("hihi", "로그인 사용자 있음" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
                }

                fa.signOut();

                if(fa.getCurrentUser() == null) {
                    Log.d("hihi", "로그아웃후 사용자 없음");
                }else{
                    Log.d("hihi", "로그아웃후 사용자 있음" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
                }


                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "회원탈퇴", Toast.LENGTH_SHORT).show();
            }
        });


    }
}