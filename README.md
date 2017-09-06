# NumberKeyboard
封装好的数字键盘库

![image](https://github.com/Simon986793021/NumberKeyboard/blob/master/keyboard/src/main/res/drawable/gif.gif)


# 用法
1.在项目build.gradle文件中添加jitpack，添加jitpcak就够了。

```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

2.在module 的build.gradle文件添加依赖

```
  compile 'com.github.Simon986793021:NumberKeyboard:v1.0'
```

3.在布局文件中添加布局

```
<com.wind.keyboard.OfoKeyboardView
        android:id="@+id/keyboard_view"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:focusable="true"
        android:paddingTop="0dp"
        android:focusableInTouchMode="true"
        android:keyBackground="@drawable/bg_keyboardview"
        android:keyPreviewOffset="0dp"
        android:keyTextColor="#000"
        android:shadowColor="#fff"
        android:shadowRadius="0.0"
        android:layout_alignParentBottom="true"
        />
```

4.在MainActivity调用。

```
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
```
