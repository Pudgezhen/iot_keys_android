package com.keys.iot.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.keys.iot.R;
import com.keys.iot.api.Api;
import com.keys.iot.api.ApiConfig;
import com.keys.iot.api.HttpCallback;
import com.keys.iot.entity.Res;
import com.keys.iot.util.StringUtils;
import java.util.HashMap;


public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    private void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",account);
        params.put("password",pwd);
        Api.config(ApiConfig.LOGIN,params).postRequest(this,new HttpCallback() {
            @Override
            public void onSuccess(String res) {
                Gson json = new Gson();
                Res r = json.fromJson(res, Res.class);
                if (r.getCode() == 0) {
                    SharedPreferences sp = getSharedPreferences("keys", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", r.getData().toString());
                    editor.apply();
                    navigateTo(HomeActivity.class);
                    showToastSync("登陆成功");
                }else{
                    showToastSync("登陆失败");
                }
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("登陆失败，服务器连接异常");
            }
        });
    }

//    private void login(String account,String pwd){
//        if(StringUtils.isEmpty(account)){
//            Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(StringUtils.isEmpty(pwd)){
//            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//                //第一步创建OKHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        Map m = new HashMap();
//        m.put("mobile", account);
//        m.put("password", pwd);
//        JSONObject jsonObject = new JSONObject(m);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson =
//                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
//                        , jsonStr);
//        //第三步创建Rquest
//        Request request = new Request.Builder()
//                .url(AppConfig.BASE_URI + "/app/login")
//                .addHeader("contentType", "application/json;charset=UTF-8")
//                .post(requestBodyJson)
//                .build();
//        //第四步创建call回调对象
//        final Call call = client.newCall(request);
//        //第五步发起请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("onFailure", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                // 在主线程执行
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }

    }