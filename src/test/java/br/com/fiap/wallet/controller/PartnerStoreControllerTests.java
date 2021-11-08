package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.dto.PartnerStoreForm;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PartnerStoreControllerTests {

	@InjectMocks
	PartnerStoreController controller;

	@Mock
	PartnerStoreRepository storeRepository;
	@Mock
	ModelMapper mapper;

	@Test
	public void mustReturn404WhenStoreNotFound() {
		when(storeRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseEntity<PartnerStore> responseEntity = controller.getById(1L);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void mustStoreCorrectly() {
		when(storeRepository.findById(1L)).thenReturn(Optional.of(PartnerStore.builder()
				.build()));

		ResponseEntity<PartnerStore> responseEntity = controller.getById(1L);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void mustReturnAllStores() {
		when(storeRepository.findAll()).thenReturn(Collections.emptyList());
		ResponseEntity<List<PartnerStore>> listResponseEntity = controller.listStores();
		assertEquals(listResponseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void mustSaveAStore() {
		PartnerStoreForm storeForm = PartnerStoreForm.builder()
				.build();
		PartnerStore mapVar = PartnerStore.builder()
				.build();
		when(mapper.map(storeForm, PartnerStore.class)).thenReturn(mapVar);
		when(storeRepository.save(mapVar)).thenReturn(mapVar);
		ResponseEntity<PartnerStore> responseEntity = controller.saveNewStore(storeForm);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void mustReturnNotFoundWhenStoreNotFound() {
		when(storeRepository.findById(1L)).thenReturn(Optional.empty());
		ResponseEntity<?> responseEntity = controller.updateById(1L, null);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void mustUpdateUser() {
		PartnerStore partnerStore = PartnerStore.builder()
				.build();
		PartnerStoreForm partnerStoreForm = PartnerStoreForm.builder()
				.name("novo nome")
				.cnpj("novo cnpj")
				.percent(50L)
				.build();
		when(storeRepository.findById(1L)).thenReturn(Optional.of(partnerStore));
		ResponseEntity<?> responseEntity = controller.updateById(1L, partnerStoreForm);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void mustDeleteAStore() {
		ResponseEntity<?> responseEntity = controller.deleteById(1L);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
	}

}
