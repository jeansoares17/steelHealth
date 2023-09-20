package br.com.fiap.steelhealth.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fiap.steelhealth.models.*;
import br.com.fiap.steelhealth.repository.*;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MetaRepository metaRepository;

    @Autowired
    TreinoRepository treinoRepository;

    @Autowired
    AlimentacaoRepository alimentacaoRepository;

    @Override
    public void run(String... args) throws Exception {
        Usuario u1 = new Usuario(1L, "Jean", "jean@gmail.com", "1234");
        Usuario u2 = new Usuario(2L, "Kaio", "kaio@gmail.com", "1234");
        usuarioRepository.saveAll(List.of(c1, c2));

        metaRepository.saveAll(List.of(
                Meta.builder().Objetivo("emagrecer").PesoAtual(new BigDecimal(98.00)).Altura(1.85)
                        .PesoDesejado(new BigDecimal(75.00)).usuario(u1).build(),
                Meta.builder().Objetivo("ganhar massa magra").PesoAtual(new BigDecimal(50.00)).Altura(1.75)
                        .PesoDesejado(new BigDecimal(70.00)).usuario(u2).build()));

        usuarioRepository.save(Usuario.builder()
                .nome("Jean Soares")
                .email("jean@gmail.com")
                .senha("1234")
                .build());
    }

}