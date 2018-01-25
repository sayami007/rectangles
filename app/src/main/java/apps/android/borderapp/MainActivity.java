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
    RectangleClass rectangleClass;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rectangleClass = new RectangleClass(this);
        binding.mainView.addView(rectangleClass);
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
            case R.id.ok:
                rectangleClass.setControlItemsHidden(showItems);
                this.showItems = !showItems;
                rectangleClass.getBorder().setErasable(false);
                return true;
            case R.id.erase:
                rectangleClass.setControlItemsHidden(false);
                rectangleClass.getBorder().setErasable(true);
                rectangleClass.getBorder().setFinalBitmap(true);
                rectangleClass.getBorder().invalidate();
                return true;
            case R.id.color:
                rectangleClass.getBorder().getmPaint().setColor(Color.RED);
                rectangleClass.getBorder().getmPaint().setStrokeWidth(40);
                rectangleClass.getBorder().invalidate();
                return true;
        }
        return false;
    }
}
