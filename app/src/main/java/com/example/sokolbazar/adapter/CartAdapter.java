package com.example.sokolbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sokolbazar.R;
import com.example.sokolbazar.activity.CartActivity;
import com.example.sokolbazar.activity.OnDataSend;
import com.example.sokolbazar.model.ModelCartRoom;
import com.example.sokolbazar.repository.CartRepository;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<ModelCartRoom> cart;
    Context context;
    CartRepository repository;
    private OnDataSend dataSend;

    int total = 0;
    int taka = 0;
    int quantity;


    public CartAdapter(Context context, List<ModelCartRoom> cart, CartRepository repository, OnDataSend dataSend) {
        this.cart = cart;
        this.context = context;
        this.repository = repository;
        this.dataSend = dataSend;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(cart.get(position).getP_name());
        holder.price.setText(cart.get(position).getP_price());
        // holder.cart_quantity.setText(cart.get(position).getQuantity());

        holder.cart_quantity.setText(cart.get(position).getQuantity());


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitys = Integer.parseInt(cart.get(position).getQuantity());

                //TODO ai minus hoya data room a update kora lagbe
                if (quantitys > 1) {
                    quantitys -= 1;

                    CartRepository repository = new CartRepository(context);
                    cart.get(position).setQuantity("" + quantitys);;
                    repository.update(cart.get(position));

                    holder.cart_quantity.setText(cart.get(position).getQuantity());

                    taka = 0;
                    total =0;


                }
            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantitys = Integer.parseInt(cart.get(position).getQuantity());
                quantitys += 1;

                //TODO ai plus hoya data room a update kora lagbe

                CartRepository repository = new CartRepository(context);
                cart.get(position).setQuantity("" + quantitys);
                repository.update(cart.get(position));
                holder.cart_quantity.setText(cart.get(position).getQuantity());


                taka = 0;
                total =0;


            }
        });

       quantity = Integer.parseInt(cart.get(position).getQuantity());
        taka = (Integer.parseInt(cart.get(position).getP_price())) * quantity;
        Log.d("taka", ""+taka);
       total = total + taka;






        String offer = cart.get(position).getOffers();




        if (offer.equals("5")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer5)
                    .centerCrop()
                    .into(holder.offerpercent);


        }
        if (offer.equals("10")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer10)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("15")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer15)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("20")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer20)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("25")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer25)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("30")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer30)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("35")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer35)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("40")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer40)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("45")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer45)
                    .centerCrop()
                    .into(holder.offerpercent);
        }

        if (offer.equals("50")) {
            Glide
                    .with(context)
                    .load(R.drawable.offer50)
                    .centerCrop()
                    .into(holder.offerpercent);
        }


        Glide
                .with(context)
                .load(cart.get(position).getUrl())
                .centerCrop()
                .into(holder.productimage);


        String shopname = cart.get(position).getC_logo();


        if (shopname.equals("shwapno")) {
            Glide
                    .with(context)
                    .load(R.drawable.swapno)
                    .centerCrop()
                    .into(holder.brandimage);
            holder.productlayout.setBackgroundColor(Color.parseColor("#FE6268"));
        }
        if (shopname.equals("agora")) {
            Glide
                    .with(context)
                    .load(R.drawable.agora)
                    .centerCrop()
                    .into(holder.brandimage);
            holder.productlayout.setBackgroundColor(Color.parseColor("#74F5D2"));
        }

        if (shopname.equals("aarong")) {
            Glide
                    .with(context)
                    .load(R.drawable.aarong)
                    .centerCrop()
                    .into(holder.brandimage);
            holder.productlayout.setBackgroundColor(Color.parseColor("#FBA257"));
        }

        if (shopname.equals("")) {
            holder.productlayout.setBackgroundColor(Color.parseColor("#74F5D2"));
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  repository.deleteSingleData(cart.get(position).getId());

                try {
                    CartRepository repository = new CartRepository(context);
                    ModelCartRoom modelCartRoom = new ModelCartRoom();
                    modelCartRoom.setId(cart.get(position).getId());
                    total = 0;
                    repository.delete(modelCartRoom);
                    cart.clear();
                    cart.remove(cart.get(position).getId() - 1);

                    //taka =0;

                    notifyDataSetChanged();
                    //    Toast.makeText(context, ""+cart.get(position).getId(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                }


            }
        });


        dataSend.totalPrice("" + total);
      //  dataSend.totalPrice("" + positonnn);


    }


    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView productimage, brandimage, offerpercent, delete;
        public TextView title, price, cart_quantity, plus, minus;
        ConstraintLayout productlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_name_id);
            price = itemView.findViewById(R.id.price_product_id);
            productimage = itemView.findViewById(R.id.product_image_id);
            brandimage = itemView.findViewById(R.id.brand_logo_id);
            cart_quantity = itemView.findViewById(R.id.cart_quantity_id);
            offerpercent = itemView.findViewById(R.id.offer_id);
            productlayout = itemView.findViewById(R.id.productlayoutId);
            delete = itemView.findViewById(R.id.delete_fromCart);
            plus = itemView.findViewById(R.id.plusebtid);
            minus = itemView.findViewById(R.id.minusbtid);
        }
    }
}
