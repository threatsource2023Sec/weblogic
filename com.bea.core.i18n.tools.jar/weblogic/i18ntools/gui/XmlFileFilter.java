package weblogic.i18ntools.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

final class XmlFileFilter extends FileFilter {
   public boolean accept(File fil) {
      return fil.getName().endsWith(".xml") || fil.isDirectory();
   }

   public String getDescription() {
      return "xml files";
   }
}
