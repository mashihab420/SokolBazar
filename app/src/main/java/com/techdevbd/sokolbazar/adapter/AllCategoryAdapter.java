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

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder> {
    private List<ModelProducts> allcategoryitem;
    Context context;

    public AllCategoryAdapter(List<ModelProducts> allcategoryitem, Context context) {
        this.allcategoryitem = allcategoryitem;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_product,parent,false);

        return  new AllCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(allcategoryitem.get(position).getPName());
        holder.price.setText(allcategoryitem.get(position).getPPrice()+" BDT");
        String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allcategoryitem.get(position).getImageUrl();
        Glide
                .with(context)
                .load(url)
                .into(holder.pic);


        holder.addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CartRepository repository = new CartRepository(context);
                String name = allcategoryitem.get(position).getPName();
                String price = allcategoryitem.get(position).getPPrice();
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allcategoryitem.get(position).getImageUrl();
                //   String description = offers.get(position).getDescription();
                String quantity = "1";
                String offer = allcategoryitem.get(position).getOffers();
                String shopname = allcategoryitem.get(position).getShopName();

                repository.insertSingleData(new ModelCartRoom(name,price,quantity,offer,url,shopname));
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = allcategoryitem.get(position).getPName();
                String price = allcategoryitem.get(position).getPPrice();
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allcategoryitem.get(position).getImageUrl();
                String quantity = "1";
                String description = allcategoryitem.get(position).getDescription();
                String offer = allcategoryitem.get(position).getOffers();
                String shopname = allcategoryitem.get(position).getShopName();


                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("pname",name);
                intent.putExtra("pprice",price);
                intent.putExtra("quantity",quantity);
                intent.putExtra("description",description);
                intent.putExtra("offers",offer);
                intent.putExtra("url",url);
                intent.putExtra("shopname",shopname);
                intent.putExtra("activity","categories");

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allcategoryitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price,addicon;
        public ImageView pic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.p_price);
            pic = itemView.findViewById(R.id.searchitem_image);
            addicon = itemView.findViewById(R.id.item_id);
        }
    }
}
