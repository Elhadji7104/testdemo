package fr.demo2.repository;

import fr.demo2.domain.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Fournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    @Query(value = "select distinct fournisseur from Fournisseur fournisseur left join fetch fournisseur.achats",
        countQuery = "select count(distinct fournisseur) from Fournisseur fournisseur")
    Page<Fournisseur> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct fournisseur from Fournisseur fournisseur left join fetch fournisseur.achats")
    List<Fournisseur> findAllWithEagerRelationships();

    @Query("select fournisseur from Fournisseur fournisseur left join fetch fournisseur.achats where fournisseur.id =:id")
    Optional<Fournisseur> findOneWithEagerRelationships(@Param("id") Long id);

}
