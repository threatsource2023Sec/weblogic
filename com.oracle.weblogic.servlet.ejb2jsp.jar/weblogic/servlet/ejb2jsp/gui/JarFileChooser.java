package weblogic.servlet.ejb2jsp.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class JarFileChooser extends FileFilter {
   public boolean accept(File f) {
      if (f != null) {
         if (f.isDirectory()) {
            return true;
         }

         String name = f.getName();
         if (name != null) {
            return name.toLowerCase().endsWith(".jar");
         }
      }

      return false;
   }

   public String getDescription() {
      return "EJB Jar Files";
   }
}
