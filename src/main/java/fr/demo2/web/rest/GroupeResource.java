package fr.demo2.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.demo2.domain.Groupe;
import fr.demo2.repository.GroupeRepository;
import fr.demo2.web.rest.errors.BadRequestAlertException;
import fr.demo2.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Groupe.
 */
@RestController
@RequestMapping("/api")
public class GroupeResource {

    private final Logger log = LoggerFactory.getLogger(GroupeResource.class);

    private static final String ENTITY_NAME = "groupe";

    private final GroupeRepository groupeRepository;

    public GroupeResource(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    /**
     * POST  /groupes : Create a new groupe.
     *
     * @param groupe the groupe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupe, or with status 400 (Bad Request) if the groupe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupes")
    @Timed
    public ResponseEntity<Groupe> createGroupe(@RequestBody Groupe groupe) throws URISyntaxException {
        log.debug("REST request to save Groupe : {}", groupe);
        if (groupe.getId() != null) {
            throw new BadRequestAlertException("A new groupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Groupe result = groupeRepository.save(groupe);
        return ResponseEntity.created(new URI("/api/groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groupes : Updates an existing groupe.
     *
     * @param groupe the groupe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupe,
     * or with status 400 (Bad Request) if the groupe is not valid,
     * or with status 500 (Internal Server Error) if the groupe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/groupes")
    @Timed
    public ResponseEntity<Groupe> updateGroupe(@RequestBody Groupe groupe) throws URISyntaxException {
        log.debug("REST request to update Groupe : {}", groupe);
        if (groupe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Groupe result = groupeRepository.save(groupe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groupes : get all the groupes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of groupes in body
     */
    @GetMapping("/groupes")
    @Timed
    public List<Groupe> getAllGroupes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Groupes");
        return groupeRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /groupes/:id : get the "id" groupe.
     *
     * @param id the id of the groupe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupe, or with status 404 (Not Found)
     */
    @GetMapping("/groupes/{id}")
    @Timed
    public ResponseEntity<Groupe> getGroupe(@PathVariable Long id) {
        log.debug("REST request to get Groupe : {}", id);
        Optional<Groupe> groupe = groupeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(groupe);
    }

    /**
     * DELETE  /groupes/:id : delete the "id" groupe.
     *
     * @param id the id of the groupe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groupes/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        log.debug("REST request to delete Groupe : {}", id);

        groupeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
