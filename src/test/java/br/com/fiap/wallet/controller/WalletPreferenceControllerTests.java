package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletPreferenceControllerTests {

  @InjectMocks
  WalletPreferenceController controller;
  @Mock
  WalletRepository repository;
  @Mock
  PartnerStoreRepository partnerStoreRepository;

  @Test
  public void mustReturnHttpStatusOkWithList() {
    when(repository.findByUserCpf("123"))
        .thenReturn(
            Optional.of(
                Wallet.builder()
                    .partnerStore(
                        new HashSet<>(Arrays.asList(PartnerStore.builder().name("loja").build())))
                    .build()));
    final ResponseEntity<Set<PartnerStore>> myPreferences = controller.getMyPreferences("123");

    assertEquals(myPreferences, ResponseEntity.ok(myPreferences.getBody()));
  }

  @Test
  public void mustReturnHttpStatusNotFoundWhenCpfNotFound() {
    when(repository.findByUserCpf("123"))
        .thenReturn(Optional.empty());
    final ResponseEntity<Set<PartnerStore>> myPreferences = controller.getMyPreferences("123");

    assertEquals(myPreferences, ResponseEntity.notFound().build());
  }

  @Test
  public void mustAddANewStoreOnTheUserPreferences(){
    var mockStore = PartnerStore.builder().build();
    var mockWallet = Wallet.builder().partnerStore(new HashSet<>()).build();
    when(repository.findByUserCpf("123")).thenReturn(Optional.of(mockWallet));
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.of(mockStore));

    final var walletSaved = controller.saveNewPreference("123", 1L);

    assertTrue(walletSaved.getBody().getPartnerStore().contains(mockStore));
  }

}
