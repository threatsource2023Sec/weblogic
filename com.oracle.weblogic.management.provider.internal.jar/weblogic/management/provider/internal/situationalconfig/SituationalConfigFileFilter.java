package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import java.io.FileFilter;

public class SituationalConfigFileFilter implements FileFilter {
   public static final String SITUATIONAL_CONFIG_FILENAME_SUFFIX = "situational-config.xml";

   public boolean accept(File pathname) {
      return pathname.getName().endsWith("situational-config.xml");
   }
}
