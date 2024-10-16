
package Modelo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {

    private static final String SMTP_HOST = "gfgffsseffsdsefs"; //colocar correo valido
    private static final String SMTP_PORT = "465";
    private static final String SMTP_USER = "gfdgfdfdfdgdft"; // Deben colocar su correo.
    private static final String SMTP_PASSWORD = "gfhgfhdffghgfh";  // Reemplaza con la contraseña real

    public static boolean enviarCorreoConCodigo(String toEmail, String codigoVerificacion) {

        // Configuración de propiedades para la conexión SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", SMTP_PORT);

        // Autenticación con el servidor SMTP
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
                    }
                });

        try {
            // Crear el mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Tu código de verificación");
            message.setText("Tu código de verificación es: " + codigoVerificacion);

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Correo enviado correctamente");

    } catch (MessagingException e) {
        // Manejar el error aquí
        System.err.println("Error al enviar correo: " + e.getMessage());
        return false; // Indicar que el correo no se envió
    }
        
        return true;
    }
}


