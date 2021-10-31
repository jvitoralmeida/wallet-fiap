package br.com.fiap.wallet.repository;

import br.com.fiap.wallet.model.PartnerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerStoreRepository extends JpaRepository<PartnerStore, Long> {
}