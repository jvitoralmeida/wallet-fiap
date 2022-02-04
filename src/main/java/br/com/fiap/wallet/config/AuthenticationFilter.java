package br.com.fiap.wallet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  AuthenticationManager authenticationManager;

  private String secret = "96DBAC71D8D977534128BE20F2F5CBBCD196CD975D1184573D1970D7152CE26F";

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    setFilterProcessesUrl("/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      br.com.fiap.wallet.model.User creds =
          new ObjectMapper()
              .readValue(request.getInputStream(), br.com.fiap.wallet.model.User.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getCpf(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException("Could not read request" + e);
    }
  }

  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain,
      Authentication authentication)
      throws IOException {
    var cpf = ((User) authentication.getPrincipal()).getUsername().toString();
    String token =
        Jwts.builder()
            .setSubject(cpf)
            .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();
    response.addHeader("Authorization", "Bearer " + token);

    var json =
        new HashMap<String, String>() {
          {
            put("cpf", cpf);
            put("token", token);
          }
        };

    response.getWriter().write(new JSONObject(json).toString());
    response.getWriter().flush();
  }
}
