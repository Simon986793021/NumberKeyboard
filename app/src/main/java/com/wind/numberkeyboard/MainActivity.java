package com.wind.numberkeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wind.keyboard.OfoKeyboard;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button changebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.et_numberplate);
        changebutton= (Button) findViewById(R.id.bt_change_keyboard);
        final OfoKeyboard keyboard = new OfoKeyboard(MainActivity.this);//获取到keyboard对象
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText,true);//eiditext绑定keyboard，true表示随机数字
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText,false);//eiditext绑定keyboard，false表示普通数字键盘

            }
        });
        /*
        确定按钮
         */
        keyboard.setOnOkClick(new OfoKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                Log.i(">>>>>>","点击了确定");
                Toast.makeText(MainActivity.this,editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //隐藏键盘按钮
        keyboard.setOnCancelClick(new OfoKeyboard.OnCancelClcik() {
            @Override
            public void onCancelClick() {
                Toast.makeText(MainActivity.this,"隐藏键盘",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
