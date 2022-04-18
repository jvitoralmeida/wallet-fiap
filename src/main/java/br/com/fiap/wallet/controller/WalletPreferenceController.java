package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.model.dto.PartnerStoreDto;
import br.com.fiap.wallet.model.dto.UserDto;
import br.com.fiap.wallet.model.dto.WalletDto;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/preference")
public class WalletPreferenceController {

  @Autowired WalletRepository repository;

  @Autowired PartnerStoreRepository partnerStoreRepository;

  @Autowired ModelMapper mapper;

  @GetMapping("/{cpf}")
  public ResponseEntity<Set<PartnerStore>> getMyPreferences(@PathVariable String cpf) {
    final Optional<Wallet> wallet = repository.findByUserCpf(cpf);

    return wallet
        .map(value -> ResponseEntity.ok(value.getPartnerStore()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{cpf}/{idStore}")
  @Transactional
  public ResponseEntity<WalletDto> saveNewPreference(
      @PathVariable String cpf, @PathVariable Long idStore) {
    final Optional<PartnerStore> store = partnerStoreRepository.findById(idStore);
    final Optional<Wallet> walletOptional = repository.findByUserCpf(cpf);
    if (store.isPresent() && walletOptional.isPresent()) {
      final var partnerStore = store.get();
      final var wallet = walletOptional.get();
      wallet.getPartnerStore().add(partnerStore);

      final WalletDto build =
          WalletDto.builder()
              .id(wallet.getId())
              .user(mapper.map(wallet.getUser(), UserDto.class))
              .partnerStore(wallet.getPartnerStore())
              .value(wallet.getValue())
              .build();

      return ResponseEntity.ok(build);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{cpf}/{idStore}")
  @Transactional
  public ResponseEntity<WalletDto> deletePreference(
      @PathVariable String cpf, @PathVariable Long idStore) {
    final Optional<PartnerStore> store = partnerStoreRepository.findById(idStore);
    final Optional<Wallet> walletOptional = repository.findByUserCpf(cpf);
    if (store.isPresent() && walletOptional.isPresent()) {
      final var partnerStore = store.get();
      final var wallet = walletOptional.get();
      wallet.getPartnerStore().remove(partnerStore);

      final WalletDto build =
          WalletDto.builder()
              .id(wallet.getId())
              .user(mapper.map(wallet.getUser(), UserDto.class))
              .partnerStore(wallet.getPartnerStore())
              .value(wallet.getValue())
              .build();
      return ResponseEntity.ok(build);
    }
    return ResponseEntity.notFound().build();
  }
}
