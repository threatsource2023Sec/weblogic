package org.cryptacular.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.bouncycastle.util.io.Streams;
import org.cryptacular.StreamException;
import org.cryptacular.io.ChunkHandler;

public final class StreamUtil {
   public static final int CHUNK_SIZE = 1024;

   private StreamUtil() {
   }

   public static byte[] readAll(String path) throws StreamException {
      return readAll(new File(path));
   }

   public static byte[] readAll(File file) throws StreamException {
      InputStream input = makeStream(file);

      byte[] var2;
      try {
         var2 = readAll(input, (int)file.length());
      } finally {
         closeStream(input);
      }

      return var2;
   }

   public static byte[] readAll(InputStream input) throws StreamException {
      return readAll((InputStream)input, 1024);
   }

   public static byte[] readAll(InputStream input, int sizeHint) throws StreamException {
      ByteArrayOutputStream output = new ByteArrayOutputStream(sizeHint);

      try {
         Streams.pipeAll(input, output);
      } catch (IOException var7) {
         throw new StreamException(var7);
      } finally {
         closeStream(input);
         closeStream((OutputStream)output);
      }

      return output.toByteArray();
   }

   public static String readAll(Reader reader) throws StreamException {
      return readAll((Reader)reader, 1024);
   }

   public static String readAll(Reader reader, int sizeHint) throws StreamException {
      CharArrayWriter writer = new CharArrayWriter(sizeHint);
      char[] buffer = new char[1024];

      try {
         int len;
         try {
            while((len = reader.read(buffer)) > 0) {
               writer.write(buffer, 0, len);
            }
         } catch (IOException var9) {
            throw new StreamException(var9);
         }
      } finally {
         closeReader(reader);
         closeWriter(writer);
      }

      return writer.toString();
   }

   public static void pipeAll(InputStream in, OutputStream out, ChunkHandler handler) throws StreamException {
      byte[] buffer = new byte[1024];

      try {
         int count;
         while((count = in.read(buffer)) > 0) {
            handler.handle(buffer, 0, count, out);
         }

      } catch (IOException var6) {
         throw new StreamException(var6);
      }
   }

   public static InputStream makeStream(File file) throws StreamException {
      try {
         return new BufferedInputStream(new FileInputStream(file));
      } catch (FileNotFoundException var2) {
         throw new StreamException(file + " does not exist");
      }
   }

   public static Reader makeReader(File file) throws StreamException {
      try {
         return new InputStreamReader(new BufferedInputStream(new FileInputStream(file)));
      } catch (FileNotFoundException var2) {
         throw new StreamException(file + " does not exist");
      }
   }

   public static void closeStream(InputStream in) {
      try {
         in.close();
      } catch (IOException var2) {
         System.err.println("Error closing " + in + ": " + var2);
      }

   }

   public static void closeStream(OutputStream out) {
      try {
         out.close();
      } catch (IOException var2) {
         System.err.println("Error closing " + out + ": " + var2);
      }

   }

   public static void closeReader(Reader reader) {
      try {
         reader.close();
      } catch (IOException var2) {
         System.err.println("Error closing " + reader + ": " + var2);
      }

   }

   public static void closeWriter(Writer writer) {
      try {
         writer.close();
      } catch (IOException var2) {
         System.err.println("Error closing " + writer + ": " + var2);
      }

   }
}
