package com.ccsw.tutorial.loan;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.loan.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    // Método para recuperar un listado paginado de préstamos
    @Query("select l from Loan l where (:game_name is null or l.gameName like '%'||:game_name||'%') and (:client_name is null or l.clientName like '%'||:client_name||'%') and (DATE(:loan_date) is null or :loan_date between l.initialDate and l.finalDate)")
    Page<Loan> findAll(Pageable pageable, @Param("game_name") String gameName, @Param("client_name") String clientName,
            @Param("loan_date") Date loanDate);

    // and ((:initial_date between l.initialDate and l.finalDate) or :final_date
    // between l.initialDate and l.finalDate")
    @Query("select l from Loan l where (:game_name is null or l.gameName like '%'||:game_name||'%')and ((:initial_date is null or :initial_date between l.initialDate and l.finalDate) or (:final_date is null or :final_date between l.initialDate and l.finalDate)) ")
    List<Loan> findByGameName(@Param("game_name") String gameName, @Param("initial_date") Date initialDate,
            @Param("final_date") Date finalDate);

    @Query("select l from Loan l where (:client_name is null or l.clientName like '%'||:client_name||'%')and ((:initial_date is null or :initial_date between l.initialDate and l.finalDate) or (:final_date is null or :final_date between l.initialDate and l.finalDate))")
    List<Loan> findByClientName(@Param("client_name") String clientName, @Param("initial_date") Date initialDate,
            @Param("final_date") Date finalDate);

}
