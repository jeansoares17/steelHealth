package br.com.fiap.steelhealth.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.steelhealth.exceptions.RestNotFoundException;
import br.com.fiap.steelhealth.models.*;
import br.com.fiap.steelhealth.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/alimentacao")
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "alimentacao")
public class AlimentacaoController {

    @Autowired
   AlimentacaoRepository repository;

   @Autowired
    ContaRepository contaRepository;

   @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Alimentacao> alimentacao = (busca == null)?
            repository.findAll(pageable):
            repository.findByDescricaoContaining(busca, pageable);

        return assembler.toModel(alimentacao.map(Alimentacao::toEntityModel));
    }

    @PostMapping
    @ApiResponses ({
        @ApiResponse(responseCode = "201", description = "Alimentação cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Os campos enviados são inválidos")
    })
    public ResponseEntity<Object> create(@RequestBody@Valid Alimentacao alimentacao){
        log.info("cadastrando alimentação " + alimentacao);
        repository.save(alimentacao);
        alimentacao.setConta(contaRepository.findById(alimentacao.getConta().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentacao.toEntityModel());
    }
    
    @GetMapping("{id}")
    @Operation(
        summary = "Detalhar alimentação",
        description = "Endpoint que recebe um id e retorna os dados da alimentação. O id deve ser ..."
        
    )
    public EntityModel<Alimentacao> show(@PathVariable Long id){
        log.info("detalhando alimentação " + id);
        var alimentacao = repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("alimentação não encontrada"));

        return alimentacao.toEntityModel();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Alimentacao> destroy(@PathVariable Long id){
        log.info("apagando alimentação " + id);
        var alimentacao = repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, alimentação não encontrada"));

            repository.delete(alimentacao);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Alimentacao> update(@PathVariable Long id, @RequestBody @Valid Alimentacao alimentacao){
        log.info("atualizando alimentação " + id);
        repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, alimentação não encontrada"));

        alimentacao.setId(id);
        repository.save(alimentacao);

        return alimentacao.toEntityModel();
    }
}