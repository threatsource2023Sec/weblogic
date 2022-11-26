package com.bea.logging;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.utils.collections.ConcurrentHashSet;

public class MsgIdPrefixConverter {
   private static final String SYSTEM_PROP = "com.oracle.weblogic.logging.MsgIdPrefixCompatibilityModeEnabled";
   private static final String SYSTEM_RESOURCE = "com/oracle/weblogic/i18n/UpdatedBEAPrefixCatalog.properties";
   private static final String UPDATE_PREFIXES_KEY = "updatedPrefixes";
   private static final boolean DEBUG = false;
   private static Set msgIdPrefixes = new ConcurrentHashSet();
   private static boolean initialized = false;
   private static boolean compatibilityModeEnabled = true;

   public static String convertMsgIdPrefix(String msgIdPrefix) {
      ensureInitialized();
      if (isCompatibilityModeEnabled()) {
         if (msgIdPrefixes.contains(msgIdPrefix)) {
            return "BEA";
         }
      } else if (msgIdPrefix.equals("BEA")) {
         return "WL";
      }

      return msgIdPrefix;
   }

   private static void ensureInitialized() {
      if (!initialized) {
         registerMsgIdPrefix("WL");
         BufferedInputStream input = null;

         try {
            Enumeration e = ClassLoader.getSystemResources("com/oracle/weblogic/i18n/UpdatedBEAPrefixCatalog.properties");

            while(e.hasMoreElements()) {
               URL resource = (URL)e.nextElement();
               input = new BufferedInputStream(resource.openStream());
               Properties props = new Properties();
               props.load(input);
               String prefixes = (String)props.get("updatedPrefixes");
               setMsgIdPrefixes(prefixes);
            }
         } catch (IOException var13) {
         } finally {
            initialized = true;
            if (input != null) {
               try {
                  input.close();
               } catch (IOException var12) {
               }
            }

         }

      }
   }

   private static void setMsgIdPrefixes(String prefixes) {
      if (prefixes != null && !prefixes.equals("")) {
         StringTokenizer st = new StringTokenizer(prefixes, ",");

         while(st.hasMoreTokens()) {
            String prefix = st.nextToken();
            registerMsgIdPrefix(prefix);
         }

      }
   }

   public static void registerMsgIdPrefix(String prefix) {
      msgIdPrefixes.add(prefix);
   }

   public static boolean isCompatibilityModeEnabled() {
      if (MsgIdPrefixConverter.SystemPropertyInitializer.systemPropertySet) {
         compatibilityModeEnabled = MsgIdPrefixConverter.SystemPropertyInitializer.systemPropertyValue;
      }

      return compatibilityModeEnabled;
   }

   public static void setCompatibilityModeEnabled(boolean enabled) {
      if (!MsgIdPrefixConverter.SystemPropertyInitializer.systemPropertySet) {
         compatibilityModeEnabled = enabled;
      }

   }

   public static String getDefaultMsgIdPrefix() {
      return compatibilityModeEnabled ? "BEA" : "WL";
   }

   public static String getDefaultMsgId() {
      return compatibilityModeEnabled ? "BEA-000000" : "WL-000000";
   }

   public static void main(String[] args) {
      ensureInitialized();
   }

   private static final class SystemPropertyInitializer {
      private static final boolean systemPropertySet = System.getProperty("com.oracle.weblogic.logging.MsgIdPrefixCompatibilityModeEnabled") != null;
      private static final boolean systemPropertyValue = Boolean.getBoolean("com.oracle.weblogic.logging.MsgIdPrefixCompatibilityModeEnabled");
   }
}
