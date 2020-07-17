package com.example.sokolbazar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sokolbazar.R;
import com.example.sokolbazar.model.ModelCategory;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.VH> {
    private List<ModelCategory> categories;
    Context context;

    public CategoriesAdapter(List<ModelCategory> categories, Context context) {
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

        holder.category_name.setText(categories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView category_name;
        public VH(@NonNull View itemView) {
            super(itemView);
            category_name=itemView.findViewById(R.id.titleid);
        }
    }
}
