package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    public static final String CLIENT_NAME = "Jordi";
    public static final Long EXISTS_CLIENT_ID = 1L;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    public void findAllShouldReturnAllClients() {
        List<Client> list = new ArrayList<Client>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    public void saveNotExistClientIdShouldInsert() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        try {
            clientService.save(null, clientDto);
        } catch (Exception ex) {
        }

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    @Test
    public void saveExistsClientIdShouldUpdate() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        try {
            clientService.save(EXISTS_CLIENT_ID, clientDto);
        } catch (Exception e) {
        }

        verify(clientRepository).save(client);
    }

    @Test
    public void deleteExistsClientIdShouldDelete() {
        clientService.delete(EXISTS_CLIENT_ID);
        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }
}
