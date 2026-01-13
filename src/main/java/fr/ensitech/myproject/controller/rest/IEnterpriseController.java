package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.dto.EnterpriseDto;

import javax.ws.rs.core.Response;
import java.util.List;

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