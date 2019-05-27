package com.example.directwoodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private OrdersAdapter mOrderAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Order> orders, List<String> keys) {
        mContext = context;
        mOrderAdapter = new OrdersAdapter(orders, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mOrderAdapter);
    }

    class OrderItemView extends RecyclerView.ViewHolder{
        private TextView mCost_material;
        private TextView mCust_name;
        private TextView mNo_of_rooms;
        private TextView mOrder_date;
        private TextView mRoom_area;
        private TextView mTotal_cost;
        private TextView mVat;
        private TextView mDateDaysRemaining;

        private String key;

        public OrderItemView (ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false));

            mCust_name = (TextView) itemView.findViewById(R.id.tvCustName);
            mTotal_cost = (TextView) itemView.findViewById(R.id.tvTotalCost);
            mOrder_date = (TextView) itemView.findViewById(R.id.tvOrderDate);
            mCost_material = (TextView) itemView.findViewById(R.id.tvMaterial);
            mDateDaysRemaining = (TextView) itemView.findViewById(R.id.tvDate);


            // Data for Update/Delete activity --> OrderDetailsActivity

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("cust_name", mCust_name.getText().toString());
                    intent.putExtra("order_date", mOrder_date.getText().toString());
                    intent.putExtra("total_cost", mTotal_cost.getText().toString());
                    intent.putExtra("cost_material", mCost_material.getText().toString());
                    // Add more items (roomL, roomW etc.)

                    mContext.startActivity(intent);
                }
            });
        }

        // Bind Data

        public void bind(Order order, String key){
            mCust_name.setText(order.getCust_name());
            mOrder_date.setText(order.getOrder_date());
            mTotal_cost.setText(order.getTotal_cost());
            //mCost_material.setText(order.getCost_material());
            this.key = key;

            // Show remaining days to complete a order.
            // Not working properly, does not account for months. Works only in days.

            String orderDate = mOrder_date.getText().toString();
            Calendar calCurr = Calendar.getInstance();
            Calendar day = Calendar.getInstance();
            try {
                day.setTime(new SimpleDateFormat("dd/mm/yyyy").parse(orderDate));
                if(day.before(calCurr)){
                    mDateDaysRemaining.setText("" + (day.get(Calendar.DATE) - (calCurr.get(Calendar.DATE))));
                }else{
                    mDateDaysRemaining.setText("error");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    class OrdersAdapter extends RecyclerView.Adapter<OrderItemView> {
        private List<Order> mOrderList;
        private List<String> mKeys;

        public OrdersAdapter(List<Order> mOrderList, List<String> mKeys) {
            this.mOrderList = mOrderList;
            this.mKeys = mKeys;
        }

        @Override
        public OrderItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OrderItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderItemView holder, int position) {
            holder.bind(mOrderList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mOrderList.size();
        }
    }
}
