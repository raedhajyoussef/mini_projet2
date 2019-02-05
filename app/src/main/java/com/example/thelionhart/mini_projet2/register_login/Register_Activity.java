package com.example.thelionhart.mini_projet2.register_login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thelionhart.mini_projet2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class Register_Activity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = Register_Activity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutName =  findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword =  findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword =  findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail =  findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword =  findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword =  findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister =  findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink =  findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postData();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postData() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        final String name=this.textInputLayoutName.getEditText().toString().trim();
        final String email=this.textInputLayoutEmail.getEditText().toString().trim();
        final String password=this.textInputLayoutPassword.getEditText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.1.4:80/android_register_login/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject =new JSONObject(response);
                    String sucess=jsonobject.getString("success");
                    if (sucess.equals("1")){
                        Snackbar.make(nestedScrollView, "Register Success", Snackbar.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Snackbar.make(nestedScrollView, "Register Error", Snackbar.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(nestedScrollView, error.toString(), Snackbar.LENGTH_LONG).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                return params;
            }

        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

       /* } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
*/

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}

