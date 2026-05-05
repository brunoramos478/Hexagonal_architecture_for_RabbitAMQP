package br.com.fusion.banck.shared.dto;

import br.com.fusion.banck.shared.exceptions.FusionApiUserIsSave;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.stream.Stream;

public class UserDto {

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "CPF is required")
    @Pattern(
            regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Invalid CPF"
    )
    private String cpf;
    @Past(message = "Birth date must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "\\d{10,11}|\\(\\d{2}\\)\\d{4,5}-\\d{4}",
            message = "Invalid phone"
    )
    private String phone;
    @NotBlank(message = "Address is required")
    private String address;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Region is required")
    private String region;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "CEP is required")
    @Pattern(
            regexp = "\\d{8}|\\d{5}-\\d{3}",
            message = "Invalid CEP"
    )
    private String cep;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String cpf, LocalDate birthDate,
                   String phone, String address, String email, String region, String city,
                   String state, String cep, String password) {

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

    public void validate(UserDto dto) {
        boolean isInvalid = Stream.of(
                dto.getFirstName(), dto.getLastName(), dto.getCpf(), dto.getBirthDate(),
                dto.getPhone(), dto.getAddress(), dto.getEmail(), dto.getRegion(),
                dto.getCity(), dto.getState(), dto.getCep(), dto.getPassword()
        ).anyMatch(field -> field == null || (field instanceof String s && s.isBlank()));

        if (isInvalid) {
            throw new FusionApiUserIsSave("Erro ao salvar usuário. Certifique-se de que todos os campos estão preenchidos corretamente.");
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}