package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Administrator;
import fr.ensitech.myproject.repository.IAdministratorRepository;
import fr.ensitech.myproject.repository.AdministratorRepository;
import fr.ensitech.myproject.utils.PasswordHasher;

public class AdministratorService implements IAdministratorService {

    private final IAdministratorRepository adminRepository;

    public AdministratorService() {
        this.adminRepository = new AdministratorRepository();
    }

    @Override
    public void createAdmin(Administrator admin) throws Exception {
        if (adminRepository.getAdminByLogin(admin.getLogin()) != null) {
            throw new Exception("Ce login est déjà utilisé par un autre administrateur.");
        }
        // Hachage du mot de passe avant sauvegarde
        admin.setPassword(PasswordHasher.hashPassword(admin.getPassword()));
        this.adminRepository.addAdmin(admin);
    }

    @Override
    public Administrator login(String login, String password) throws Exception {
        Administrator admin = adminRepository.getAdminByLogin(login);
        if (admin != null && PasswordHasher.verifyPassword(password, admin.getPassword())) {
            return admin;
        }
        throw new Exception("Identifiants administrateur incorrects.");
    }

    @Override
    public Administrator getAdminById(Long id) throws Exception {
        return this.adminRepository.getAdminById(id);
    }

    @Override
    public void updateAdmin(Administrator admin) throws Exception {
        this.adminRepository.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(Long id) throws Exception {
        Administrator admin = this.adminRepository.getAdminById(id);
        if (admin != null) {
            this.adminRepository.deleteAdmin(admin);
        }
    }
}