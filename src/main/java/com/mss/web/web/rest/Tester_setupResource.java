package com.mss.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mss.web.domain.Tester_setup;

import com.mss.web.repository.Tester_setupRepository;
import com.mss.web.web.rest.errors.BadRequestAlertException;
import com.mss.web.web.rest.util.HeaderUtil;
import com.mss.web.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tester_setup.
 */
@RestController
@RequestMapping("/api")
public class Tester_setupResource {

    private final Logger log = LoggerFactory.getLogger(Tester_setupResource.class);

    private static final String ENTITY_NAME = "tester_setup";

    private final Tester_setupRepository tester_setupRepository;

    public Tester_setupResource(Tester_setupRepository tester_setupRepository) {
        this.tester_setupRepository = tester_setupRepository;
    }

    /**
     * POST  /tester-setups : Create a new tester_setup.
     *
     * @param tester_setup the tester_setup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tester_setup, or with status 400 (Bad Request) if the tester_setup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tester-setups")
    @Timed
    public ResponseEntity<Tester_setup> createTester_setup(@RequestBody Tester_setup tester_setup) throws URISyntaxException {
        log.debug("REST request to save Tester_setup : {}", tester_setup);
        if (tester_setup.getId() != null) {
            throw new BadRequestAlertException("A new tester_setup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tester_setup result = tester_setupRepository.save(tester_setup);
        return ResponseEntity.created(new URI("/api/tester-setups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tester-setups : Updates an existing tester_setup.
     *
     * @param tester_setup the tester_setup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tester_setup,
     * or with status 400 (Bad Request) if the tester_setup is not valid,
     * or with status 500 (Internal Server Error) if the tester_setup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tester-setups")
    @Timed
    public ResponseEntity<Tester_setup> updateTester_setup(@RequestBody Tester_setup tester_setup) throws URISyntaxException {
        log.debug("REST request to update Tester_setup : {}", tester_setup);
        if (tester_setup.getId() == null) {
            return createTester_setup(tester_setup);
        }
        Tester_setup result = tester_setupRepository.save(tester_setup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tester_setup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tester-setups : get all the tester_setups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tester_setups in body
     */
    @GetMapping("/tester-setups")
    @Timed
    public ResponseEntity<List<Tester_setup>> getAllTester_setups(Pageable pageable) {
        log.debug("REST request to get a page of Tester_setups");
        Page<Tester_setup> page = tester_setupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tester-setups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tester-setups/:id : get the "id" tester_setup.
     *
     * @param id the id of the tester_setup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tester_setup, or with status 404 (Not Found)
     */
    @GetMapping("/tester-setups/{id}")
    @Timed
    public ResponseEntity<Tester_setup> getTester_setup(@PathVariable Long id) {
        log.debug("REST request to get Tester_setup : {}", id);
        Tester_setup tester_setup = tester_setupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tester_setup));
    }

    /**
     * DELETE  /tester-setups/:id : delete the "id" tester_setup.
     *
     * @param id the id of the tester_setup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tester-setups/{id}")
    @Timed
    public ResponseEntity<Void> deleteTester_setup(@PathVariable Long id) {
        log.debug("REST request to delete Tester_setup : {}", id);
        tester_setupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
