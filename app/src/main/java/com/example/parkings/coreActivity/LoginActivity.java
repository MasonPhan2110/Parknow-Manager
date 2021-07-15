package com.example.parkings.coreActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parkings.R;
import com.example.parkings.controller.CheckUser;
import com.example.parkings.controller.auth;
import com.example.parkings.extend.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

public class LoginActivity extends BaseActivity {
    Button fb,gg, btn_signup;
    EditText email, pass;
    LinearLayout footer;
    TextView text, signin,text1, text_signup;
    View back, sep_signup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        CheckUser check = new CheckUser();
        if(check.check()){
            super.onCreate(savedInstanceState, persistentState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(fuser !=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        attachKeyboardListeners();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        Window window = LoginActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(LoginActivity.this.getResources().getColor(R.color.green));
        window.setNavigationBarColor(LoginActivity.this.getResources().getColor(android.R.color.white));

        //btn
        fb = findViewById(R.id.log_withfb);
        gg = findViewById(R.id.log_withgg);
        btn_signup = findViewById(R.id.btn_signup);
        //edit text
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        //linear layout
        footer = findViewById(R.id.footer);
        //Text view
        text = findViewById(R.id.text);
        signin = findViewById(R.id.signin);
        text1 = findViewById(R.id.text1);
        back = findViewById(R.id.back);
        text_signup = findViewById(R.id.text_signup);
        //View
        sep_signup = findViewById(R.id.sep_signup);
        int dp = 10;
        int px = (int) (dp*getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (width - 3*px)/2,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0,0,px,0);
        fb.setLayoutParams(layoutParams);
        //layoutParams.setMargins(0,0,0,0);
        gg.setLayoutParams(layoutParams);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                signin.setTextColor(Color.parseColor("#EBEBEB"));
                signin.setClickable(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 6){
                    signin.setTextColor(Color.parseColor("#FFFFFF"));
                    signin.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) LoginActivity.this.getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isAcceptingText()){
                    inputMethodManager.hideSoftInputFromWindow(
                            LoginActivity.this.getCurrentFocus().getWindowToken(),
                            0
                    );
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth auth = new auth(email.getText().toString(),pass.getText().toString(),LoginActivity.this);
                auth.signin();
            }
        });
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        super.onShowKeyboard(keyboardHeight);
        Log.d("AAA","Show");
        footer.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        text1.setVisibility(View.GONE);
        sep_signup.setVisibility(View.GONE);
        text_signup.setVisibility(View.GONE);
        btn_signup.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        super.onHideKeyboard();
        Log.d("AAA","Hide");
        footer.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        text1.setVisibility(View.VISIBLE);
        sep_signup.setVisibility(View.VISIBLE);
        text_signup.setVisibility(View.VISIBLE);
        btn_signup.setVisibility(View.VISIBLE);
    }
}