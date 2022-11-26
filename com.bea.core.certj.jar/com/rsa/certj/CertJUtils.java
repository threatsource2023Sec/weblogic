package com.rsa.certj;

import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.IssuerAltName;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.jsafe.cert.ObjectID;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public final class CertJUtils {
   private CertJUtils() {
   }

   /** @deprecated */
   public static void mergeLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Object var3 = var1.elementAt(var2);
            if (!var0.contains(var3)) {
               var0.addElement(var3);
            }
         }

      }
   }

   /** @deprecated */
   public static void subtractLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            if (var0.contains(var3)) {
               var0.removeElement(var3);
            }
         }

      }
   }

   /** @deprecated */
   public static void intersectLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.size();

         while(var2 > 0) {
            --var2;
            if (!var1.contains(var0.elementAt(var2))) {
               var0.removeElementAt(var2);
            }
         }

      }
   }

   /** @deprecated */
   public static void uniteLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Object var3 = var1.elementAt(var2);
            if (!var0.contains(var3)) {
               var0.addElement(var3);
            }
         }

      }
   }

   /** @deprecated */
   public static boolean byteArraysEqual(byte[] var0, byte[] var1) {
      return Arrays.equals(var0, var1);
   }

   /** @deprecated */
   public static boolean byteArraysEqual(byte[] var0, int var1, int var2, byte[] var3) {
      return byteArraysEqual(var0, var1, var2, var3, 0, var3.length);
   }

   /** @deprecated */
   public static boolean byteArraysEqual(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
      if (var0 == null && var3 == null) {
         return true;
      } else if (var0 != null && var3 != null) {
         if (var2 != var5) {
            return false;
         } else {
            for(int var6 = 0; var6 < var2; ++var6) {
               if (var0[var1 + var6] != var3[var4 + var6]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean containsByteArray(Vector var0, byte[] var1) {
      Iterator var2 = var0.iterator();

      byte[] var3;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         var3 = (byte[])var2.next();
      } while(!byteArraysEqual(var3, var1));

      return true;
   }

   /** @deprecated */
   public static void intersectByteArrayLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.size();

         while(var2 > 0) {
            --var2;
            if (!containsByteArray(var1, (byte[])((byte[])var0.elementAt(var2)))) {
               var0.removeElementAt(var2);
            }
         }

      }
   }

   /** @deprecated */
   public static void uniteByteArrayLists(Vector var0, Vector var1) {
      if (var0 != null && var1 != null) {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            byte[] var3 = (byte[])((byte[])var1.elementAt(var2));
            if (!containsByteArray(var0, var3)) {
               var0.addElement(var3);
            }
         }

      }
   }

   /** @deprecated */
   public static boolean sequencesEqual(Vector var0, Vector var1) {
      int var2 = var0.size();
      int var3 = var1.size();
      if (var2 != var3) {
         return false;
      } else {
         for(int var4 = 0; var4 < var2; ++var4) {
            if (!((Certificate)var0.elementAt(var4)).equals(var1.elementAt(var4))) {
               return false;
            }
         }

         return true;
      }
   }

   /** @deprecated */
   public static boolean setsEqual(Vector var0, Vector var1) {
      int var2 = var0.size();
      int var3 = var1.size();
      if (var2 != var3) {
         return false;
      } else {
         for(int var4 = 0; var4 < var2; ++var4) {
            if (!var0.contains(var1.elementAt(var4))) {
               return false;
            }
         }

         return true;
      }
   }

   /** @deprecated */
   public static boolean containsExtension(X509V3Extension var0, X509V3Extensions var1) {
      int var2 = var0.getExtensionType();
      X509V3Extension var3 = null;

      try {
         var3 = var1.getExtensionByType(var2);
      } catch (CertificateException var10) {
         return false;
      }

      if (var3 == null) {
         return false;
      } else {
         switch (var2) {
            case 18:
               GeneralNames var4 = ((IssuerAltName)var0).getGeneralNames();
               GeneralNames var5 = ((IssuerAltName)var3).getGeneralNames();

               try {
                  for(int var6 = 0; var6 < var4.getNameCount(); ++var6) {
                     if (var5.contains(var4.getGeneralName(var6))) {
                        return true;
                     }
                  }

                  return false;
               } catch (NameException var9) {
                  return false;
               }
            default:
               byte[] var11 = new byte[var0.getDERLen(0)];
               byte[] var7 = new byte[var3.getDERLen(0)];
               if (var11.length != var7.length) {
                  return false;
               } else {
                  var0.getDEREncoding(var11, 0, 0);
                  var3.getDEREncoding(var7, 0, 0);

                  for(int var8 = 0; var8 < var11.length; ++var8) {
                     if (var11[var8] != var7[var8]) {
                        return false;
                     }
                  }

                  return true;
               }
         }
      }
   }

   /** @deprecated */
   public static boolean compareExtensions(X509V3Extensions var0, X509V3Extensions var1) {
      if (var0 == null) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         try {
            for(int var2 = 0; var2 < var0.getExtensionCount(); ++var2) {
               if (!containsExtension(var0.getExtensionByIndex(var2), var1)) {
                  return false;
               }
            }

            return true;
         } catch (CertificateException var3) {
            return false;
         }
      }
   }

   /** @deprecated */
   public static int bytesToHashCode(byte[] var0) {
      return bytesToHashCode(var0, 0, var0.length);
   }

   /** @deprecated */
   public static int bytesToHashCode(byte[] var0, int var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = var1 + var2;
      int var6 = 0;

      for(int var7 = var1; var7 < var5; ++var7) {
         int var8 = (var0[var7] & 255) << var6;
         var4 += var0[var7];
         var3 ^= var8;
         if (var6 == 24) {
            var6 = 0;
         } else {
            var6 += 8;
         }
      }

      var3 ^= var2;
      var3 ^= var4;
      return var3;
   }

   /** @deprecated */
   public static String objectArrayToString(Object[] var0, String var1) {
      if (var0 == null) {
         return "";
      } else {
         String var2 = var1 == null ? ", " : var1;
         StringBuffer var3 = new StringBuffer();

         for(int var4 = 0; var4 < var0.length; ++var4) {
            Object var5 = var0[var4];
            var3.append(var2);
            if (var5 != null) {
               var3.append(var5.toString());
            }
         }

         if (var0.length > 0) {
            var3.delete(0, var2.length());
         }

         return var3.toString();
      }
   }

   /** @deprecated */
   public static byte[] oidStringToBytes(String var0) throws IllegalArgumentException {
      return (new ObjectID(var0)).getEncoded();
   }

   /** @deprecated */
   public static char[] byteArrayToCharArray(byte[] var0) {
      char[] var1 = new char[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (char)(var0[var2] & 255);
      }

      return var1;
   }
}
