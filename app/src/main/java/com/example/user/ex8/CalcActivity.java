package com.example.user.ex8;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalcActivity extends Activity {
    public static final String ACTION_CALC = "calc";
    public static final String ACTION_CHECK = "check";
    TextView TV;
    Button Breturn;
    double result, celcius, farenheit;
    int percision;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        TV = (TextView)findViewById(R.id.TVResult);
        Breturn = (Button)findViewById(R.id.Breturn);
        Breturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        Intent i = getIntent();
        if(i==null || i.getAction()==null)
        {
            Toast.makeText(getApplicationContext(),"Bad activitaion of the register activity",Toast.LENGTH_LONG).show();

            finish();
        }
        else
        {
            celcius = i.getExtras().getDouble("celcius");
            farenheit = i.getExtras().getDouble("farenheit");
            percision = i.getExtras().getInt("percision");
            if(i.getAction().equals(ACTION_CALC))
            {
                String a = "";
                String first = "The temperature ";
                String last = ", is converted to ";
                if(i.getExtras().getBoolean("celOrFar"))
                {
                    result = (9.0/5.0)*celcius+32;
                    a = first + String.format("%."+percision+"f", farenheit) + "C" + last + String.format("%."+percision+"f", result) + "F";
                }
                else
                {
                    result = (5.0/9.0)*(farenheit-32);
                    a = first + String.format("%."+percision+"f", farenheit) + "F" + last + String.format("%."+percision+"f", result) + "C";
                }
                TV.setText(a);

            }
            else
            {
                String a = "";
                String first = "Bravo! the temprature ";
                String last = ", is indeed ";
                String wrong = "Oops!, your answer is wrong, you may try again!";
                result = (9.0/5.0)*(celcius)+32;
                if(result ==farenheit)
                {
                    a = first + String.format("%."+percision+"f", celcius) + "C" + last + String.format("%."+percision+"f", result) + "F";
                    TV.setText(a);
                }
                else
                {
                    TV.setText(wrong);
                }

            }
        }
    }
}
