package weblogic.xml.saaj.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public final class IOUtils {
   public static void closeQuietly(Reader input) {
      if (input != null) {
         try {
            input.close();
         } catch (IOException var2) {
         }

      }
   }

   public static void closeQuietly(Writer output) {
      if (output != null) {
         try {
            output.close();
         } catch (IOException var2) {
         }

      }
   }

   public static void closeQuietly(OutputStream output) {
      if (output != null) {
         try {
            output.close();
         } catch (IOException var2) {
         }

      }
   }

   public static void closeQuietly(InputStream input) {
      if (input != null) {
         try {
            input.close();
         } catch (IOException var2) {
         }

      }
   }

   public static String toString(InputStream input) throws IOException {
      StringWriter sw = new StringWriter();
      CopyUtils.copy((InputStream)input, (Writer)sw);
      return sw.toString();
   }

   public static String toString(InputStream input, String encoding) throws IOException {
      StringWriter sw = new StringWriter();
      CopyUtils.copy((InputStream)input, sw, encoding);
      return sw.toString();
   }

   public static byte[] toByteArray(InputStream input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      CopyUtils.copy((InputStream)input, (OutputStream)output);
      return output.toByteArray();
   }

   public static String toString(Reader input) throws IOException {
      StringWriter sw = new StringWriter();
      CopyUtils.copy((Reader)input, (Writer)sw);
      return sw.toString();
   }

   public static byte[] toByteArray(Reader input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      CopyUtils.copy((Reader)input, (OutputStream)output);
      return output.toByteArray();
   }

   public static byte[] toByteArray(String input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      CopyUtils.copy((String)input, (OutputStream)output);
      return output.toByteArray();
   }

   public static String toString(byte[] input) throws IOException {
      StringWriter sw = new StringWriter();
      CopyUtils.copy((byte[])input, (Writer)sw);
      return sw.toString();
   }

   public static String toString(byte[] input, String encoding) throws IOException {
      StringWriter sw = new StringWriter();
      CopyUtils.copy((byte[])input, sw, encoding);
      return sw.toString();
   }

   public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
      InputStream bufferedInput1 = new BufferedInputStream(input1);
      InputStream bufferedInput2 = new BufferedInputStream(input2);

      int ch2;
      for(int ch = bufferedInput1.read(); -1 != ch; ch = bufferedInput1.read()) {
         ch2 = bufferedInput2.read();
         if (ch != ch2) {
            return false;
         }
      }

      ch2 = bufferedInput2.read();
      if (-1 != ch2) {
         return false;
      } else {
         return true;
      }
   }

   public static InputStream dumpInputStream(InputStream inputStream) throws IOException {
      String inputStreamString = toString(inputStream);
      System.out.println("\nSOAPMessage: DUMPING INPUTSTREAM BELOW *****");
      System.out.println(inputStreamString);
      System.out.println("SOAPMessage: DUMPED INPUTSTREAM ABOVE *****");
      InputStream inputStream = new ByteArrayInputStream(inputStreamString.getBytes());
      return inputStream;
   }

   public static InputStream dumpInputStream(String marker, InputStream inputStream) throws IOException {
      System.out.println("\n" + marker);
      return dumpInputStream(inputStream);
   }
}
