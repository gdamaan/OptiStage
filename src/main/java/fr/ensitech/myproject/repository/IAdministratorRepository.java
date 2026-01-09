package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Administrator;

public interface IAdministratorRepository {
    void addAdmin(Administrator admin) throws Exception;
    Administrator getAdminByLogin(String login) throws Exception;
    Administrator getAdminById(Long id) throws Exception;
    void updateAdmin(Administrator admin) throws Exception;
    void deleteAdmin(Administrator admin) throws Exception;
}