package fr.demo2.repository;

import fr.demo2.domain.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query(value = "select distinct produit from Produit produit left join fetch produit.users left join fetch produit.achats",
        countQuery = "select count(distinct produit) from Produit produit")
    Page<Produit> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct produit from Produit produit left join fetch produit.users left join fetch produit.achats")
    List<Produit> findAllWithEagerRelationships();

    @Query("select produit from Produit produit left join fetch produit.users left join fetch produit.achats where produit.id =:id")
    Optional<Produit> findOneWithEagerRelationships(@Param("id") Long id);

}
