package br.com.fiap.steelhealth.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.steelhealth.exceptions.RestNotFoundException;
import br.com.fiap.steelhealth.models.*;
import br.com.fiap.steelhealth.repository.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MetaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public List<Meta> index() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Meta> create(@RequestBody @Valid Meta meta) {
        log.info("cadastrando meta " + meta);
        repository.save(meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(meta);
    }

    @GetMapping("{id}")
    public ResponseEntity<Meta> show(@PathVariable Long id) {
        log.info("detalhando meta " + id);
        return ResponseEntity.ok(getMeta(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Meta> destroy(@PathVariable Long id) {
        log.info("apagando meta " + id);
        getMeta(id);
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Meta> update(@PathVariable Long id, @RequestBody @Valid Meta meta) {
        log.info("atualizando meta " + id);
        getMeta(id);
        meta.setId(id);
        repository.save(meta);
        return ResponseEntity.ok(meta);
    }

    private Meta getMeta(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestNotFoundException("meta não encontrada"));
    }

}