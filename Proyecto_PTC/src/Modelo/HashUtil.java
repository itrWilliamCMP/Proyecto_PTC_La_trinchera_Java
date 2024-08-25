package Modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashUtil {

    public static String generateSHA512Hash(String clave)  {
        // Obtener una instancia del algoritmo SHA-512
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Aplicar el hash a la cadena de texto
        byte[] hashBytes = digest.digest(clave.getBytes());

        // Convertir el resultado a una representación hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        // Retornar el hash en formato hexadecimal
        return sb.toString();
    }
}
