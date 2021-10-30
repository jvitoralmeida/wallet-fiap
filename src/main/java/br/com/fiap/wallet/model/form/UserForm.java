package br.com.fiap.wallet.model.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserForm {
    private String cpf;
    private String password;
}
