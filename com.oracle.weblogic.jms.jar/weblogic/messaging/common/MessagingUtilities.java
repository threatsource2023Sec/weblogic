package weblogic.messaging.common;

import java.io.StreamCorruptedException;
import java.security.SecureRandom;
import weblogic.messaging.MessagingLogger;

public class MessagingUtilities {
   public static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return new StreamCorruptedException(MessagingLogger.logUnsupportedClassVersionLoggable(version, minExpectedVersion, maxExpectedVersion).getMessage());
   }

   public static final int calcObjectSize(Object obj) {
      if (obj == null) {
         return 2;
      } else if (obj instanceof Integer) {
         return 6;
      } else if (obj instanceof String) {
         return 4 + (((String)obj).length() << 2);
      } else if (obj instanceof Long) {
         return 10;
      } else if (obj instanceof Boolean) {
         return 3;
      } else if (obj instanceof Byte) {
         return 3;
      } else if (obj instanceof Short) {
         return 4;
      } else if (obj instanceof Float) {
         return 6;
      } else if (obj instanceof Double) {
         return 10;
      } else {
         return obj instanceof byte[] ? ((byte[])((byte[])obj)).length + 6 : 0;
      }
   }

   public static final int getSeed() {
      int idSeed = 0;
      SecureRandom secureRandom = new SecureRandom();
      byte[] secureRandomBytes = new byte[4];
      secureRandom.nextBytes(secureRandomBytes);

      for(int i = 0; i < 4; ++i) {
         idSeed = idSeed << 4 | secureRandomBytes[i] & 255;
      }

      return idSeed;
   }

   public static String getSortingString(String orig) {
      String ret = "";
      String word = "";
      char lastCh = 0;
      char[] var4 = orig.toCharArray();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char ch = var4[var6];
         if (word.length() != 0 && Character.isDigit(ch) ^ Character.isDigit(lastCh)) {
            ret = ret + getSortingWord(word);
            word = "";
         }

         word = word + ch;
         lastCh = ch;
      }

      ret = ret + getSortingWord(word);
      return ret;
   }

   private static String getSortingWord(String word) {
      if (word.length() == 0) {
         return word;
      } else {
         if (Character.isDigit(word.charAt(0))) {
            for(int i = word.length(); i < 6; ++i) {
               word = '0' + word;
            }
         }

         return word;
      }
   }
}
