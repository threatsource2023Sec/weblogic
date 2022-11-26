package org.apache.xml.security.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecurityPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JavaUtils {
   private static final Logger LOG = LoggerFactory.getLogger(JavaUtils.class);
   private static final SecurityPermission REGISTER_PERMISSION = new SecurityPermission("org.apache.xml.security.register");

   private JavaUtils() {
   }

   public static byte[] getBytesFromFile(String fileName) throws FileNotFoundException, IOException {
      byte[] refBytes = null;
      InputStream inputStream = Files.newInputStream(Paths.get(fileName));
      Throwable var3 = null;

      try {
         UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
         Throwable var5 = null;

         try {
            byte[] buf = new byte[1024];

            int len;
            while((len = inputStream.read(buf)) > 0) {
               baos.write(buf, 0, len);
            }

            refBytes = baos.toByteArray();
            return refBytes;
         } catch (Throwable var18) {
            var5 = var18;
            throw var18;
         } finally {
            $closeResource(var5, baos);
         }
      } catch (Throwable var20) {
         var3 = var20;
         throw var20;
      } finally {
         if (inputStream != null) {
            $closeResource(var3, inputStream);
         }

      }
   }

   public static void writeBytesToFilename(String filename, byte[] bytes) {
      if (filename != null && bytes != null) {
         try {
            OutputStream outputStream = Files.newOutputStream(Paths.get(filename));
            Throwable var3 = null;

            try {
               outputStream.write(bytes);
            } catch (Throwable var9) {
               var3 = var9;
               throw var9;
            } finally {
               if (outputStream != null) {
                  $closeResource(var3, outputStream);
               }

            }
         } catch (IOException var11) {
            LOG.debug(var11.getMessage(), var11);
         }
      } else {
         LOG.debug("writeBytesToFilename got null byte[] pointed");
      }

   }

   public static byte[] getBytesFromStream(InputStream inputStream) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      Throwable var2 = null;

      try {
         byte[] buf = new byte[4096];

         int len;
         while((len = inputStream.read(buf)) > 0) {
            baos.write(buf, 0, len);
         }

         byte[] var5 = baos.toByteArray();
         return var5;
      } catch (Throwable var9) {
         var2 = var9;
         throw var9;
      } finally {
         $closeResource(var2, baos);
      }
   }

   public static byte[] convertDsaASN1toXMLDSIG(byte[] asn1Bytes, int size) throws IOException {
      if (asn1Bytes[0] == 48 && asn1Bytes[1] == asn1Bytes.length - 2 && asn1Bytes[2] == 2) {
         byte rLength = asn1Bytes[3];

         int i;
         for(i = rLength; i > 0 && asn1Bytes[4 + rLength - i] == 0; --i) {
         }

         byte sLength = asn1Bytes[5 + rLength];

         int j;
         for(j = sLength; j > 0 && asn1Bytes[6 + rLength + sLength - j] == 0; --j) {
         }

         if (i <= size && asn1Bytes[4 + rLength] == 2 && j <= size) {
            byte[] xmldsigBytes = new byte[size * 2];
            System.arraycopy(asn1Bytes, 4 + rLength - i, xmldsigBytes, size - i, i);
            System.arraycopy(asn1Bytes, 6 + rLength + sLength - j, xmldsigBytes, size * 2 - j, j);
            return xmldsigBytes;
         } else {
            throw new IOException("Invalid ASN.1 format of DSA signature");
         }
      } else {
         throw new IOException("Invalid ASN.1 format of DSA signature");
      }
   }

   public static byte[] convertDsaXMLDSIGtoASN1(byte[] xmldsigBytes, int size) throws IOException {
      int totalSize = size * 2;
      if (xmldsigBytes.length != totalSize) {
         throw new IOException("Invalid XMLDSIG format of DSA signature");
      } else {
         int i;
         for(i = size; i > 0 && xmldsigBytes[size - i] == 0; --i) {
         }

         int j = i;
         if (xmldsigBytes[size - i] < 0) {
            j = i + 1;
         }

         int k;
         for(k = size; k > 0 && xmldsigBytes[totalSize - k] == 0; --k) {
         }

         int l = k;
         if (xmldsigBytes[totalSize - k] < 0) {
            l = k + 1;
         }

         byte[] asn1Bytes = new byte[6 + j + l];
         asn1Bytes[0] = 48;
         asn1Bytes[1] = (byte)(4 + j + l);
         asn1Bytes[2] = 2;
         asn1Bytes[3] = (byte)j;
         System.arraycopy(xmldsigBytes, size - i, asn1Bytes, 4 + j - i, i);
         asn1Bytes[4 + j] = 2;
         asn1Bytes[5 + j] = (byte)l;
         System.arraycopy(xmldsigBytes, totalSize - k, asn1Bytes, 6 + j + l - k, k);
         return asn1Bytes;
      }
   }

   public static void checkRegisterPermission() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(REGISTER_PERMISSION);
      }

   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
