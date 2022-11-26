package weblogic.management.rest.lib.bean.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportedVersions {
   private static final Map versionNumbers = new HashMap();
   private static final List versionNames = new ArrayList();
   public static final int NO_VERSION_NUMBER = -1;
   public static final int EARLIEST_VERSION_NUMBER = 0;
   public static final int LATEST_VERSION_NUMBER;
   public static final String LATEST_VERSION_NAME;

   private static void registerVersion(String name) {
      versionNumbers.put(name, versionNames.size());
      versionNames.add(name);
   }

   private static void aliasVersion(String name, String alias) {
      versionNumbers.put(alias, versionNumbers.get(name));
   }

   public static int getVersionNumber(String versionName) {
      Integer version = (Integer)versionNumbers.get(versionName);
      return version == null ? -1 : version;
   }

   public static List getVersionNames() {
      return Collections.unmodifiableList(versionNames);
   }

   static {
      registerVersion("12.2.1.0.0");
      aliasVersion("12.2.1.0.0", "12.2.1.0");
      registerVersion("12.2.1.1.0");
      registerVersion("12.2.1.2.0");
      registerVersion("12.2.1.3.0");
      registerVersion("12.2.1.4.0");
      registerVersion("14.1.1.0.0");
      LATEST_VERSION_NUMBER = versionNames.size() - 1;
      LATEST_VERSION_NAME = (String)versionNames.get(LATEST_VERSION_NUMBER);
      aliasVersion(LATEST_VERSION_NAME, "latest");
   }
}
