package br.com.fiap.wallet.service;

import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        final Optional<User> user = repository.findByCpf(cpf);

        if (user.isPresent()) {
            final User userPresent = user.get();
            return new org.springframework.security.core.userdetails.User(userPresent.getCpf(), user.get().getPassword(), new ArrayList<>());
        }else{
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }
}
