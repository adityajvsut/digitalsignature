package com.ut.digitalsignature.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AesService {

    public String encryptionKeyString = "digitalsignut.12";
    public byte[] encryptionKeyBytes = encryptionKeyString.getBytes();
    public SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
    public Cipher cipher;

    public String AesEncryption(String originalMessage) throws Exception{
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(originalMessage.getBytes()));
    }

    public String AesDecryption(String encryptedMessage) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)));
    }
}