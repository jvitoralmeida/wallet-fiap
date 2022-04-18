package br.com.fiap.wallet.repository;

import br.com.fiap.wallet.model.PartnerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerStoreRepository extends JpaRepository<PartnerStore, Long> {

  List<PartnerStore> findByNameContainingIgnoreCase(String name);
}
