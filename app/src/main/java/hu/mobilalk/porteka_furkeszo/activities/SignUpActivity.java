package hu.mobilalk.porteka_furkeszo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.models.User;

public class SignUpActivity extends AppCompatActivity {
    TextView loginTv;
    EditText emailET, passwordET, passwordReET;
    Button signUpBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwordReET = findViewById(R.id.passwordReET);

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(view -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            String passwordRe = passwordReET.getText().toString();

            if (password.equals(passwordRe) && !password.isEmpty() && !email.isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                String uid = firebaseUser.getUid();
                                User user = new User(uid, email, password);
                                signUpToFireStore(user);
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(
                                        SignUpActivity.this,
                                        "Hiba történt a regisztráció során: "
                                                + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(
                        SignUpActivity.this,
                        "Valamelyik adat hiányos vagy nem jól adtad meg...:/",
                        Toast.LENGTH_SHORT).show();
            }
        });

        loginTv = findViewById(R.id.loginTV);
        loginTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void signUpToFireStore(User user) {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(
                                SignUpActivity.this,
                                "Sikeres regisztráció!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(
                        SignUpActivity.this,
                        "Sikertelen regisztráció!",
                        Toast.LENGTH_SHORT).show());
    }
}