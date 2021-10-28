package br.com.fiap.wallet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class WalletScore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;
	@ManyToOne
	@JoinColumn(name = "partner_store_id")
	private PartnerStore partnerStore;
}