package hu.mobilalk.porteka_furkeszo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.adapters.ProductAdapter;
import hu.mobilalk.porteka_furkeszo.models.Product;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Product> products;
    ProductAdapter adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    public HomeFragment() {

    }

    private void EventChangeListener() {

        db.collection("products").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getContext(), "Hopp-hopp, valami hiba történt:/", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            products.add(dc.getDocument().toObject(Product.class));
                        }
                    }

                    adapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adatok kifejtése a bányából");
        progressDialog.show();

        recyclerView = rootView.findViewById(R.id.homeRecyclerView);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            db = FirebaseFirestore.getInstance();
            products = new ArrayList<>();
            adapter = new ProductAdapter(getContext(), products);
            recyclerView.setAdapter(adapter);

            EventChangeListener();
        } else {
            Toast.makeText(getContext(), "Hopp-hopp, valami hiba történt:/", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}