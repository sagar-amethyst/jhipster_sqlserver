package com.mss.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Tester_setup.
 */
@Entity
@Table(name = "tester_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tester_setup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "test_name")
    private String test_name;

    @Column(name = "product")
    private String product;

    @Column(name = "quater")
    private String quater;

    @Column(name = "week")
    private String week;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest_name() {
        return test_name;
    }

    public Tester_setup test_name(String test_name) {
        this.test_name = test_name;
        return this;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getProduct() {
        return product;
    }

    public Tester_setup product(String product) {
        this.product = product;
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuater() {
        return quater;
    }

    public Tester_setup quater(String quater) {
        this.quater = quater;
        return this;
    }

    public void setQuater(String quater) {
        this.quater = quater;
    }

    public String getWeek() {
        return week;
    }

    public Tester_setup week(String week) {
        this.week = week;
        return this;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getStatus() {
        return status;
    }

    public Tester_setup status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tester_setup tester_setup = (Tester_setup) o;
        if (tester_setup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tester_setup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tester_setup{" +
            "id=" + getId() +
            ", test_name='" + getTest_name() + "'" +
            ", product='" + getProduct() + "'" +
            ", quater='" + getQuater() + "'" +
            ", week='" + getWeek() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
