package br.com.fiap.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TesteTeste {

    @Test
    public void teste(){
        var soma = 1+1;

        assertEquals(soma,2);
    }
}
