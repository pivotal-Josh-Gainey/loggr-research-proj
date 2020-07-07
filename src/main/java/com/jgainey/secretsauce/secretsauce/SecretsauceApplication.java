package com.jgainey.secretsauce.secretsauce;

import com.jgainey.secretsauce.secretsauce.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecretsauceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretsauceApplication.class, args);
        Utils.initLogger();
        new Main().start();
    }

}
