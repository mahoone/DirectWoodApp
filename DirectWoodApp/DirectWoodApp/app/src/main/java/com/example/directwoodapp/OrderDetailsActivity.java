package com.example.directwoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private EditText mCustName;
    private EditText mRoomL;
    private EditText mRoomW;
    private EditText mDueDate;
    private TextView mRoomArea;
    private Spinner mMaterial;
    private Button mUpdate, mDelete;
    private Double area, sub, vat, total;
    private String AreaConvert;
    private String TotalConvert, VATConvert, SubConvert;
    private TextView tvSubTotal, tvTotal, tvVat;
    private Double L, W, A;

    //Receiving data from object

    private String key;
    private String cust_name;
    private String order_date;
    private String total_cost;
    private String cost_material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Data received from Main activity

        getDataMain();

        // Set user UIs

        getuserUIs();

        // Update order

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()) {

                // Calculate Length and Width of Room

                L = Double.parseDouble(mRoomL.getText().toString());
                W = Double.parseDouble(mRoomW.getText().toString());
                A = L * W;

                if (mMaterial.getSelectedItem().toString().equals("Tiles")){

                    // Calculate Subtotal & Convert/Display as String
                    sub = A * 35;
                    SubConvert = String.valueOf(sub);
                    tvSubTotal.setText("£" + SubConvert);

                    vat = (sub * 20) / 100;
                    VATConvert = String.valueOf(vat);
                    tvVat.setText("£" + VATConvert);

                    total = sub + vat;
                    TotalConvert = String.valueOf(total);
                    tvTotal.setText("£" + TotalConvert);

                }
                else if (mMaterial.getSelectedItem().toString().equals("Wood")){

                    // Calculate Subtotal & Convert/Display as String
                    sub = A * 30;
                    SubConvert = String.valueOf(sub);
                    tvSubTotal.setText("£" + SubConvert);

                    vat = (sub * 20) / 100;
                    VATConvert = String.valueOf(vat);
                    tvVat.setText("£" + VATConvert);

                    total = sub + vat;
                    TotalConvert = String.valueOf(total);
                    tvTotal.setText("£" + TotalConvert);
                }
                else if (mMaterial.getSelectedItem().toString().equals("Laminate")){

                    // Calculate Subtotal & Convert/Display as String
                    sub = A * 25;
                    SubConvert = String.valueOf(sub);
                    tvSubTotal.setText("£" + SubConvert);

                    vat = (sub * 20) / 100;
                    VATConvert = String.valueOf(vat);
                    tvVat.setText("£" + VATConvert);

                    total = sub + vat;
                    TotalConvert = String.valueOf(total);
                    tvTotal.setText("£" + TotalConvert);
                }
                else if (mMaterial.getSelectedItem().toString().equals("Carpet")){

                    // Calculate Subtotal & Convert/Display as String
                    sub = A * 20;
                    SubConvert = String.valueOf(sub);
                    tvSubTotal.setText("£" + SubConvert);

                    vat = (sub * 20) / 100;
                    VATConvert = String.valueOf(vat);
                    tvVat.setText("£" + VATConvert);

                    total = sub + vat;
                    TotalConvert = String.valueOf(total);
                    tvTotal.setText("£" + TotalConvert);
                }
                else if (mMaterial.getSelectedItem().toString().equals("Vinyl")){

                    // Calculate Subtotal & Convert/Display as String
                    sub = A * 15;
                    SubConvert = String.valueOf(sub);
                    tvSubTotal.setText("£" + SubConvert);

                    vat = (sub * 20) / 100;
                    VATConvert = String.valueOf(vat);
                    tvVat.setText("£" + VATConvert);

                    total = sub + vat;
                    TotalConvert = String.valueOf(total);
                    tvTotal.setText("£" + TotalConvert);
                }
                else{
                    Toast.makeText(OrderDetailsActivity.this, "Please select material", Toast.LENGTH_SHORT).show();
                }

                // Convert int values to strings

                AreaConvert = String.valueOf(A);
                mRoomArea.setText(AreaConvert + " M2");

                AreaConvert = String.valueOf(L);
                mRoomL.setText(AreaConvert);

                AreaConvert = String.valueOf(W);
                mRoomW.setText(AreaConvert);

                Order order = new Order();
                order.setRoomL(mRoomL.getText().toString());
                order.setRoomW(mRoomW.getText().toString());
                order.setCust_name(mCustName.getText().toString());
                order.setRoom_area(mRoomArea.getText().toString());
                order.setOrder_date(mDueDate.getText().toString());
                order.setVat(tvVat.getText().toString());
                order.setTotal_cost(tvTotal.getText().toString());
                order.setCost_material(mMaterial.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateOrder(key, order, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Order> orders, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(OrderDetailsActivity.this, "Order updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }}
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteOrder(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Order> orders, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(OrderDetailsActivity.this, "Order deleted", Toast.LENGTH_SHORT).show();
                        finish(); return;
                    }
                });
            }
        });
    }

    // Get current position of spinner.

    private int getIndex_SpinnerItem(Spinner spinner, String item) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    // Textbox validation

    private Boolean validation(){
        Boolean result = false;

        String name = mCustName.getText().toString();
        String date = mDueDate.getText().toString();
        String roomL = mRoomL.getText().toString();
        String roomW = mRoomW.getText().toString();

        if(name.isEmpty() || date.isEmpty() || roomL.isEmpty() || roomW.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void getDataMain(){
        key = getIntent().getStringExtra("key");
        cust_name = getIntent().getStringExtra("cust_name");
        order_date = getIntent().getStringExtra("order_date");
        total_cost = getIntent().getStringExtra("total_cost");
        cost_material = getIntent().getStringExtra("cost_material");
    }

    private void getuserUIs(){
        mCustName = (EditText)findViewById(R.id.etCustName);
        mCustName.setText(cust_name);
        mDueDate = (EditText)findViewById(R.id.etOrderDueDate);
        mDueDate.setText(order_date);
        mRoomArea = (TextView)findViewById(R.id.tvTotalRoomArea);
        tvSubTotal = (TextView)findViewById(R.id.tvSubTotal);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvTotal.setText(total_cost);
        tvVat = (TextView)findViewById(R.id.tvVat);
        mRoomL = (EditText) findViewById(R.id.etRoomL);
        mRoomW = (EditText) findViewById(R.id.etRoomW);
        mUpdate = (Button)findViewById(R.id.btnUpdateOrder);
        mDelete = (Button)findViewById(R.id.btnDelete);
        mMaterial = (Spinner)findViewById(R.id.spMaterials);
        mMaterial.setSelection(getIndex_SpinnerItem(mMaterial, cost_material));
    }
}
