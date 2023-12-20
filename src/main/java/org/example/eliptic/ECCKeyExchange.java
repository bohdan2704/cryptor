package org.example.eliptic;

import org.example.helpful.PrimeGenerator;

import java.math.BigInteger;

public class ECCKeyExchange {
    static EllipticCurve curve;

    public static void main(String[] args) {
        int securityBits = 128;

        // NIST BASE VALUES FOR G POINT
        BigInteger xG = new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286");
        BigInteger yG = new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109");

        // Define elliptic curve parameters (a, b, p)
        BigInteger a = BigInteger.valueOf(-3);
        BigInteger b = PrimeGenerator.generatePrimeNumber(securityBits);
        BigInteger p = PrimeGenerator.generatePrimeNumber(securityBits);

        curve = new EllipticCurve(a, b, p);

        // Generate a base point on the curve (P)
        Point P = new Point(xG, yG);

        // Bob computes nP (where n is a random secret)
        BigInteger n = PrimeGenerator.generatePrimeNumber(securityBits);
        Point nP = curve.multiply(P, n);

        // Bob sends curve parameters, P, and nP to Alice

        // Alice generates her own random secret (m)
        BigInteger m = PrimeGenerator.generatePrimeNumber(securityBits);

        // Alice computes mP
        Point mP = curve.multiply(P, m);

        // Alice sends mP to Bob

        // Both parties compute the shared key
        Point sharedKeyBob = curve.multiply(nP, m);
        Point sharedKeyAlice = curve.multiply(mP, n);

        // Verify that both shared keys are equal (they should be)
        if (sharedKeyBob.getX().equals(sharedKeyAlice.getX()) && sharedKeyBob.getY().equals(sharedKeyAlice.getY())) {
            System.out.println("Shared Key Alice: " + sharedKeyAlice.getX() + ", " + sharedKeyAlice.getY());
            System.out.println("Shared Key Bob: " + sharedKeyBob.getX() + ", " + sharedKeyBob.getY());

        } else {
            System.out.println("Error: Shared keys do not match.");
        }
    }
}
