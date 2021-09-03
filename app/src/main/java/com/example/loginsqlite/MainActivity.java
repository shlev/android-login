package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button btnSignUP, btnSignIn;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUP = findViewById(R.id.btnSignUp);

        myDB = new DBHelper((this));

        btnSignUP.setOnClickListener(view -> signup());

        btnSignIn.setOnClickListener(view -> signIn());
    }

    private void signup() {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();

        if (user.equals("") || pass.equals("") || repass.equals("")) {
            toastMessage("Fill all the fields");
        } else {
            if (pass.equals(repass)) {
                Boolean userCheckResult = myDB.checkUsername(user);
                if (userCheckResult == false) {
                    Boolean regResult = myDB.insertData(user, pass);
                    if (regResult == true) {
                        toastMessage("Registration Successful");
                        signIn();
                    } else {
                        toastMessage("Registration Failed");
                    }

                } else {
                    toastMessage("User already Exist\nPlease Sign In");
                }
            } else {
                toastMessage("Password not Matching");
            }
        }
    }

    private void signIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

    }
}