package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Enterprise;
import java.util.List;

public interface IEnterpriseRepository {
    void addEnterprise(Enterprise enterprise) throws Exception;
    Enterprise getEnterpriseById(Long id) throws Exception;
    Enterprise getEnterpriseByUserId(Long userId) throws Exception;
    void updateEnterprise(Enterprise enterprise) throws Exception;
    void deleteEnterprise(Enterprise enterprise) throws Exception;
    List<Enterprise> getAllEnterprises() throws Exception;
}