package weblogic.servlet.utils.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class Streams {
   private static final int DEFAULT_BUFFER_SIZE = 8192;

   private Streams() {
   }

   public static long copy(InputStream pInputStream, OutputStream pOutputStream, boolean pClose) throws IOException {
      return copy(pInputStream, pOutputStream, pClose, new byte[8192]);
   }

   public static long copy(InputStream pIn, OutputStream pOut, boolean pClose, byte[] pBuffer) throws IOException {
      OutputStream out = pOut;
      InputStream in = pIn;

      try {
         long total = 0L;

         while(true) {
            int res = in.read(pBuffer);
            if (res == -1) {
               if (out != null) {
                  if (pClose) {
                     out.close();
                  } else {
                     out.flush();
                  }

                  out = null;
               }

               in.close();
               in = null;
               long var21 = total;
               return var21;
            }

            if (res > 0) {
               total += (long)res;
               if (out != null) {
                  out.write(pBuffer, 0, res);
               }
            }
         }
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Throwable var19) {
            }
         }

         if (pClose && out != null) {
            try {
               out.close();
            } catch (Throwable var18) {
            }
         }

      }
   }

   public static String asString(InputStream pStream) throws IOException {
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      copy(pStream, baos, true);
      return baos.toString();
   }

   public static String asString(InputStream pStream, String pEncoding) throws IOException {
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      copy(pStream, baos, true);
      return baos.toString(pEncoding);
   }
}
