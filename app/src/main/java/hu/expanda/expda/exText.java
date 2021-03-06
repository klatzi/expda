package hu.expanda.expda;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * Created by Encsi on 2015.02.02..
 */


public class exText extends EditText {
    private Context parent;
    private ObjText textobj;
    private exPane pane;
    public exPane getPane() {
        return pane;
    }



    public exText(Context parent, Object b,ViewGroup layout,exPane pane){
        super(parent);
        this.parent=parent;
        this.textobj=(ObjText)b;
        this.pane = pane;
        this.setVisibility(getObj().getVisibility());
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        this.setText(getObj().getText());
        int width = getObj().getWidth();
        if (width==0) {
            width=60;
            getObj().setWidth(width);
        }


        int height = getObj().getHeight();
        if (height==0) {
            height=20;
            getObj().setHeight(height);
        }

        if (getObj().getForeColor() != -1) this.setTextColor(getObj().getForeColor());
        if (getObj().getBackColor() != -1) this.setBackgroundColor(getObj().getBackColor());
        this.setTextSize(getObj().getFontSize());
        if (getObj().isFontBold() && getObj().isFontItalic())
            this.setTypeface(null, Typeface.BOLD_ITALIC);
        else if (getObj().isFontBold() && !getObj().isFontItalic())
            this.setTypeface(null, Typeface.BOLD);
        else if (!getObj().isFontBold() && getObj().isFontItalic())
            this.setTypeface(null, Typeface.ITALIC);
        ArrayList<ObjStyle> styles = getObj().getStyle();

        if (styles!=null){
            for (ObjStyle style : styles) {
                this.setPadding(style.getPaddingLeft(), style.getPaddingTop(), style.getPaddingRight(), style.getPaddingBottom());
                if (style.getFontSize()>0) this.setTextSize(style.getFontSize());
                if (style.getForeColor() != -1) this.setTextColor(style.getForeColor());
                else this.setTextColor(Color.BLACK);
                if (style.getBackColor() != -1) this.setBackgroundColor(style.getBackColor());
                if (style.isFontBold() && style.isFontItalic())
                    this.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
                else if (style.isFontBold() && !style.isFontItalic())
                    this.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                else if (!style.isFontBold() && style.isFontItalic())
                    this.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
                if (style.getTop() != -99) this.setTop(style.getTop());
                if (style.getLeft() != -99) this.setLeft(style.getLeft());
                if (style.getHeight() != -99) this.setHeight(style.getHeight());
                if (style.getWidth() != -99) this.setWidth(style.getWidth());

            }
        }
        this.setSingleLine(true);
        layout.addView(this, new AbsoluteLayout.LayoutParams(getObj().getWidth(), getObj().getHeight(), getObj().getLeft(), getObj().getTop()));

        this.setTag(getObj().getName());

        this.setOnTouchListener((new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 return false;
            }
        }));
        this.setOnClickListener((new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getContext()).hideSoftKeyboard(v);
                    }
                })

        );
        this.setOnFocusChangeListener((new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((MainActivity)getContext()).hideSoftKeyboard(v);
                if (!hasFocus) {
                    getPane().luaInit(getObj().getLuaOnExit());
                    /*
                    try {
                        getPane().getExtLib().runMethod(getObj().getExtFunctionOnExit());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    */
                }
                else {
                    getPane().luaInit(getObj().getLuaOnEnter());
                }
            }
        }));
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    getPane().luaInit(getObj().getLuaOnChange());
                    /*
                    try {
                        getPane().getExtLib().runMethod(getObj().getExtFunctionOnChange());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    */

                    return true;
                } else return false;

            }
        });
       /* setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    getPane().luaInit(getObj().getLuaOnChange());
                    try {
                        getPane().getExtLib().runMethod(getObj().getExtFunctionOnChange());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return true;
                } else return false;
            }
        });
        */

    }


    public void setBounds(String command, int val){
        if (command.equalsIgnoreCase("SETTOP")) this.setTop1(val);
        if (command.equalsIgnoreCase("SETWIDTH")) this.setWidth1(val);
        if (command.equalsIgnoreCase("SETHEIGHT")) this.setHeight1(val);
        if (command.equalsIgnoreCase("SETLEFT")) this.setLeft1(val);
    }

    public void setFontColor(String colorstr){
        this.getObj().setForeColor(colorstr);
        this.setTextColor(Color.parseColor(colorstr));
    }
    public void setBgColor(String colorstr){
        this.getObj().setBackColor(colorstr);
        this.setBackgroundColor(Color.parseColor(colorstr));
    }


    public void setWidth1(int width){
        getObj().setWidth(width);
        this.setWidth(getObj().getWidth());
    }
    public void setHeight1(int height){
        getObj().setHeight(height);
        this.setHeight(getObj().getHeight());
    }


    public void setTop1(int top){
        getObj().setTop(top);
        this.setTop(getObj().getTop());

    }



    public void setLeft1(int left){
        getObj().setLeft(left);
        this.setLeft(getObj().getLeft());
    }


    public ObjText getObj(){
        return this.textobj;
    }
}
