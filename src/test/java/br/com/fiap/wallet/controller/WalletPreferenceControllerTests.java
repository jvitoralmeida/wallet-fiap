package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// @RunWith(JUnitPlatform.class)
public class WalletPreferenceControllerTests {

  @InjectMocks WalletPreferenceController controller;
  @Mock WalletRepository repository;
  @Mock PartnerStoreRepository partnerStoreRepository;

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
    when(repository.findByUserCpf("123")).thenReturn(Optional.empty());
    final ResponseEntity<Set<PartnerStore>> myPreferences = controller.getMyPreferences("123");

    assertEquals(myPreferences, ResponseEntity.notFound().build());
  }

  @Test
  public void mustAddANewStoreOnTheUserPreferences() {
    var mockStore = PartnerStore.builder().build();
    var mockWallet = Wallet.builder().partnerStore(new HashSet<>()).build();
    when(repository.findByUserCpf("123")).thenReturn(Optional.of(mockWallet));
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.of(mockStore));

    final var walletSaved = controller.saveNewPreference("123", 1L);

    assertTrue(walletSaved.getBody().getPartnerStore().contains(mockStore));
  }

  @Test
  public void mustDeleteUserPreference() {
    final PartnerStore loja = PartnerStore.builder().id(1L).name("loja").build();
    final Wallet wallet = Wallet.builder().partnerStore(new HashSet<>(Arrays.asList(loja))).build();
    when(repository.findByUserCpf("123")).thenReturn(Optional.of(wallet));
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.of(loja));

    final ResponseEntity<Wallet> responseEntity = controller.deletePreference("123", 1L);
    wallet.setPartnerStore(new HashSet<>());

    assertEquals(responseEntity, ResponseEntity.ok(wallet));
  }

  @Test
  public void mustReturnNotFoundWhenCpfDoesNotExist() {
    when(repository.findByUserCpf("123")).thenReturn(Optional.empty());
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.empty());

    final ResponseEntity<Wallet> responseEntity = controller.deletePreference("123", 1L);

    assertEquals(responseEntity, ResponseEntity.notFound().build());
  }

  @Test
  public void mustReturnNotFoundWhenCpfDoesNotExistOnMethodAdd() {
    when(repository.findByUserCpf("123")).thenReturn(Optional.empty());
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.empty());
    final ResponseEntity<Wallet> responseEntity = controller.saveNewPreference("123", 1L);
    assertEquals(responseEntity, ResponseEntity.notFound().build());
  }

  @Test
  public void mustReturnNotFoundWhenThereIsAStoreButNotAWallet() {
    when(repository.findByUserCpf("123")).thenReturn(Optional.empty());
    when(partnerStoreRepository.findById(1L))
        .thenReturn(Optional.of(PartnerStore.builder().build()));
    final ResponseEntity<Wallet> responseEntity = controller.deletePreference("123", 1L);
    assertEquals(responseEntity, ResponseEntity.notFound().build());
  }

  @Test
  public void mustReturnNotFoundWhenThereIsAWalletButNotAStore() {
    when(repository.findByUserCpf("123")).thenReturn(Optional.of(Wallet.builder().build()));
    when(partnerStoreRepository.findById(1L)).thenReturn(Optional.empty());
    final ResponseEntity<Wallet> responseEntity = controller.deletePreference("123", 1L);
    assertEquals(responseEntity, ResponseEntity.notFound().build());
  }
}
