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

        if (this.clientRepository.findByName(dto.getName()) == null) {
            client.setName(dto.getName());
            this.clientRepository.save(client);
        }
    }

    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }
}
