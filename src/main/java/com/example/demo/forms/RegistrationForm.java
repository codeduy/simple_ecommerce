package com.example.demo.forms;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationForm {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;
    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    private String rePassword;

    public boolean isPasswordMatch() {
        return password != null && password.equals(rePassword);
    }
}
