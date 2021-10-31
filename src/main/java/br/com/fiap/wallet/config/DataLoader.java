package br.com.fiap.wallet.config;

import br.com.fiap.wallet.controller.UserController;
import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.model.dto.UserDto;
import br.com.fiap.wallet.model.form.UserForm;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class DataLoader implements ApplicationRunner {
    @Autowired
    PartnerStoreRepository storeRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserController userController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final var store1 = PartnerStore.builder().cnpj("1").name("starbucks").percent(5L).build();
        final var store2 = PartnerStore.builder().cnpj("2").name("riachuelo").percent(3L).build();
        final var store3 = PartnerStore.builder().cnpj("3").name("saraiva").percent(10L).build();

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        final var user1 = UserForm.builder().cpf("1234").birthDate(LocalDate.now()).email("usuario1@gmail.com").password("senhadificil").name("fulano").build();
        final var user2 = UserForm.builder().cpf("12345").birthDate(LocalDate.now()).email("usuario2@gmail.com").password("senhadificil").name("ciclano").build();
        final var user3 = UserForm.builder().cpf("12346").birthDate(LocalDate.now()).email("usuario3@gmail.com").password("senhadificil").name("beltrano").build();

        final var users = new ArrayList<User>();
        Arrays.asList(user1, user2, user3).forEach(user -> {
            final var userSaved = userController.signUp(user);
            users.add(modelMap().map(userSaved.getBody(), User.class));
        });

        users.forEach(user -> walletRepository.save(Wallet.builder().user(user).value(new BigDecimal("0.0")).partnerStore(new HashSet<>(Arrays.asList(store1, store2, store3))).build()));
    }

    @Bean
    public ModelMapper modelMap() {
        return new ModelMapper();
    }
}
