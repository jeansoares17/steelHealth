package br.com.fiap.steelhealth.repository;

import br.com.fiap.steelhealth.models.Meta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaRepository extends JpaRepository <Meta, Long>{
    // @Query("SELECT d FROM Meta d WHERE d.descricao LIKE %?1%") //JPQL
    Page<Meta> findByDescricaoContaining(String busca, Pageable pageable);

    // @Query("SELECT d FROM Meta d ORDER BY d.id LIMIT ?1 OFFSET ?2")
    // List<Meta> buscarPaginado(int tamanho, int offset);
}
