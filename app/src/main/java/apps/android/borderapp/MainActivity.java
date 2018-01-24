package apps.android.borderapp;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import apps.android.borderapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //region:Main
    ActivityMainBinding binding;
    RectangleClass rectangleClass;
    //endregion

    //region:borderVariable

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rectangleClass = new RectangleClass(getApplicationContext());
        binding.mainView.addView(rectangleClass);
    }



    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
