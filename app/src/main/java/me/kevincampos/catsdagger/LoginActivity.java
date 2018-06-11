package me.kevincampos.catsdagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.kevincampos.catsdagger.di.AppDIComponent;
import me.kevincampos.catsdagger.di.SharedPrefFavoriteRepoDIModule;
import me.kevincampos.catsdagger.di.UserDIComponent;
import me.kevincampos.catsdagger.login.LoginService;
import me.kevincampos.catsdagger.login.LoginUseCase;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView usernameActv;
    private EditText passwordEt;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameActv = findViewById(R.id.login_username_actv);
        passwordEt = findViewById(R.id.login_password_et);
        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        errorTv = findViewById(R.id.login_error_tv);

        Button usernameSignInButton = findViewById(R.id.login_sign_in_bt);
        usernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onResume() {
        if (UserDIComponent.get() != null) {
            UserDIComponent.get().close();
        }
        super.onResume();
    }

    private void attemptLogin() {
        errorTv.setVisibility(View.GONE);
        String username = usernameActv.getText().toString();
        String password = passwordEt.getText().toString();

        LoginService loginService = new LoginService();
        LoginUseCase loginUseCase = new LoginUseCase(loginService);
        String token = loginUseCase.login(username, password);

        if (token == null) {
            errorTv.setVisibility(View.VISIBLE);
        } else {
            UserDIComponent.initialize(new SharedPrefFavoriteRepoDIModule(AppDIComponent.get(), token));
            FavoritesActivity.launch(this, false);
        }
    }
}

