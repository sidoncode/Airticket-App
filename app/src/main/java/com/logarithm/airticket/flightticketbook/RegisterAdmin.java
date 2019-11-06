package com.logarithm.airticket.flightticketbook;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logarithm.airticket.flightticketbook.ParametersClass.Credentials;
import com.logarithm.airticket.flightticketbook.RestAPI.APIClient;
import com.logarithm.airticket.flightticketbook.RestAPI.APIInterface;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterAdmin extends AppCompatActivity {

    public static String  TOKEN_ID=null;
    TextView edt_username,edt_name,edt_pass;
    Button btn_login;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_username=findViewById(R.id.edt_username);
        edt_name=findViewById(R.id.edt_name);
        edt_pass=findViewById(R.id.edt_pass);
        btn_login=findViewById(R.id.btn_login);

        Log.i("ACT ","REGISTER");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new SpotsDialog.Builder().setContext(RegisterAdmin.this).setTheme(R.style.Custom).build();
                alertDialog.setMessage("Registering... ");
                alertDialog.show();

                if (edt_pass.getText().length() > 0 && edt_username.getText().length() > 0) {

                    //   Credentials credentials=new Credentials(editTextUserId.getText().toString(),editTextPassword.getText().toString());
                    com.logarithm.airticket.flightticketbook.ParametersClass.Register credentials = new com.logarithm.airticket.flightticketbook.ParametersClass.Register(edt_username.getText().toString(), edt_pass.getText().toString(),edt_name.getText().toString());

                    final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
                    Call<com.logarithm.airticket.flightticketbook.ModelClass.Register.Register> call2 = apiService.registerAdmin(credentials);
                    call2.enqueue(new Callback<com.logarithm.airticket.flightticketbook.ModelClass.Register.Register>() {
                        @Override
                        public void onResponse(Call<com.logarithm.airticket.flightticketbook.ModelClass.Register.Register> call, Response<com.logarithm.airticket.flightticketbook.ModelClass.Register.Register> response) {
                            try {
                                alertDialog.dismiss();
                                if (response.body().getSuccess()) {
                                    Toast.makeText(RegisterAdmin.this, "Sucessfully Registered !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),LoginAdmin.class));
                                    finish();
                                } else {
                                    Toast.makeText(RegisterAdmin.this,"Admin already exist !", Toast.LENGTH_SHORT).show();
                                }
                                //   alertDialog.dismiss();

                            } catch (Exception e) {
                                Toast.makeText(RegisterAdmin.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
//                            alertDialog.dismiss();

                            }
                        }

                        @Override
                        public void onFailure(Call<com.logarithm.airticket.flightticketbook.ModelClass.Register.Register> call, Throwable t) {
                            Toast.makeText(RegisterAdmin.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                            alertDialog.dismiss();

                        }
                    });
                } else {
                    alertDialog.dismiss();
                    Toast.makeText(RegisterAdmin.this, "Fields cannot be blank !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
