package com.example.sokolbazar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sokolbazar.R;
import com.example.sokolbazar.activity.CartActivity;
import com.example.sokolbazar.fragment.FragmentHome;
import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelCart;
import com.example.sokolbazar.model.ModelCartRoom;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<ModelCartRoom> cart;
     Context context;

    public CartAdapter(Context context, List<ModelCartRoom> cart) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      
        holder.title.setText(cart.get(position).getP_name());
        holder.price.setText(cart.get(position).getP_price());
        holder.cart_quantity.setText(cart.get(position).getQuantity());

        String offer = cart.get(position).getOffers();

        if (offer.equals("20")){
            holder.offerpercent.setVisibility(View.VISIBLE);
        }


        Glide
                .with(context)
                .load(cart.get(position).getUrl())
                .centerCrop()
                .into(holder.productimage);

        Glide
                .with(context)
                .load(R.drawable.demopic2)
                .centerCrop()
                .into(holder.brandimage);

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView productimage,brandimage,offerpercent;
        public TextView title,price,cart_quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_name_id);
            price = itemView.findViewById(R.id.price_product_id);
            productimage = itemView.findViewById(R.id.product_image_id);
            brandimage = itemView.findViewById(R.id.brand_logo_id);
            cart_quantity = itemView.findViewById(R.id.cart_quantity_id);
            offerpercent = itemView.findViewById(R.id.offer_id);
        }
    }
}
