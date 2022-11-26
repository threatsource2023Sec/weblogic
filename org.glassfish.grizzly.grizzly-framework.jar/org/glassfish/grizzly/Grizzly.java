package org.glassfish.grizzly;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.grizzly.attributes.AttributeBuilder;

public class Grizzly {
   private static final Pattern versionPattern = Pattern.compile("((\\d+)\\.(\\d+)(\\.\\d+)*){1}(?:-(.+))?");
   public static final AttributeBuilder DEFAULT_ATTRIBUTE_BUILDER;
   private static final String dotedVersion;
   private static final int major;
   private static final int minor;
   private static boolean isTrackingThreadCache;

   public static Logger logger(Class clazz) {
      return Logger.getLogger(clazz.getName());
   }

   public static void main(String[] args) {
      System.out.println(getDotedVersion());
   }

   public static String getDotedVersion() {
      return dotedVersion;
   }

   public static int getMajorVersion() {
      return major;
   }

   public static int getMinorVersion() {
      return minor;
   }

   public static boolean equalVersion(int major, int minor) {
      return minor == Grizzly.minor && major == Grizzly.major;
   }

   public static boolean isTrackingThreadCache() {
      return isTrackingThreadCache;
   }

   public static void setTrackingThreadCache(boolean isTrackingThreadCache) {
      Grizzly.isTrackingThreadCache = isTrackingThreadCache;
   }

   static {
      DEFAULT_ATTRIBUTE_BUILDER = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER;
      InputStream is = null;
      Properties prop = new Properties();

      try {
         is = Grizzly.class.getResourceAsStream("version.properties");
         prop.load(is);
      } catch (IOException var11) {
         var11.printStackTrace();
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var10) {
            }
         }

      }

      String version = prop.getProperty("grizzly.version");
      Matcher matcher = versionPattern.matcher(version);
      if (matcher.matches()) {
         dotedVersion = matcher.group(1);
         major = Integer.parseInt(matcher.group(2));
         minor = Integer.parseInt(matcher.group(3));
      } else {
         dotedVersion = "no.version";
         major = -1;
         minor = -1;
      }

   }
}
