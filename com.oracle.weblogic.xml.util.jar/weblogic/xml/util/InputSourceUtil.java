package weblogic.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.xml.sax.InputSource;

public class InputSourceUtil {
   public static byte[] getInputByteData(InputSource ins) throws IOException {
      InputStream bytesIn = null;
      byte[] retval = null;
      bytesIn = ins.getByteStream();
      if (bytesIn == null) {
         return null;
      } else {
         ByteArrayOutputStream bo = new ByteArrayOutputStream();
         byte[] bbuf = new byte[1024];
         int bytesRead = false;

         byte[] retval;
         try {
            int bytesRead;
            while((bytesRead = bytesIn.read(bbuf)) != -1) {
               bo.write(bbuf, 0, bytesRead);
            }

            retval = bo.toByteArray();
         } finally {
            if (bytesIn != null) {
               bytesIn.close();
            }

            if (bo != null) {
               bo.close();
            }

         }

         return retval;
      }
   }

   public static char[] getInputCharData(InputSource ins) throws IOException {
      Reader charsIn = null;
      char[] retval = null;
      charsIn = ins.getCharacterStream();
      if (charsIn == null) {
         return null;
      } else {
         CharArrayWriter cw = new CharArrayWriter();
         char[] cbuf = new char[1024];
         int charsRead = false;

         char[] retval;
         try {
            int charsRead;
            while((charsRead = charsIn.read(cbuf)) != -1) {
               cw.write(cbuf, 0, charsRead);
            }

            retval = cw.toCharArray();
         } finally {
            if (charsIn != null) {
               charsIn.close();
            }

            if (cw != null) {
               cw.close();
            }

         }

         return retval;
      }
   }

   public static byte[] forceGetInputByteData(InputSource ins) throws IOException, UnsupportedEncodingException {
      byte[] byteData = getInputByteData(ins);
      if (byteData == null) {
         char[] charData = getInputCharData(ins);
         if (charData != null) {
            byteData = (new String(charData)).getBytes(ins.getEncoding());
         }
      }

      return byteData;
   }

   public static boolean isEqual(InputSource s1, InputSource s2) throws IOException, IllegalArgumentException {
      boolean var4;
      try {
         byte[] data1 = s1 != null ? forceGetInputByteData(s1) : null;
         byte[] data2 = s2 != null ? forceGetInputByteData(s2) : null;
         var4 = Arrays.equals(data1, data2);
      } finally {
         resetInputSource(s1);
         resetInputSource(s2);
      }

      return var4;
   }

   public static void resetInputSource(InputSource is) throws IOException, IllegalArgumentException {
      if (is != null) {
         InputStream byteStream = is.getByteStream();
         Reader reader = is.getCharacterStream();
         if (byteStream != null) {
            if (!(byteStream instanceof ByteArrayInputStream)) {
               throw new IllegalArgumentException("no byte array input stream in input source: " + byteStream.getClass().getName());
            }

            byteStream.reset();
         } else if (reader != null) {
            if (!(reader instanceof CharArrayReader)) {
               throw new IllegalArgumentException("no char array reader stream in input source: " + reader.getClass().getName());
            }

            reader.reset();
         }

      }
   }

   public static void transformInputSource(InputSource is) throws IOException {
      if (is != null) {
         InputStream byteStream = is.getByteStream();
         Reader reader = is.getCharacterStream();
         if (byteStream != null) {
            if (!(byteStream instanceof ByteArrayInputStream)) {
               byte[] data = getInputByteData(is);
               InputStream byteStream = new ByteArrayInputStream(data);
               is.setByteStream(byteStream);
            }
         } else if (reader != null && !(reader instanceof CharArrayReader2)) {
            char[] data = getInputCharData(is);
            Reader reader = new CharArrayReader2(data);
            is.setCharacterStream(reader);
         }

      }
   }

   private static class CharArrayReader2 extends CharArrayReader {
      public CharArrayReader2(char[] buf) {
         super(buf);
      }

      public CharArrayReader2(char[] buf, int offset, int length) {
         super(buf, offset, length);
      }

      public void close() {
      }

      public void forceClose() {
         super.close();
      }
   }
}
