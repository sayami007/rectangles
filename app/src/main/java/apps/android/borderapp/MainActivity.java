package apps.android.borderapp;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import apps.android.borderapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //region:Variables
    ActivityMainBinding binding;
    MainBorderClass mainBorderClass;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBorderClass = new MainBorderClass(this);
        binding.mainView.addView(mainBorderClass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change, menu);
        return true;
    }

    private boolean showItems = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.erase:
                mainBorderClass.setControlItemVisible(false);
                mainBorderClass.getBorder().setErasable(true);
                mainBorderClass.getBorder().setFinalBitmap(true);
                mainBorderClass.getBorder().invalidate();
                return true;
            case R.id.color:
                mainBorderClass.getBorder().getmPaint().setColor(Color.RED);
                mainBorderClass.getBorder().getmPaint().setStrokeWidth(2000);
                mainBorderClass.getBorder().invalidate();
                return true;
        }
        return false;
    }
}
