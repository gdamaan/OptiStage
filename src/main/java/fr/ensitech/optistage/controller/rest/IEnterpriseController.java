package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.EnterpriseDto;

import javax.ws.rs.core.Response;

public interface IEnterpriseController {

    // Fondamentaux
    Response createEnterprise(EnterpriseDto enterpriseDto) throws Exception;
    Response getEnterpriseById(Long id) throws Exception;
    Response getAllEnterprises() throws Exception;
    Response updateEnterprise(EnterpriseDto enterpriseDto) throws Exception;
    Response deleteEnterprise(Long id) throws Exception;

    // Filtres
    Response getEnterprisesBySector(String sector) throws Exception;
    Response getEnterpriseByManagerId(Long managerId) throws Exception;
}