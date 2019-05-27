package com.example.directwoodapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NewOrderActivity extends AppCompatActivity {
    public static String EXTRA_TEXT = "com.example.directwoodapp.EXTRA_TEST";
    public static String EXTRA_TEXT_2 = "com.example.directwoodapp.EXTRA_TEST_2";
    public static String EXTRA_TEXT_3 = "com.example.directwoodapp.EXTRA_TEST_3";
    public static String EXTRA_TEXT_4 = "com.example.directwoodapp.EXTRA_TEST_4";
    public static String EXTRA_TEXT_5 = "com.example.directwoodapp.EXTRA_TEST_5";

    private EditText mCustName;
    private EditText mRoomL;
    private EditText mRoomW;
    private EditText mDueDate;
    private TextView mRoomArea;
    private Spinner mMaterial;
    private Button mSave;
    private Double L, W, A;
    private Double area, sub, vat, total;
    private String AreaConvert;
    private String TotalConvert, VATConvert, SubConvert;
    private TextView tvSubTotal, tvTotal, tvVat;
    private FirebaseAuth firebaseAuth;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        mCustName = (EditText)findViewById(R.id.etCustName);
        mRoomL = (EditText)findViewById(R.id.etRoomL);
        mRoomW = (EditText)findViewById(R.id.etRoomW);
        mDueDate = (EditText)findViewById(R.id.etOrderDueDate);
        mMaterial = (Spinner)findViewById(R.id.spMaterials);
        mRoomArea = (TextView)findViewById(R.id.tvTotalRoomArea);
        tvSubTotal = (TextView)findViewById(R.id.tvSubTotal);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvVat = (TextView)findViewById(R.id.tvVat);

        mSave = (Button)findViewById(R.id.btnUpdateOrder);

        // Save Order and calculate total cost of order

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validationText()){

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
                    Toast.makeText(NewOrderActivity.this, "Please select material", Toast.LENGTH_SHORT).show();
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


                new FirebaseDatabaseHelper().addOrder(order, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Order> orders, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        openPopActivity();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                    }
                });
            }}
        });

    }
    // New popup activity

    public void openPopActivity(){
        String custName = mCustName.getText().toString();
        String totalCost = tvTotal.getText().toString();
        String materialUpdate = mMaterial.getSelectedItem().toString();
        String orderDueDate = mDueDate.getText().toString();
        String roomSizeUpdate = mRoomArea.getText().toString();

        Intent intent = new Intent(this, PopActivity.class);
        intent.putExtra(EXTRA_TEXT, custName);
        intent.putExtra(EXTRA_TEXT_2, totalCost);
        intent.putExtra(EXTRA_TEXT_3, materialUpdate);
        intent.putExtra(EXTRA_TEXT_4, orderDueDate);
        intent.putExtra(EXTRA_TEXT_5, roomSizeUpdate);
        startActivity(intent);
    }

    // Textbox validation

    private Boolean validationText(){
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
}
