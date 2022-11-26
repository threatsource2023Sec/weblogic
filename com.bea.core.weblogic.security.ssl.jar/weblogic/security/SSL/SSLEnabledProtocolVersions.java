package weblogic.security.SSL;

import java.util.ArrayList;
import java.util.Locale;

public class SSLEnabledProtocolVersions {
   private static final String SSLv2Hello = "SSLv2Hello";

   public static int getSSLContextDelegateProtocolVersions(String minimumProtocolVersion, LogListener logger) {
      if (null != logger && logger.isDebugEnabled()) {
         logger.debug("supported protocol version modes: V2HELLO_SSL3_TLS1, TLS1_ONLY", (Throwable)null);
      }

      debug_givenMinProtocolVersion(minimumProtocolVersion, logger);
      if (null == minimumProtocolVersion) {
         throw new NullPointerException("Unexpected null minimumProtocolVersion.");
      } else if ("".equals(minimumProtocolVersion)) {
         if (null != logger) {
            logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, "SSLv2Hello");
         }

         debug_selectedProtocolMode(logger, "TLS1_ONLY");
         return 0;
      } else {
         ProtocolVersion minVersion;
         try {
            minVersion = new ProtocolVersion(minimumProtocolVersion);
         } catch (IllegalArgumentException var4) {
            debug_unableToInstantiateProtocolVersion(minimumProtocolVersion, "minimum", logger, var4);
            if (null != logger) {
               logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, "SSLv2Hello");
            }

            debug_selectedProtocolMode(logger, "TLS1_ONLY");
            return 0;
         }

         if (minVersion.greaterThan(SSLEnabledProtocolVersions.ProtocolVersion.SSLV3)) {
            if (minVersion.greaterThan(SSLEnabledProtocolVersions.ProtocolVersion.TLSV1_0) && null != logger) {
               logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, SSLEnabledProtocolVersions.ProtocolVersion.TLSV1_0.toString());
            }

            debug_selectedProtocolMode(logger, "TLS1_ONLY");
            return 0;
         } else {
            if (null != logger) {
               logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, "SSLv2Hello");
            }

            debug_selectedProtocolMode(logger, "TLS1_ONLY");
            return 0;
         }
      }
   }

   public static String[] getJSSEProtocolVersions(String minimumProtocolVersion, String[] supportedProtocolVersions, LogListener logger) throws IllegalArgumentException {
      if (null != logger && logger.isDebugEnabled()) {
         logger.debug("supportedProtocolVersions=" + toString(supportedProtocolVersions), (Throwable)null);
      }

      if (null != supportedProtocolVersions && 0 != supportedProtocolVersions.length) {
         debug_givenMinProtocolVersion(minimumProtocolVersion, logger);
         if (null == minimumProtocolVersion) {
            throw new NullPointerException("Unexpected null minimumProtocolVersion.");
         } else {
            boolean overriddenMinimum = false;
            ProtocolVersion minVersion;
            if ("".equals(minimumProtocolVersion)) {
               overriddenMinimum = true;
               minVersion = SSLEnabledProtocolVersions.ProtocolVersion.DEFAULT_MIN_TLS;
            } else {
               try {
                  minVersion = new ProtocolVersion(minimumProtocolVersion);
               } catch (IllegalArgumentException var15) {
                  debug_unableToInstantiateProtocolVersion(minimumProtocolVersion, "minimum", logger, var15);
                  overriddenMinimum = true;
                  minVersion = SSLEnabledProtocolVersions.ProtocolVersion.DEFAULT_MIN_TLS;
               }
            }

            ProtocolVersion nextLowerThanMin = null;
            ProtocolVersion nextHigherThanMin = null;
            ProtocolVersion foundMin = null;
            ArrayList enabledList = new ArrayList(3);
            String[] var9 = supportedProtocolVersions;
            int var10 = supportedProtocolVersions.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String supportedProt = var9[var11];
               if (null != supportedProt) {
                  ProtocolVersion supportedProtVersion;
                  try {
                     supportedProtVersion = new ProtocolVersion(supportedProt);
                  } catch (IllegalArgumentException var16) {
                     debug_unableToInstantiateProtocolVersion(supportedProt, "provider-supported", logger, var16);
                     continue;
                  }

                  if (null == foundMin) {
                     if (supportedProtVersion.equals(minVersion)) {
                        foundMin = supportedProtVersion;
                        addIfNotPresent(enabledList, supportedProtVersion);
                     } else if (supportedProtVersion.lessThan(minVersion)) {
                        if (null == nextLowerThanMin) {
                           nextLowerThanMin = supportedProtVersion;
                        } else if (supportedProtVersion.greaterThan(nextLowerThanMin)) {
                           nextLowerThanMin = supportedProtVersion;
                        }
                     } else {
                        if (null == nextHigherThanMin) {
                           nextHigherThanMin = supportedProtVersion;
                        } else if (supportedProtVersion.lessThan(nextHigherThanMin)) {
                           nextHigherThanMin = supportedProtVersion;
                        }

                        addIfNotPresent(enabledList, supportedProtVersion);
                     }
                  } else if (supportedProtVersion.greaterThan(minVersion)) {
                     addIfNotPresent(enabledList, supportedProtVersion);
                  }
               }
            }

            if (null == foundMin) {
               if (null != nextLowerThanMin) {
                  if (null != logger) {
                     logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, nextLowerThanMin.toString());
                  }

                  addIfNotPresent(enabledList, nextLowerThanMin);
               } else if (null == nextHigherThanMin) {
                  if (null != logger && logger.isDebugEnabled()) {
                     logger.debug("nextHigherThanMin unexpectedly null. ", (Throwable)null);
                  }
               } else if (null != logger) {
                  logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, nextHigherThanMin.toString());
               }
            } else if (overriddenMinimum && null != logger) {
               logger.logUnsupportedMinimumProtocolVersion(minimumProtocolVersion, foundMin.toString());
            }

            return (String[])enabledList.toArray(new String[enabledList.size()]);
         }
      } else {
         throw new IllegalArgumentException("No supported SSL protocol versions.");
      }
   }

   private static void addIfNotPresent(ArrayList enabledList, ProtocolVersion protVersion) {
      String versionStr = protVersion.toString();
      if (!enabledList.contains(versionStr)) {
         enabledList.add(versionStr);
      }

   }

   static String toString(String[] list) {
      if (null == list) {
         return "null";
      } else {
         StringBuilder sb = new StringBuilder(list.length * 5);
         char delimiter = true;
         String[] var3 = list;
         int var4 = list.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (null != s) {
               if (sb.length() > 0) {
                  sb.append(',');
               }

               sb.append(s);
            }
         }

         return sb.toString();
      }
   }

   private static void debug_selectedProtocolMode(LogListener logger, String modeName) {
      if (null != logger && logger.isDebugEnabled()) {
         logger.debug("selected protocol version mode: " + modeName, (Throwable)null);
      }

   }

   private static void debug_givenMinProtocolVersion(String minimumProtocolVersion, LogListener logger) {
      if (null != logger && logger.isDebugEnabled()) {
         logger.debug("given minimumProtocolVersion=" + minimumProtocolVersion, (Throwable)null);
      }

   }

   private static void debug_unableToInstantiateProtocolVersion(String minimumProtocolVersion, String kindOfProtocolVersion, LogListener logger, IllegalArgumentException e) {
      if (null != logger && logger.isDebugEnabled()) {
         logger.debug("Unable to instantiate ProtocolVersion for " + kindOfProtocolVersion + " protocol version " + minimumProtocolVersion + ": " + e.getMessage(), (Throwable)null);
      }

   }

   interface LogListener {
      boolean isDebugEnabled();

      void debug(String var1, Throwable var2);

      void logUnsupportedMinimumProtocolVersion(String var1, String var2);
   }

   static class ProtocolVersion {
      public static final ProtocolVersion SSLV3 = new ProtocolVersion("SSLv3");
      public static final ProtocolVersion TLSV1_0 = new ProtocolVersion("TLSv1.0");
      public static final ProtocolVersion DEFAULT_MIN_TLS = new ProtocolVersion("TLSv1.1");
      private static final byte SSLV3_ORDINAL = 0;
      private static final byte TLS_MAJOR_VERSION_ORDINAL_MULTIPLIER = 10;
      private final int ordinal;
      private final String originalString;

      ProtocolVersion(String input) {
         if (null == input) {
            throw new IllegalArgumentException("Null input string.");
         } else {
            this.originalString = input;
            String normInput = input.toLowerCase(Locale.US).trim();
            if (normInput.equals("sslv3")) {
               this.ordinal = 0;
            } else {
               if (!normInput.startsWith("tlsv")) {
                  throw new IllegalArgumentException("Unknown protocol: " + input);
               }

               String tlsVersion = normInput.substring(4);
               if (tlsVersion.equals("1")) {
                  this.ordinal = 10;
               } else {
                  if (tlsVersion.length() < 3) {
                     throw new IllegalArgumentException("Bad prefix: " + input);
                  }

                  int dotIndex = tlsVersion.indexOf(46);
                  if (-1 == dotIndex) {
                     throw new IllegalArgumentException("Missing dot: " + input);
                  }

                  String tlsMajorVersion = tlsVersion.substring(0, dotIndex);
                  if (tlsMajorVersion.length() != 1) {
                     throw new IllegalArgumentException("Only 1 digit major version supported: " + input);
                  }

                  byte majorValue;
                  try {
                     majorValue = Byte.valueOf(tlsMajorVersion, 10);
                  } catch (NumberFormatException var11) {
                     throw new IllegalArgumentException("Major version not a number: " + input);
                  }

                  if (majorValue < 1 || majorValue > 9) {
                     throw new IllegalArgumentException("Major version range 1-9: " + input);
                  }

                  String tlsMinorVersion = tlsVersion.substring(dotIndex + 1);
                  if (tlsMinorVersion.length() != 1) {
                     throw new IllegalArgumentException("Only 1 digit minor version supported: " + input);
                  }

                  byte minorValue;
                  try {
                     minorValue = Byte.valueOf(tlsMinorVersion, 10);
                  } catch (NumberFormatException var10) {
                     throw new IllegalArgumentException("Minor version not a number: " + input);
                  }

                  if (minorValue < 0 || minorValue > 9) {
                     throw new IllegalArgumentException("Minor version range 0-9: " + input);
                  }

                  this.ordinal = majorValue * 10 + minorValue;
               }
            }

         }
      }

      public String toString() {
         return this.originalString;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ProtocolVersion that = (ProtocolVersion)o;
            return this.ordinal == that.ordinal;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.ordinal;
      }

      boolean lessThan(ProtocolVersion that) {
         if (that == null) {
            throw new IllegalArgumentException("Unexpected null ProtocolVersion.");
         } else {
            return this.ordinal < that.ordinal;
         }
      }

      boolean greaterThan(ProtocolVersion that) {
         if (that == null) {
            throw new IllegalArgumentException("Unexpected null ProtocolVersion.");
         } else {
            return this.ordinal > that.ordinal;
         }
      }
   }
}
