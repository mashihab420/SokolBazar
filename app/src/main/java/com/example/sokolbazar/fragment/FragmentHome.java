package com.example.sokolbazar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.sokolbazar.adapter.CategoriesAdapter;
import com.example.sokolbazar.databinding.FragmentHomeBinding;
import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelCategory;
import com.example.sokolbazar.viewModel.ViewModelHome;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {

    private ViewModelHome viewModelHome;
  private FragmentHomeBinding binding;
    private List<ModelCategory> category_list;
    private CategoriesAdapter categoriesAdapter;

    List<Employee> allEmployee = new ArrayList<>();


    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        initViewModel();

        category_list = new ArrayList<>();

        // Categories Adapter initialization
        categoriesAdapter = new CategoriesAdapter(category_list, getContext());
        initRecyclerView(categoriesAdapter, binding.recyclerCategoryItem);

       // dataRetriever();
        dataret();
       return view;





    }

    private void dataret(){
        viewModelHome.getCategories().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
               // Log.d("FragmentHome","Data: "+allEmployee.get(0).getEmail());

            }
        });
    }

   /* //get All Data for HomeFragment
    private void dataRetriever() {
        viewModelHome.getCategories().observe(getViewLifecycleOwner(), new Observer<List<ModelCategory>>() {
            @Override
            public void onChanged(List<ModelCategory> modelCategories) {
                 if(category_list==null ){
                     Log.d("error", "onChanged: getting null 1");
                 }else if( modelCategories==null) {
                     Log.d("error", "onChanged:getting error 2 ");
                 } else {
                     category_list.addAll(modelCategories);
                 }
                categoriesAdapter.notifyDataSetChanged();
            }
        });




    }
*/




    private void initRecyclerView(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(ViewModelHome.class);
    }
}