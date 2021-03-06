package com.blindingdark.geektrans.activitys.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.tools.Number;
import com.blindingdark.geektrans.trans.baidu.BaiduSettingsString;


public class BaiduSettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editTextBDAPPID;
    EditText editTextBDKey;
    EditText editTextBDToastTime;
    Spinner spinnerBDFrom;
    Spinner spinnerBDTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTextBDAPPID = (EditText) findViewById(R.id.BDAPPID);
        String BDAPPID = preferences.getString(BaiduSettingsString.baiduAppId, "");
        editTextBDAPPID.setText(BDAPPID);
        editTextBDAPPID.addTextChangedListener(bdAPPIDTextWatcher);


        editTextBDKey = (EditText) findViewById(R.id.BDKey);
        String key = preferences.getString(BaiduSettingsString.baiduKey, "");
        editTextBDKey.setText(key);
        editTextBDKey.addTextChangedListener(bdKeyTextWatcher);


        editTextBDToastTime = (EditText) findViewById(R.id.editTextBaiduToastTime);
        String bdToastTime = preferences.getString(BaiduSettingsString.baiduToastTime, BaiduSettingsString.defBaiduToastTime);
        editTextBDToastTime.setText(bdToastTime);
        editTextBDToastTime.addTextChangedListener(bdToastTimeWatcher);


        spinnerBDFrom = (Spinner) findViewById(R.id.BDLangFromSpinner);
        String defFrom = preferences.getString(BaiduSettingsString.baiduFrom, "0_auto");
        spinnerBDFrom.setSelection(Integer.parseInt(defFrom.split("_")[0]));

        spinnerBDFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String from = parent.getItemAtPosition(position).toString();
                String fromCode = from.split("_")[1];
                editor.putString(BaiduSettingsString.baiduFrom, position + "_" + fromCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerBDTo = (Spinner) findViewById(R.id.BDLangToSpinner);
        String defTo = preferences.getString(BaiduSettingsString.baiduTo, "0_0");
        spinnerBDTo.setSelection(Integer.parseInt(defTo.split("_")[0]));

        spinnerBDTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String to = parent.getItemAtPosition(position).toString();
                String toCode = to.split("_")[1];
                editor.putString(BaiduSettingsString.baiduTo, position + "_" + toCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    TextWatcher bdAPPIDTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(BaiduSettingsString.baiduAppId, editTextBDAPPID.getText().toString());
            editor.commit();
        }
    };

    TextWatcher bdKeyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(BaiduSettingsString.baiduKey, editTextBDKey.getText().toString());
            editor.commit();
        }
    };

    TextWatcher bdToastTimeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String time = editTextBDToastTime.getText().toString();

            // 检测是否是合法数字
            if (Number.isLegalToastTime(time)) {
                editor.putString(BaiduSettingsString.baiduToastTime, time);
                editor.commit();
            }

        }
    };

    public void baiduLogoOnClick(View view) {
        Uri uri = Uri.parse("http://api.fanyi.baidu.com/api/trans/product/index");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
