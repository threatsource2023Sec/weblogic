package com.bea.common.security.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import weblogic.utils.io.TerminalIO;

public class NonEchoCmdlineReader {
   public String readPassword(String hint) {
      String password = null;
      System.out.print(hint + ":");
      if (TerminalIO.isNoEchoAvailable()) {
         password = TerminalIO.readTerminalNoEcho();
         System.out.println();
      } else {
         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

         try {
            password = in.readLine();
            System.out.println();
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }
      }

      return password;
   }

   public String readAndConfirmPassword(String hint) {
      String pw = "";
      String pw2 = "";
      if (TerminalIO.isNoEchoAvailable()) {
         while(true) {
            System.out.print("enter " + hint + ": ");
            pw = TerminalIO.readTerminalNoEcho();
            System.out.println("");
            System.out.print("re-enter " + hint + ": ");
            pw2 = TerminalIO.readTerminalNoEcho();
            System.out.println("");
            if (pw == null) {
               if (pw2 == null) {
                  break;
               }
            } else if (pw.equals(pw2)) {
               break;
            }

            System.out.println("They don't match.\n");
         }
      } else {
         try {
            pw = this.readLine(hint);
         } catch (IOException var5) {
            System.out.println(var5.getMessage());
            pw = null;
         }
      }

      return pw;
   }

   public String readLine(String hint) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      System.out.print(hint + ": ");
      String retVal = in.readLine();
      return retVal == null ? "" : retVal.trim();
   }
}
