package com.example.cycletracker.login.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.login.model.LoginFormState;
import com.example.cycletracker.login.viewmodel.LoginViewModel;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.home.activity.MainActivity;
import com.example.cycletracker.lock.activity.LockActivity;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.model.BookedCycleResp;
import com.example.cycletracker.util.WApiConsts;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_PREFERENCES = "login preferences";

    @Inject
    LoginViewModel loginViewModel;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    private View container;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        container = findViewById(R.id.container);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, (@Nullable LoginFormState loginFormState) -> {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
        });

        loggedInUserViewModel.getLoggedInUser().observe(this, (@Nullable LoggedInUser loggedInUser) -> {
                if(loggedInUser != null) {
                    updateUiWithUser(loggedInUser);
                } else {
                    showLoginFailed();
                }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

//        passwordEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event)->{
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                loggedInUserViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
//            }
//            return false;
//        });

        loginButton.setOnClickListener((View v) -> {
            showProgress(true);
            loggedInUserViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void showProgress(boolean show) {
        usernameEditText.setEnabled(!show);
        passwordEditText.setEnabled(!show);
        loginButton.setEnabled(!show);
        loadingProgressBar.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void startLockActivity(int cycleId) {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        startActivity(intent);
    }

    private void updateUiWithUser(LoggedInUser model) {
        //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        ApiClient.getInstance().getCycleIssuerClient().getBookedCycleId()
                .enqueue(new Callback<BookedCycleResp>() {
                    @Override
                    public void onResponse(Call<BookedCycleResp> call, Response<BookedCycleResp> response) {
                        showProgress(false);
                        if(response.isSuccessful()) {
                            BookedCycleResp resp = response.body();
                            if(resp!=null && resp.getCycleId()!=null) {
                                try {
                                    int cycle_id = resp.getCycleId();
                                    startLockActivity(cycle_id);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookedCycleResp> call, Throwable t) {
                        showProgress(false);
                        t.printStackTrace();
                    }
                });
    }

    private void showLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }
}
