package hu.mobilalk.porteka_furkeszo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import hu.mobilalk.porteka_furkeszo.activities.LoginActivity;
import hu.mobilalk.porteka_furkeszo.databinding.ActivityMainBinding;
import hu.mobilalk.porteka_furkeszo.fragments.AccountFragment;
import hu.mobilalk.porteka_furkeszo.fragments.CartFragment;
import hu.mobilalk.porteka_furkeszo.fragments.HomeFragment;
import hu.mobilalk.porteka_furkeszo.fragments.ProductDetailFragment;
import hu.mobilalk.porteka_furkeszo.models.Cart;
import hu.mobilalk.porteka_furkeszo.models.Product;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding binding;
    boolean isLoggedIn;
    ArrayList<Product> shoppingCart;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isLoggedIn = getIntent().getBooleanExtra("isLoggedIn", false);
        if (isLoggedIn) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            shoppingCart = new ArrayList<>();

            BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.home).setChecked(true);
            replaceFragment(new HomeFragment());
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (item.getItemId() == R.id.profile) {
                    replaceFragment(new AccountFragment());
                } else if (item.getItemId() == R.id.cart) {
                    replaceFragment(new CartFragment());
                }
                return true;
            });
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void onClickExit(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof ProductDetailFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();
        }
    }

    public void onClickAdd(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof ProductDetailFragment) {
            Product product = ((ProductDetailFragment) currentFragment).getProductFromView();

            shoppingCart.add(product);
            Toast.makeText(
                    MainActivity.this,
                    "Termék hozzáadva a kosárhoz.",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void onClickRemove(View view) {
        if (view.getParent() instanceof ViewGroup) {
            ViewGroup parentView = (ViewGroup) view.getParent();
            TextView productNameTV = parentView.findViewById(R.id.itemNameTextView);
            String productName = productNameTV.getText().toString();

            CartFragment cartFragment = new CartFragment();
            for (int i = 0; i < shoppingCart.size(); i++) {
                Product product = shoppingCart.get(i);
                if (product.getName().equals(productName)) {
                    shoppingCart.remove(i);
                    cartFragment.removeProduct();
                    break;
                }
            }
            Toast.makeText(
                    MainActivity.this,
                    "Termék eltávolítva, frissítsd az oldalt kérlek.:)",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void onClickBuy(View view) {
        String id = UUID.randomUUID().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        Date date = new Date();
        int total = 0;
        for (Product product : shoppingCart) {
            total += Integer.parseInt(product.getPrice());
        }

        Cart cart = new Cart(id, uid, shoppingCart, date, total);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .add(cart)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        shoppingCart.clear();
                        CartFragment.newInstance().removeProduct();
                        Toast.makeText(
                                MainActivity.this,
                                "Sikeres vásárlás!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MainActivity.this,
                                "Valami nem sikerült:/!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}