package com.example.directwoodapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopActivity extends Activity {

    private TextView customerName;
    private TextView material;
    private TextView orderDate;
    private TextView roomSize;
    private TextView totalCost;

    //Receiving data from object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        // Get Data from NewOrderActivity

        customerName = (TextView)findViewById(R.id.tvCustomerName);
        material = (TextView)findViewById(R.id.tvMaterial);
        orderDate = (TextView)findViewById(R.id.tvOrderDueDate);
        roomSize = (TextView)findViewById(R.id.tvRoomSize);
        totalCost = (TextView)findViewById(R.id.tvTotalCost);

        // Data received from Main activity

        Intent intent = getIntent();
        String customer_name = intent.getStringExtra(NewOrderActivity.EXTRA_TEXT);
        String total_cost = intent.getStringExtra(NewOrderActivity.EXTRA_TEXT_2);
        String materialUpdate = intent.getStringExtra(NewOrderActivity.EXTRA_TEXT_3);
        String orderDueDate = intent.getStringExtra(NewOrderActivity.EXTRA_TEXT_4);
        String roomSizeUpdate = intent.getStringExtra(NewOrderActivity.EXTRA_TEXT_5);

        customerName.setText("Customer name: " + customer_name);
        material.setText("Material: " + materialUpdate);
        totalCost.setText("Total cost Inc. VAT: " + total_cost);
        orderDate.setText("Order Due: " + orderDueDate);
        roomSize.setText("Room size: " + roomSizeUpdate);

        // parameters for pop up window

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}
