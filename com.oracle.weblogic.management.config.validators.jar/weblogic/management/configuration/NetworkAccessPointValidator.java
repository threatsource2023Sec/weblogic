package weblogic.management.configuration;

import weblogic.logging.Loggable;
import weblogic.security.SecurityLogger;

public class NetworkAccessPointValidator {
   public static void validateListenPort(int value) {
      if (value != -1 && (value < 1 || value > 65535)) {
         throw new IllegalArgumentException("Illegal value for ListenPort: " + value);
      }
   }

   public static void validatePublicPort(int value) {
      if (value != -1 && (value < 1 || value > 65535)) {
         throw new IllegalArgumentException("Illegal value for PublicPort: " + value);
      }
   }

   public static void validateMaxMessageSize(int value) {
      if (value != -1 && (value < 1 || value > 65534)) {
         throw new IllegalArgumentException("Illegal value for MaxMessageSize: " + value);
      }
   }

   public static void validateHttpEnabledForThisProtocol(NetworkAccessPointMBean nap, boolean value) {
      if (nap.isTunnelingEnabled() && nap.isHttpEnabledForThisProtocol() && !value) {
         throw new IllegalArgumentException("Can't disable HTTP as tunneling is enabled for channel: " + nap.getName());
      }
   }

   public static void validateTunnelingEnabled(NetworkAccessPointMBean nap, boolean value) {
      if (!nap.isHttpEnabledForThisProtocol() && !nap.isTunnelingEnabled() && value) {
         throw new IllegalArgumentException("Can't enable tunneling as HTTP is disabled for channel: " + nap.getName());
      }
   }

   private static boolean anyCustomIdentityKeyStoreSet(NetworkAccessPointMBean nap) {
      return nap.isSet("CustomIdentityKeyStoreFileName") || nap.isSet("CustomIdentityKeyStoreType") || nap.isSet("CustomIdentityKeyStorePassPhrase") || nap.isSet("CustomIdentityKeyStorePassPhraseEncrypted");
   }

   private static boolean allCustomIdentityKeyStoreSet(NetworkAccessPointMBean nap) {
      return nap.isSet("CustomIdentityKeyStoreFileName") && nap.isSet("CustomIdentityKeyStoreType") && nap.isSet("CustomIdentityKeyStorePassPhrase") && nap.isSet("CustomIdentityKeyStorePassPhraseEncrypted");
   }

   public static void validateNetworkAccessPoint(NetworkAccessPointMBean nap) throws IllegalArgumentException {
      Loggable loggable = null;
      if (anyCustomIdentityKeyStoreSet(nap)) {
         if (!allCustomIdentityKeyStoreSet(nap)) {
            loggable = SecurityLogger.logChannelIdentityKeyStoreIncompleteLoggable();
         } else if (!nap.isChannelIdentityCustomized()) {
            loggable = SecurityLogger.logChannelIdentityKeyStoreInactiveLoggable();
         } else if (!nap.isSet("CustomPrivateKeyAlias")) {
            loggable = SecurityLogger.logChannelIdentityKeyStoreMissingAliasLoggable();
         }
      }

      if (null != loggable) {
         loggable.log();
         throw new IllegalArgumentException(loggable.getMessage());
      }
   }
}
