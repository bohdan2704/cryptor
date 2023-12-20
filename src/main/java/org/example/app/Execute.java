package org.example.app;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;

public interface Execute {
    void executeRSA(PrintWriter out, ObjectOutputStream objectOutputStream, BufferedReader in, ObjectInputStream objectInputStream);
    BigInteger executeECDH(PrintWriter out, ObjectOutputStream objectOutputStream, BufferedReader in, ObjectInputStream objectInputStream);
    String executeAESEncrypt(BigInteger key, String toEncrypt);
    String executeAESDecrypt(BigInteger key, String toEncrypt);
}
