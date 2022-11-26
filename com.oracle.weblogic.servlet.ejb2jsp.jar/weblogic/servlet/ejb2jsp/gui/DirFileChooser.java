package weblogic.servlet.ejb2jsp.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class DirFileChooser extends FileFilter {
   public boolean accept(File f) {
      return f.isDirectory();
   }

   public String getDescription() {
      return "Directories";
   }
}
