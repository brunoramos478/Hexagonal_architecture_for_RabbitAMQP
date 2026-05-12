package br.com.fusion.banck.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    public UserDto() {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.region = region;
        this.city = city;
        this.state = state;
        this.cep = cep;
        this.password = password;
    }

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 25, message = "First name must be between 3 and 25 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;
    @NotNull
    @NotBlank(message = "CPF is required")
    @Pattern(
            regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Invalid CPF"
    )
    @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
    private String cpf;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "\\d{10,19}|\\(\\d{2}\\)\\d{4,5}-\\d{4}",
            message = "Invalid phone"
    )
    @Size(min = 10, max = 19, message = "Phone must be between 10 and 11 characters")
    private String phone;

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    private String address;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 70, message = "Email must be between 5 and 50 characters")
    private String email;

    @NotNull(message = "Region is required")
    @NotBlank(message = "Region is required")
    @Size(min = 3, max = 40, message = "Region must be between 3 and 50 characters")
    private String region;

    @NotNull(message = "City is required")
    @NotBlank(message = "City is required")
    @Size(min = 3, max = 50, message = "City must be between 3 and 50 characters")
    private String city;

    @NotNull(message = "State is required")
    @NotBlank(message = "State is required")
    @Size(min = 2, max = 2, message = "State must be 2 characters")
    private String state;

    @NotNull(message = "CEP is required")
    @NotBlank(message = "CEP is required")
    @Pattern(
            regexp = "\\d{8}|\\d{5}-\\d{3}",
            message = "Invalid CEP"
    )
    @Size(min = 8, max = 10, message = "CEP must be between 8 and 10 characters")
    private String cep;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}