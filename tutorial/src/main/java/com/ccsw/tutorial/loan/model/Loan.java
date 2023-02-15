package com.ccsw.tutorial.loan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @Column(name = "initial_date", nullable = false)
    private Date initialDate;

    @Column(name = "final_date", nullable = false)
    private Date finalDate;

    /**
     * LOAN GETTERS AND SETTERS
     */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setGameName(String name) {
        this.gameName = name;
    }

    public Date getInitialDate() {
        return this.initialDate;
    }

    public void setInitialDate(Date date) {
        this.initialDate = date;
    }

    public Date getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
}
