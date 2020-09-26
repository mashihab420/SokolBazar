package com.example.sokolbazar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.sokolbazar.fragment.FragmentHome;
import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelCart;
import com.example.sokolbazar.model.ModelCartRoom;
import com.example.sokolbazar.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<ModelCartRoom> cart;
     Context context;
     CartRepository repository;

     public int total = 0;
    int taka = 0;
    int quantity;

    int quantitys=1;

    public CartAdapter(Context context, List<ModelCartRoom> cart, CartRepository repository) {
        this.cart = cart;
        this.context = context;
        this.repository = repository;
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





        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO ai minus hoya data room a update kora lagbe

                if (quantitys==1){
                    holder.cart_quantity.setText("1");
                }else
                {
                    quantitys -=1;
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantitys +=1;
                holder.cart_quantity.setText(""+quantitys);

                /*try {
                    CartRepository repository = new CartRepository(context);
                    ModelCartRoom modelCartRoom = new ModelCartRoom();
                    modelCartRoom.setId(cart.get(position).getId());
                    modelCartRoom.setQuantity(""+quantitys);
                    repository.UpdateSingleData(modelCartRoom);
                    //cart.remove(cart.get(position).getId()-1);
                    notifyDataSetChanged();

                }catch (Exception e){

                }*/


                //TODO ai plus hoya data room a update kora lagbe

              /*  CartRepository repository = new CartRepository(context);
                repository.UpdateSingleData(new ModelCartRoom(cart.get(position).getP_name(),
                        cart.get(position).getP_price(),""+quantitys,cart.get(position).getOffers(),cart.get(position).getUrl(),cart.get(position).getC_logo()));*/
            }
        });

        quantity = Integer.parseInt(cart.get(position).getQuantity());
         taka = (Integer.parseInt(cart.get(position).getP_price()))*quantity;
        total = total+taka;

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
                .load(R.drawable.swapno)
                .centerCrop()
                .into(holder.brandimage);

        holder.productlayout.setBackgroundColor(Color.parseColor("#FE6268"));
        

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  repository.deleteSingleData(cart.get(position).getId());

                try {
                    CartRepository repository = new CartRepository(context);
                    ModelCartRoom modelCartRoom = new ModelCartRoom();
                    modelCartRoom.setId(cart.get(position).getId());
                    repository.delete(modelCartRoom);
                    cart.remove(cart.get(position).getId()-1);
                    notifyDataSetChanged();
                    taka =0;
                    total =0;
                //    Toast.makeText(context, ""+cart.get(position).getId(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                   // Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                }



            }
        });



        Toast.makeText(context, ""+total, Toast.LENGTH_SHORT).show();

    }




    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView productimage,brandimage,offerpercent,delete;
        public TextView title,price,cart_quantity,plus,minus;
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
            plus= itemView.findViewById(R.id.plusebtid);
            minus = itemView.findViewById(R.id.minusbtid);
        }
    }
}
