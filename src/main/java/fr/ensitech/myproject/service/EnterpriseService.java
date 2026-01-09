package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Enterprise;
import fr.ensitech.myproject.repository.EnterpriseRepository;

import java.util.List;


public class EnterpriseService implements IEnterpriseService{

    final EnterpriseRepository enterpriseRepository = new EnterpriseRepository();

    @Override
    public void createEnterprise(Enterprise enterprise) throws Exception {
        Enterprise existingEnterprise = enterpriseRepository.getEnterpriseBySiret(enterprise.getSiret());
        if (existingEnterprise != null) {
            throw new Exception("Enterprise with the same SIRET already exists : " + enterprise.getSiret() +
                    " (name: " + existingEnterprise.getName() + ")");
        }
        if (enterpriseRepository.getEnterpriseByUserId(enterprise.getUser().getId()) != null) {
            throw new Exception("Ce recruteur est déjà lié à une entreprise.");
        }
        this.enterpriseRepository.addEnterprise(enterprise);

    }

    @Override
    public Enterprise getEnterpriseById(Long id) throws Exception {
        return this.enterpriseRepository.getEnterpriseById(id);
    }

    @Override
    public Enterprise getEnterpriseByUserId(Long userId) throws Exception {
        return this.enterpriseRepository.getEnterpriseByUserId(userId);
    }

    @Override
    public void updateEnterprise(Enterprise enterprise) throws Exception {
        this.enterpriseRepository.updateEnterprise(enterprise);
    }

    @Override
    public void deleteEnterprise(Long id) throws Exception {
        Enterprise enterprise = enterpriseRepository.getEnterpriseById(id);
        if (enterprise != null) {
            this.enterpriseRepository.deleteEnterprise(enterprise);
        }
        else  throw new Exception("Enterprise with id " + id + " does not exist");
    }

    @Override
    public List<Enterprise> getAllEnterprises() throws Exception {
        return this.enterpriseRepository.getAllEnterprises();
    }
}
