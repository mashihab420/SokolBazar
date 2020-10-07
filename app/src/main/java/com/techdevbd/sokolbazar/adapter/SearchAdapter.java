package com.techdevbd.sokolbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.activity.ProductDetails;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelProducts;
import com.techdevbd.sokolbazar.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<ModelProducts> allproduct;
    Context context;

    public SearchAdapter(List<ModelProducts> allproduct, Context context) {
        this.allproduct = allproduct;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(allproduct.get(position).getPName());
        holder.price.setText(allproduct.get(position).getPPrice());

        String imageurl = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/" + allproduct.get(position).getImageUrl();

        Glide
                .with(context)
                .load(imageurl)
                .centerCrop()
                .into(holder.bg_offer);

        holder.addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CartRepository repository = new CartRepository(context);
                String name = allproduct.get(position).getPName();
                String price = allproduct.get(position).getPPrice();
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/" + allproduct.get(position).getImageUrl();
                String quantity = "1";
                String offer = allproduct.get(position).getOffers();
                String shopname = allproduct.get(position).getShopName();

                repository.insertSingleData(new ModelCartRoom(name, price, quantity, offer, url, shopname));
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = allproduct.get(position).getPName();
                String price = allproduct.get(position).getPPrice();
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/" + allproduct.get(position).getImageUrl();
                String quantity = "1";

                String description = allproduct.get(position).getDescription();
                String offer = allproduct.get(position).getOffers();
                String shopname = allproduct.get(position).getShopName();

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("pname", name);
                intent.putExtra("pprice", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("description", description);
                intent.putExtra("offers", offer);
                intent.putExtra("shopname", shopname);
                intent.putExtra("url", url);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return allproduct.size();
    }

    public void search_filter_list(ArrayList<ModelProducts> filter_list) {

        allproduct = filter_list;
        notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price, addicon;
        public ImageView bg_offer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title_id);
            price = itemView.findViewById(R.id.price_id);
            bg_offer = itemView.findViewById(R.id.searchitem_image);
            addicon = itemView.findViewById(R.id.item_id);
        }
    }
}
