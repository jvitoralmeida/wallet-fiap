package br.com.fiap.wallet.service;

import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class WalletService {

	@Autowired
	WalletRepository repository;

	public void increaseValue(String cpfCustomer, BigDecimal valueToIncrease) {
		Optional<Wallet> wallet = repository.findByUserCpf(cpfCustomer);

		wallet.ifPresentOrElse(walletReturned -> {
			walletReturned.getValue()
					.add(valueToIncrease);
			repository.save(walletReturned);
		}, () -> log.info("Usuário não encontrado"));
	}
}
