package weblogic.security.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public final class SSLCipherUtility {
   private static final List cipherInfoList = initCipherInfoList();
   private static final ConcurrentHashMap cipherInfoCache = new ConcurrentHashMap(50);
   private static final String ASTERISK = "*";

   public static int getKeySize(String cipherSuite) {
      if (null != cipherSuite && cipherSuite.length() != 0) {
         String normCipherSuite = cipherSuite.trim().toUpperCase(Locale.US);
         CipherInfo foundInfo = (CipherInfo)cipherInfoCache.get(normCipherSuite);
         if (foundInfo != null) {
            return foundInfo.keySize;
         } else {
            foundInfo = find(normCipherSuite);
            if (null == foundInfo) {
               foundInfo = extract(normCipherSuite);
            }

            if (null != foundInfo) {
               cipherInfoCache.put(normCipherSuite, foundInfo);
               return foundInfo.keySize;
            } else {
               return -1;
            }
         }
      } else {
         return -1;
      }
   }

   private static String extractSubstring(String source, String startTag, String endTag) {
      if (null == source) {
         return null;
      } else if (null == startTag) {
         return null;
      } else if (null == endTag) {
         return null;
      } else {
         int startIndex = source.indexOf(startTag);
         if (-1 == startIndex) {
            return null;
         } else {
            startIndex += startTag.length();
            int endIndex = source.indexOf(endTag, startIndex);
            return -1 == endIndex ? null : source.substring(startIndex, endIndex);
         }
      }
   }

   private static int extractSubstringPositiveIntOrZero(String source, String startTag, String endTag) {
      String subString = extractSubstring(source, startTag, endTag);
      if (null == subString) {
         return -1;
      } else {
         String normSubString = subString.trim();

         int result;
         try {
            result = Integer.parseInt(normSubString);
         } catch (NumberFormatException var7) {
            return -1;
         }

         if (result < 0) {
            result = -1;
         }

         return result;
      }
   }

   private static CipherInfo find(String cipherSuite) {
      if (null == cipherSuite) {
         return null;
      } else {
         Iterator var1 = cipherInfoList.iterator();

         CipherInfo info;
         do {
            if (!var1.hasNext()) {
               return null;
            }

            info = (CipherInfo)var1.next();
         } while(!info.applies(cipherSuite));

         return info;
      }
   }

   private static CipherInfo extract(String cipherSuite) {
      int foundKeySize = extractSubstringPositiveIntOrZero(cipherSuite, "_WITH_RC4_", "_");
      if (foundKeySize < 0) {
         foundKeySize = extractSubstringPositiveIntOrZero(cipherSuite, "_WITH_AES_", "_");
      }

      return foundKeySize >= 0 ? new CipherInfo(cipherSuite, foundKeySize) : null;
   }

   public static String[] removeNullCipherSuites(String[] cipherSuites) {
      int count = countNullCipherSuites(cipherSuites);
      if (count == 0) {
         return cipherSuites;
      } else {
         String[] suites = new String[cipherSuites.length - count];
         int i = 0;

         for(int j = 0; i < cipherSuites.length; ++i) {
            if (!isNullCipherSuite(cipherSuites[i])) {
               suites[j++] = cipherSuites[i];
            }
         }

         return suites;
      }
   }

   public static String[] removeExcludedCiphersuites(String[] cipherSuites, String[] excludedCipherSuites) {
      String[] result = cipherSuites != null && cipherSuites.length > 0 ? (String[])Arrays.copyOf(cipherSuites, cipherSuites.length) : null;
      if (cipherSuites != null && cipherSuites.length > 0 && excludedCipherSuites != null && excludedCipherSuites.length > 0) {
         List cipherList = new ArrayList(cipherSuites.length);
         String[] var4 = cipherSuites;
         int var5 = cipherSuites.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String cipher = var4[var6];
            boolean matched = false;
            String[] var9 = excludedCipherSuites;
            int var10 = excludedCipherSuites.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String pattern = var9[var11];
               if (pattern != null && cipher != null) {
                  if (pattern.startsWith("*") && pattern.endsWith("*") && pattern.length() > 2) {
                     if (cipher.contains(pattern.substring(1, pattern.length() - 1))) {
                        matched = true;
                        break;
                     }
                  } else if (pattern.endsWith("*") && pattern.length() > 1) {
                     if (cipher.contains(pattern.substring(0, pattern.length() - 1))) {
                        matched = true;
                        break;
                     }
                  } else if (pattern.startsWith("*") && pattern.length() > 1) {
                     if (cipher.contains(pattern.substring(1))) {
                        matched = true;
                        break;
                     }
                  } else if (cipher.equals(pattern)) {
                     matched = true;
                     break;
                  }
               }
            }

            if (!matched) {
               cipherList.add(cipher);
            }
         }

         if (cipherList.size() > 0) {
            result = (String[])cipherList.toArray(new String[0]);
         }
      }

      return result;
   }

   public static void normalizeNames(String[] cipherSuites) {
      if (cipherSuites != null) {
         for(int i = 0; i < cipherSuites.length; ++i) {
            cipherSuites[i] = normalizeName(cipherSuites[i]);
         }
      }

   }

   private static String normalizeName(String cipherSuite) {
      if (!cipherSuite.startsWith("TLS_")) {
         if (cipherSuite.startsWith("SSL_")) {
            cipherSuite = cipherSuite.substring(4);
         }

         cipherSuite = "TLS_" + cipherSuite;
      }

      return cipherSuite;
   }

   private static boolean isNullCipherSuite(String cipherSuite) {
      return cipherSuite.equals("SSL_NULL_WITH_NULL_NULL") || cipherSuite.equals("TLS_NULL_WITH_NULL_NULL");
   }

   private static int countNullCipherSuites(String[] cipherSuites) {
      int count = 0;
      if (cipherSuites != null) {
         String[] var2 = cipherSuites;
         int var3 = cipherSuites.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String cipherSuite = var2[var4];
            if (isNullCipherSuite(cipherSuite)) {
               ++count;
            }
         }
      }

      return count;
   }

   private static List initCipherInfoList() {
      ArrayList list = new ArrayList(20);
      list.add(new CipherInfo("_WITH_NULL_", 0));
      list.add(new CipherInfo("_WITH_IDEA_CBC_", 128));
      list.add(new CipherInfo("_WITH_RC2_CBC_40_", 40));
      list.add(new CipherInfo("_WITH_RC4_40_", 40));
      list.add(new CipherInfo("_WITH_RC4_56_", 56));
      list.add(new CipherInfo("_WITH_RC4_128_", 128));
      list.add(new CipherInfo("_WITH_DES40_CBC_", 40));
      list.add(new CipherInfo("_WITH_DES_40_CBC_", 40));
      list.add(new CipherInfo("_WITH_DES_CBC_40_", 40));
      list.add(new CipherInfo("_WITH_DES_CBC_", 56));
      list.add(new CipherInfo("_WITH_3DES_EDE_CBC_", 168));
      list.add(new CipherInfo("_WITH_AES_128_CBC_", 128));
      list.add(new CipherInfo("_WITH_AES_256_CBC_", 256));
      list.add(new CipherInfo("_EMPTY_RENEGOTIATION_INFO_SCSV", 0));
      list.add(new CipherInfo("_AES_128_CCM_", 128));
      list.add(new CipherInfo("_AES_128_GCM_", 128));
      list.add(new CipherInfo("_AES_256_GCM_", 256));
      list.add(new CipherInfo("_CHACHA20_POLY1305_SHA256", 256));
      return Collections.unmodifiableList(list);
   }

   private static class CipherInfo {
      private final String phrase;
      private final int keySize;

      private CipherInfo(String phrase, int keySize) {
         if (null == phrase) {
            throw new IllegalArgumentException("Unexpected null phrase.");
         } else {
            this.phrase = phrase;
            this.keySize = keySize;
         }
      }

      boolean applies(String cipherSuite) {
         if (null != cipherSuite && cipherSuite.length() != 0) {
            return cipherSuite.indexOf(this.phrase) != -1;
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      CipherInfo(String x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
