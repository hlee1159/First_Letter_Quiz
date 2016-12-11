package com.hlee1159.android.firstquiz;

import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends GroundActivity {
    private static String VALUE = "myValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "시작하시려면 아무 곳이나 눌러주세요.", Toast.LENGTH_SHORT).show();

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

    }
    public void startNewActivity(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(VALUE, "초성게임을 시작합니다");
        startActivity(intent);
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
