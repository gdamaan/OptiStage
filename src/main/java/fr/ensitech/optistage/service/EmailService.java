package fr.ensitech.optistage.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // Attention Monsieur : ne commitez jamais vos vrais mots de passe sur GitHub.
    // Pour l'instant on les met en dur pour tester, mais à terme, on utilisera des variables d'environnement.
    private static final String SMTP_USER = "optistage.noreply@gmail.com";
    // Si vous utilisez Gmail, il vous faudra un "Mot de passe d'application" (16 lettres), pas votre vrai mot de passe.
    private static final String SMTP_PASSWORD = "yfko pijz aerc uukl";

    public static void sendEmail(String recipientEmail, String subject, String content) throws Exception {

        // 1. Configuration des tuyaux SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Connexion sécurisée
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // 2. Authentification de l'application
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        // 3. Rédaction et envoi du courrier
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER, "OptiStage Auto-Mailer"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(content); // Vous pourrez remplacer ça par setContent(html, "text/html") plus tard si vous voulez faire du design.

        Transport.send(message);
        // Si on arrive ici, c'est que le mail est parti sans exploser en vol.
    }
}