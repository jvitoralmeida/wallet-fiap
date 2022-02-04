package br.com.fiap.wallet.model.form;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserForm {
    private String name;
    private LocalDate birthDate;
    private String email;
    private String cpf;
    private Long cellphone;
    private String password;
    private Boolean sendNotification;
}
