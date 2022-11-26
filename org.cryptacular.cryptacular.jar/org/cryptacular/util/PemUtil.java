package org.cryptacular.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.regex.Pattern;
import org.cryptacular.codec.Base64Decoder;

public final class PemUtil {
   public static final int LINE_LENGTH = 64;
   public static final String HEADER_BEGIN = "-----BEGIN";
   public static final String FOOTER_END = "-----END";
   public static final String PROC_TYPE = "Proc-Type:";
   public static final String DEK_INFO = "DEK-Info:";
   private static final Pattern PEM_SPLITTER = Pattern.compile("-----(?:BEGIN|END) [A-Z ]+-----");
   private static final Pattern LINE_SPLITTER = Pattern.compile("[\r\n]+");

   private PemUtil() {
   }

   public static boolean isPem(byte[] data) {
      String start = (new String(data, 0, 10, ByteUtil.ASCII_CHARSET)).trim();
      if (!start.startsWith("-----BEGIN") && !start.startsWith("Proc-Type:")) {
         for(int i = 0; i < 64; ++i) {
            if (!isBase64Char(data[i])) {
               if (i <= 61) {
                  return false;
               }

               if (data[i] != 61) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public static boolean isBase64Char(byte b) {
      return b >= 47 && b <= 122 && (b <= 57 || b >= 65) && (b <= 90 || b >= 97) || b == 43;
   }

   public static byte[] decode(byte[] pem) {
      return decode(new String(pem, ByteUtil.ASCII_CHARSET));
   }

   public static byte[] decode(String pem) {
      Base64Decoder decoder = new Base64Decoder();
      CharBuffer buffer = CharBuffer.allocate(pem.length());
      ByteBuffer output = ByteBuffer.allocate(pem.length() * 3 / 4);
      String[] var4 = PEM_SPLITTER.split(pem);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String object = var4[var6];
         buffer.clear();
         String[] var8 = LINE_SPLITTER.split(object);
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String line = var8[var10];
            if (!line.startsWith("DEK-Info:") && !line.startsWith("Proc-Type:")) {
               buffer.append(line);
            }
         }

         buffer.flip();
         decoder.decode(buffer, output);
         decoder.finalize(output);
      }

      output.flip();
      return ByteUtil.toArray(output);
   }
}
