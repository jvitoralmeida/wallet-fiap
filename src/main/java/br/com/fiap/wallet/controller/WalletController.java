package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.model.WalletScore;
import br.com.fiap.wallet.model.dto.UserDto;
import br.com.fiap.wallet.model.dto.WalletDto;
import br.com.fiap.wallet.model.dto.WalletScoreDTO;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import br.com.fiap.wallet.repository.WalletScoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  @Autowired WalletScoreRepository walletScoreRepository;
  @Autowired PartnerStoreRepository partnerStoreRepository;
  @Autowired WalletRepository walletRepository;

  @Autowired ModelMapper mapper;

  @PostMapping("/earn")
  @Transactional
  public ResponseEntity<Object> earnPoints(@RequestBody WalletScoreDTO earnPoints) {

    Optional<Wallet> customerWallet = walletRepository.findByUserCpf(earnPoints.getCpf());
    Optional<PartnerStore> partnerStore =
        partnerStoreRepository.findById(earnPoints.getPartnerStoreId());

    var wallet =
        walletScoreRepository.save(
            WalletScore.builder()
                .wallet(customerWallet.get())
                .partnerStore(partnerStore.get())
                .transactionId(UUID.randomUUID().toString())
                .value(earnPoints.getValue())
                .build());

    customerWallet.get().setValue(customerWallet.get().getValue().add(earnPoints.getValue()));

    final WalletDto build =
        WalletDto.builder()
            .id(wallet.getId())
            .user(mapper.map(wallet.getWallet().getUser(), UserDto.class))
            .partnerStore(wallet.getWallet().getPartnerStore())
            .value(customerWallet.get().getValue())
            .build();
    return ResponseEntity.ok(build);
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<WalletDto> findWalletByCpf(@PathVariable String cpf) {

    var customerWallet = walletRepository.findByUserCpf(cpf).get();

    final WalletDto build =
        WalletDto.builder()
            .id(customerWallet.getId())
            .user(mapper.map(customerWallet.getUser(), UserDto.class))
            .partnerStore(customerWallet.getPartnerStore())
            .value(customerWallet.getValue())
            .build();
    return ResponseEntity.ok(build);
  }
}
