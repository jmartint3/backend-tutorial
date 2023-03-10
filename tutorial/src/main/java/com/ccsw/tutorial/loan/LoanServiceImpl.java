package com.ccsw.tutorial.loan;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, String gameName, String clientName, Date loanDate) {
        return this.loanRepository.findAll(dto.getPageable(), gameName, clientName, loanDate);
    }

    @Override
    public void save(LoanDto data) throws Exception {

        if (saveParametersComprobation(data)) {
            Loan loan = new Loan();
            BeanUtils.copyProperties(data, loan, "id");

            this.loanRepository.save(loan);
        }
    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);

    }

    private boolean saveParametersComprobation(LoanDto data) throws Exception {
        double diff = Math.abs(data.getFinalDate().getTime() - data.getInitialDate().getTime());
        double diffDays = Math.ceil(diff / (1000 * 3600 * 24));

        if (data.getFinalDate().compareTo(data.getInitialDate()) < 0 || diffDays > 14) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "La fecha final debe ser posterior a la inicial y como máximo pueden tener 14 días de diferencia");
        }

        if (this.loanRepository.findByGameName(data.getGameName(), data.getInitialDate(), data.getFinalDate())
                .size() == 0) {
            if (this.loanRepository.findByClientName(data.getClientName(), data.getInitialDate(), data.getFinalDate())
                    .size() < 2) {
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                        "El cliente no puede tener más de 2 juegos en un mismo día");

            }
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "El mismo juego no puede estar prestado a dos clientes distintos en un mismo día.");
        }
    }

}
