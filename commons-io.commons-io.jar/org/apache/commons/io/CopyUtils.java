package org.apache.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

/** @deprecated */
@Deprecated
public class CopyUtils {
   private static final int DEFAULT_BUFFER_SIZE = 4096;

   public static void copy(byte[] input, OutputStream output) throws IOException {
      output.write(input);
   }

   /** @deprecated */
   @Deprecated
   public static void copy(byte[] input, Writer output) throws IOException {
      ByteArrayInputStream in = new ByteArrayInputStream(input);
      copy((InputStream)in, (Writer)output);
   }

   public static void copy(byte[] input, Writer output, String encoding) throws IOException {
      ByteArrayInputStream in = new ByteArrayInputStream(input);
      copy((InputStream)in, (Writer)output, encoding);
   }

   public static int copy(InputStream input, OutputStream output) throws IOException {
      byte[] buffer = new byte[4096];
      int count = 0;

      int n;
      for(int n = false; -1 != (n = input.read(buffer)); count += n) {
         output.write(buffer, 0, n);
      }

      return count;
   }

   public static int copy(Reader input, Writer output) throws IOException {
      char[] buffer = new char[4096];
      int count = 0;

      int n;
      for(int n = false; -1 != (n = input.read(buffer)); count += n) {
         output.write(buffer, 0, n);
      }

      return count;
   }

   /** @deprecated */
   @Deprecated
   public static void copy(InputStream input, Writer output) throws IOException {
      InputStreamReader in = new InputStreamReader(input, Charset.defaultCharset());
      copy((Reader)in, (Writer)output);
   }

   public static void copy(InputStream input, Writer output, String encoding) throws IOException {
      InputStreamReader in = new InputStreamReader(input, encoding);
      copy((Reader)in, (Writer)output);
   }

   /** @deprecated */
   @Deprecated
   public static void copy(Reader input, OutputStream output) throws IOException {
      OutputStreamWriter out = new OutputStreamWriter(output, Charset.defaultCharset());
      copy((Reader)input, (Writer)out);
      out.flush();
   }

   public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
      OutputStreamWriter out = new OutputStreamWriter(output, encoding);
      copy((Reader)input, (Writer)out);
      out.flush();
   }

   /** @deprecated */
   @Deprecated
   public static void copy(String input, OutputStream output) throws IOException {
      StringReader in = new StringReader(input);
      OutputStreamWriter out = new OutputStreamWriter(output, Charset.defaultCharset());
      copy((Reader)in, (Writer)out);
      out.flush();
   }

   public static void copy(String input, OutputStream output, String encoding) throws IOException {
      StringReader in = new StringReader(input);
      OutputStreamWriter out = new OutputStreamWriter(output, encoding);
      copy((Reader)in, (Writer)out);
      out.flush();
   }

   public static void copy(String input, Writer output) throws IOException {
      output.write(input);
   }
}
