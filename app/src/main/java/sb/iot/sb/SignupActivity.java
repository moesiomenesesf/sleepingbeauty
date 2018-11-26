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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sb.iot.entidades.Usuarios;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private AutoCompleteTextView mNomeView;
    private AutoCompleteTextView mSobrenomeView;
    private AutoCompleteTextView mEmailView;
    private RadioGroup radioGroup;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;


    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        Button mEmailSignUpButton = (Button) findViewById(R.id.signup);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });


        mNomeView = findViewById(R.id.nome);
        mSobrenomeView = findViewById(R.id.sobrenome);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.senha);
        mPasswordConfirmView = (EditText) findViewById(R.id.confirmarsenha);
        radioGroup = findViewById(R.id.radiogroup);

    }

    public void signUp(){


        String nome = mNomeView.getText().toString();
        String sobrenome = mSobrenomeView.getText().toString();
        String email = mEmailView.getText().toString();

        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirmView.getText().toString();




        if(!password.equals(passwordConfirm)){

            Toast.makeText(SignupActivity.this, "Senhas não são iguais",
                    Toast.LENGTH_SHORT).show();
        }
        if(password.length() < 6) {
            Toast.makeText(SignupActivity.this, "A senha deve conter no mínimo seis caracteres, por favor tente novamente!",
                    Toast.LENGTH_SHORT).show();

        } else {

            mProgressDialog = ProgressDialog.show(SignupActivity.this, "Cadastro", "Cadastrando usuário");
            mProgressDialog.setCanceledOnTouchOutside(false); // main method that force user cannot click outside
            mProgressDialog.setCancelable(true);

            Usuarios user = new Usuarios();
            user.setEmail(email);
            user.setNome(nome);
            user.setSobrenome(sobrenome);
            user.setSenha(password);

            final Intent intent = new Intent(this, HomeActivity.class);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {

                                Toast.makeText(SignupActivity.this, "Usuário criado com sucesso",
                                        Toast.LENGTH_SHORT).show();


                                startActivity(intent);
                            } else {

                                Toast.makeText(SignupActivity.this, "Falha ao criar usuário",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }


        }


    }

