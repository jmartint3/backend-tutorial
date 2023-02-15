package com.ccsw.tutorial.loan.model;

import java.util.Date;

public class LoanDto {

    private Long id;
    private String clientName;
    private String gameName;
    private Date initialDate;
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
