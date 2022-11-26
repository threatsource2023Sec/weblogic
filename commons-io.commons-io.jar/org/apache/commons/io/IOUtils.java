package org.apache.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.StringBuilderWriter;

public class IOUtils {
   public static final int EOF = -1;
   public static final char DIR_SEPARATOR_UNIX = '/';
   public static final char DIR_SEPARATOR_WINDOWS = '\\';
   public static final char DIR_SEPARATOR;
   public static final String LINE_SEPARATOR_UNIX = "\n";
   public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
   public static final String LINE_SEPARATOR;
   private static final int DEFAULT_BUFFER_SIZE = 4096;
   private static final int SKIP_BUFFER_SIZE = 2048;
   private static char[] SKIP_CHAR_BUFFER;
   private static byte[] SKIP_BYTE_BUFFER;

   public static void close(URLConnection conn) {
      if (conn instanceof HttpURLConnection) {
         ((HttpURLConnection)conn).disconnect();
      }

   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Reader input) {
      closeQuietly((Closeable)input);
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Writer output) {
      closeQuietly((Closeable)output);
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(InputStream input) {
      closeQuietly((Closeable)input);
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(OutputStream output) {
      closeQuietly((Closeable)output);
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Closeable closeable) {
      try {
         if (closeable != null) {
            closeable.close();
         }
      } catch (IOException var2) {
      }

   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Closeable... closeables) {
      if (closeables != null) {
         Closeable[] var1 = closeables;
         int var2 = closeables.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Closeable closeable = var1[var3];
            closeQuietly(closeable);
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Socket sock) {
      if (sock != null) {
         try {
            sock.close();
         } catch (IOException var2) {
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(Selector selector) {
      if (selector != null) {
         try {
            selector.close();
         } catch (IOException var2) {
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(ServerSocket sock) {
      if (sock != null) {
         try {
            sock.close();
         } catch (IOException var2) {
         }
      }

   }

   public static InputStream toBufferedInputStream(InputStream input) throws IOException {
      return ByteArrayOutputStream.toBufferedInputStream(input);
   }

   public static InputStream toBufferedInputStream(InputStream input, int size) throws IOException {
      return ByteArrayOutputStream.toBufferedInputStream(input, size);
   }

   public static BufferedReader toBufferedReader(Reader reader) {
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
   }

   public static BufferedReader toBufferedReader(Reader reader, int size) {
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, size);
   }

   public static BufferedReader buffer(Reader reader) {
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
   }

   public static BufferedReader buffer(Reader reader, int size) {
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, size);
   }

   public static BufferedWriter buffer(Writer writer) {
      return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
   }

   public static BufferedWriter buffer(Writer writer, int size) {
      return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer, size);
   }

   public static BufferedOutputStream buffer(OutputStream outputStream) {
      if (outputStream == null) {
         throw new NullPointerException();
      } else {
         return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream);
      }
   }

   public static BufferedOutputStream buffer(OutputStream outputStream, int size) {
      if (outputStream == null) {
         throw new NullPointerException();
      } else {
         return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream, size);
      }
   }

   public static BufferedInputStream buffer(InputStream inputStream) {
      if (inputStream == null) {
         throw new NullPointerException();
      } else {
         return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream);
      }
   }

   public static BufferedInputStream buffer(InputStream inputStream, int size) {
      if (inputStream == null) {
         throw new NullPointerException();
      } else {
         return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream, size);
      }
   }

   public static byte[] toByteArray(InputStream input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      Throwable var2 = null;

      byte[] var3;
      try {
         copy((InputStream)input, (OutputStream)output);
         var3 = output.toByteArray();
      } catch (Throwable var12) {
         var2 = var12;
         throw var12;
      } finally {
         if (output != null) {
            if (var2 != null) {
               try {
                  output.close();
               } catch (Throwable var11) {
                  var2.addSuppressed(var11);
               }
            } else {
               output.close();
            }
         }

      }

      return var3;
   }

   public static byte[] toByteArray(InputStream input, long size) throws IOException {
      if (size > 2147483647L) {
         throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
      } else {
         return toByteArray(input, (int)size);
      }
   }

   public static byte[] toByteArray(InputStream input, int size) throws IOException {
      if (size < 0) {
         throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
      } else if (size == 0) {
         return new byte[0];
      } else {
         byte[] data = new byte[size];

         int offset;
         int read;
         for(offset = 0; offset < size && (read = input.read(data, offset, size - offset)) != -1; offset += read) {
         }

         if (offset != size) {
            throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
         } else {
            return data;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static byte[] toByteArray(Reader input) throws IOException {
      return toByteArray(input, Charset.defaultCharset());
   }

   public static byte[] toByteArray(Reader input, Charset encoding) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      Throwable var3 = null;

      byte[] var4;
      try {
         copy((Reader)input, (OutputStream)output, (Charset)encoding);
         var4 = output.toByteArray();
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (output != null) {
            if (var3 != null) {
               try {
                  output.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               output.close();
            }
         }

      }

      return var4;
   }

   public static byte[] toByteArray(Reader input, String encoding) throws IOException {
      return toByteArray(input, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static byte[] toByteArray(String input) throws IOException {
      return input.getBytes(Charset.defaultCharset());
   }

   public static byte[] toByteArray(URI uri) throws IOException {
      return toByteArray(uri.toURL());
   }

   public static byte[] toByteArray(URL url) throws IOException {
      URLConnection conn = url.openConnection();

      byte[] var2;
      try {
         var2 = toByteArray(conn);
      } finally {
         close(conn);
      }

      return var2;
   }

   public static byte[] toByteArray(URLConnection urlConn) throws IOException {
      InputStream inputStream = urlConn.getInputStream();
      Throwable var2 = null;

      byte[] var3;
      try {
         var3 = toByteArray(inputStream);
      } catch (Throwable var12) {
         var2 = var12;
         throw var12;
      } finally {
         if (inputStream != null) {
            if (var2 != null) {
               try {
                  inputStream.close();
               } catch (Throwable var11) {
                  var2.addSuppressed(var11);
               }
            } else {
               inputStream.close();
            }
         }

      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   public static char[] toCharArray(InputStream is) throws IOException {
      return toCharArray(is, Charset.defaultCharset());
   }

   public static char[] toCharArray(InputStream is, Charset encoding) throws IOException {
      CharArrayWriter output = new CharArrayWriter();
      copy((InputStream)is, (Writer)output, (Charset)encoding);
      return output.toCharArray();
   }

   public static char[] toCharArray(InputStream is, String encoding) throws IOException {
      return toCharArray(is, Charsets.toCharset(encoding));
   }

   public static char[] toCharArray(Reader input) throws IOException {
      CharArrayWriter sw = new CharArrayWriter();
      copy((Reader)input, (Writer)sw);
      return sw.toCharArray();
   }

   /** @deprecated */
   @Deprecated
   public static String toString(InputStream input) throws IOException {
      return toString(input, Charset.defaultCharset());
   }

   public static String toString(InputStream input, Charset encoding) throws IOException {
      StringBuilderWriter sw = new StringBuilderWriter();
      Throwable var3 = null;

      String var4;
      try {
         copy((InputStream)input, (Writer)sw, (Charset)encoding);
         var4 = sw.toString();
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (sw != null) {
            if (var3 != null) {
               try {
                  sw.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               sw.close();
            }
         }

      }

      return var4;
   }

   public static String toString(InputStream input, String encoding) throws IOException {
      return toString(input, Charsets.toCharset(encoding));
   }

   public static String toString(Reader input) throws IOException {
      StringBuilderWriter sw = new StringBuilderWriter();
      Throwable var2 = null;

      String var3;
      try {
         copy((Reader)input, (Writer)sw);
         var3 = sw.toString();
      } catch (Throwable var12) {
         var2 = var12;
         throw var12;
      } finally {
         if (sw != null) {
            if (var2 != null) {
               try {
                  sw.close();
               } catch (Throwable var11) {
                  var2.addSuppressed(var11);
               }
            } else {
               sw.close();
            }
         }

      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   public static String toString(URI uri) throws IOException {
      return toString(uri, Charset.defaultCharset());
   }

   public static String toString(URI uri, Charset encoding) throws IOException {
      return toString(uri.toURL(), Charsets.toCharset(encoding));
   }

   public static String toString(URI uri, String encoding) throws IOException {
      return toString(uri, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static String toString(URL url) throws IOException {
      return toString(url, Charset.defaultCharset());
   }

   public static String toString(URL url, Charset encoding) throws IOException {
      InputStream inputStream = url.openStream();
      Throwable var3 = null;

      String var4;
      try {
         var4 = toString(inputStream, encoding);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (inputStream != null) {
            if (var3 != null) {
               try {
                  inputStream.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               inputStream.close();
            }
         }

      }

      return var4;
   }

   public static String toString(URL url, String encoding) throws IOException {
      return toString(url, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static String toString(byte[] input) throws IOException {
      return new String(input, Charset.defaultCharset());
   }

   public static String toString(byte[] input, String encoding) throws IOException {
      return new String(input, Charsets.toCharset(encoding));
   }

   public static String resourceToString(String name, Charset encoding) throws IOException {
      return resourceToString(name, encoding, (ClassLoader)null);
   }

   public static String resourceToString(String name, Charset encoding, ClassLoader classLoader) throws IOException {
      return toString(resourceToURL(name, classLoader), encoding);
   }

   public static byte[] resourceToByteArray(String name) throws IOException {
      return resourceToByteArray(name, (ClassLoader)null);
   }

   public static byte[] resourceToByteArray(String name, ClassLoader classLoader) throws IOException {
      return toByteArray(resourceToURL(name, classLoader));
   }

   public static URL resourceToURL(String name) throws IOException {
      return resourceToURL(name, (ClassLoader)null);
   }

   public static URL resourceToURL(String name, ClassLoader classLoader) throws IOException {
      URL resource = classLoader == null ? IOUtils.class.getResource(name) : classLoader.getResource(name);
      if (resource == null) {
         throw new IOException("Resource not found: " + name);
      } else {
         return resource;
      }
   }

   /** @deprecated */
   @Deprecated
   public static List readLines(InputStream input) throws IOException {
      return readLines(input, Charset.defaultCharset());
   }

   public static List readLines(InputStream input, Charset encoding) throws IOException {
      InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(encoding));
      return readLines((Reader)reader);
   }

   public static List readLines(InputStream input, String encoding) throws IOException {
      return readLines(input, Charsets.toCharset(encoding));
   }

   public static List readLines(Reader input) throws IOException {
      BufferedReader reader = toBufferedReader(input);
      List list = new ArrayList();

      for(String line = reader.readLine(); line != null; line = reader.readLine()) {
         list.add(line);
      }

      return list;
   }

   public static LineIterator lineIterator(Reader reader) {
      return new LineIterator(reader);
   }

   public static LineIterator lineIterator(InputStream input, Charset encoding) throws IOException {
      return new LineIterator(new InputStreamReader(input, Charsets.toCharset(encoding)));
   }

   public static LineIterator lineIterator(InputStream input, String encoding) throws IOException {
      return lineIterator(input, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static InputStream toInputStream(CharSequence input) {
      return toInputStream(input, Charset.defaultCharset());
   }

   public static InputStream toInputStream(CharSequence input, Charset encoding) {
      return toInputStream(input.toString(), encoding);
   }

   public static InputStream toInputStream(CharSequence input, String encoding) throws IOException {
      return toInputStream(input, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static InputStream toInputStream(String input) {
      return toInputStream(input, Charset.defaultCharset());
   }

   public static InputStream toInputStream(String input, Charset encoding) {
      return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(encoding)));
   }

   public static InputStream toInputStream(String input, String encoding) throws IOException {
      byte[] bytes = input.getBytes(Charsets.toCharset(encoding));
      return new ByteArrayInputStream(bytes);
   }

   public static void write(byte[] data, OutputStream output) throws IOException {
      if (data != null) {
         output.write(data);
      }

   }

   public static void writeChunked(byte[] data, OutputStream output) throws IOException {
      if (data != null) {
         int bytes = data.length;

         int chunk;
         for(int offset = 0; bytes > 0; offset += chunk) {
            chunk = Math.min(bytes, 4096);
            output.write(data, offset, chunk);
            bytes -= chunk;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void write(byte[] data, Writer output) throws IOException {
      write(data, output, Charset.defaultCharset());
   }

   public static void write(byte[] data, Writer output, Charset encoding) throws IOException {
      if (data != null) {
         output.write(new String(data, Charsets.toCharset(encoding)));
      }

   }

   public static void write(byte[] data, Writer output, String encoding) throws IOException {
      write(data, output, Charsets.toCharset(encoding));
   }

   public static void write(char[] data, Writer output) throws IOException {
      if (data != null) {
         output.write(data);
      }

   }

   public static void writeChunked(char[] data, Writer output) throws IOException {
      if (data != null) {
         int bytes = data.length;

         int chunk;
         for(int offset = 0; bytes > 0; offset += chunk) {
            chunk = Math.min(bytes, 4096);
            output.write(data, offset, chunk);
            bytes -= chunk;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void write(char[] data, OutputStream output) throws IOException {
      write(data, output, Charset.defaultCharset());
   }

   public static void write(char[] data, OutputStream output, Charset encoding) throws IOException {
      if (data != null) {
         output.write((new String(data)).getBytes(Charsets.toCharset(encoding)));
      }

   }

   public static void write(char[] data, OutputStream output, String encoding) throws IOException {
      write(data, output, Charsets.toCharset(encoding));
   }

   public static void write(CharSequence data, Writer output) throws IOException {
      if (data != null) {
         write(data.toString(), output);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void write(CharSequence data, OutputStream output) throws IOException {
      write(data, output, Charset.defaultCharset());
   }

   public static void write(CharSequence data, OutputStream output, Charset encoding) throws IOException {
      if (data != null) {
         write(data.toString(), output, encoding);
      }

   }

   public static void write(CharSequence data, OutputStream output, String encoding) throws IOException {
      write(data, output, Charsets.toCharset(encoding));
   }

   public static void write(String data, Writer output) throws IOException {
      if (data != null) {
         output.write(data);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void write(String data, OutputStream output) throws IOException {
      write(data, output, Charset.defaultCharset());
   }

   public static void write(String data, OutputStream output, Charset encoding) throws IOException {
      if (data != null) {
         output.write(data.getBytes(Charsets.toCharset(encoding)));
      }

   }

   public static void write(String data, OutputStream output, String encoding) throws IOException {
      write(data, output, Charsets.toCharset(encoding));
   }

   /** @deprecated */
   @Deprecated
   public static void write(StringBuffer data, Writer output) throws IOException {
      if (data != null) {
         output.write(data.toString());
      }

   }

   /** @deprecated */
   @Deprecated
   public static void write(StringBuffer data, OutputStream output) throws IOException {
      write(data, output, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public static void write(StringBuffer data, OutputStream output, String encoding) throws IOException {
      if (data != null) {
         output.write(data.toString().getBytes(Charsets.toCharset(encoding)));
      }

   }

   /** @deprecated */
   @Deprecated
   public static void writeLines(Collection lines, String lineEnding, OutputStream output) throws IOException {
      writeLines(lines, lineEnding, output, Charset.defaultCharset());
   }

   public static void writeLines(Collection lines, String lineEnding, OutputStream output, Charset encoding) throws IOException {
      if (lines != null) {
         if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
         }

         Charset cs = Charsets.toCharset(encoding);

         for(Iterator var5 = lines.iterator(); var5.hasNext(); output.write(lineEnding.getBytes(cs))) {
            Object line = var5.next();
            if (line != null) {
               output.write(line.toString().getBytes(cs));
            }
         }

      }
   }

   public static void writeLines(Collection lines, String lineEnding, OutputStream output, String encoding) throws IOException {
      writeLines(lines, lineEnding, output, Charsets.toCharset(encoding));
   }

   public static void writeLines(Collection lines, String lineEnding, Writer writer) throws IOException {
      if (lines != null) {
         if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
         }

         for(Iterator var3 = lines.iterator(); var3.hasNext(); writer.write(lineEnding)) {
            Object line = var3.next();
            if (line != null) {
               writer.write(line.toString());
            }
         }

      }
   }

   public static int copy(InputStream input, OutputStream output) throws IOException {
      long count = copyLarge(input, output);
      return count > 2147483647L ? -1 : (int)count;
   }

   public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
      return copyLarge(input, output, new byte[bufferSize]);
   }

   public static long copyLarge(InputStream input, OutputStream output) throws IOException {
      return copy(input, output, 4096);
   }

   public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
      long count;
      int n;
      for(count = 0L; -1 != (n = input.read(buffer)); count += (long)n) {
         output.write(buffer, 0, n);
      }

      return count;
   }

   public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
      return copyLarge(input, output, inputOffset, length, new byte[4096]);
   }

   public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
      if (inputOffset > 0L) {
         skipFully(input, inputOffset);
      }

      if (length == 0L) {
         return 0L;
      } else {
         int bufferLength = buffer.length;
         int bytesToRead = bufferLength;
         if (length > 0L && length < (long)bufferLength) {
            bytesToRead = (int)length;
         }

         long totalRead = 0L;

         int read;
         while(bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length > 0L) {
               bytesToRead = (int)Math.min(length - totalRead, (long)bufferLength);
            }
         }

         return totalRead;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void copy(InputStream input, Writer output) throws IOException {
      copy(input, output, Charset.defaultCharset());
   }

   public static void copy(InputStream input, Writer output, Charset inputEncoding) throws IOException {
      InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(inputEncoding));
      copy((Reader)in, (Writer)output);
   }

   public static void copy(InputStream input, Writer output, String inputEncoding) throws IOException {
      copy(input, output, Charsets.toCharset(inputEncoding));
   }

   public static int copy(Reader input, Writer output) throws IOException {
      long count = copyLarge(input, output);
      return count > 2147483647L ? -1 : (int)count;
   }

   public static long copyLarge(Reader input, Writer output) throws IOException {
      return copyLarge(input, output, new char[4096]);
   }

   public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
      long count;
      int n;
      for(count = 0L; -1 != (n = input.read(buffer)); count += (long)n) {
         output.write(buffer, 0, n);
      }

      return count;
   }

   public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
      return copyLarge(input, output, inputOffset, length, new char[4096]);
   }

   public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
      if (inputOffset > 0L) {
         skipFully(input, inputOffset);
      }

      if (length == 0L) {
         return 0L;
      } else {
         int bytesToRead = buffer.length;
         if (length > 0L && length < (long)buffer.length) {
            bytesToRead = (int)length;
         }

         long totalRead = 0L;

         int read;
         while(bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length > 0L) {
               bytesToRead = (int)Math.min(length - totalRead, (long)buffer.length);
            }
         }

         return totalRead;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void copy(Reader input, OutputStream output) throws IOException {
      copy(input, output, Charset.defaultCharset());
   }

   public static void copy(Reader input, OutputStream output, Charset outputEncoding) throws IOException {
      OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(outputEncoding));
      copy((Reader)input, (Writer)out);
      out.flush();
   }

   public static void copy(Reader input, OutputStream output, String outputEncoding) throws IOException {
      copy(input, output, Charsets.toCharset(outputEncoding));
   }

   public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
      if (input1 == input2) {
         return true;
      } else {
         if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream((InputStream)input1);
         }

         if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream((InputStream)input2);
         }

         int ch2;
         for(int ch = ((InputStream)input1).read(); -1 != ch; ch = ((InputStream)input1).read()) {
            ch2 = ((InputStream)input2).read();
            if (ch != ch2) {
               return false;
            }
         }

         ch2 = ((InputStream)input2).read();
         return ch2 == -1;
      }
   }

   public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
      if (input1 == input2) {
         return true;
      } else {
         Reader input1 = toBufferedReader(input1);
         Reader input2 = toBufferedReader(input2);

         int ch2;
         for(int ch = input1.read(); -1 != ch; ch = input1.read()) {
            ch2 = input2.read();
            if (ch != ch2) {
               return false;
            }
         }

         ch2 = input2.read();
         return ch2 == -1;
      }
   }

   public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException {
      if (input1 == input2) {
         return true;
      } else {
         BufferedReader br1 = toBufferedReader(input1);
         BufferedReader br2 = toBufferedReader(input2);
         String line1 = br1.readLine();

         String line2;
         for(line2 = br2.readLine(); line1 != null && line2 != null && line1.equals(line2); line2 = br2.readLine()) {
            line1 = br1.readLine();
         }

         return line1 == null ? line2 == null : line1.equals(line2);
      }
   }

   public static long skip(InputStream input, long toSkip) throws IOException {
      if (toSkip < 0L) {
         throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
      } else {
         if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[2048];
         }

         long remain;
         long n;
         for(remain = toSkip; remain > 0L; remain -= n) {
            n = (long)input.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(remain, 2048L));
            if (n < 0L) {
               break;
            }
         }

         return toSkip - remain;
      }
   }

   public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
      if (toSkip < 0L) {
         throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
      } else {
         ByteBuffer skipByteBuffer = ByteBuffer.allocate((int)Math.min(toSkip, 2048L));

         long remain;
         int n;
         for(remain = toSkip; remain > 0L; remain -= (long)n) {
            skipByteBuffer.position(0);
            skipByteBuffer.limit((int)Math.min(remain, 2048L));
            n = input.read(skipByteBuffer);
            if (n == -1) {
               break;
            }
         }

         return toSkip - remain;
      }
   }

   public static long skip(Reader input, long toSkip) throws IOException {
      if (toSkip < 0L) {
         throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
      } else {
         if (SKIP_CHAR_BUFFER == null) {
            SKIP_CHAR_BUFFER = new char[2048];
         }

         long remain;
         long n;
         for(remain = toSkip; remain > 0L; remain -= n) {
            n = (long)input.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(remain, 2048L));
            if (n < 0L) {
               break;
            }
         }

         return toSkip - remain;
      }
   }

   public static void skipFully(InputStream input, long toSkip) throws IOException {
      if (toSkip < 0L) {
         throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
      } else {
         long skipped = skip(input, toSkip);
         if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
         }
      }
   }

   public static void skipFully(ReadableByteChannel input, long toSkip) throws IOException {
      if (toSkip < 0L) {
         throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
      } else {
         long skipped = skip(input, toSkip);
         if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
         }
      }
   }

   public static void skipFully(Reader input, long toSkip) throws IOException {
      long skipped = skip(input, toSkip);
      if (skipped != toSkip) {
         throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
      }
   }

   public static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
      if (length < 0) {
         throw new IllegalArgumentException("Length must not be negative: " + length);
      } else {
         int remaining;
         int count;
         for(remaining = length; remaining > 0; remaining -= count) {
            int location = length - remaining;
            count = input.read(buffer, offset + location, remaining);
            if (-1 == count) {
               break;
            }
         }

         return length - remaining;
      }
   }

   public static int read(Reader input, char[] buffer) throws IOException {
      return read((Reader)input, (char[])buffer, 0, buffer.length);
   }

   public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
      if (length < 0) {
         throw new IllegalArgumentException("Length must not be negative: " + length);
      } else {
         int remaining;
         int count;
         for(remaining = length; remaining > 0; remaining -= count) {
            int location = length - remaining;
            count = input.read(buffer, offset + location, remaining);
            if (-1 == count) {
               break;
            }
         }

         return length - remaining;
      }
   }

   public static int read(InputStream input, byte[] buffer) throws IOException {
      return read((InputStream)input, (byte[])buffer, 0, buffer.length);
   }

   public static int read(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
      int length = buffer.remaining();

      while(buffer.remaining() > 0) {
         int count = input.read(buffer);
         if (-1 == count) {
            break;
         }
      }

      return length - buffer.remaining();
   }

   public static void readFully(Reader input, char[] buffer, int offset, int length) throws IOException {
      int actual = read(input, buffer, offset, length);
      if (actual != length) {
         throw new EOFException("Length to read: " + length + " actual: " + actual);
      }
   }

   public static void readFully(Reader input, char[] buffer) throws IOException {
      readFully((Reader)input, (char[])buffer, 0, buffer.length);
   }

   public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
      int actual = read(input, buffer, offset, length);
      if (actual != length) {
         throw new EOFException("Length to read: " + length + " actual: " + actual);
      }
   }

   public static void readFully(InputStream input, byte[] buffer) throws IOException {
      readFully((InputStream)input, (byte[])buffer, 0, buffer.length);
   }

   public static byte[] readFully(InputStream input, int length) throws IOException {
      byte[] buffer = new byte[length];
      readFully((InputStream)input, (byte[])buffer, 0, buffer.length);
      return buffer;
   }

   public static void readFully(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
      int expected = buffer.remaining();
      int actual = read(input, buffer);
      if (actual != expected) {
         throw new EOFException("Length to read: " + expected + " actual: " + actual);
      }
   }

   static {
      DIR_SEPARATOR = File.separatorChar;
      StringBuilderWriter buf = new StringBuilderWriter(4);
      Throwable var1 = null;

      try {
         PrintWriter out = new PrintWriter(buf);
         Throwable var3 = null;

         try {
            out.println();
            LINE_SEPARATOR = buf.toString();
         } catch (Throwable var26) {
            var3 = var26;
            throw var26;
         } finally {
            if (out != null) {
               if (var3 != null) {
                  try {
                     out.close();
                  } catch (Throwable var25) {
                     var3.addSuppressed(var25);
                  }
               } else {
                  out.close();
               }
            }

         }
      } catch (Throwable var28) {
         var1 = var28;
         throw var28;
      } finally {
         if (buf != null) {
            if (var1 != null) {
               try {
                  buf.close();
               } catch (Throwable var24) {
                  var1.addSuppressed(var24);
               }
            } else {
               buf.close();
            }
         }

      }

   }
}
