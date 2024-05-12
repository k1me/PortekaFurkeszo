package hu.mobilalk.porteka_furkeszo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.activities.LoginActivity;
import hu.mobilalk.porteka_furkeszo.adapters.OrderAdapter;
import hu.mobilalk.porteka_furkeszo.adapters.ProductAdapter;
import hu.mobilalk.porteka_furkeszo.models.Cart;

public class AccountFragment extends Fragment {
    TextView nameTV;
    TextView emailTV;
    TextView registeredOnTV;
    EditText surnameET;
    EditText firstnameET;
    EditText addressET;
    Button saveBtn;
    String userID;
    Query userQuery;
    TextView deleteAccountTV;
    FloatingActionButton signOutFab;
    RecyclerView recyclerView;
    OrderAdapter adapter;
    FirebaseFirestore db;
    ArrayList<Cart> orders;

    public AccountFragment() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            userQuery = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .whereEqualTo("uid", userID);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        nameTV = rootView.findViewById(R.id.nameTV);
        emailTV = rootView.findViewById(R.id.emailTV);
        registeredOnTV = rootView.findViewById(R.id.registeredOnTV);
        surnameET = rootView.findViewById(R.id.surnameET);
        firstnameET = rootView.findViewById(R.id.firstNameET);
        addressET = rootView.findViewById(R.id.addressET);
        saveBtn = rootView.findViewById(R.id.saveBtn);
        signOutFab = rootView.findViewById(R.id.signOutFab);
        deleteAccountTV = rootView.findViewById(R.id.deleteAccountTV);
        recyclerView = rootView.findViewById(R.id.accountRecyclerView);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            db = FirebaseFirestore.getInstance();
            orders = new ArrayList<>();
            adapter = new OrderAdapter(getContext(), orders);
            recyclerView.setAdapter(adapter);

            EventChangeListener();
        }
        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String surname = document.getString("lastName");
                        surnameET.setHint(surname.isEmpty() ? "Vezetéknév" : surname);

                        String firstname = document.getString("firstName");
                        firstnameET.setHint(firstname.isEmpty() ? "Keresztnév" : firstname);

                        String address = document.getString("address");
                        addressET.setHint(address.isEmpty() ? "Lakcím" : address);

                        String email = document.getString("email");
                        emailTV.setText(email);

                        String name = document.getString("firstName");
                        nameTV.setText(name.isEmpty() ? "user" : name);

                        Date date = document.getDate("registeredOn");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        if (!(date == null)) {
                            String formattedDate = dateFormat.format(date);
                            registeredOnTV.setText(formattedDate);
                        }
                    }
                } else {
                    Toast.makeText(
                            rootView.getContext(),
                            "Nem adtad még meg az adataidat:( "
                                    + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveBtn.setOnClickListener(view -> {
            Map<String, Object> userData = new HashMap<>();
            String surname = surnameET.getText().toString();
            String firstname = firstnameET.getText().toString();
            String address = addressET.getText().toString();
            userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DocumentReference userRef = document.getReference();

                            if (!surname.isEmpty()) {
                                userData.put("lastName", surname);
                            }
                            if (!firstname.isEmpty()) {
                                userData.put("firstName", firstname);

                            }
                            if (!address.isEmpty()) {
                                userData.put("address", address);
                            }

                            userRef.update(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(
                                                    rootView.getContext(),
                                                    "Sikeres adatrögzítés:) ",
                                                    Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(
                                                    rootView.getContext(),
                                                    "Valami hiba történt az adatok eltárolásakor:( ",
                                                    Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(
                                rootView.getContext(),
                                "Valami hiba történt az adatok eltárolásakor:( ",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        });

        deleteAccountTV.setOnClickListener(view -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(rootView.getContext(),
                                                    "Fiók sikeresen törölve",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(rootView.getContext(),
                                                    "Hiba történt a fiók törlése közben",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        });

        signOutFab.setOnClickListener(view -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
        });

        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Toast.makeText(rootView.getContext(),
                                "Sikeres kijelentkezés:)",
                                Toast.LENGTH_SHORT)
                        .show();
                startActivity(intent);
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);

        return rootView;
    }

    private void EventChangeListener() {
        db.collection("orders").whereEqualTo("uid", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Cart cartItem = document.toObject(Cart.class);
                            orders.add(cartItem);
                            Log.i("f8o4sh4of8hsof", cartItem.toString());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(),
                                "Hopp-hopp, valami hiba történt:/",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

}