package com.example.sokolbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sokolbazar.R;
import com.example.sokolbazar.activity.ProductDetails;
import com.example.sokolbazar.fragment.FragmentHome;
import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelCartRoom;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.repository.CartRepository;

import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.MyViewHolder> {
    private List<ModelProducts> allproduct;
    Context context;

    public AllProductAdapter(List<ModelProducts> allproduct, Context context) {
        this.allproduct = allproduct;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offers,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      //  holder.title.setText(allproduct.get(position).getTitle());
       // holder.price.setText(""+allproduct.get(position).getAlbumId());
        holder.title.setText(allproduct.get(position).getPName());
        holder.price.setText(allproduct.get(position).getPPrice());

        String imageurl = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allproduct.get(position).getImageUrl();

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
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allproduct.get(position).getImageUrl();
                String quantity = "1";
                String offers = "0";
                   String logo = "https://1.bp.blogspot.com/-gPzD0tXqouo/VgE-dFZxK_I/AAAAAAAACsM/GdnTiZ5ie-w/s1600/agora_658147643.jpg";



                repository.insertSingleData(new ModelCartRoom(name,price,quantity,offers,url,logo));


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = allproduct.get(position).getPName();
                String price = allproduct.get(position).getPPrice();
                String url = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+allproduct.get(position).getImageUrl();
                String quantity = "1";
                String offers = "20";

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("pname",name);
                intent.putExtra("pprice",price);
                intent.putExtra("quantity",quantity);
                // intent.putExtra("description",description);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allproduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price;
        public ImageView bg_offer,addicon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.offer_Title_id);
            price = itemView.findViewById(R.id.offer_price_id);
            bg_offer = itemView.findViewById(R.id.offer_image);
            addicon = itemView.findViewById(R.id.add_item_id);
        }
    }
}
