package com.bigfoot.bigfoot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionActivity extends AppCompatActivity{
    static{
        System.loadLibrary("instr-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //if it is their first time opening the app, ask about their recycling day/type
        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        if(!prefs.getBoolean("got_prefs",false)) {
            Intent enterRecyling = new Intent(InstructionActivity.this, FirstOpenActivity.class);
            startActivity(enterRecyling);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Button button = (Button) findViewById(R.id.nextButton);
        TextView instr1 = (TextView) findViewById(R.id.inst1);
        TextView instr2 = (TextView) findViewById(R.id.inst2);
        //TextView instr3 = (TextView) findViewById(R.id.inst3);
        //TextView instr4 = (TextView) findViewById(R.id.inst4);


        instr1.setText(getInstr1());
        instr2.setText(getInstr2()+" " + getInstr3()+ " " +getInstr4());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructionActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public native String getInstr1();
    public native String getInstr2();
    public native String getInstr3();
    public native String getInstr4();

}

