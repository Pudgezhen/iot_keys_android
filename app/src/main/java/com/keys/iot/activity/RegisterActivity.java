package com.keys.iot.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.keys.iot.R;
import com.keys.iot.api.Api;
import com.keys.iot.api.ApiConfig;
import com.keys.iot.api.HttpCallback;
import com.keys.iot.entity.Res;
import com.keys.iot.util.StringUtils;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnResiter;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnResiter = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btnResiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                register(account, pwd);
            }
        });
    }


    private void register(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", account);
        params.put("password", pwd);
        Api.config(ApiConfig.REGISTER, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Gson json = new Gson();
                Res r = json.fromJson(res, Res.class);
                if (r.getCode() == 0) {
                    navigateTo(LoginActivity.class);
                    showToastSync("注册成功");
                }else{
                    showToastSync("注册失败");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });
    }


}