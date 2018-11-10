package sb.iot.sb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;


    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        Button mEmailSignUpButton = (Button) findViewById(R.id.signup);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.senha);
        mPasswordConfirmView = (EditText) findViewById(R.id.confirmarsenha);




    }

    public void signUp(){

        String email = mEmailView.getText().toString();

        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirmView.getText().toString();


        if(!password.equals(passwordConfirm)){

            Toast.makeText(SignupActivity.this, "Senhas não são iguais",
                    Toast.LENGTH_SHORT).show();

        } else {

            mProgressDialog = ProgressDialog.show(SignupActivity.this, "Cadastro", "Cadastrando usuário");
            mProgressDialog.setCanceledOnTouchOutside(false); // main method that force user cannot click outside
            mProgressDialog.setCancelable(true);

            final Intent intent = new Intent(this, LoginActivity.class);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgressDialog.dismiss();

                            if (task.isSuccessful()) {

                                Toast.makeText(SignupActivity.this, "Usuário criado com sucesso",
                                        Toast.LENGTH_SHORT).show();


                                startActivity(intent);

                            } else {

                                Toast.makeText(SignupActivity.this, "Falha ao criar usuário",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });


        }


    }
}
