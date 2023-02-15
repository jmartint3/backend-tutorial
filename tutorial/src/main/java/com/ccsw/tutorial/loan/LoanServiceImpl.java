package com.ccsw.tutorial.loan;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public void save(LoanDto data) {

        try {
            if (comprobacionParametrosSave(data)) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n AÑADIENDO LOAN \n\n\n\n\n\n\n\n\n\n");
                System.out.println("GameName: " + data.getGameName());
                System.out.println("ClientName: " + data.getClientName());
                System.out.println("InitialDate: " + data.getInitialDate());
                System.out.println("FinalDate: " + data.getFinalDate());

                Loan loan = new Loan();
                BeanUtils.copyProperties(data, loan, "id");

                this.loanRepository.save(loan);
            }
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\nERROR: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);

    }

    private boolean comprobacionParametrosSave(LoanDto data) throws Exception {
        double diff = Math.abs(data.getFinalDate().getTime() - data.getInitialDate().getTime());
        double diffDays = Math.ceil(diff / (1000 * 3600 * 24));

        if (data.getFinalDate().compareTo(data.getInitialDate()) < 0 || diffDays > 14) {
            return false;
        }

        if (this.loanRepository.findByGameName(data.getGameName(), data.getInitialDate(), data.getFinalDate())
                .size() == 0) {
            if (this.loanRepository.findByClientName(data.getClientName(), data.getInitialDate(), data.getFinalDate())
                    .size() < 2) {
                return true;
            } else {
                throw new Exception("El cliente no puede tener más de 2 juegos en un mismo día");

            }
        } else {
            throw new Exception("El mismo juego no puede estar prestado a dos clientes distintos en un mismo día.");
        }
    }

}
