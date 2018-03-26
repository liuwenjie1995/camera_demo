package com.uploaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button check_button,add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_button=(Button)findViewById(R.id.face_check_button);
        add_button = (Button)findViewById(R.id.face_add_button);

        check_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.face_check_button:
            {

            }

            case R.id.face_add_button:
            {
                Toast.makeText(MainActivity.this, "你好!", Toast.LENGTH_LONG).show();
                Intent it = new Intent(MainActivity.this,FaceAddActivity.class);
                startActivity(it);
            }

        }
    }
}
