package weblogic.corba.cos.security;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

public class GSSUtil {
   private static final boolean DEBUG = false;
   static final byte[] encodedGSSNTOID = new byte[]{6, 6, 43, 6, 1, 5, 6, 4};
   static final byte[] encodedGSSUPOID = new byte[]{6, 6, 103, -127, 2, 1, 1, 1};
   private static final int HIGH_BIT = 128;

   public static byte[] createGSSUPGSSNTExportedName(String str) {
      byte[] name = str.getBytes();
      int exportedLen = 4 + encodedGSSUPOID.length + 4 + name.length;
      byte[] exportedName = new byte[exportedLen];
      int i = 0;
      exportedName[i++] = 4;
      exportedName[i++] = 1;
      exportedName[i++] = (byte)(encodedGSSUPOID.length >>> 8);
      exportedName[i++] = (byte)(encodedGSSUPOID.length >>> 0);
      System.arraycopy(encodedGSSUPOID, 0, exportedName, i, encodedGSSUPOID.length);
      i += encodedGSSUPOID.length;
      exportedName[i++] = (byte)(name.length >>> 24);
      exportedName[i++] = (byte)(name.length >>> 16);
      exportedName[i++] = (byte)(name.length >>> 8);
      exportedName[i++] = (byte)(name.length >>> 0);
      System.arraycopy(name, 0, exportedName, i, name.length);
      return exportedName;
   }

   public static String extractGSSUPGSSNTExportedName(byte[] exportedName) {
      int exportedNameLength = exportedName.length;
      if (exportedName[0] == 4 && exportedName[1] == 1) {
         int mechLength = (exportedName[2] & 255) << 8 | exportedName[3] & 255;
         if (mechLength != encodedGSSUPOID.length) {
            return null;
         } else {
            byte[] mechOID = new byte[mechLength];
            System.arraycopy(exportedName, 4, mechOID, 0, mechLength);
            if (!Arrays.equals(mechOID, encodedGSSUPOID)) {
               return null;
            } else {
               int i = 4 + encodedGSSUPOID.length;
               int nameLength = (exportedName[i] & 255) << 24 | (exportedName[i + 1] & 255) << 16 | (exportedName[i + 2] & 255) << 8 | exportedName[i + 3] & 255;
               i += 4;
               byte[] extractedName = new byte[nameLength];
               System.arraycopy(exportedName, i, extractedName, 0, nameLength);
               return new String(extractedName);
            }
         }
      } else {
         return null;
      }
   }

   public static byte[] getGSSUPMech() {
      return encodedGSSUPOID;
   }

   public static boolean isGSSUPMech(byte[] mech) {
      return Arrays.equals(mech, getGSSUPMech());
   }

   public static byte[] getGSSUPToken(byte[] innerToken) {
      byte[] mechGSSUP = getGSSUPMech();
      int tokenLen = mechGSSUP.length + innerToken.length;
      int tokenDERNumOctets = getDERNumOctets(tokenLen);
      int gssTokenLen = 1 + tokenDERNumOctets + mechGSSUP.length + innerToken.length;
      byte[] gssToken = new byte[gssTokenLen];
      int i = 0;
      gssToken[i++] = 96;
      if (tokenLen < 128) {
         gssToken[i++] = (byte)tokenLen;
      } else {
         gssToken[i++] = (byte)(tokenDERNumOctets + 127);
         switch (tokenDERNumOctets) {
            case 2:
               gssToken[i++] = (byte)(tokenLen & 255);
               break;
            case 3:
               gssToken[i++] = (byte)(tokenLen >> 8 & 255);
               gssToken[i++] = (byte)(tokenLen & 255);
               break;
            case 4:
               gssToken[i++] = (byte)(tokenLen >> 16 & 255);
               gssToken[i++] = (byte)(tokenLen >> 8 & 255);
               gssToken[i++] = (byte)(tokenLen & 255);
               break;
            case 5:
               gssToken[i++] = (byte)(tokenLen >> 24);
               gssToken[i++] = (byte)(tokenLen >> 16 & 255);
               gssToken[i++] = (byte)(tokenLen >> 8 & 255);
               gssToken[i++] = (byte)(tokenLen & 255);
         }
      }

      System.arraycopy(mechGSSUP, 0, gssToken, i, mechGSSUP.length);
      i += mechGSSUP.length;
      System.arraycopy(innerToken, 0, gssToken, i, innerToken.length);
      return gssToken;
   }

   private static int getDERNumOctets(int length) {
      if (length < 128) {
         return 1;
      } else if (length < 256) {
         return 2;
      } else if (length < 65536) {
         return 3;
      } else {
         return length < 16777216 ? 4 : 5;
      }
   }

   public static byte[] getGSSUPInnerToken(byte[] gssToken) {
      int i = 0;
      int maxIdx = gssToken.length;
      if (gssToken.length >= 6 && gssToken[i++] == 96) {
         int tokenLen = false;
         int j;
         int tokenLen;
         if (!isHighBitSet(gssToken[i])) {
            tokenLen = gssToken[i++];
         } else {
            j = gssToken[i++] & 127;
            switch (j) {
               case 1:
                  tokenLen = gssToken[i++] & 255;
                  break;
               case 2:
                  tokenLen = gssToken[i++] << 8 & '\uff00' | gssToken[i++] & 255;
                  break;
               case 3:
                  tokenLen = gssToken[i++] << 16 & 16711680 | gssToken[i++] << 8 & '\uff00' | gssToken[i++] & 255;
                  break;
               case 4:
                  tokenLen = gssToken[i++] << 24 & -16777216 | gssToken[i++] << 16 & 16711680 | gssToken[i++] << 8 & '\uff00' | gssToken[i++] & 255;
            }
         }

         for(j = 0; j < encodedGSSUPOID.length; ++j) {
            if (i >= maxIdx || encodedGSSUPOID[j] != gssToken[i]) {
               return null;
            }

            ++i;
         }

         byte[] innerToken = new byte[maxIdx - i];
         System.arraycopy(gssToken, i, innerToken, 0, maxIdx - i);
         return innerToken;
      } else {
         return null;
      }
   }

   public static X509Certificate[] getX509CertChain(byte[] sequence) {
      int i = 0;
      if (sequence != null && sequence.length >= 2 && sequence[i++] == 48) {
         int tokenLen = 0;
         if (!isHighBitSet(sequence[i])) {
            tokenLen = sequence[i++];
         } else {
            int numOctets = sequence[i++] & 127;
            switch (numOctets) {
               case 1:
                  tokenLen = sequence[i++] & 255;
                  break;
               case 2:
                  tokenLen = sequence[i++] << 8 & '\uff00' | sequence[i++] & 255;
                  break;
               case 3:
                  tokenLen = sequence[i++] << 16 & 16711680 | sequence[i++] << 8 & '\uff00' | sequence[i++] & 255;
                  break;
               case 4:
                  tokenLen = sequence[i++] << 24 & -16777216 | sequence[i++] << 16 & 16711680 | sequence[i++] << 8 & '\uff00' | sequence[i++] & 255;
            }
         }

         if (tokenLen != sequence.length - i) {
            return null;
         } else {
            ByteArrayInputStream bis = new ByteArrayInputStream(sequence, i, tokenLen);
            ArrayList list = new ArrayList();

            try {
               CertificateFactory cf = CertificateFactory.getInstance("X.509");

               while(bis.available() > 0) {
                  list.add(cf.generateCertificate(bis));
               }
            } catch (CertificateException var6) {
               return null;
            }

            return (X509Certificate[])((X509Certificate[])list.toArray(new X509Certificate[list.size()]));
         }
      } else {
         return null;
      }
   }

   private static boolean isHighBitSet(byte b) {
      return (b & 128) == 128;
   }

   public static String getQuotedGSSUserName(String userName) {
      int i = userName.indexOf(64);
      if (i < 0) {
         return userName;
      } else {
         StringBuffer strBuf = new StringBuffer();
         if (i > 0) {
            strBuf.append(userName.substring(0, i));
         }

         strBuf.append('\\');
         strBuf.append('@');
         strBuf.append(userName.substring(i + 1));
         String quotedUserName = strBuf.toString();
         return quotedUserName;
      }
   }

   public static String getUnquotedGSSUserName(String userName) {
      int idx = userName.indexOf(92);
      if (idx < 0) {
         return userName;
      } else {
         StringBuffer strBuf = new StringBuffer();
         if (idx > 0) {
            strBuf.append(userName.substring(0, idx));
         }

         strBuf.append(userName.substring(idx + 1));
         String unQuotedUserName = strBuf.toString();
         return unQuotedUserName;
      }
   }

   private static void p(String msg) {
      System.out.println("<GSSUtil>: " + msg);
   }
}
