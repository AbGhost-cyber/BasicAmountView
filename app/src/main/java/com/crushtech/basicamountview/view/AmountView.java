package com.crushtech.basicamountview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.crushtech.basicamountview.R;

/**
 * @author UDO ABUNDANCE & CHRIS
 * Date: 2021/6/18
 * Class description: a simple amount view with incxrease and decrease functioning buttons.
 */
public class AmountView extends FrameLayout {

    private static final String TAG = "AmountView";
    private volatile double count = 0; //购买数量
    private String format;
    private double max = 9999999; //商品库存
    private Context context;


    boolean onlyInt = false;

    boolean enableButtons = false;

    private EditText etAmount;
    private TextView btnDecrease;
    private TextView btnIncrease;
    int btnTextSize, tvTextSize;

    private final View View;

    public AmountView(Context context) {
        this(context, null);
        this.context = context;
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        View = LayoutInflater.from(context).inflate(R.layout.amount_view, this);
        etAmount = findViewById(R.id.etAmount);
        etAmount.setCursorVisible(false);
        etAmount.setFocusable(false);
        etAmount.setFocusableInTouchMode(false);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);

        try {
            tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
            btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
            int count = obtainStyledAttributes.getInteger(R.styleable.AmountView_count, 1);
            boolean visible = obtainStyledAttributes.getBoolean(R.styleable.AmountView_onlyShowCount, true);
            enableButtons(visible);
            setCount(count);
        } finally {
            obtainStyledAttributes.recycle();
        }


        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }

        btnDecrease.setOnClickListener(v -> {
            Log.d(TAG, "onClick: decreased");
            if (count >= 1) {
                count--;
                setValue(etAmount, count);
            }
        });
        btnIncrease.setOnClickListener(v -> {
            Log.d(TAG, "onClick: increase");
            if (count < max) {
                count++;
                setValue(etAmount, count);
            }
        });
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setCount(double count) {
        this.count = count;
        setValue(etAmount, count);
    }

    public void setCount(double count, boolean defaultBlank) {
        if (defaultBlank) {
            this.count = 0;
            etAmount.setText("");
        } else {
            this.count = count;
            setValue(etAmount, count);
        }
    }


    public void setOnlyInt(boolean onlyInt) {
        this.onlyInt = onlyInt;
        setValue(etAmount, count);
    }

    public void setOnlyInt(boolean onlyInt, String format) {
        this.onlyInt = onlyInt;
        this.format = format;
        setValue(etAmount, count);
    }

    public void enableButtons(boolean isEnabled) {
        this.enableButtons = isEnabled;
        etAmount.setEnabled(isEnabled);
        btnDecrease.setEnabled(isEnabled);
        btnIncrease.setEnabled(isEnabled);

        etAmount.setBackgroundColor(Color.TRANSPARENT);
        btnDecrease.setBackgroundColor(Color.TRANSPARENT);
        btnIncrease.setBackgroundColor(Color.TRANSPARENT);
        this.View.setBackgroundColor(Color.TRANSPARENT);
        handleColors(R.color.divider);
    }

    public void handleColors(@ColorRes int color) {
        if (!enableButtons) {
            btnIncrease.setTextColor(ContextCompat.getColor(context, color));
            btnDecrease.setTextColor(ContextCompat.getColor(context, color));
        } else {
            btnIncrease.setTextColor(ContextCompat.getColor(context, R.color.black));
            btnDecrease.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }


    private void setValue(EditText editText, double value) {
        if (format != null && format.length() > 0) {
            String str = String.format(format, value);
            editText.setText(str);
        } else {
            if (onlyInt || isInteger(value)) {
                editText.setText(String.format("%.0f", value));
            } else {
                editText.setText(String.format("%.2f", value));
            }
        }

        editText.setSelection(editText.getText() == null ? 0 : editText.getText().length());
    }

    /**
     * Check if the passed argument is an integer value.
     *
     * @param number double
     * @return true if the passed argument is an integer value.
     */
    boolean isInteger(double number) {
        return number % 1 == 0;
    }

}
