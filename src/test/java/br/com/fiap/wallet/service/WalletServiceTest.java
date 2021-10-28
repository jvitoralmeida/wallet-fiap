package br.com.fiap.wallet.service;

import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.repository.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceTest {


	@InjectMocks
	WalletService walletService;

	@Mock
	WalletRepository wallteRepository;

	@Test
	public void mustIncreaseWalletValue() {
		BigDecimal value = new BigDecimal("150.00");
		User user = User.builder()
				.cpf("1234")
				.name("fulano")
				.build();
		Wallet mockWallet = Wallet.builder()
				.id(1L)
				.value(new BigDecimal("0"))
				.user(user)
				.build();

		when(wallteRepository.findByUserCpf("1234")).thenReturn(Optional.of(mockWallet));

		walletService.increaseValue(user.getCpf(), value);

		mockWallet.setValue(value);
		verify(wallteRepository).save(mockWallet);
	}
}
