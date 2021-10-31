package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.dto.PartnerStoreForm;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@Slf4j
public class PartnerStoreController {

    @Autowired
    PartnerStoreRepository storeRepository;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PartnerStore> getById(@PathVariable Long id) {
        final var partnerStore = storeRepository.findById(id);
        return partnerStore.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<PartnerStore>> listStores(@PathVariable Long id) {
        return ResponseEntity.ok(storeRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<PartnerStore> saveNewStore(@RequestBody PartnerStoreForm store) {
        final var storeSaved = storeRepository.save(mapper.map(store, PartnerStore.class));
        return ResponseEntity.ok(storeSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        storeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody PartnerStoreForm store) {
        final Optional<PartnerStore> partnerStore = storeRepository.findById(id);
        if (partnerStore.isPresent()) {
            final var storeToUpdate = partnerStore.get();
            storeToUpdate.setName(store.getName());
            storeToUpdate.setCnpj(store.getCnpj());
            storeToUpdate.setPercent(store.getPercent());
            return ResponseEntity.ok(storeToUpdate);
        }
        return ResponseEntity.notFound().build();
    }
}
