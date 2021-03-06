package me.kevincampos.catsdagger.login;

import android.util.Log;

public class LoginUseCase {

    private LoginService loginService;

    public LoginUseCase(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing user associated to the given username.
     * Will return null if username or password are invalid.
     */
    public String login(String username, String password) {
        Log.d("LoginUseCase", "Login: " + username + ", " + password);

        try {
            String token = loginService.login(username, password);
            Log.d("LoginUseCase", "Login token: " + token);
            return token;
        } catch (RuntimeException | LoginService.UsernamePasswordDoesNotMatchException e) {
            Log.e("LoginUseCase", "Login failed");
            return null;
        }

    }
}
