package weblogic.management.commo;

import java.io.File;
import java.io.FilenameFilter;

class XMLFileNameFilter implements FilenameFilter {
   public boolean accept(File dir, String name) {
      return name.endsWith(".xml");
   }
}
