package com.example.sokolbazar.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.sokolbazar.adapter.AllProductAdapter;
import com.example.sokolbazar.adapter.CategoriesAdapter;
import com.example.sokolbazar.adapter.OffersAdapter;
import com.example.sokolbazar.databinding.FragmentHomeBinding;
import com.example.sokolbazar.model.ModelCart;
import com.example.sokolbazar.model.ModelCartRoom;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.repository.CartRepository;
import com.example.sokolbazar.viewModel.ViewModelHome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class FragmentHome extends Fragment implements ZXingScannerView.ResultHandler {

    FloatingActionButton floatingActionButton;


    private ZXingScannerView mScannerView;
    private static final int WRITE_EXST = 1;
    private static final int REQUEST_PERMISSION = 123;
    int CAMERA;
    String position,formt;
    TextView swape;
    String s1;
    Context context;


    private ViewModelHome viewModelHome;
  private FragmentHomeBinding binding;

    private CategoriesAdapter categoriesAdapter;
    private OffersAdapter offersAdapter;
    private AllProductAdapter allProductAdapter;

    public static ArrayList<ModelCart> arrayList = new ArrayList<>();

    List<ModelProducts> allEmployee; //= new ArrayList<>();

    List<ModelProducts> products;

    List<ModelProducts> allOffer;



    Boolean popup = true;

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


        if( ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},5);
            }
        }

       // ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(getContext());
        binding.contentFrame.addView(mScannerView);



        binding.moveLayoutId.setTranslationY(1750);

binding.fabId.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        if (popup == true){
            mScannerView.setResultHandler(FragmentHome.this);
            mScannerView.startCamera();
          //  Toast.makeText(getActivity(), ""+s1, Toast.LENGTH_SHORT).show();
            binding.moveLayoutId.setTranslationY(800);
            popup = false;
        }
        else if(popup == false){
            mScannerView.stopCamera();
           // Toast.makeText(getActivity(), "Camera Off", Toast.LENGTH_SHORT).show();
            binding.moveLayoutId.setTranslationY(1750);
            popup = true;
        }
    }
});



       // dataRetriever();

        //get all category
        allEmployee=new ArrayList<>();

        categoriesAdapter = new CategoriesAdapter(allEmployee, getContext());
        initRecyclerView(categoriesAdapter, binding.recyclerCategoryItem);


        //get all offers
        allOffer = new ArrayList<>();
        offersAdapter = new OffersAdapter(allOffer, getContext());
        initRecyclerViewOffer(offersAdapter, binding.recyclerOfferItem);


        products = new ArrayList<>();
        allProductAdapter = new AllProductAdapter(products, getContext());
        initRecyclerViewAllproduct(allProductAdapter, binding.recyclerAllItem);



       return view;

    }

    private void dataset(){

        //get categories
        viewModelHome.getCategories().observe(getActivity(), new Observer<List<ModelProducts>>() { //todo this was changed with getActivity()
            @Override
            public void onChanged(List<ModelProducts> products) {
                  allEmployee.addAll(products);
                  categoriesAdapter.notifyDataSetChanged();



               // Log.d("errordipu", "onChanged: "+allEmployee.get(0).getTitle());
            }
        });







    }


    private void datasetallproduct(){

        //todo get All product

        viewModelHome.getProducts().observe(getActivity(), new Observer<List<ModelProducts>>() {
            @Override
            public void onChanged(List<ModelProducts> employees) {
                products.addAll(employees);
                allProductAdapter.notifyDataSetChanged();

                //  Toast.makeText(getContext(), ""+allOffer.get(0).getTitle(), Toast.LENGTH_SHORT).show();



            }
        });

    }


    private void datasetoffer(){


        //todo get offers

        viewModelHome.getOffers().observe(getActivity(), new Observer<List<ModelProducts>>() {
            @Override
            public void onChanged(List<ModelProducts> products) {
                allOffer.addAll(products);
                offersAdapter.notifyDataSetChanged();

              //  Toast.makeText(getContext(), ""+allOffer.get(0).getTitle(), Toast.LENGTH_SHORT).show();



            }
        });






    }






    private void initRecyclerView(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
        categoriesAdapter.notifyDataSetChanged();


        dataset();
    }

    private void initRecyclerViewOffer(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
        offersAdapter.notifyDataSetChanged();

        datasetoffer();
    }


    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
        allProductAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(ViewModelHome.class);
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(FragmentHome.this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {

       /* new CountDownTimer(2000, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                mScannerView.setResultHandler(FragmentHome.this);
                mScannerView.startCamera();
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();*/

        s1= result.getText();

        String[]  array = s1.split(",");


        final CartRepository repository = new CartRepository(getContext());

        String name = array[0];
        String price = array[1];
        String quantity = array[2];
        String offers = array[3];
        String url = array[4];
        String logo = array[5];

        repository.insertSingleData(new ModelCartRoom(name,price,quantity,offers,url,logo));



        if(!s1.equals("")){
            onPause();
            onResume();
        }
    }
}