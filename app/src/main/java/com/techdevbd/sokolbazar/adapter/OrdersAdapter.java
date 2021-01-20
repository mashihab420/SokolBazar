package com.techdevbd.sokolbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.activity.ConfirmOrderActivity;
import com.techdevbd.sokolbazar.activity.DeliveryActivity;
import com.techdevbd.sokolbazar.activity.OrderDetailsActivity;
import com.techdevbd.sokolbazar.activity.OrdersActivity;
import com.techdevbd.sokolbazar.activity.TrackOrderActivity;
import com.techdevbd.sokolbazar.model.ModelCartRoom;

import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    List<ModelOrderProduct> orders;
    Context context;

    public OrdersAdapter(List<ModelOrderProduct> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ordernumber.setText("#"+orders.get(position).getInvoice_number());
        holder.datetime.setText(orders.get(position).getEntry_time());

        String orderid = orders.get(position).getInvoice_number();
        String phone = orders.get(position).getPhone();
        String deliverytype = orders.get(position).getDelivery_type();
        String orderotpnum = orders.get(position).getOrderOtp();

        String text = orderid+","+phone;

        QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,500);
        try{
            Bitmap qrbits = qrgEncoder.getBitmap();
            holder.qrcode.setImageBitmap(qrbits);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        holder.truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackOrderActivity.class);
                intent.putExtra("order_id",orderid);
                intent.putExtra("phone",phone);
                intent.putExtra("delivery_type",deliverytype);
                intent.putExtra("orders_otp",orderotpnum);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("order_id",orderid);
                //intent.putExtra("phone",phone);
                // intent.putExtra("delivery_type",deliverytype);
                intent.putExtra("phone",phone);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView qrcode,truck;
        TextView ordernumber,datetime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            qrcode = itemView.findViewById(R.id.imageView6);
            ordernumber = itemView.findViewById(R.id.textView10);
            datetime = itemView.findViewById(R.id.textView14);
            truck = itemView.findViewById(R.id.imageView18);

        }
    }
}
