package hu.mobilalk.porteka_furkeszo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hu.mobilalk.porteka_furkeszo.MainActivity;
import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.adapters.CartAdapter;
import hu.mobilalk.porteka_furkeszo.models.Product;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Product> cart;
    CartAdapter adapter;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = rootView.findViewById(R.id.cartRecyclerView);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                cart = mainActivity.getShoppingCart();
                adapter = new CartAdapter(getContext(), cart);
                recyclerView.setAdapter(adapter);
            }

        } else {
            Toast.makeText(getContext(), "Hopp-hopp, valami hiba történt:/", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    public void removeProduct() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            cart = mainActivity.getShoppingCart();
        }
    }
}