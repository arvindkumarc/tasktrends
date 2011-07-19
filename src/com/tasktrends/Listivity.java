package com.tasktrends;

import android.app.Activity;
import android.os.Bundle;
import com.tasktrends.dao.DataHelper;

public class Listivity extends Activity {
    private DataHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tasktrends.R.layout.main);
        initializeControls();
        dbHelper = DataHelper.getInstance(getApplicationContext());
    }

    private void initializeControls() {

    }

}
