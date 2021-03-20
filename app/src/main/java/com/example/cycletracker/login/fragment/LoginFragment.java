package com.example.cycletracker.login.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.login.model.LoginFormState;
import com.example.cycletracker.login.viewmodel.LoginViewModel;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.util.Utility;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

public class LoginFragment extends LoginBaseFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private NavController navController;

    @Inject
    LoginViewModel loginViewModel;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        getLoginSubComponent().inject(this);
        navController = NavHostFragment.findNavController(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.btn_login);
        loadingProgressBar = view.findViewById(R.id.loading);

        loggedInUserViewModel.getLoggedInUser().observe(this.getViewLifecycleOwner(), (@Nullable LoggedInUser loggedInUser) -> {
            showProgress(false);
            if(loggedInUser != null) {
                updateUiWithUser(loggedInUser);
            } else {
                showLoginFailed();
            }
        });

        loginViewModel.getLoginFormState().observe(this.getViewLifecycleOwner(), (LoginFormState loginFormState) -> {
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

        return view;
    }

    private void showProgress(boolean show) {
        usernameEditText.setEnabled(!show);
        passwordEditText.setEnabled(!show);
        loginButton.setEnabled(!show);
        loadingProgressBar.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void updateUiWithUser(LoggedInUser loggedInUser) {
        Toast.makeText(this.getContext(), "Welcome "+loggedInUser.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.action_dest_login_fragment_to_dest_main_fragment);
    }

    private void showLoginFailed() {
        Toast.makeText(this.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }
}