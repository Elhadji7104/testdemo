package fr.demo2.web.rest;

import fr.demo2.Demo2App;

import fr.demo2.domain.Produit;
import fr.demo2.repository.ProduitRepository;
import fr.demo2.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static fr.demo2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProduitResource REST controller.
 *
 * @see ProduitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Demo2App.class)
public class ProduitResourceIntTest {

    private static final String DEFAULT_CAS = "AAAAAAAAAA";
    private static final String UPDATED_CAS = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITE = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_MM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RISQUE = "AAAAAAAAAA";
    private static final String UPDATED_RISQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MOLECULE = "AAAAAAAAAA";
    private static final String UPDATED_MOLECULE = "BBBBBBBBBB";

    @Autowired
    private ProduitRepository produitRepository;

    @Mock
    private ProduitRepository produitRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProduitMockMvc;

    private Produit produit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduitResource produitResource = new ProduitResource(produitRepository);
        this.restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .cas(DEFAULT_CAS)
            .quantite(DEFAULT_QUANTITE)
            .lieu(DEFAULT_LIEU)
            .nom(DEFAULT_NOM)
            .mm(DEFAULT_MM)
            .risque(DEFAULT_RISQUE)
            .molecule(DEFAULT_MOLECULE);
        return produit;
    }

    @Before
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getCas()).isEqualTo(DEFAULT_CAS);
        assertThat(testProduit.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testProduit.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduit.getMm()).isEqualTo(DEFAULT_MM);
        assertThat(testProduit.getRisque()).isEqualTo(DEFAULT_RISQUE);
        assertThat(testProduit.getMolecule()).isEqualTo(DEFAULT_MOLECULE);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].cas").value(hasItem(DEFAULT_CAS.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].mm").value(hasItem(DEFAULT_MM.toString())))
            .andExpect(jsonPath("$.[*].risque").value(hasItem(DEFAULT_RISQUE.toString())))
            .andExpect(jsonPath("$.[*].molecule").value(hasItem(DEFAULT_MOLECULE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProduitsWithEagerRelationshipsIsEnabled() throws Exception {
        ProduitResource produitResource = new ProduitResource(produitRepositoryMock);
        when(produitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProduitMockMvc.perform(get("/api/produits?eagerload=true"))
        .andExpect(status().isOk());

        verify(produitRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProduitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProduitResource produitResource = new ProduitResource(produitRepositoryMock);
            when(produitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProduitMockMvc.perform(get("/api/produits?eagerload=true"))
        .andExpect(status().isOk());

            verify(produitRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.cas").value(DEFAULT_CAS.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.mm").value(DEFAULT_MM.toString()))
            .andExpect(jsonPath("$.risque").value(DEFAULT_RISQUE.toString()))
            .andExpect(jsonPath("$.molecule").value(DEFAULT_MOLECULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .cas(UPDATED_CAS)
            .quantite(UPDATED_QUANTITE)
            .lieu(UPDATED_LIEU)
            .nom(UPDATED_NOM)
            .mm(UPDATED_MM)
            .risque(UPDATED_RISQUE)
            .molecule(UPDATED_MOLECULE);

        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduit)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getCas()).isEqualTo(UPDATED_CAS);
        assertThat(testProduit.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProduit.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduit.getMm()).isEqualTo(UPDATED_MM);
        assertThat(testProduit.getRisque()).isEqualTo(UPDATED_RISQUE);
        assertThat(testProduit.getMolecule()).isEqualTo(UPDATED_MOLECULE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Create the Produit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Get the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produit.class);
        Produit produit1 = new Produit();
        produit1.setId(1L);
        Produit produit2 = new Produit();
        produit2.setId(produit1.getId());
        assertThat(produit1).isEqualTo(produit2);
        produit2.setId(2L);
        assertThat(produit1).isNotEqualTo(produit2);
        produit1.setId(null);
        assertThat(produit1).isNotEqualTo(produit2);
    }
}
