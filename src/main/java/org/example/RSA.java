package org.example;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    public RSA(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = choosePublicExponent(phi);
        this.d = this.calculatePrivateKey();
    }

    private BigInteger calculatePrivateKey() {
        BigInteger dBigInteger = e.modInverse(phi);
        return dBigInteger;
    }

    public String encrypt(String message) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) c);
            BigInteger encrypted = m.modPow(e, n);
            ciphertextBuilder.append(encrypted).append(" ");
        }
        // Get normal string, not ASCII
        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        // Get ascii representation of the text
//        ciphertext = toAsciiString(ciphertext);
        StringBuilder plaintextBuilder = new StringBuilder();
        String[] encryptedChars = ciphertext.split(" ");
        for (String encryptedChar : encryptedChars) {
            BigInteger encrypted = new BigInteger(encryptedChar);
            BigInteger decrypted = encrypted.modPow(d, n);
            char plaintextChar = (char) decrypted.intValue();
            plaintextBuilder.append(plaintextChar);
        }
        return plaintextBuilder.toString();
    }

    private static BigInteger choosePublicExponent(BigInteger phiN) {
        // Start with a candidate value for e (commonly used value: 65537)
        BigInteger e = BigInteger.valueOf(65537);

        // Check if e and phi(n) are coprime
        while (!e.gcd(phiN).equals(BigInteger.ONE)) {
            // Choose a different value for e if not coprime
            System.out.println("Searching for coprime. Default value is bad");
            e = BigInteger.valueOf(phiN.intValue()).add(BigInteger.ONE);
        }

        return e;
    }

    private static String toAsciiString(String message) {
        StringBuilder sb = new StringBuilder();
        for (char c : message.toCharArray()) {
            int asciiCode = (int) c;
            sb.append(asciiCode);
        }
        return sb.toString();
    }

    public static String unconcatenate(String input) {
        StringBuilder result = new StringBuilder();

        // Iterate over pairs of digits
        for (int i = 0; i < input.length(); i += 2) {
            // Get the current two-digit substring
            String digits = input.substring(i, Math.min(i + 2, input.length()));

            // Convert the two-digit substring to a character
            char letter = (char) Integer.parseInt(digits);

            // Append the character to the result
            result.append(letter);
        }

        // Convert the result to uppercase
        return result.toString().toUpperCase();
    }

    public String showKeys() {
        return e.toString() + System.lineSeparator() + d.toString() + System.lineSeparator() + n.toString();
    }
}