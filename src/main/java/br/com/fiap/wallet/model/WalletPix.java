package br.com.fiap.wallet.model;

import br.com.fiap.wallet.model.enums.Bank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class WalletPix {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String recipient;
	private BigDecimal value;
	private String idTransaction;
	private Bank bank;
	private String agency;
	private String account;
}
