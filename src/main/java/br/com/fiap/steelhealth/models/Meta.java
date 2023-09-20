package br.com.fiap.steelhealth.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.fiap.steelhealth.controllers.*;
import java.math.BigDecimal;
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
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String Objetivo;

    @Min(value = 0, message = "deve ser positivo")
    @NotNull
    private BigDecimal PesoAtual;

    @Min(value = 0, message = "deve ser positiva")
    @NotNull
    private Double Altura;

    @Min(value = 0, message = "deve ser positivo")
    @NotNull
    private BigDecimal PesoDesejado;

    @ManyToOne
    private Conta conta;

    public EntityModel<Meta> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(MetaController.class).show(id)).withSelfRel(),
                linkTo(methodOn(MetaController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(MetaController.class).index(null, Pageable.unpaged())).withRel("all"),
                linkTo(methodOn(ContaController.class).show(this.getConta().getId())).withRel("conta"));
    }
}