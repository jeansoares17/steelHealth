package br.com.fiap.steelhealth.models;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.steelhealth.controllers.UsuarioController;
import br.com.fiap.steelhealth.controllers.AlimentacaoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Alimentacaoid;

    @NotBlank
    @Size(min = 5, max = 50)
    private String TipoDaRefeicao;

    @NotBlank
    @Size(min = 5, max = 255)
    private String Descricao;

    @Min(value = 0, message = "deve ser positivo")
    @NotNull
    private int QtdRefeicoes;

    @ManyToOne
    private Conta conta;

    public EntityModel<Alimentacao> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(AlimentacaoController.class).show(id)).withSelfRel(),
                linkTo(methodOn(AlimentacaoController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(AlimentacaoController.class).index(null, Pageable.unpaged())).withRel("all"),
                linkTo(methodOn(ContaController.class).show(this.getConta().getId())).withRel("conta"));
    }
}