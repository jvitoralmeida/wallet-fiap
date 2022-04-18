package br.com.fiap.wallet.config;

import br.com.fiap.wallet.controller.UserController;
import br.com.fiap.wallet.model.PartnerStore;
import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.form.UserForm;
import br.com.fiap.wallet.repository.PartnerStoreRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

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
        final var store1 = PartnerStore.builder().cnpj("1").name("Starbucks").percent(5L).urlLogo("https://upload.wikimedia.org/wikipedia/pt/0/0f/Starbucks_Corporation_Logo_2011.svg.png").build();
        final var store2 = PartnerStore.builder().cnpj("2").name("Riachuelo").percent(3L).urlLogo("https://99prod.s3.amazonaws.com/uploads/c1723294-8cc6-4692-a9bd-8fdbcac6d50d/riachuelo.png").build();
        final var store3 = PartnerStore.builder().cnpj("3").name("Saraiva").percent(10L).urlLogo("https://pbs.twimg.com/profile_images/1213100715478016001/ze-4gdQR_400x400.jpg").build();

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        final var user1 = UserForm.builder().cpf("1234").birthDate(LocalDate.now()).email("usuario1@gmail.com").password("senhadificil").name("fulano").build();
        final var user2 = UserForm.builder().cpf("12345").birthDate(LocalDate.now()).email("usuario2@gmail.com").password("senhadificil").name("ciclano").build();
        final var user3 = UserForm.builder().cpf("12346").birthDate(LocalDate.now()).email("usuario3@gmail.com").password("senhadificil").name("beltrano").build();

        Arrays.asList(user1, user2, user3).forEach(user -> {
            final var userSaved = userController.signUp(user);
        });    }

    @Bean
    public ModelMapper modelMap() {
        return new ModelMapper();
    }
}
