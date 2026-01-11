package fr.ensitech.myproject.utils;
import fr.ensitech.myproject.entity.*;
import fr.ensitech.myproject.entity.dto.*;

public abstract class Dto {

    public static UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setBirthdate(user.getBirthdate());
        userDto.setIsActive(user.getIsActive());
        return userDto;
    }

    public static InternshipOfferDto offerToDto(InternshipOffer offer) {
        if (offer == null) return null;

        InternshipOfferDto dto = new InternshipOfferDto();
        dto.setId(offer.getId());
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setStartDate(offer.getStartDate());
        dto.setEndDate(offer.getEndDate());
        dto.setSalary(offer.getRemuneration());
        dto.setVille(offer.getVille());

        // On extrait le nom de l'entreprise si elle existe
        if (offer.getEnterprise() != null) {
            dto.setEnterpriseName(offer.getEnterprise().getName());
        }

        return dto;
    }

    public static EnterpriseDto enterpriseToDto(Enterprise enterprise) {
        if (enterprise == null) return null;

        EnterpriseDto dto = new EnterpriseDto();
        dto.setId(enterprise.getId());
        dto.setName(enterprise.getName());
        dto.setSiret(enterprise.getSiret());
        dto.setSector(enterprise.getSector());
        dto.setDescription(enterprise.getDescription());
        dto.setWebsite(enterprise.getWebsite());

        if (enterprise.getUser() != null) {
            dto.setManagerId(enterprise.getUser().getId());
            // On concatène Prénom + Nom pour l'affichage
            dto.setManagerName(enterprise.getUser().getFirstname() + " " + enterprise.getUser().getLastname());
            dto.setManagerEmail(enterprise.getUser().getEmail());
        }
        return dto;
    }

    public static ApplicationDto applicationToDto(Application application) {
        if (application == null) return null;

        ApplicationDto dto = new ApplicationDto();
        dto.setId(application.getId());
        dto.setApplyDate(application.getApplyDate());
        dto.setStatus(application.getStatus());
        dto.setMotivationLetter(application.getMotivationLetter());

        if (application.getStudent() != null) {
            dto.setStudentId(application.getStudent().getId());
            dto.setStudentName(application.getStudent().getFirstname() + " " + application.getStudent().getLastname());
        }

        // Récupération des infos de l'offre et de l'entreprise
        if (application.getOffer() != null) {
            dto.setOfferId(application.getOffer().getId());
            dto.setOfferTitle(application.getOffer().getTitle());
            if (application.getOffer().getEnterprise() != null) {
                dto.setEnterpriseName(application.getOffer().getEnterprise().getName());
            }
        }

        return dto;
    }
}
