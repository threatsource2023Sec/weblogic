package weblogic.servlet.ejb2jsp.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class EJB2JSPFileChooser extends FileFilter {
   public boolean accept(File f) {
      if (f != null) {
         if (f.isDirectory()) {
            return true;
         }

         String name = f.getName();
         if (name != null) {
            return name.endsWith(".ejb2jsp");
         }
      }

      return false;
   }

   public String getDescription() {
      return "ejb2jsp project files";
   }
}
