package mx.nopaloverflow.vetapi.users.handlers.requests;

import mx.nopaloverflow.vetapi.users.dtos.UserDto;

public class UserRegistrationRequest {
    private UserDto userInfo;
    private String password;
    private String repeatedPassword;

    public UserDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDto userInfo) {
        this.userInfo = userInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
