package org.python.jline.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Configuration {
   public static final String JLINE_CONFIGURATION = "org.python.jline.configuration";
   public static final String JLINE_RC = ".jline.rc";
   private static volatile Properties properties;

   private static Properties initProperties() {
      URL url = determineUrl();
      Properties props = new Properties();

      try {
         loadProperties(url, props);
      } catch (FileNotFoundException var3) {
         Log.debug("Unable to read configuration: ", var3.toString());
      } catch (IOException var4) {
         Log.warn("Unable to read configuration from: ", url, var4);
      }

      return props;
   }

   private static void loadProperties(URL url, Properties props) throws IOException {
      Log.debug("Loading properties from: ", url);
      InputStream input = url.openStream();

      try {
         props.load(new BufferedInputStream(input));
      } finally {
         try {
            input.close();
         } catch (IOException var9) {
         }

      }

      if (Log.DEBUG) {
         Log.debug("Loaded properties:");
         Iterator var3 = props.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            Log.debug("  ", entry.getKey(), "=", entry.getValue());
         }
      }

   }

   private static URL determineUrl() {
      String tmp = System.getProperty("org.python.jline.configuration");
      if (tmp != null) {
         return Urls.create(tmp);
      } else {
         File file = new File(getUserHome(), ".jline.rc");
         return Urls.create(file);
      }
   }

   public static void reset() {
      Log.debug("Resetting");
      properties = null;
      getProperties();
   }

   public static Properties getProperties() {
      if (properties == null) {
         properties = initProperties();
      }

      return properties;
   }

   public static String getString(String name, String defaultValue) {
      Preconditions.checkNotNull(name);
      String value = System.getProperty(name);
      if (value == null) {
         value = getProperties().getProperty(name);
         if (value == null) {
            value = defaultValue;
         }
      }

      return value;
   }

   public static String getString(String name) {
      return getString(name, (String)null);
   }

   public static boolean getBoolean(String name) {
      return getBoolean(name, false);
   }

   public static boolean getBoolean(String name, boolean defaultValue) {
      String value = getString(name);
      if (value == null) {
         return defaultValue;
      } else {
         return value.length() == 0 || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true");
      }
   }

   public static int getInteger(String name, int defaultValue) {
      String str = getString(name);
      return str == null ? defaultValue : Integer.parseInt(str);
   }

   public static long getLong(String name, long defaultValue) {
      String str = getString(name);
      return str == null ? defaultValue : Long.parseLong(str);
   }

   public static String getLineSeparator() {
      return System.getProperty("line.separator");
   }

   public static File getUserHome() {
      return new File(System.getProperty("user.home"));
   }

   public static String getOsName() {
      return System.getProperty("os.name").toLowerCase();
   }

   public static boolean isWindows() {
      return getOsName().startsWith("windows");
   }

   public static boolean isHpux() {
      return getOsName().startsWith("hp");
   }

   public static String getFileEncoding() {
      return System.getProperty("file.encoding");
   }

   public static String getEncoding() {
      String[] var0 = new String[]{"LC_ALL", "LC_CTYPE", "LANG"};
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         String envOption = var0[var2];
         String envEncoding = extractEncodingFromCtype(System.getenv(envOption));
         if (envEncoding != null) {
            try {
               if (Charset.isSupported(envEncoding)) {
                  return envEncoding;
               }
            } catch (IllegalCharsetNameException var6) {
            }
         }
      }

      return getString("input.encoding", Charset.defaultCharset().name());
   }

   static String extractEncodingFromCtype(String ctype) {
      if (ctype != null && ctype.indexOf(46) > 0) {
         String encodingAndModifier = ctype.substring(ctype.indexOf(46) + 1);
         return encodingAndModifier.indexOf(64) > 0 ? encodingAndModifier.substring(0, encodingAndModifier.indexOf(64)) : encodingAndModifier;
      } else {
         return null;
      }
   }
}
