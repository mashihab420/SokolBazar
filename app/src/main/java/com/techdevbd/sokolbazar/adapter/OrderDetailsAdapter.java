package com.techdevbd.sokolbazar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.activity.OnDataSend;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    List<ModelOrderProduct> productList;
    Context context;
    int price = 0;
    int total =0;
    OnDataSend onDataSend;

    public OrderDetailsAdapter(List<ModelOrderProduct> productList, Context context, OnDataSend onDataSend) {
        this.productList = productList;
        this.context = context;
        this.onDataSend = onDataSend;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(productList.get(position).getProduct_name());
        holder.price.setText("" + productList.get(position).getPrice());
        holder.quantity.setText("" + productList.get(position).getQuantity());
         int subtotal = productList.get(position).getPrice();
        String totall = productList.get(position).getTotal();
          String discount = productList.get(position).getDiscount();


         total = total+subtotal;
       // onDataSend.orderprice("" + productList.get(position).getSubtotal());
        onDataSend.orderprice(""+total,""+totall,""+discount);
        Glide
                .with(context)
                .load(productList.get(position).getImage_url())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title_name_id);
            price = itemView.findViewById(R.id.price_product_id);
            quantity = itemView.findViewById(R.id.cart_quantity_id);
            imageView = itemView.findViewById(R.id.product_image_id);
        }
    }
}
