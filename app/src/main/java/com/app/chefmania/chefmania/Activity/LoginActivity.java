package com.app.chefmania.chefmania.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import com.app.chefmania.chefmania.Controller.AuthController;
import com.app.chefmania.chefmania.Model.Assignment;
import com.app.chefmania.chefmania.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mEmailInput;
    private EditText mPassInput;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser() == null || FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            return;
        }
        chooseUserHome();

        mEmailInput = (EditText) findViewById(R.id.email);
        mPassInput = (EditText) findViewById(R.id.password);

        mLoginButton = (Button) findViewById(R.id.email_sign_in_button);
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), (String) "Logginggggg", Toast.LENGTH_SHORT).show();
//                if(isValid()) {
//                    Snackbar.make(view, "Logging in. Please wait.", Snackbar.LENGTH_LONG).show();
//                    new AuthController(view.getContext())
//                            .signIn(mEmailInput.getText().toString().trim(), mPassInput.getText().toString().trim(), new AuthController.OnSignInCompleteListener() {
//                                @Override
//                                public void onUserLoggedIn() {
//                                    Snackbar.make(view, "Admin logged in.", Snackbar.LENGTH_LONG).show();
//                                }
//
//                                @Override
//                                public void onAdminLoggedIn() {
//                                    OpenMainActivity(view);
//                                }
//
//                                @Override
//                                public void onFailed(Exception ex) {
//                                    ex.printStackTrace();
//                                    Snackbar.make(view, "Failed to login.", Snackbar.LENGTH_LONG).show();
//                                }
//
//                                @Override
//                                public void noSuchUserEnrolledInOrg() {
//
//                                }
//
//                                @Override
//                                public void onNetworkFailed() {
//                                    Snackbar.make(view, "Network connectivity issue.", Snackbar.LENGTH_LONG).show();
//                                }
//                            });
//                }
//                else{
//                    Snackbar.make(view, "Not valid bruhhh.", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });

//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), (String) "Logginggggg", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void chooseUserHome() {
        switch (AuthController.getLoggedInUserType(this)) {
            case AuthController.TYPE_USER:
                Toast.makeText(getApplicationContext(), (String) " Only Admin can access", Toast.LENGTH_SHORT).show();
                break;
            case AuthController.TYPE_ADMIN:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    public void OpenMainActivity(View view) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    private boolean isValid() {
        boolean valid = true;

//        if(mEmailInput.getText().toString().trim().length() == 0) {
//            mEmailInput.setError("Please input an email.");
//            valid = false;
//        }else if(!Patterns.EMAIL_ADDRESS.matcher(mEmailInput.getText().toString()).matches()) {
//            valid = false;
//            mEmailInput.setError("Email is not valid");
//        }
//
//        if(mPassInput.getText().toString().trim().length() == 0) {
//            mPassInput.setError("Please input password");
//            valid = false;
//        }
//        Log.i("valids", "valid " + String.valueOf(valid));
        return valid;
    }
}

