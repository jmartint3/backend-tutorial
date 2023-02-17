package com.ccsw.tutorial.client;

import java.util.List;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

public interface ClientService {
    /**
     * Método para recuperar todos los Client
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar un Client
     */
    void save(Long id, ClientDto dto) throws Exception;

    /**
     * Método para borrar un Client
     */
    void delete(Long id);
}
