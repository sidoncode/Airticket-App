package com.logarithm.airticket.flightticketbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logarithm.airticket.flightticketbook.Adapter.ItemData_Cusine;
import com.logarithm.airticket.flightticketbook.Adapter.Spinner_Cusine_DataAdapter;
import com.logarithm.airticket.flightticketbook.RestAPI.APIClient;
import com.logarithm.airticket.flightticketbook.RestAPI.APIInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.logarithm.airticket.flightticketbook.LoginAdmin.TOKEN_ID_ADMIN;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> fruits;

    public AlertDialog alertDialog = null;
    public static String TOKEN="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYWlydGlja2V0LWRldiIsImlkIjoiNWRiYTZlMGU2ZTM1N2U0OGVkM2JjODUzIiwiZW1haWwiOiJhaXJ0aWNrZXRkZXZAZ21haWwuY29tIiwicGFzc3dvcmQiOiIkMmEkMTAkRzBmMVVSWVFyMUE4cUNsNy44ZXpCdVdwbDdnNkpRd0dxc011N0dsQTBXOC8zRG5nYm51UEciLCJwcm9maWxlcGljIjoiaHR0cHM6Ly9lZGxpZmUuZWR1Lm12L3dwLWNvbnRlbnQvdXBsb2Fkcy8yMDE3LzA1LzIwMTYxMDE0XzU4MDA2YmZkNzZkY2YucG5nIiwiaWF0IjoxNTczMDQ2NTQ5fQ.uKcra8Ydm23HcG9PnfJLhl8I3uuS3hglhJ7ALmTZrrY";
    Date date;
    private EditText edittext, edittext2;
    private TextView textView, text_roundtrip, text_oneway;
    private Button btnSubmit;
    private ImageView img;
    private Spinner spinner1, spinner2, spin;
    private LinearLayout linear1, linear2, line11, line22;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    AutoCompleteTextView actv;
    AutoCompleteTextView actvv;
    ArrayAdapter<String> dataAdapter;
    Spinner sp;


    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerPassengers);
        fruits=new ArrayList<>();

        list.add(new ItemData_Cusine("1",R.drawable.ic_onewoman));
        list.add(new ItemData_Cusine("2",R.drawable.ic_family));
        list.add(new ItemData_Cusine("3",R.drawable.ic_threepersons));
//        sp = (Spinner) findViewById(R.id.spinnerPassengers);
//        Spinner_Cusine_DataAdapter adapter = new Spinner_Cusine_DataAdapter(MainActivity.this, list);
//        sp.setAdapter(adapter);
//        spinner.setAdapter(adapter);

        addItemsOnSpinner2();
        addListenerOnButton();


        text_roundtrip = (TextView) findViewById(R.id.text_roundtrip);
//        text_oneway = (TextView) findViewById(R.id.text_oneway);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
//        linear2 = (LinearLayout) findViewById(R.id.linear2);
        line11 = (LinearLayout) findViewById(R.id.line11);
//        line22 = (LinearLayout) findViewById(R.id.line22);
        edittext = (EditText) findViewById(R.id.txtdata);
//        edittext2 = (EditText) findViewById(R.id.txtdata2);

        try {
            alertDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setTheme(R.style.Custom).build();
            alertDialog.setMessage("Getting Airports info..");
            alertDialog.show();
            final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
            Call<com.logarithm.airticket.flightticketbook.ModelClass.DeleteAirport.DeleteAirport> call2 = apiService.getAllAirports(TOKEN_ID_ADMIN);
            call2.enqueue(new Callback<com.logarithm.airticket.flightticketbook.ModelClass.DeleteAirport.DeleteAirport>() {
                @Override

                public void onResponse(Call<com.logarithm.airticket.flightticketbook.ModelClass.DeleteAirport.DeleteAirport> call, Response<com.logarithm.airticket.flightticketbook.ModelClass.DeleteAirport.DeleteAirport> response) {
                    try {
                        alertDialog.dismiss();
                        Log.i("JSON", response.body().getSuccess().toString());
                        if (response.body().getSuccess()) {

                            for(int i=0;i<response.body().getMessage().size();i++)
                            {
                                fruits.add(response.body().getMessage().get(i).getName());

                            }


                            alertDialog.dismiss();



                        } else {
                            Log.i("TEST", response.body().getMessage().toString());
                        }
                    } catch (Exception e) {
                        alertDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<com.logarithm.airticket.flightticketbook.ModelClass.DeleteAirport.DeleteAirport> call, Throwable t) {
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_roundtrip.setTextColor(Color.parseColor("#000000"));
                text_oneway.setTextColor(Color.parseColor("#837a7a"));
                line11.setBackgroundColor(Color.parseColor("#000000"));
                line22.setVisibility(textView.INVISIBLE);
                line11.setVisibility(textView.VISIBLE);

            }
        });

//        linear2.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        text_roundtrip.setTextColor(Color.parseColor("#837a7a"));
//                        text_oneway.setTextColor(Color.parseColor("#000000"));
//                        line22.setBackgroundColor(Color.parseColor("#000000"));
//                        line11.setVisibility(textView.INVISIBLE);
//                        line22.setVisibility(textView.VISIBLE);
//
//                    }
//                }
//        );


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
              /*  myCalendar.set(Calendar.YEAR, year);*/
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelone();
            }
        };
//        edittext2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(MainActivity.this, date2, myCalendar2
//                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
//                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });


    }
    private void updateLabel() {
        String myFormat = "MMM dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        edittext.setText(sdf.format(myCalendar.getTime()));
        //edittext2.setText(sdf.format(myCalendar2.getTime()));
    }

    private void updateLabelone() {
        String myFormat = "MMM dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        //edittext.setText(sdf.format(myCalendar.getTime()));
        edittext2.setText(sdf.format(myCalendar2.getTime()));
    }

    // add items into spinner dynamically

    private void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinnerClass);
        List<String> list = new ArrayList<String>();
        list.add("First Class");
        list.add("Business Class");
        list.add("Premium Economy");
        list.add("Economy Class");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }


    ArrayList<ItemData_Cusine> list = new ArrayList<>();





    // get the selected dropdown list value
    public void addListenerOnButton() {
//        spinner1 = (Spinner) findViewById(R.id.spinnerPassengers);
        spinner2 = (Spinner) findViewById(R.id.spinnerClass);


        img = (ImageView) findViewById(R.id.btn1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.i("FROM",actv.getText().toString());
                Log.i("TO",actvv.getText().toString());
                 Log.i("DATE",edittext.getText().toString());
                Log.i("CLASS",spinner2.getSelectedItem().toString());

                Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                startActivity(intent);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);

        //Getting the instance of AutoCompleteTextView
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);

        ArrayAdapter<String> adapter_new = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);

        //Getting the instance of AutoCompleteTextView
       actvv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewTo);
        actvv.setThreshold(1);//will start working from first character
        actvv.setAdapter(adapter_new);//setting the adapter data into the AutoCompleteTextView
        actvv.setTextColor(Color.BLACK);

    }

    public class ClickableTextView extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener {
        public ClickableTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
            super(context, attrs, defStyle);
            setup();
        }

        public ClickableTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setup();
        }

        public ClickableTextView(Context context, int checkableId) {
            super(context);
            setup();
        }

        public ClickableTextView(Context context) {
            super(context);
            setup();
        }

        private void setup() {
            setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (hasOnClickListeners()) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setSelected(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        setSelected(false);
                        break;
                }
            }

            // allow target view to handle click
            return false;
        }


    }
}






