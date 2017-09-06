package com.wind.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangcong on 2017/8/28.
 */

public class    OfoKeyboard {
    private Activity activity;
    private Keyboard keyboard;
    private OfoKeyboardView keyboardView;
    private EditText editText;
    private boolean isRandom=false;
    public  OfoKeyboard (Activity activity )
    {
        this.activity=activity;
        keyboardView= (OfoKeyboardView) activity.findViewById(R.id.keyboard_view);
    }
    //点击事件触发
    public void attachTo(EditText editText,boolean isRandom){

        /*
        切换键盘需要重新new Keyboard对象，否则键盘不会改变,keyboardView放到构造函数里面，避免每次点击重新new 对象，提高性能
         */
        keyboard=new Keyboard(activity,R.xml.keyboard);
        this.isRandom=isRandom;
        Log.i(">>>>>","attachTo");
        this.editText=editText;
        hideSystemSofeKeyboard(activity,editText);
        showSoftKeyboard();
    }


    private void showSoftKeyboard() {
        if (keyboard == null) {
            keyboard = new Keyboard(activity, R.xml.keyboard);
        }
        if(keyboardView==null)
        {
            keyboardView= (OfoKeyboardView) activity.findViewById(R.id.keyboard_view);
        }
        if (isRandom)
        {
            randomKeyboardNumber();
        }
        else {
            keyboardView.setKeyboard(keyboard);
        }
            keyboardView.setEnabled(true);
            keyboardView.setPreviewEnabled(false);
            keyboardView.setVisibility(View.VISIBLE);
            keyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable=editText.getText();
            int start =editText.getSelectionStart();
            if (primaryCode==Keyboard.KEYCODE_DELETE)//key  codes 为-5
            {
                if (editable!=null&&editable.length()>0)
                {
                    if (start>0)
                    {
                        editable.delete(start-1,start);
                    }
                }
            }
            else if (primaryCode==Keyboard.KEYCODE_CANCEL)
            {
                hideKeyBoard();
                if (mCancelClick!=null)
                {
                    mCancelClick.onCancelClick();
                }
            }
            else if (primaryCode==Keyboard.KEYCODE_DONE)
            {
                hideKeyBoard();
                if (mOkClick!=null)
                {
                    mOkClick.onOkClick();
                }
            }
           else {
                Log.i(">>>>>>",primaryCode+"1");
                Log.i(">>>>>>",(char) primaryCode+"2");
                editable.insert(start,Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
    public interface OnOkClick {
        void onOkClick();
    }
    public interface OnCancelClcik{
        void onCancelClick();
    }
    public OnOkClick mOkClick;
    public OnCancelClcik mCancelClick;
    public void setOnOkClick(OnOkClick onOkClick)
    {
        this.mOkClick=onOkClick;
    }
    public void setOnCancelClick (OnCancelClcik onCancelClick)
    {
        this.mCancelClick=onCancelClick;
    }
    private void hideKeyBoard() {
        int visibility=keyboardView.getVisibility();
        if (visibility==KeyboardView.VISIBLE)
        {
            keyboardView.setVisibility(KeyboardView.GONE);
        }
    }
    private boolean isNumber(String str) {
        String wordstr = "0123456789";
        return wordstr.contains(str);
    }

    private void randomKeyboardNumber() {
        List<Keyboard.Key> keyList = keyboard.getKeys();
        // 查找出0-9的数字键
        List<Keyboard.Key> newkeyList = new ArrayList<Keyboard.Key>();
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).label != null
                    && isNumber(keyList.get(i).label.toString())) {
                newkeyList.add(keyList.get(i));
            }
        }
        // 数组长度
        int count = newkeyList.size();
        // 结果集
        List<KeyModel> resultList = new ArrayList<KeyModel>();
        // 用一个LinkedList作为中介
        LinkedList<KeyModel> temp = new LinkedList<KeyModel>();
        // 初始化temp
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(48 + i, i + ""));
        }
        // 取数
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            resultList.add(new KeyModel(temp.get(num).getCode(),
                    temp.get(num).getLable()));
            temp.remove(num);
        }
        for (int i = 0; i < newkeyList.size(); i++) {
            newkeyList.get(i).label = resultList.get(i).getLable();
            newkeyList.get(i).codes[0] = resultList.get(i)
                    .getCode();
        }
     //   hideKeyBoard();
        keyboardView.setKeyboard(keyboard);
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        Log.i(">>>>>","hide");
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
