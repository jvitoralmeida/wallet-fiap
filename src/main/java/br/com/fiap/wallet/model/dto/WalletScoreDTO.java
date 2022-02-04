package br.com.fiap.wallet.model.dto;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.Wallet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class WalletScoreDTO {
	private String cpf;
	private Long partnerStoreId;
	private BigDecimal value;
	private String transactionId;
}