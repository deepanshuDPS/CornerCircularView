package com.ad97.ccv;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CornerCircularView cornerCircularView = findViewById(R.id.ccv);
        List<ModelInfo> modelInfo = new ArrayList<>();
        // adding information of the item to get menu on screen with these added items
        modelInfo.add(new ModelInfo("Message", Color.RED, R.drawable.message, R.color.colorAccent ));
        modelInfo.add(new ModelInfo("Mic", Color.BLUE, R.drawable.mic, R.color.colorPrimary));
        modelInfo.add(new ModelInfo("Photo", Color.MAGENTA, R.drawable.photo, R.color.colorPrimaryDark));
        modelInfo.add(new ModelInfo("Settings", Color.BLACK, R.drawable.settings, R.color.colorAccent));

        // setting menu items from our itemList (modelInfo)
        cornerCircularView.setMenuFromList(modelInfo);
        // setting addButton color according to our choice
        cornerCircularView.setAddButtonColor(Color.BLACK);

        cornerCircularView.onItemClick(new CornerCircularView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this,"You Clicked Item: "+(position+1),Toast.LENGTH_LONG).show();
            }
        });
    }
}
