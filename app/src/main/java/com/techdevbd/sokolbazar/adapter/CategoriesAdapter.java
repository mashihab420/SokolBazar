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
import com.techdevbd.sokolbazar.activity.CategoryActivity;
import com.techdevbd.sokolbazar.model.ModelProducts;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.VH> {
    private List<ModelProducts> categories;
    Context context;

    public CategoriesAdapter(List<ModelProducts> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories,parent,false);
        VH vh=new VH(view);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.category_name.setText(categories.get(position).getCategory());

        String imageurl = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+categories.get(position).getImageUrl();
        Glide
                .with(context)
                .load(imageurl)
                .centerCrop()
                .into(holder.bgImage);

        holder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("category",categories.get(position).getCategory());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView category_name;
        public ImageView bgImage;
        public VH(@NonNull View itemView) {
            super(itemView);
            category_name=itemView.findViewById(R.id.titleid);
            bgImage=itemView.findViewById(R.id.bg_image_id);
        }
    }
}
