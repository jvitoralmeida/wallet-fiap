package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.model.dto.UserDto;
import br.com.fiap.wallet.model.form.UserForm;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.UserRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

	@InjectMocks
	UserController controller;

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder bCryptPasswordEncoder;
	@Mock
	private WalletRepository walletRepository;
	@Mock
	private ModelMapper mapper;

	@Test
	public void mustReturn404WhenUserNotFound() {
		when(userRepository.findByCpf("123")).thenReturn(Optional.empty());

		ResponseEntity<UserDto> responseEntity = controller.updateUser("123", UserForm.builder()
				.build());
		assertEquals(responseEntity.getStatusCode(),HttpStatus.NOT_FOUND);
	}

	@Test
	public void mustUpdateUserCorrectly() {
		when(userRepository.findByCpf("123")).thenReturn(Optional.of(User.builder().cpf("123").id(1L).name("fulano").build()));
		ResponseEntity<UserDto> responseEntity = controller.updateUser("123",
				UserForm.builder().cellphone(1234L).email("fulano@email.com")
				.build());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

}
