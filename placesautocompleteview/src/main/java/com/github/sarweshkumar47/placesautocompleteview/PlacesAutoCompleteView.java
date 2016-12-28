package com.github.sarweshkumar47.placesautocompleteview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import static com.github.sarweshkumar47.placesautocompleteview.Utils.Constants.BUILD_DEBUG;

public class PlacesAutoCompleteView extends AutoCompleteTextView {

    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 250;
    private String TAG = "PlacesAutoCompleteView";
    private String geoCodingApiKey = "";
    private String languageQuery = "en-US";
    private int autoTextThreshold = 3;
    private int autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private final String baseURL = "http://locationiq.org/v1/search.php?key=";
    private static String geoCodingUrl = null;
    private static int textSuggestViewBackground = Color.WHITE;
    private static int textSuggestViewFontColor = Color.BLACK;
    private static int textSuggestViewTextSize = 16;
    private static boolean singleLineEnabled = false;


    private Drawable drawable;
    private Handler mHandler;
    private Runnable mRunnable;
    private boolean mEditedByProgram = false;

    public PlacesAutoCompleteView(Context context) {
        super(context);
        init(context, null);
    }

    public PlacesAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(BUILD_DEBUG)Log.d(TAG, "PlacesAutoCompleteView, attributes init");
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.PlacesAutoCompleteView);

            final int N = a.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.PlacesAutoCompleteView_language) {
                    setLanguageQuery(a.getString(attr));

                } else if (attr == R.styleable.PlacesAutoCompleteView_geocoding_api_key) {
                    setGeoCodingApiKey(a.getString(attr));

                } else if (attr == R.styleable.PlacesAutoCompleteView_autoTextThreshold) {
                    setAutoTextThreshold(a.getInteger(attr, -1));

                } else if (attr == R.styleable.PlacesAutoCompleteView_autoTextDelay) {
                    setAutoCompleteDelay(a.getInteger(attr, -1));

                } else if (attr == R.styleable.PlacesAutoCompleteView_textSuggestViewTextSize) {
                    setTextSuggestViewTextSize(a.getDimensionPixelSize(
                            R.styleable.PlacesAutoCompleteView_textSuggestViewTextSize, -1));

                } else if (attr == R.styleable.PlacesAutoCompleteView_textSuggestSingleLineEnabled) {
                    setSingleLineEnabled(a.getBoolean(
                            R.styleable.PlacesAutoCompleteView_textSuggestSingleLineEnabled, false));

                } else if (attr == R.styleable.PlacesAutoCompleteView_textSuggestViewBackground) {
                    setTextSuggestViewBackground(a.getColor(attr, -1));

                } else if (attr == R.styleable.PlacesAutoCompleteView_textSuggestViewFontColor) {
                    setTextSuggestViewFontColor(a.getColor(attr, -1));

                } else if (attr == R.styleable.PlacesAutoCompleteView_clearButtonDrawable) {
                    setDrawable(a.getDrawable(attr));

                }

            }
            a.recycle();
        }

        initHandler();
        initUrl();
        initThreshold(getAutoTextThreshold());
        initTextListener();
        initClearDrawable(context);

        PlacesAutoCompleteAdapter adapter = new PlacesAutoCompleteAdapter(
                context, R.layout.lib_places_auto_complete_item_view);
        super.setAdapter(adapter);
    }

    private void initHandler() {
        mHandler = new Handler();
    }

    private void initUrl() {
        setGeoCodingUrl(baseURL + getGeoCodingApiKey() + "&format=json"
                + "&accept-language=" + getLanguageQuery() + "&q=");
    }

    private void initThreshold(int threshold) {
        super.setThreshold(threshold);
    }

    private void initTextListener() {
        super.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, addTextChangedListener beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, addTextChangedListener onTextChanged");
                if (s.length() > 0)
                    hideShowDrawable(true);
                else
                    hideShowDrawable(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, addTextChangedListener afterTextChanged");

            }
        });
    }


    private void initClearDrawable(Context cont) {

        if (drawable == null)
            drawable = ContextCompat.getDrawable(cont, R.drawable.ic_close_black_24dp);

        setCompoundDrawablePadding(8);
        drawable.setBounds(new Rect(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
        if (super.getCompoundDrawables()[2] == null) {
            super.setCompoundDrawables(null, null, drawable, null);
        }
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(TAG, "PlacesAutoCompleteView, setOnTouchListener onTouch");

                final int DRAWABLE_RIGHT = 2;
                if (getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - drawable
                            .getIntrinsicWidth());
                    if (tappedX) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            setText("");
                        }
                        return true;
                    }
                }
                return false;
            }

        });
        hideShowDrawable(false);
    }

    private void hideShowDrawable(boolean show) {
        if (show)
            super.setCompoundDrawables(null, null, drawable, null);
        else
            super.setCompoundDrawables(null, null, null, null);

    }

    @Override
    protected void replaceText(CharSequence text) {
        if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, replaceText");
        super.replaceText(text);
        setmEditedByProgram(true);
    }

    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        //super.performFiltering(text, keyCode);
        mHandler.removeCallbacks(mRunnable);
        if (!ismEditedByProgram()) {
            mRunnable = new Runnable() {
                public void run() {
                    if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, performFiltering: " + keyCode);
                    PlacesAutoCompleteView.super.performFiltering(text, keyCode);
                }
            };
            mHandler.postDelayed(mRunnable, getAutoCompleteDelay());
        } else {
            setmEditedByProgram(false);
        }
    }

    @Override
    public void onFilterComplete(int count) {
        if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, onFilterComplete");
        super.onFilterComplete(count);
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)) {
                return true;
            }
        }

        return super.onKeyPreIme(keyCode, event);
    }

    public String getGeoCodingApiKey() {
        return geoCodingApiKey;
    }

    public void setGeoCodingApiKey(String geoCodingApiKey) {
        this.geoCodingApiKey = geoCodingApiKey;
    }

    public String getLanguageQuery() {
        return languageQuery;
    }

    public void setLanguageQuery(String languageQuery) {
        this.languageQuery = languageQuery;
    }

    public int getAutoTextThreshold() {
        return autoTextThreshold;
    }

    public void setAutoTextThreshold(int autoTextThreshold) {
        this.autoTextThreshold = autoTextThreshold;
    }

    public int getAutoCompleteDelay() {
        return autoCompleteDelay;
    }

    public void setAutoCompleteDelay(int autoCompleteDelay) {
        this.autoCompleteDelay = autoCompleteDelay;
    }

    public static String getGeoCodingUrl() {
        return geoCodingUrl;
    }

    public static void setGeoCodingUrl(String geoCodingUrl) {
        PlacesAutoCompleteView.geoCodingUrl = geoCodingUrl;
    }

    public static int getTextSuggestViewBackground() {
        return textSuggestViewBackground;
    }

    public static void setTextSuggestViewBackground(int textSuggestViewBackground) {
        PlacesAutoCompleteView.textSuggestViewBackground = textSuggestViewBackground;
    }

    public static int getTextSuggestViewFontColor() {
        return textSuggestViewFontColor;
    }

    public static void setTextSuggestViewFontColor(int textSuggestViewFontColor) {
        PlacesAutoCompleteView.textSuggestViewFontColor = textSuggestViewFontColor;
    }

    public static int getTextSuggestViewTextSize() {
        return textSuggestViewTextSize;
    }

    public static void setTextSuggestViewTextSize(int textSuggestViewTextSize) {
        PlacesAutoCompleteView.textSuggestViewTextSize = textSuggestViewTextSize;
    }

    public static boolean isSingleLineEnabled() {
        return singleLineEnabled;
    }

    public static void setSingleLineEnabled(boolean singleLineEnabled) {
        PlacesAutoCompleteView.singleLineEnabled = singleLineEnabled;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean ismEditedByProgram() {
        synchronized (getContext()) {
            return mEditedByProgram;
        }
    }

    public void setmEditedByProgram(boolean mEditedByProgram) {
        synchronized (getContext()) {
            this.mEditedByProgram = mEditedByProgram;
        }
    }
}
