package com.example.cafemanagment.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.DB.LoginDB;
import com.example.cafemanagment.R;

public class ChangeUsernameActivity extends AppCompatActivity {

    EditText newUsernameEditText, confirmUsername, passwordEditText;
    Button confirmButton;
    LoginDB db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new LoginDB(this);
        cursor = db.getReadableDatabase().rawQuery("select * from login", null);
        cursor.moveToFirst();

        newUsernameEditText = findViewById(R.id.ed_new_user_name);
        confirmUsername = findViewById(R.id.ed_re_new_user_name);
        passwordEditText = findViewById(R.id.ed_pass_n_u_a);
        confirmButton = findViewById(R.id.btn_new_user_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor.getCount() == 0) {
                    if (passwordEditText.getText().toString().equals(Constants.PASSWORD)
                            && newUsernameEditText.getText().toString().equals(confirmUsername.getText().toString())) {
                        db.insert(newUsernameEditText.getText().toString(), Constants.PASSWORD);
                        Toast.makeText(getApplicationContext(), "Username Changed", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String passwrd = cursor.getString(cursor.getColumnIndex("password"));

                    if (passwordEditText.getText().toString().equals(passwrd)
                            && newUsernameEditText.getText().toString().equals(confirmUsername.getText().toString())) {
                        db.changeUser(newUsernameEditText.getText().toString());
                        Toast.makeText(getApplicationContext(), "Username Changed", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
