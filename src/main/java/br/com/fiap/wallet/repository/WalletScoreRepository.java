package br.com.fiap.wallet.repository;

import br.com.fiap.wallet.model.WalletScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletScoreRepository extends JpaRepository<WalletScore, Long> {

  List<WalletScore> findByWalletId(Long id);
}
