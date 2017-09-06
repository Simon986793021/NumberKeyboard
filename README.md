# NumberKeyboard
封装好的数字键盘库

# 用法
step 1
Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2 Add the dependency

	dependencies {
	        compile 'com.github.Simon986793021:NumberKeyboard:v1.0'
	}
  
  Step 3 add layout 
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
  Step 4 use
    final OfoKeyboard keyboard = new OfoKeyboard(MainActivity.this);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText,true);
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText,false);

            }
        });
        keyboard.setOnOkClick(new OfoKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                Log.i(">>>>>>","点击了确定");
                Toast.makeText(MainActivity.this,editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        keyboard.setOnCancelClick(new OfoKeyboard.OnCancelClcik() {
            @Override
            public void onCancelClick() {
                Toast.makeText(MainActivity.this,"隐藏键盘",Toast.LENGTH_SHORT).show();
            }
        });
