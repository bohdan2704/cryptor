package org.example.app;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface Execute {
    void executeRSA(PrintWriter out, BufferedReader in);
    void executeECDH(PrintWriter out, BufferedReader in);
    void executeAES(PrintWriter out, BufferedReader in);
}
