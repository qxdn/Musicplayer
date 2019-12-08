package com.qianxu.musicplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        final EditText username=(EditText)findViewById(R.id.login_username);
        final EditText password=(EditText)findViewById(R.id.login_password);

        final CheckBox Remeberuser=(CheckBox)findViewById(R.id.remember_user);

        //读取保存的用户名
        final SharedPreferences pref =getSharedPreferences("userinfo",MODE_PRIVATE);
        username.setText(pref.getString("username","")); //读取用户名
        password.setText(pref.getString("password","")); //读取密码
        Remeberuser.setChecked(pref.getBoolean("checked",false));



        Button quit=(Button)findViewById(R.id.btn_quit);
        Button login=(Button)findViewById(R.id.btn_login);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")||username.getText().toString()==null||!password.getText().toString().equals("123456"))
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                else
                {
                    SharedPreferences.Editor editor=pref.edit();
                    if(Remeberuser.isChecked())
                    {
                        editor.putString("username",username.getText().toString());
                        editor.putString("password",password.getText().toString());
                        editor.putBoolean("checked",true);
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    /*
                    String user=username.getText().toString();
                    Intent intent=new Intent(LoginActivity.this,MusicListActivity.class);
                    intent.putExtra("username",user);
                    startActivity(intent);

                     */
                }
            }
        });

    }

}

