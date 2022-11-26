package org.python;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;
import org.python.core.CodeFlag;

public class Version {
   public static String PY_VERSION;
   public static int PY_MAJOR_VERSION;
   public static int PY_MINOR_VERSION;
   public static int PY_MICRO_VERSION;
   public static int PY_RELEASE_LEVEL;
   public static int PY_RELEASE_SERIAL;
   public static String DATE;
   public static String TIME;
   public static String HG_BRANCH;
   public static String HG_TAG;
   public static String HG_VERSION;
   private static final Collection defaultCodeFlags;

   private static void loadProperties() {
      boolean loaded = false;
      String versionProperties = "/org/python/version.properties";
      InputStream in = Version.class.getResourceAsStream("/org/python/version.properties");
      if (in != null) {
         try {
            Properties properties = new Properties();
            properties.load(in);
            loaded = true;
            PY_VERSION = properties.getProperty("jython.version");
            PY_MAJOR_VERSION = Integer.valueOf(properties.getProperty("jython.major_version"));
            PY_MINOR_VERSION = Integer.valueOf(properties.getProperty("jython.minor_version"));
            PY_MICRO_VERSION = Integer.valueOf(properties.getProperty("jython.micro_version"));
            PY_RELEASE_LEVEL = Integer.valueOf(properties.getProperty("jython.release_level"));
            PY_RELEASE_SERIAL = Integer.valueOf(properties.getProperty("jython.release_serial"));
            DATE = properties.getProperty("jython.build.date");
            TIME = properties.getProperty("jython.build.time");
            HG_BRANCH = properties.getProperty("jython.build.hg_branch");
            HG_TAG = properties.getProperty("jython.build.hg_tag");
            HG_VERSION = properties.getProperty("jython.build.hg_version");
         } catch (IOException var12) {
            System.err.println("There was a problem loading ".concat("/org/python/version.properties").concat(":"));
            var12.printStackTrace();
         } finally {
            try {
               in.close();
            } catch (IOException var11) {
            }

         }
      }

      if (!loaded) {
         throw new RuntimeException("unable to load ".concat("/org/python/version.properties"));
      }
   }

   public static String getHGVersion() {
      return HG_VERSION;
   }

   public static String getHGIdentifier() {
      return !"".equals(HG_TAG) && !"tip".equals(HG_TAG) ? HG_TAG : HG_BRANCH;
   }

   public static String getBuildInfo() {
      String revision = getHGVersion();
      String sep = "".equals(revision) ? "" : ":";
      String hgId = getHGIdentifier();
      return String.format("%s%s%s, %.20s, %.9s", hgId, sep, revision, DATE, TIME);
   }

   public static String getVM() {
      return String.format("\n[%s (%s)]", System.getProperty("java.vm.name"), System.getProperty("java.vm.vendor"));
   }

   public static String getVersion() {
      return String.format("%.80s (%.80s) %.80s", PY_VERSION, getBuildInfo(), getVM());
   }

   public static Set getDefaultCodeFlags() {
      return EnumSet.copyOf(defaultCodeFlags);
   }

   static {
      defaultCodeFlags = Arrays.asList(CodeFlag.CO_NESTED, CodeFlag.CO_GENERATOR_ALLOWED, CodeFlag.CO_FUTURE_WITH_STATEMENT);
      loadProperties();
   }
}
