package com.example.directwoodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_orders);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        new FirebaseDatabaseHelper().readOrders(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Order> orders, List<String> keys) {
                findViewById(R.id.loading_orders).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView, SecondActivity.this, orders, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogout);

        // Logout function.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    // Logout function

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    // Inflate layout with menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        getMenuInflater().inflate(R.menu.menu, menu);
        if(user!=null){
            menu.getItem(0).setVisible(true); // New order
            menu.getItem(1).setVisible(true); // Logout
        }else{
            menu.getItem(0).setVisible(false); // New order
            menu.getItem(1).setVisible(false); // Logout
        }
        return true;
    }

    // Menu item - Logout and New Order

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        progressDialog = new ProgressDialog(this);

        switch(item.getItemId()){
            case R.id.new_order: {
                startActivity(new Intent(this, NewOrderActivity.class));
                return true;
            }
            case R.id.logoutMenu: {
                progressDialog.setMessage("Logging out");
                progressDialog.show();
                Logout();
                invalidateOptionsMenu();
            }
        }
        return true;
    }
}
