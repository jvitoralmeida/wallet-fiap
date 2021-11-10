package br.com.fiap.wallet.controller;

import br.com.fiap.wallet.model.User;
import br.com.fiap.wallet.model.Wallet;
import br.com.fiap.wallet.model.dto.UserDto;
import br.com.fiap.wallet.model.form.UserForm;
import br.com.fiap.wallet.repository.UserRepository;
import br.com.fiap.wallet.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder bCryptPasswordEncoder;

  @Autowired private WalletRepository walletRepository;

  @Autowired private ModelMapper mapper;

  @PostMapping("/signup")
  public ResponseEntity<UserDto> signUp(@RequestBody UserForm userForm) {
    userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
    final User user = mapper.map(userForm, User.class);
    final var userSaved = userRepository.save(user);

    final Wallet newWalletUser =
        Wallet.builder().user(userSaved).value(new BigDecimal("0.0")).build();
    walletRepository.save(newWalletUser);
    return ResponseEntity.ok(mapper.map(userSaved, UserDto.class));
  }

  @PutMapping("/{cpf}")
  @Transactional
  public ResponseEntity<UserDto> updateUser(
      @PathVariable String cpf, @RequestBody UserForm userForm) {
    final Optional<User> userOp = userRepository.findByCpf(cpf);
    if (userOp.isPresent()) {
      final User user = userOp.get();
      user.setEmail(userForm.getEmail());
      user.setCellphone(userForm.getCellphone());
      user.setSendNotification(userForm.getSendNotification());
      user.setBirthDate(userForm.getBirthDate());
      UserDto dto = mapper.map(user, UserDto.class);
      return ResponseEntity.ok(dto);
    }
    return ResponseEntity.notFound().build();
  }
}
