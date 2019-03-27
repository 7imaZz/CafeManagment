package com.example.cafemanagment.Activities;

import android.content.Intent;
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

public class CreateAccountActivity extends AppCompatActivity {

    EditText oldUsernameEditText, oldPasswordEditText, newUsernameEditText, newPasswordEditText;
    Button confirmButton;

    LoginDB db = new LoginDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        oldUsernameEditText = findViewById(R.id.ed_old_username_create);
        oldPasswordEditText = findViewById(R.id.ed_old_pass_create);
        newUsernameEditText = findViewById(R.id.ed_new_username_create);
        newPasswordEditText = findViewById(R.id.ed_new_password_create);

        confirmButton = findViewById(R.id.btn_confirm_create);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldUsernameEditText.getText().toString().equals(Constants.USERNAME) &&
                        oldPasswordEditText.getText().toString().equals(Constants.PASSWORD)){
                    if (!newUsernameEditText.getText().toString().isEmpty() &&
                            !newPasswordEditText.getText().toString().isEmpty()){

                        db.insert(newUsernameEditText.getText().toString(), newPasswordEditText.getText().toString());
                        Toast.makeText(getApplicationContext(), "New Account Created", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(CreateAccountActivity.this, OwnerActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Old Username Or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
