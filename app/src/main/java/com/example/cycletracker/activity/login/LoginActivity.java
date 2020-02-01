package com.example.cycletracker.activity.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.activity.ViewModelFactory;
import com.example.cycletracker.activity.home.HomeActivity;
import com.example.cycletracker.activity.lock.LockActivity;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.models.BookedCycleResp;
import com.example.cycletracker.util.WApiConsts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_PREFERENCES = "login preferences";
    private LoginViewModel loginViewModel;
    private View container;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(getApplication()))
                .get(LoginViewModel.class);

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

        loginViewModel.getLoginResult().observe(this, (@Nullable LoginResult loginResult) -> {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy fetchUserDetails activity once successful
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

        passwordEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event)->{
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener((View v) -> {
            showProgress(true);
            loginViewModel.login(usernameEditText.getText().toString(),
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

    private void updateUiWithUser(LoggedInUserView model) {
        //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        ApiClient.getInstance().getCycleIssuerClient().getBookedCycleId()
                .enqueue(new Callback<BookedCycleResp>() {
                    @Override
                    public void onResponse(Call<BookedCycleResp> call, Response<BookedCycleResp> response) {
                        showProgress(false);
                        if(response.isSuccessful()) {
                            BookedCycleResp resp = response.body();
                            if(resp!=null && !resp.getCycleId().equals("")) {
                                try {
                                    int cycle_id = Integer.parseInt(resp.getCycleId());
                                    startLockActivity(cycle_id);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        showProgress(false);
    }
}
