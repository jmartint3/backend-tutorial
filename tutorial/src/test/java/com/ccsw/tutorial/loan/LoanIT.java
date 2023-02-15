package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan/";

    public static final Long DELETE_LOAN_ID = 6L;
    public static final Long MODIFY_LOAN_ID = 3L;
    public static final String NEW_LOAN_CLIENT_NAME = "Isaac";
    public static final String NEW_LOAN_GAME_NAME = "Azul";
    public static final Date NEW_LOAN_INITIAL_DATE = new Date(2021, 12, 03);
    public static final Date NEW_LOAN_FINAL_DATE = new Date(2021, 12, 10);

    public static final String CLIENT_NAME_WITH_2_LOANS = "Eric";
    public static final String GAME_NAME_LOANED = "On Mars";
    public static final Date INITIAL_DATE_EXISTING_LOAN = new Date(2023, 01, 17);
    public static final Date FINAL_DATE_EXISTING_LOAN = new Date(2023, 01, 25);
    public static final String ERROR_SAME_GAME_MORE_THAN_ONE_CLIENT = "El mismo juego no puede estar prestado a dos clientes distintos en un mismo día.";
    public static final String ERROR_SAME_CLIENT_MORE_THAN_TWO_GAMES = "El cliente no puede tener más de 2 juegos en un mismo día";

    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<Page<LoanDto>> responseTypePage = new ParameterizedTypeReference<Page<LoanDto>>() {
    };

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(1, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void saveWithourIdShouldCreateNewLoan() {
        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();
        dto.setClientName(NEW_LOAN_CLIENT_NAME);
        dto.setGameName(NEW_LOAN_GAME_NAME);
        dto.setInitialDate(NEW_LOAN_INITIAL_DATE);
        dto.setFinalDate(NEW_LOAN_FINAL_DATE);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), responseTypePage);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, (int) newLoanSize));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId))
                .findFirst().orElse(null);
        assertNotNull(loan);
        assertEquals(NEW_LOAN_CLIENT_NAME, loan.getClientName());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteCategory() {
        long newLoanSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + DELETE_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, TOTAL_LOANS));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {
        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + deleteLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void findByGameNameShouldReturn() {
        List<Loan> list = new ArrayList<>();
        list.add(mock(Loan.class));

        when(loanRepository.findByGameName("On Mars", new Date(2021, 02, 01), new Date(2021, 02, 10))).thenReturn(list);
    }

    @Test
    public void findByClientNameShouldReturn() {
        List<Loan> list = new ArrayList<>();
        list.add(mock(Loan.class));

        when(loanRepository.findByGameName("Eric", new Date(2021, 02, 01), new Date(2021, 02, 10))).thenReturn(list);
    }

    @Test
    public void saveSameGameByMoreThanOneClientShouldThrowError() {
        LoanDto dto = new LoanDto();
        dto.setClientName(CLIENT_NAME_WITH_2_LOANS);
        dto.setGameName(GAME_NAME_LOANED);
        dto.setInitialDate(INITIAL_DATE_EXISTING_LOAN);
        dto.setFinalDate(FINAL_DATE_EXISTING_LOAN);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, TOTAL_LOANS));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        // assertEquals(EXISTS_GAME_ID, response.getBody().get(0).getId());
    }

    @Test
    public void saveMoreThanTwoGamesBySameClientShouldThrowError() {

    }
}
