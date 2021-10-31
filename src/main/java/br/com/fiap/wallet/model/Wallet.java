package br.com.fiap.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private BigDecimal value;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "WALLET_STORE_PREFERENCE", joinColumns = @JoinColumn(name = "wallet_id"), inverseJoinColumns = @JoinColumn(name = "partner_store"))
    @JsonManagedReference
    private Set<PartnerStore> partnerStore;
}
