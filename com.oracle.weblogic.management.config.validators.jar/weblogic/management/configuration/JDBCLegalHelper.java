package weblogic.management.configuration;

import weblogic.jdbc.common.internal.JDBCConstants;
import weblogic.utils.FileUtils;

public final class JDBCLegalHelper extends JDBCConstants {
   private static final String DEFAULT_APPENDIX = "-jdbc.xml";

   public static String constructDefaultJDBCSystemFilename(String name, SystemResourceMBean mbean) {
      name = name.trim();
      if (name.endsWith("-jdbc")) {
         name = name.substring(0, name.length() - 5);
      }

      return "jdbc/" + FileUtils.mapNameToFileName(name) + "-jdbc.xml";
   }
}
