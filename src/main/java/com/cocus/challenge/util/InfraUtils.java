package com.cocus.challenge.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class InfraUtils {

    public static String getStackTrace(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
