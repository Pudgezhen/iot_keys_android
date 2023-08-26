package com.keys.iot.activity;



import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.keys.iot.R;
import com.keys.iot.api.Api;
import com.keys.iot.api.ApiConfig;
import com.keys.iot.api.HttpCallback;
import com.keys.iot.entity.Res;

import java.util.HashMap;

public class HomeActivity extends BaseActivity {


    private Button btn_led_on;
    private Button btn_led_off;
    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        btn_led_on = findViewById(R.id.btn_LED_ON);
        btn_led_off = findViewById(R.id.btn_LED_OFF);
    }

    @Override
    protected void initData() {
        btn_led_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f = 0;
                changeLed(f);
            }
        });
        btn_led_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f = 1;
                changeLed(f);
            }
        });
    }

    private void changeLed(int f) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("deviceId","keys001");
        params.put("operate",f);
        Api.config(ApiConfig.COMMAND, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(String res) {
                Gson json = new Gson();
                Res r = json.fromJson(res, Res.class);
                if (r.getCode() == 0) {
                    showToastSync("操作成功");
                } else {
                    showToastSync("操作失败");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}