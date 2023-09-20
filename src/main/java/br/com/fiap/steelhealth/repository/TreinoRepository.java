package br.com.fiap.steelhealth.repository;

import br.com.fiap.steelhealth.models.Treino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinoRepository extends JpaRepository <Treino, Long>{
    // @Query("SELECT d FROM Treino d WHERE d.descricao LIKE %?1%") //JPQL
    Page<Treino> findByDescricaoContaining(String busca, Pageable pageable);

    // @Query("SELECT d FROM Treino d ORDER BY d.id LIMIT ?1 OFFSET ?2")
    // List<Treino> buscarPaginado(int tamanho, int offset);
}
