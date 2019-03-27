package com.example.cafemanagment.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.DB.LoginDB;
import com.example.cafemanagment.R;

public class OwnerActivity extends AppCompatActivity {

    LoginDB db = new LoginDB(this);

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private TextView createTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEditText = findViewById(R.id.ed_logname);
        passwordEditText = findViewById(R.id.ed_password);

        loginButton = findViewById(R.id.btn_login);
        signUpButton = findViewById(R.id.btn_sign_up);

        createTextView = findViewById(R.id.tv_create_ac);

        final Cursor cursor = db.getReadableDatabase().rawQuery("select * from login", null);

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if(!b)
                    hideKeyboard(v);
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if(!b)
                    hideKeyboard(v);
            }
        });

        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        if (cursor.getCount() > 0) {
            createTextView.setVisibility(View.GONE);
            signUpButton.setVisibility(View.GONE);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor.getCount() == 0){
                    if (usernameEditText.getText().toString().equals(Constants.USERNAME) &&
                            passwordEditText.getText().toString().equals(Constants.PASSWORD)){
                        Intent intent = new Intent(OwnerActivity.this, OwnerActivity2.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    cursor.moveToFirst();
                    String username = cursor.getString(cursor.getColumnIndex("user"));
                    String passwrd = cursor.getString(cursor.getColumnIndex("password"));

                    if (usernameEditText.getText().toString().equals(username)
                            && passwordEditText.getText().toString().equals(passwrd)){
                        Intent intent = new Intent(OwnerActivity.this, OwnerActivity2.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
