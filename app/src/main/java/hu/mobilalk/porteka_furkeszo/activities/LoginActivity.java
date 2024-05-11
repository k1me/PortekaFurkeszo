package hu.mobilalk.porteka_furkeszo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hu.mobilalk.porteka_furkeszo.MainActivity;
import hu.mobilalk.porteka_furkeszo.R;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTV;
    Button loginBtn;
    EditText emailET;
    EditText passwordET;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpTV = findViewById(R.id.signUpTV);
        loginBtn = findViewById(R.id.loginBtn);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        isLoggedIn = false;

        loginBtn.setOnClickListener(view -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                isLoggedIn = true;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("isLoggedIn", isLoggedIn);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        LoginActivity.this,
                                        "Hopp-hopp, az email vagy a jelszó nem jó:/",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(
                        LoginActivity.this,
                        "Hopp-hopp, nem adtad meg az emailt vagy jelszót",
                        Toast.LENGTH_SHORT).show();
            }

        });

        signUpTV.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}