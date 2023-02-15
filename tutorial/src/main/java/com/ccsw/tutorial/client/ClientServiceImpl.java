package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    private Boolean isNameRepeated = false;

    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    @Override
    public void save(Long id, ClientDto dto) {

        Client client = null;

        if (id == null) {
            client = new Client();
        } else {
            client = this.clientRepository.findById(id).orElse(null);
        }

        client.setName(dto.getName());

        String clientName = client.getName();

        isNameRepeated = false;
        this.findAll().forEach(arrayClient -> {
            if (arrayClient.getName().equals(clientName)) {
                System.out.println("ArrayClientName: " + arrayClient.getName() + ", clientName: " + clientName);
                this.isNameRepeated = true;
            }
        });

        if (this.isNameRepeated) {
            return;
        }

        this.clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }
}
