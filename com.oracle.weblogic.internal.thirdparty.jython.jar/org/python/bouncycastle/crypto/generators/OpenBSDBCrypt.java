package org.python.bouncycastle.crypto.generators;

import java.io.ByteArrayOutputStream;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Strings;

public class OpenBSDBCrypt {
   private static final byte[] encodingTable = new byte[]{46, 47, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
   private static final byte[] decodingTable = new byte[128];
   private static final String version = "2a";

   private static String createBcryptString(byte[] var0, byte[] var1, int var2) {
      StringBuffer var3 = new StringBuffer(60);
      var3.append('$');
      var3.append("2a");
      var3.append('$');
      var3.append(var2 < 10 ? "0" + var2 : Integer.toString(var2));
      var3.append('$');
      var3.append(encodeData(var1));
      byte[] var4 = BCrypt.generate(var0, var1, var2);
      var3.append(encodeData(var4));
      return var3.toString();
   }

   public static String generate(char[] var0, byte[] var1, int var2) {
      if (var0 == null) {
         throw new IllegalArgumentException("Password required.");
      } else if (var1 == null) {
         throw new IllegalArgumentException("Salt required.");
      } else if (var1.length != 16) {
         throw new DataLengthException("16 byte salt required: " + var1.length);
      } else if (var2 >= 4 && var2 <= 31) {
         byte[] var3 = Strings.toUTF8ByteArray(var0);
         byte[] var4 = new byte[var3.length >= 72 ? 72 : var3.length + 1];
         if (var4.length > var3.length) {
            System.arraycopy(var3, 0, var4, 0, var3.length);
         } else {
            System.arraycopy(var3, 0, var4, 0, var4.length);
         }

         Arrays.fill((byte[])var3, (byte)0);
         String var5 = createBcryptString(var4, var1, var2);
         Arrays.fill((byte[])var4, (byte)0);
         return var5;
      } else {
         throw new IllegalArgumentException("Invalid cost factor.");
      }
   }

   public static boolean checkPassword(String var0, char[] var1) {
      if (var0.length() != 60) {
         throw new DataLengthException("Bcrypt String length: " + var0.length() + ", 60 required.");
      } else if (var0.charAt(0) == '$' && var0.charAt(3) == '$' && var0.charAt(6) == '$') {
         if (!var0.substring(1, 3).equals("2a")) {
            throw new IllegalArgumentException("Wrong Bcrypt version, 2a expected.");
         } else {
            boolean var2 = false;

            int var6;
            try {
               var6 = Integer.parseInt(var0.substring(4, 6));
            } catch (NumberFormatException var5) {
               throw new IllegalArgumentException("Invalid cost factor: " + var0.substring(4, 6));
            }

            if (var6 >= 4 && var6 <= 31) {
               if (var1 == null) {
                  throw new IllegalArgumentException("Missing password.");
               } else {
                  byte[] var3 = decodeSaltString(var0.substring(var0.lastIndexOf(36) + 1, var0.length() - 31));
                  String var4 = generate(var1, var3, var6);
                  return var0.equals(var4);
               }
            } else {
               throw new IllegalArgumentException("Invalid cost factor: " + var6 + ", 4 < cost < 31 expected.");
            }
         }
      } else {
         throw new IllegalArgumentException("Invalid Bcrypt String format.");
      }
   }

   private static String encodeData(byte[] var0) {
      if (var0.length != 24 && var0.length != 16) {
         throw new DataLengthException("Invalid length: " + var0.length + ", 24 for key or 16 for salt expected");
      } else {
         boolean var1 = false;
         if (var0.length == 16) {
            var1 = true;
            byte[] var2 = new byte[18];
            System.arraycopy(var0, 0, var2, 0, var0.length);
            var0 = var2;
         } else {
            var0[var0.length - 1] = 0;
         }

         ByteArrayOutputStream var9 = new ByteArrayOutputStream();
         int var3 = var0.length;

         for(int var4 = 0; var4 < var3; var4 += 3) {
            int var5 = var0[var4] & 255;
            int var6 = var0[var4 + 1] & 255;
            int var7 = var0[var4 + 2] & 255;
            var9.write(encodingTable[var5 >>> 2 & 63]);
            var9.write(encodingTable[(var5 << 4 | var6 >>> 4) & 63]);
            var9.write(encodingTable[(var6 << 2 | var7 >>> 6) & 63]);
            var9.write(encodingTable[var7 & 63]);
         }

         String var8 = Strings.fromByteArray(var9.toByteArray());
         return var1 ? var8.substring(0, 22) : var8.substring(0, var8.length() - 1);
      }
   }

   private static byte[] decodeSaltString(String var0) {
      char[] var1 = var0.toCharArray();
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(16);
      if (var1.length != 22) {
         throw new DataLengthException("Invalid base64 salt length: " + var1.length + " , 22 required.");
      } else {
         int var4;
         for(int var3 = 0; var3 < var1.length; ++var3) {
            var4 = var1[var3];
            if (var4 > 122 || var4 < 46 || var4 > 57 && var4 < 65) {
               throw new IllegalArgumentException("Salt string contains invalid character: " + var4);
            }
         }

         char[] var11 = new char[24];
         System.arraycopy(var1, 0, var11, 0, var1.length);
         var1 = var11;
         var4 = var11.length;

         for(int var5 = 0; var5 < var4; var5 += 4) {
            byte var6 = decodingTable[var1[var5]];
            byte var7 = decodingTable[var1[var5 + 1]];
            byte var8 = decodingTable[var1[var5 + 2]];
            byte var9 = decodingTable[var1[var5 + 3]];
            var2.write(var6 << 2 | var7 >> 4);
            var2.write(var7 << 4 | var8 >> 2);
            var2.write(var8 << 6 | var9);
         }

         byte[] var12 = var2.toByteArray();
         byte[] var10 = new byte[16];
         System.arraycopy(var12, 0, var10, 0, var10.length);
         return var10;
      }
   }

   static {
      int var0;
      for(var0 = 0; var0 < decodingTable.length; ++var0) {
         decodingTable[var0] = -1;
      }

      for(var0 = 0; var0 < encodingTable.length; ++var0) {
         decodingTable[encodingTable[var0]] = (byte)var0;
      }

   }
}
