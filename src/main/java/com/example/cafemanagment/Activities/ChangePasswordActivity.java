package com.example.cafemanagment.Activities;

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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newPassEditText, confirmPass, oldPasswordEditText;
    Button confirmButton;
    LoginDB db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new LoginDB(this);
        cursor = db.getReadableDatabase().rawQuery("select * from login", null);
        cursor.moveToFirst();

        newPassEditText = findViewById(R.id.ed_new_password);
        confirmPass = findViewById(R.id.ed_re_new_password);
        oldPasswordEditText = findViewById(R.id.ed_pass_old_pass);

        confirmButton = findViewById(R.id.btn_new_pass_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor.getCount() == 0) {
                    if (oldPasswordEditText.getText().toString().equals(Constants.PASSWORD)
                            && newPassEditText.getText().toString().equals(confirmPass.getText().toString())) {
                        db.insert(Constants.USERNAME, newPassEditText.getText().toString());
                        Toast.makeText(getApplicationContext(), "Username Changed", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String passwrd = cursor.getString(cursor.getColumnIndex("password"));

                    if (oldPasswordEditText.getText().toString().equals(passwrd)
                            && newPassEditText.getText().toString().equals(confirmPass.getText().toString())) {
                        db.changePass(newPassEditText.getText().toString());
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
