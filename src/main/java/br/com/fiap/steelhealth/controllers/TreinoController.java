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
@RequestMapping("/api/metas")
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "treino")
public class TreinoController {

    @Autowired
    TreinoRepository repository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public  PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Treino> treinos = (busca == null)?
        repository.findAll(pageable):
        repository.findByDescricaoContaining(busca, pageable);

        return assembler.toModel(treinos.map(Treino::toEntityModel));
    }

    @PostMapping
    @ApiResponses ({
        @ApiResponse(responseCode = "201", description = "Treino cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Os campos enviados são inválidos")
    })
    
    public ResponseEntity<Object> create(@RequestBody Treino treino){
        log.info("cadastrando treino " + treino);
        repository.save(treino);
        treino.setConta(contaRepository.findById(treino.getConta().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(treino.toEntityModel());
    }
    
    @GetMapping("{id}")
    @Operation(
        summary = "Detalhar meta",
        description = "Endpoint que recebe um id e retorna os dados da meta. O id deve ser ..."
        
    )
    public EntityModel<Treino> show(@PathVariable Long id){
        log.info("detalhando treino " + id);
        var treino = repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("treino não encontrada"));

        return treino.toEntityModel();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Treino> destroy(@PathVariable Long id){
        log.info("apagando treino " + id);
        var treino = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, treino não encontrada"));

    repository.delete(treino);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Treino> update(@PathVariable Long id, @RequestBody @Valid Treino treino){
        log.info("atualizando treino " + id);
        repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, meta não encontrada"));
        treino.setId(id);
        repository.save(treino);

        return treino.toEntityModel();
    }
}