package com.hlee1159.android.firstquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.preference.PreferenceManager;


public class MainActivity extends GroundActivity {

    private Button AnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "시작하시려면 아무 곳이나 눌러주세요.", Toast.LENGTH_SHORT).show();
        init();
    }

    public void init() {
        Button AnswerButton = (Button) findViewById(R.id.AnswerButton);
        AnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity();
            }
       });
    }
    public void startNewActivity(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String level1=preferences.getString("level1", DEFAULT);
        String level2=preferences.getString("level2", DEFAULT);
        String level3=preferences.getString("level3", DEFAULT);
        String level4=preferences.getString("level4", DEFAULT);
        String level5=preferences.getString("level5", DEFAULT);

        Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
        Intent intent2 = new Intent(MainActivity.this, Main3Activity.class);
        Intent intent3 = new Intent(MainActivity.this, Main4Activity.class);
        Intent intent4 = new Intent(MainActivity.this, Main5Activity.class);
        Intent intent5 = new Intent(MainActivity.this, Main6Activity.class);
        Intent intent6= new Intent(MainActivity.this, Main7Activity.class);

            if (level4 != DEFAULT) {
                startActivity(intent5);
            } else {
                if (level3 != DEFAULT) {
                    startActivity(intent4);
                } else {
                    if (level2 != DEFAULT) {
                        startActivity(intent3);
                    } else {
                        if (level1 != DEFAULT) {
                            startActivity(intent2);
                        } else {
                            startActivity(intent1);
                        }
                    }
                }

            }

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
