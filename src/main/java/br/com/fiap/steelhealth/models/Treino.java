package br.com.fiap.steelhealth.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.steelhealth.controllers.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Entity
@Data
@NoArgsConstructor
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Treinoid;

    @NotBlank
    @Size(min = 5, max = 255)
    @NotNull
    private String AgrupamentoMuscular;

    @NotBlank
    @Size(min = 5, max = 255)
    @NotNull
    private String NomeDoExercicio;

    @Min(value = 0, message = "deve ser positivo")
    @NotNull
    private int QtdRpt;

    @Min(value = 0, message = "deve ser positivo")
    @NotNull
    private int QtdSeries;

    @ManyToOne
    private Conta conta;

    public EntityModel<Treino> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(TreinoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(TreinoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(TreinoController.class).index(null, Pageable.unpaged())).withRel("all"),
                linkTo(methodOn(ContaController.class).show(this.getConta().getId())).withRel("conta"));
    }

}