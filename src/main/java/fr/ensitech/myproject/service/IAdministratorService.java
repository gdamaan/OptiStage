package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Administrator;

public interface IAdministratorService {
    void createAdmin(Administrator admin) throws Exception;
    Administrator login(String login, String password) throws Exception;
    Administrator getAdminById(Long id) throws Exception;
    void updateAdmin(Administrator admin) throws Exception;
    void deleteAdmin(Long id) throws Exception;
}