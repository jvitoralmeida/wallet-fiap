package br.com.fiap.wallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletScore {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "wallet_id")
  @JsonBackReference
  private Wallet wallet;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "partner_store_id")
  private PartnerStore partnerStore;

  private BigDecimal value;
  private String transactionId;
}
