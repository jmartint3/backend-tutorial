package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

public interface LoanService {
    // Recupera un listado paginado
    Page<Loan> findPage(LoanSearchDto dto, String gameName, String clientName, Date loanDate);

    // Guarda un nuevo préstamo
    void save(LoanDto data) throws Exception;

    // Elimina un préstamo
    void delete(Long id);
}
