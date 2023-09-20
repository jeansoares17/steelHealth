package br.com.fiap.steelhealth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.steelhealth.models.Alimentacao;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {

    // @Query("SELECT d FROM Alimentacao d WHERE d.descricao LIKE %?1%") //JPQL
    Page<Alimentacao> findByDescricaoContaining(String busca, Pageable pageable);

    // @Query("SELECT d FROM Alimentacao d ORDER BY d.id LIMIT ?1 OFFSET ?2")
    // List<Alimentacao> buscarPaginado(int tamanho, int offset);
    
}