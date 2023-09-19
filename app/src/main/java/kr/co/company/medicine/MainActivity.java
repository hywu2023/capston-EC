package kr.co.company.medicine;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public CalendarView calendarView;
    Calender activity_main;
    SelfTest activity_selftest;
    MapViewActivity activity_mapview;
    Mypage activity_mypage;
    RecyclerView rv_list;
    ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;
    ArrayList<ListItem> selectingList = new ArrayList<>();
    MyDBHelper myHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(it);
            }
        });
        calendarView = findViewById(R.id.calendarView);

        // XML -- 0902 S

        FloatingActionButton floatbtn = findViewById(R.id.floatbtn);

        activity_main = new Calender();
        activity_selftest = new SelfTest();
        activity_mapview = new MapViewActivity();
        activity_mypage = new Mypage();

        //기본화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, activity_main);

        NavigationBarView navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { //아이템 선택 시 호출
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calender:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, activity_main);
                        return true;
                    case R.id.test:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, activity_selftest);
                        return true;
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, activity_mapview);
                        return true;
                    case R.id.myPage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, activity_mypage);
                        return true;
                }
                return false;
            }
        });

        // XML -- 0902 E


        // MapActivity -- 0902 S

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission2 == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this,"이미 권한이 부여되어 있음.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"권한을 설정해야함.", Toast.LENGTH_LONG).show();
            String[] need_perm = new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(MainActivity.this, need_perm, 777);

        }

        // MapActivity -- 0902 E

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MedSearch.class);
                startActivity(intent);
            }
        }); //임시 코드


        myHelper = new MyDBHelper(this);

        selectingList.addAll(myHelper.allListItems());

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        listItemRecyclerViewAdapter = new ListItemRecyclerViewAdapter(selectingList, this);
        rv_list.setAdapter(listItemRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);

        //0811S



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String  Year = String.valueOf(year);
                String  Month = String.valueOf(month);
                String  curDate = String.valueOf(dayOfMonth);

                String Selected = Year + "-" + Month + "-" + curDate;
            }
        });

        //0811E

    }

    // MapActivity -- 0902 S
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 777) {
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"사용자가 권한을 승인함.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"사용자가 권한을 거부함.", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(MainActivity.this,"권한설정 여부에 대한 결과정보가 존재하지 않음.", Toast.LENGTH_LONG).show();
            }

        }
    }
    // MapActivity -- 0902 E

}