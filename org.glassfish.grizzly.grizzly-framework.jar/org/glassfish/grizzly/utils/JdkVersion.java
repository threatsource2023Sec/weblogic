package org.glassfish.grizzly.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.grizzly.Grizzly;

public class JdkVersion implements Comparable {
   private static final Logger LOGGER = Grizzly.logger(JdkVersion.class);
   private static final Pattern VERSION_PATTERN = Pattern.compile("([0-9]+)(\\.([0-9]+))?(\\.([0-9]+))?([_\\.]([0-9]+))?.*");
   private static final JdkVersion UNKNOWN_VERSION = new JdkVersion(-1, -1, -1, -1);
   private static final JdkVersion JDK_VERSION = parseVersion(System.getProperty("java.version"));
   private final int major;
   private final int minor;
   private final int maintenance;
   private final int update;

   private JdkVersion(int major, int minor, int maintenance, int update) {
      this.major = major;
      this.minor = minor;
      this.maintenance = maintenance;
      this.update = update;
   }

   public static JdkVersion parseVersion(String versionString) {
      try {
         Matcher matcher = VERSION_PATTERN.matcher(versionString);
         if (matcher.matches()) {
            return new JdkVersion(parseInt(matcher.group(1)), parseInt(matcher.group(3)), parseInt(matcher.group(5)), parseInt(matcher.group(7)));
         }

         LOGGER.log(Level.FINE, "Can't parse the JDK version {0}", versionString);
      } catch (Exception var2) {
         LOGGER.log(Level.FINE, "Error parsing the JDK version " + versionString, var2);
      }

      return UNKNOWN_VERSION;
   }

   public static JdkVersion getJdkVersion() {
      return JDK_VERSION;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getMaintenance() {
      return this.maintenance;
   }

   public int getUpdate() {
      return this.update;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("JdkVersion");
      sb.append("{major=").append(this.major);
      sb.append(", minor=").append(this.minor);
      sb.append(", maintenance=").append(this.maintenance);
      sb.append(", update=").append(this.update);
      sb.append('}');
      return sb.toString();
   }

   public int compareTo(String versionString) {
      return this.compareTo(parseVersion(versionString));
   }

   public int compareTo(JdkVersion otherVersion) {
      if (this.major < otherVersion.major) {
         return -1;
      } else if (this.major > otherVersion.major) {
         return 1;
      } else if (this.minor < otherVersion.minor) {
         return -1;
      } else if (this.minor > otherVersion.minor) {
         return 1;
      } else if (this.maintenance < otherVersion.maintenance) {
         return -1;
      } else if (this.maintenance > otherVersion.maintenance) {
         return 1;
      } else if (this.update < otherVersion.update) {
         return -1;
      } else {
         return this.update > otherVersion.update ? 1 : 0;
      }
   }

   private static int parseInt(String s) {
      return s != null ? Integer.parseInt(s) : 0;
   }
}
