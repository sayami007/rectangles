package apps.android.borderapp;

import android.databinding.DataBindingUtil;
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
        return true;
    }
}
