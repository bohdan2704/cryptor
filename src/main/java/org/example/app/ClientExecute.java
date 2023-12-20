package org.example.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.eliptic.ECCKeyExchange;
import org.example.sync.AES;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

import static org.example.app.Client.DESIRED_AES_KEY_LEN;

public class ClientExecute implements Execute {
    private static final Logger logger = LogManager.getLogger(ClientExecute.class);

    @Override
    public void executeRSA(PrintWriter out, ObjectOutputStream objectOutputStream, BufferedReader in, ObjectInputStream objectInputStream) {
        System.out.println();
    }

    @Override
    public String executeAESDecrypt(BigInteger key, String encryptedMsg) {
        key = key.shiftRight(key.bitLength() - DESIRED_AES_KEY_LEN + 1 );
        System.out.println(Arrays.toString(key.toByteArray()));
        System.out.println(key.toByteArray().length);
        AES aes = new AES(key.toByteArray());

//        System.out.println("Encrypted msg: " + encryptedMsg);
        byte[] decrypted = aes.ECB_decrypt(AES.hexStringToByteArray(encryptedMsg));
        String decryptedMsg = new String(AES.trimZeroesFromEnd(decrypted));
//        System.out.println("Decrypted msg: " + decryptedMsg);
        return decryptedMsg;
    }

    @Override
    public String executeAESEncrypt(BigInteger key, String msgToEncrypt) {
        key = key.shiftRight(key.bitLength() - DESIRED_AES_KEY_LEN + 1);
        System.out.println(Arrays.toString(key.toByteArray()));
        System.out.println(key.toByteArray().length);

        AES aes = new AES(key.toByteArray());
        byte[] ecbEncrypt = aes.ECB_encrypt(msgToEncrypt.getBytes());
        return AES.byteArrayToHexString(ecbEncrypt);
    }



    @Override
    public BigInteger executeECDH(PrintWriter out, ObjectOutputStream objectOutputStream, BufferedReader in, ObjectInputStream objectInputStream) {
        System.out.println("ClientExecuteECDH");
        ECCKeyExchange eccKeyExchange = new ECCKeyExchange();
        try {
            return eccKeyExchange.clientStart(out, objectOutputStream, in, objectInputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
