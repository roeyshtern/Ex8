package com.example.user.ex8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements TextWatcher{

    boolean celOrFar;
    boolean result;
    double farenhiet, celcius;
    int RBcurrentIndex;
    EditText ETfarenheit, ETcelcius;
    Button Bgo;
    RadioGroup RG;
    RadioButton RBcheck, RBcalculate;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context, menu);

        int[] colors = new int[]{Color.RED, Color.rgb(0,153,0), Color.BLUE};
        for (int i = 0; i<colors.length;i++)
        {
            MenuItem item = menu.getItem(i);
            SpannableString s  = new SpannableString(item.getTitle());
            s.setSpan(new ForegroundColorSpan(colors[i]), 0,s.length(),0);
            item.setTitle(s);
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.action_help:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Conversion_of_units_of_temperature"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETfarenheit = (EditText)findViewById(R.id.ETFeranheit);
        ETcelcius = (EditText)findViewById(R.id.ETcelcius);

        ETfarenheit.setText("");
        ETcelcius.setText("");

        Bgo = (Button)findViewById(R.id.Bgo);

        RG = (RadioGroup)findViewById(R.id.RG);

        RBcheck = (RadioButton)findViewById(R.id.RBcheck);
        RBcalculate = (RadioButton)findViewById(R.id.RBcalculate);

        registerForContextMenu(ETcelcius);
        registerForContextMenu(ETfarenheit);


        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton rb = (RadioButton)group.findViewById(checkedId);
                RBcurrentIndex = group.indexOfChild(rb);
                if(RBcurrentIndex==0)
                {
                    Bgo.setEnabled(false);
                    setNewState(true);
                }
                else if(RBcurrentIndex==1)
                {
                    if(!ETfarenheit.getText().toString().isEmpty() && !ETcelcius.getText().toString().isEmpty())
                    {
                        Bgo.setEnabled(true);
                        setNewState(true);
                    }
                    else
                    {
                        setNewState(true);
                    }
                }
            }
        });

        ETfarenheit.addTextChangedListener(this);
        ETcelcius.addTextChangedListener(this);


        Bgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CalcActivity.class);
                if(result == true)
                {
                    i.setAction(CalcActivity.ACTION_CALC);
                    i.putExtra("celOrFar", celOrFar);
                }
                else
                {
                    i.setAction(CalcActivity.ACTION_CHECK);
                }
                i.putExtra("farenheit", farenhiet);
                i.putExtra("celcius", celcius);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RG.clearCheck();
        ETcelcius.setText("");
        ETfarenheit.setText("");
        setNewState(false);

    }

    public void setNewState(boolean newState)
    {
        ETfarenheit.setEnabled(newState);
        ETcelcius.setEnabled(newState);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (RBcurrentIndex == 0) {
            if (!ETcelcius.getText().toString().isEmpty() && ETfarenheit.getText().toString().isEmpty())
            {
                celOrFar = true;
                result = true;
                Bgo.setEnabled(true);
                celcius = Double.valueOf(ETcelcius.getText().toString());
            }
            else if (ETcelcius.getText().toString().isEmpty() && !ETfarenheit.getText().toString().isEmpty())
            {
                celOrFar = false;
                result = true;
                Bgo.setEnabled(true);
                farenhiet = Double.valueOf(ETfarenheit.getText().toString());
            }
            else
            {
                Bgo.setEnabled(false);
            }
        }
        else if (RBcurrentIndex == 1)
        {
            if (!ETcelcius.getText().toString().isEmpty() && !ETfarenheit.getText().toString().isEmpty())
            {
                result = false;
                Bgo.setEnabled(true);
                celcius = Double.valueOf(ETcelcius.getText().toString());
                farenhiet = Double.valueOf(ETfarenheit.getText().toString());
            }
            else
            {
                Bgo.setEnabled(false);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
