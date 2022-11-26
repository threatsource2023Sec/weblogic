package com.asn1c.codec;

import com.asn1c.core.LicenseViolatedError;
import java.net.InetAddress;
import java.net.UnknownHostException;

final class License {
   static byte[] data;
   private static final String datastring = "@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@";
   private static final String ident1 = "$ASNProduct: ASN.1 Runtime Library V1.3d $        ";
   private static final String ident2 = "$ASNCopyright: Copyright (C) Boris Nikolaus, Germany, 1996-2001. All rights reserved. $";
   private static final String ident3 = "$ASNSerial: 200108008 $";
   private static final String ident4 = "$ASNIPAddress: not checked $                                               ";
   private static final String ident5 = "$ASNDate: 20 Nov 2001 $";
   private static final String ident6 = "$ASNExpire: never $      ";
   private static final String ident7 = "$ASNLicensee: Octet String, Inc $                                               ";
   private static final String ident8 = "$ASNLicense: This library may be distributed by Octet String, Inc. If you can read this message in a product that has not been developed by Octet String, Inc, please email to bn@asn1c.com. $                                                                                                                                                                                                                 ";
   private static final char[] exp = new char[]{'æ', 'Ã', 'É', 'Ï', 'Ä', 'Ù', 'Ï', '\u008a', 'Ï', 'Ò', 'Ú', 'Ã', 'Ø', 'Ï', 'Î', '\u008a', 'Ë', 'Þ', '\u008a'};
   private static final char[] vio = new char[]{'æ', 'Ã', 'É', 'Ï', 'Ä', 'Ù', 'Ï', '\u008a', 'Ü', 'Ã', 'Å', 'Æ', 'Ë', 'Þ', 'Ï', 'Î'};
   private static final char[] cpy = new char[]{'ë', 'ù', 'ä', '\u0084', '\u009b', '\u008a', 'é', 'Å', 'Ç', 'Ú', 'Ã', 'Æ', 'Ï', 'Ø', ' ', 'é', 'Å', 'Ú', 'Ó', 'Ø', 'Ã', 'Í', 'Â', 'Þ', '\u008a', '\u0082', 'é', '\u0083', '\u008a', 'è', 'Å', 'Ø', 'Ã', 'Ù', '\u008a', 'ä', 'Ã', 'Á', 'Å', 'Æ', 'Ë', 'ß', 'Ù', '\u0086', '\u008a', 'í', 'Ï', 'Ø', 'Ç', 'Ë', 'Ä', 'Ó', '\u0086', '\u008a', '\u009b', '\u0093', '\u0093', '\u009c', '\u0087', '\u0098', '\u009a', '\u009a', '\u009b', '\u0084', '\u008a', 'ë', 'Æ', 'Æ', '\u008a', 'Ø', 'Ã', 'Í', 'Â', 'Þ', 'Ù', '\u008a', 'Ø', 'Ï', 'Ù', 'Ï', 'Ø', 'Ü', 'Ï', 'Î', '\u0084', ' '};
   private static boolean check = false;

   public static void checkLicense() throws LicenseViolatedError {
      if (!check) {
         data = new byte[136];

         int var0;
         for(var0 = 0; var0 < 34; ++var0) {
            data[4 * var0] = (byte)(("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0) & 63) << 2 | ("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 1) & 48) >> 4);
            data[4 * var0 + 1] = (byte)(("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 1) & 15) << 4 | ("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 2) & 60) >> 2);
            data[4 * var0 + 2] = (byte)(("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 2) & 3) << 6 | "@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 3) & 63);
            data[4 * var0 + 3] = (byte)(("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 4) & 63) << 2 | ("@aIHA@@PL@A@B~ugz@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SKb`P@@@@@@Fc\\cLpIW@@I`KsTqNP]W`RM@IdQGQ`PTACP`STqOS`RTaKR`]WQw]`\\WAs\\`_Wq\u007f_`^Wa{^`YVQgY`XVAcX`[Vqo[`ZVakZ`@@@@@p@@@@@@@@@@@@@@@@@@@@@@@@cI_P|`K[jrm@".charAt(6 * var0 + 5) & 48) >> 4);
         }

         int var2 = (data[36] & 255) << 24 | (data[37] & 255) << 16 | (data[38] & 255) << 8 | data[39] & 255;
         int var1 = (data[40] & 255) << 24 | (data[41] & 255) << 16 | (data[42] & 255) << 8 | data[43] & 255;
         if (var1 != 0) {
            long var5 = System.currentTimeMillis() / 86400000L;
            int var7 = (int)(var5 / 1461L * 4L + 1970L);
            var5 %= 1461L;

            while(true) {
               if (var5 <= (var7 % 4 == 0 ? 366L : 365L)) {
                  int var8 = 1;
                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= (var7 % 4 == 0 ? 29L : 28L)) {
                     ++var8;
                     var5 -= var7 % 4 == 0 ? 29L : 28L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= 30L) {
                     ++var8;
                     var5 -= 30L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= 30L) {
                     ++var8;
                     var5 -= 30L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= 30L) {
                     ++var8;
                     var5 -= 30L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  if (var5 >= 30L) {
                     ++var8;
                     var5 -= 30L;
                  }

                  if (var5 >= 31L) {
                     ++var8;
                     var5 -= 31L;
                  }

                  int var9 = (int)var5 + 1;
                  if (var7 > var1 % 10000 || var7 == var1 % 10000 && (var8 > var1 / 10000 % 100 || var8 == var1 / 10000 % 100 && var9 > var1 / 1000000) || var7 < var2 % 10000 || var7 == var2 % 10000 && (var8 < var2 / 10000 % 100 || var8 == var2 / 10000 % 100 && var9 < var2 / 1000000)) {
                     StringBuffer var10 = new StringBuffer();
                     var10.append("$ASNProduct: ASN.1 Runtime Library V1.3d $        ".substring(13, "$ASNProduct: ASN.1 Runtime Library V1.3d $        ".length() - 2));
                     var10.append('\n');
                     var10.append("$ASNCopyright: Copyright (C) Boris Nikolaus, Germany, 1996-2001. All rights reserved. $".substring(15, "$ASNCopyright: Copyright (C) Boris Nikolaus, Germany, 1996-2001. All rights reserved. $".length() - 2));
                     var10.append('\n');

                     for(var0 = 0; var0 < exp.length; ++var0) {
                        var10.append((char)(exp[var0] ^ 170));
                     }

                     var10.append("$ASNExpire: never $      ".substring(12, "$ASNExpire: never $      ".length() - 2));
                     var10.append('.');
                     String var11 = var10.toString();
                     System.err.println(var11);
                     System.err.flush();

                     try {
                        Thread.sleep(1000L);
                     } catch (InterruptedException var13) {
                     }

                     throw new LicenseViolatedError(var11);
                  }
                  break;
               }

               var5 -= var7 % 4 == 0 ? 366L : 365L;
               ++var7;
            }
         }

         if (data[12] != 0 || data[13] != 0 || data[13] != 0 || data[15] != 0) {
            try {
               byte[] var17 = InetAddress.getLocalHost().getAddress();
               int var6 = (var17[0] & 255) << 24 | (var17[1] & 255) << 16 | (var17[2] & 255) << 8 | var17[3] & 255;

               for(var0 = 0; var0 < 3; ++var0) {
                  int var3 = (data[12 + 4 * var0] & 255) << 24 | (data[13 + 4 * var0] & 255) << 16 | (data[14 + 4 * var0] & 255) << 8 | data[15 + 4 * var0] & 255;
                  int var4 = (data[24 + 4 * var0] & 255) << 24 | (data[25 + 4 * var0] & 255) << 16 | (data[26 + 4 * var0] & 255) << 8 | data[27 + 4 * var0] & 255;
                  if (var3 != 0 && (var6 & var4) == (var3 & var4)) {
                     break;
                  }
               }
            } catch (UnknownHostException var15) {
               var0 = 3;
            }

            if (var0 == 3) {
               StringBuffer var18 = new StringBuffer();
               var18.append("$ASNProduct: ASN.1 Runtime Library V1.3d $        ".substring(13, "$ASNProduct: ASN.1 Runtime Library V1.3d $        ".length() - 2));
               var18.append('\n');
               var18.append("$ASNCopyright: Copyright (C) Boris Nikolaus, Germany, 1996-2001. All rights reserved. $".substring(15, "$ASNCopyright: Copyright (C) Boris Nikolaus, Germany, 1996-2001. All rights reserved. $".length() - 2));
               var18.append('\n');

               for(var0 = 0; var0 < vio.length; ++var0) {
                  var18.append((char)(vio[var0] ^ 170));
               }

               var18.append('.');
               String var16 = var18.toString();
               System.err.println(var16);
               System.err.flush();

               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var14) {
               }

               throw new LicenseViolatedError(var16);
            }
         }

         check = true;
      }
   }
}
