package weblogic.nodemanager.util;

import com.bea.security.utils.ssl.SSLContextProtocolSelector;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import weblogic.nodemanager.NodeManagerCommonTextFormatter;

public class SSLProtocolsUtil {
   private static final NodeManagerCommonTextFormatter nmText = NodeManagerCommonTextFormatter.getInstance();
   static String DEFAULT_MIN_PROTOCOL = "TLSv1";
   static String MIN_PROTOCOL_PROP = "weblogic.security.SSL.minimumProtocolVersion";
   private static final String TLS_REJECT_CLIENT_INIT_RENEGOTIATION = "jdk.tls.rejectClientInitiatedRenegotiation";
   private static final boolean IS_JDK_CLIENT_INIT_SECURE_RENEGOTIATION_PROPERTY_SET = System.getProperty("jdk.tls.rejectClientInitiatedRenegotiation") != null;
   private static final String CLIENT_INIT_SECURE_RENEGOTIATION_ACCEPTED_PROP = "weblogic.ssl.ClientInitSecureRenegotiationAccepted";
   private static final boolean IS_CLIENT_INIT_SECURE_RENEGOTIATION_ACCEPTED = Boolean.getBoolean("weblogic.ssl.ClientInitSecureRenegotiationAccepted");

   public static String getSSLContextProtocol() {
      return SSLProtocolsUtil.SSLContextProtocolName.INSTANCE;
   }

   public static String getMinProtocolVersion() {
      return System.getProperty(MIN_PROTOCOL_PROP, DEFAULT_MIN_PROTOCOL);
   }

   public static String[] getJSSEProtocolVersions(String minimumProtocolVersion, String[] supportedProtocolVersions, Logger nmLog) throws IllegalArgumentException {
      if (nmLog.isLoggable(Level.FINEST)) {
         nmLog.finest("supportedProtocolVersions=" + toString(supportedProtocolVersions));
      }

      if (null != supportedProtocolVersions && 0 != supportedProtocolVersions.length) {
         if (nmLog.isLoggable(Level.FINEST)) {
            nmLog.finest("given minimumProtocolVersion=" + minimumProtocolVersion);
         }

         if (null == minimumProtocolVersion) {
            throw new NullPointerException("Unexpected null minimumProtocolVersion.");
         } else {
            boolean overriddenMinimum = false;
            ProtocolVersion minVersion;
            if ("".equals(minimumProtocolVersion)) {
               overriddenMinimum = true;
               minVersion = SSLProtocolsUtil.ProtocolVersion.SSLV3;
            } else {
               try {
                  minVersion = new ProtocolVersion(minimumProtocolVersion);
               } catch (IllegalArgumentException var16) {
                  if (nmLog.isLoggable(Level.FINEST)) {
                     nmLog.finest("Unable to instantiate ProtocolVersion for minimum protocol version " + minimumProtocolVersion + ": " + var16.getMessage());
                  }

                  overriddenMinimum = true;
                  minVersion = SSLProtocolsUtil.ProtocolVersion.SSLV3;
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
                  } catch (IllegalArgumentException var15) {
                     if (nmLog.isLoggable(Level.FINEST)) {
                        nmLog.finest("Unable to instantiate ProtocolVersion for provider-supported protocol version " + minimumProtocolVersion + ": " + var15.getMessage());
                     }
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
                  nmLog.warning(nmText.getUnsupportedSSLMinimumProtocolVersion(minimumProtocolVersion, nextLowerThanMin.toString()));
                  addIfNotPresent(enabledList, nextLowerThanMin);
               } else if (null == nextHigherThanMin) {
                  if (nmLog.isLoggable(Level.FINEST)) {
                     nmLog.finest("nextHigherThanMin unexpectedly null. ");
                  }
               } else {
                  nmLog.warning(nmText.getUnsupportedSSLMinimumProtocolVersion(minimumProtocolVersion, nextHigherThanMin.toString()));
               }
            } else if (overriddenMinimum) {
               nmLog.warning(nmText.getUnsupportedSSLMinimumProtocolVersion(minimumProtocolVersion, foundMin.toString()));
            }

            if (nmLog.isLoggable(Level.FINEST)) {
               nmLog.finest("enabledProtocols=" + toString((String[])enabledList.toArray(new String[enabledList.size()])));
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

   public static void configureClientInitSecureRenegotiation(HandshakeCompletedEvent handshakeCompletedEvent, Logger nmLog) {
      if (!IS_JDK_CLIENT_INIT_SECURE_RENEGOTIATION_PROPERTY_SET) {
         SSLSession session = handshakeCompletedEvent.getSession();
         SSLSocket socket = handshakeCompletedEvent.getSocket();
         if (session != null && socket != null) {
            if (!IS_CLIENT_INIT_SECURE_RENEGOTIATION_ACCEPTED) {
               session.invalidate();
            }

            socket.setEnableSessionCreation(IS_CLIENT_INIT_SECURE_RENEGOTIATION_ACCEPTED);
            if (nmLog.isLoggable(Level.CONFIG)) {
               nmLog.config("TLS client initiated secure renegotiation is " + (IS_CLIENT_INIT_SECURE_RENEGOTIATION_ACCEPTED ? "enabled." : "disabled."));
            }
         }
      } else if (nmLog.isLoggable(Level.CONFIG)) {
         nmLog.config("TLS client initiated secure renegotiation setting is configured with -Djdk.tls.rejectClientInitiatedRenegotiation");
      }

   }

   private static String toString(String[] list) {
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

   private static class ProtocolVersion {
      public static final ProtocolVersion SSLV3 = new ProtocolVersion("SSLv3");
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

   private static final class SSLContextProtocolName {
      private static final String INSTANCE = SSLContextProtocolSelector.getSSLContextProtocol();
   }
}
