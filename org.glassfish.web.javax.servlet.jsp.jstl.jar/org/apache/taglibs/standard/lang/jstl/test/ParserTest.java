package org.apache.taglibs.standard.lang.jstl.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.jstl.Evaluator;

public class ParserTest {
   public static void runTests(DataInput pIn, PrintStream pOut) throws IOException {
      while(true) {
         String str = pIn.readLine();
         if (str == null) {
            return;
         }

         if (!str.startsWith("#") && !"".equals(str.trim())) {
            if ("@@non-ascii".equals(str)) {
               str = "ᄑ";
            }

            pOut.println("Attribute value: " + str);

            try {
               String result = Evaluator.parseAndRender(str);
               pOut.println("Parses to: " + result);
            } catch (JspException var4) {
               pOut.println("Causes an error: " + var4.getMessage());
            }
         } else {
            pOut.println(str);
         }
      }
   }

   public static void runTests(File pInputFile, File pOutputFile) throws IOException {
      FileInputStream fin = null;
      FileOutputStream fout = null;

      try {
         fin = new FileInputStream(pInputFile);
         BufferedInputStream bin = new BufferedInputStream(fin);
         DataInputStream din = new DataInputStream(bin);

         try {
            fout = new FileOutputStream(pOutputFile);
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            PrintStream pout = new PrintStream(bout);
            runTests((DataInput)din, (PrintStream)pout);
            pout.flush();
         } finally {
            if (fout != null) {
               fout.close();
            }

         }
      } finally {
         if (fin != null) {
            fin.close();
         }

      }

   }

   public static boolean isDifferentFiles(DataInput pIn1, DataInput pIn2) throws IOException {
      while(true) {
         String str1 = pIn1.readLine();
         String str2 = pIn2.readLine();
         if (str1 == null && str2 == null) {
            return false;
         }

         if (str1 != null && str2 != null) {
            if (str1.equals(str2)) {
               continue;
            }

            return true;
         }

         return true;
      }
   }

   public static boolean isDifferentFiles(File pFile1, File pFile2) throws IOException {
      FileInputStream fin1 = null;

      boolean var8;
      try {
         fin1 = new FileInputStream(pFile1);
         BufferedInputStream bin1 = new BufferedInputStream(fin1);
         DataInputStream din1 = new DataInputStream(bin1);
         FileInputStream fin2 = null;

         try {
            fin2 = new FileInputStream(pFile2);
            BufferedInputStream bin2 = new BufferedInputStream(fin2);
            DataInputStream din2 = new DataInputStream(bin2);
            var8 = isDifferentFiles((DataInput)din1, (DataInput)din2);
         } finally {
            if (fin2 != null) {
               fin2.close();
            }

         }
      } finally {
         if (fin1 != null) {
            fin1.close();
         }

      }

      return var8;
   }

   public static void main(String[] pArgs) throws IOException {
      if (pArgs.length != 2 && pArgs.length != 3) {
         usage();
         System.exit(1);
      }

      File in = new File(pArgs[0]);
      File out = new File(pArgs[1]);
      runTests(in, out);
      if (pArgs.length > 2) {
         File compare = new File(pArgs[2]);
         if (isDifferentFiles(out, compare)) {
            System.out.println("Test failure - output file " + out + " differs from expected output file " + compare);
         } else {
            System.out.println("tests passed");
         }
      }

   }

   static void usage() {
      System.err.println("usage: java org.apache.taglibs.standard.lang.jstl.test.ParserTest {input file} {output file} [{compare file}]");
   }
}
