package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Enterprise;
import java.util.List;

public interface IEnterpriseService {
    void createEnterprise(Enterprise enterprise) throws Exception;
    Enterprise getEnterpriseById(Long id) throws Exception;
    Enterprise getEnterpriseByUserId(Long userId) throws Exception;
    void updateEnterprise(Enterprise enterprise) throws Exception;
    void deleteEnterprise(Long id) throws Exception;
    List<Enterprise> getAllEnterprises() throws Exception;
}