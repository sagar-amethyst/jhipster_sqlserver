package com.mss.web.web.rest;

import com.mss.web.MssjappApp;

import com.mss.web.domain.Tester_setup;
import com.mss.web.repository.Tester_setupRepository;
import com.mss.web.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mss.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tester_setupResource REST controller.
 *
 * @see Tester_setupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MssjappApp.class)
public class Tester_setupResourceIntTest {

    private static final String DEFAULT_TEST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_QUATER = "AAAAAAAAAA";
    private static final String UPDATED_QUATER = "BBBBBBBBBB";

    private static final String DEFAULT_WEEK = "AAAAAAAAAA";
    private static final String UPDATED_WEEK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private Tester_setupRepository tester_setupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTester_setupMockMvc;

    private Tester_setup tester_setup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Tester_setupResource tester_setupResource = new Tester_setupResource(tester_setupRepository);
        this.restTester_setupMockMvc = MockMvcBuilders.standaloneSetup(tester_setupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tester_setup createEntity(EntityManager em) {
        Tester_setup tester_setup = new Tester_setup()
            .test_name(DEFAULT_TEST_NAME)
            .product(DEFAULT_PRODUCT)
            .quater(DEFAULT_QUATER)
            .week(DEFAULT_WEEK)
            .status(DEFAULT_STATUS);
        return tester_setup;
    }

    @Before
    public void initTest() {
        tester_setup = createEntity(em);
    }

    @Test
    @Transactional
    public void createTester_setup() throws Exception {
        int databaseSizeBeforeCreate = tester_setupRepository.findAll().size();

        // Create the Tester_setup
        restTester_setupMockMvc.perform(post("/api/tester-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tester_setup)))
            .andExpect(status().isCreated());

        // Validate the Tester_setup in the database
        List<Tester_setup> tester_setupList = tester_setupRepository.findAll();
        assertThat(tester_setupList).hasSize(databaseSizeBeforeCreate + 1);
        Tester_setup testTester_setup = tester_setupList.get(tester_setupList.size() - 1);
        assertThat(testTester_setup.getTest_name()).isEqualTo(DEFAULT_TEST_NAME);
        assertThat(testTester_setup.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testTester_setup.getQuater()).isEqualTo(DEFAULT_QUATER);
        assertThat(testTester_setup.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testTester_setup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTester_setupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tester_setupRepository.findAll().size();

        // Create the Tester_setup with an existing ID
        tester_setup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTester_setupMockMvc.perform(post("/api/tester-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tester_setup)))
            .andExpect(status().isBadRequest());

        // Validate the Tester_setup in the database
        List<Tester_setup> tester_setupList = tester_setupRepository.findAll();
        assertThat(tester_setupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTester_setups() throws Exception {
        // Initialize the database
        tester_setupRepository.saveAndFlush(tester_setup);

        // Get all the tester_setupList
        restTester_setupMockMvc.perform(get("/api/tester-setups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tester_setup.getId().intValue())))
            .andExpect(jsonPath("$.[*].test_name").value(hasItem(DEFAULT_TEST_NAME.toString())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT.toString())))
            .andExpect(jsonPath("$.[*].quater").value(hasItem(DEFAULT_QUATER.toString())))
            .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getTester_setup() throws Exception {
        // Initialize the database
        tester_setupRepository.saveAndFlush(tester_setup);

        // Get the tester_setup
        restTester_setupMockMvc.perform(get("/api/tester-setups/{id}", tester_setup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tester_setup.getId().intValue()))
            .andExpect(jsonPath("$.test_name").value(DEFAULT_TEST_NAME.toString()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT.toString()))
            .andExpect(jsonPath("$.quater").value(DEFAULT_QUATER.toString()))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTester_setup() throws Exception {
        // Get the tester_setup
        restTester_setupMockMvc.perform(get("/api/tester-setups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTester_setup() throws Exception {
        // Initialize the database
        tester_setupRepository.saveAndFlush(tester_setup);
        int databaseSizeBeforeUpdate = tester_setupRepository.findAll().size();

        // Update the tester_setup
        Tester_setup updatedTester_setup = tester_setupRepository.findOne(tester_setup.getId());
        // Disconnect from session so that the updates on updatedTester_setup are not directly saved in db
        em.detach(updatedTester_setup);
        updatedTester_setup
            .test_name(UPDATED_TEST_NAME)
            .product(UPDATED_PRODUCT)
            .quater(UPDATED_QUATER)
            .week(UPDATED_WEEK)
            .status(UPDATED_STATUS);

        restTester_setupMockMvc.perform(put("/api/tester-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTester_setup)))
            .andExpect(status().isOk());

        // Validate the Tester_setup in the database
        List<Tester_setup> tester_setupList = tester_setupRepository.findAll();
        assertThat(tester_setupList).hasSize(databaseSizeBeforeUpdate);
        Tester_setup testTester_setup = tester_setupList.get(tester_setupList.size() - 1);
        assertThat(testTester_setup.getTest_name()).isEqualTo(UPDATED_TEST_NAME);
        assertThat(testTester_setup.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testTester_setup.getQuater()).isEqualTo(UPDATED_QUATER);
        assertThat(testTester_setup.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testTester_setup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTester_setup() throws Exception {
        int databaseSizeBeforeUpdate = tester_setupRepository.findAll().size();

        // Create the Tester_setup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTester_setupMockMvc.perform(put("/api/tester-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tester_setup)))
            .andExpect(status().isCreated());

        // Validate the Tester_setup in the database
        List<Tester_setup> tester_setupList = tester_setupRepository.findAll();
        assertThat(tester_setupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTester_setup() throws Exception {
        // Initialize the database
        tester_setupRepository.saveAndFlush(tester_setup);
        int databaseSizeBeforeDelete = tester_setupRepository.findAll().size();

        // Get the tester_setup
        restTester_setupMockMvc.perform(delete("/api/tester-setups/{id}", tester_setup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tester_setup> tester_setupList = tester_setupRepository.findAll();
        assertThat(tester_setupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tester_setup.class);
        Tester_setup tester_setup1 = new Tester_setup();
        tester_setup1.setId(1L);
        Tester_setup tester_setup2 = new Tester_setup();
        tester_setup2.setId(tester_setup1.getId());
        assertThat(tester_setup1).isEqualTo(tester_setup2);
        tester_setup2.setId(2L);
        assertThat(tester_setup1).isNotEqualTo(tester_setup2);
        tester_setup1.setId(null);
        assertThat(tester_setup1).isNotEqualTo(tester_setup2);
    }
}
