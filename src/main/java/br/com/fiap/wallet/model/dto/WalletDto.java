package br.com.fiap.wallet.model.dto;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDto {
    private Long id;
    private UserDto user;
    private BigDecimal value;
    @JsonManagedReference
    private Set<PartnerStore> partnerStore;
}
