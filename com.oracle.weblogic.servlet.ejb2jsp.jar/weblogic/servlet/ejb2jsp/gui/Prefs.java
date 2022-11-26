package weblogic.servlet.ejb2jsp.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Prefs {
   public String compiler;
   public String webapp;
   public String sourceDir;
   private static final String PROP_FILE = ".ejb2jsp.properties";

   public String toString() {
      return "compiler=" + this.compiler + " app=" + this.webapp + " src=" + this.sourceDir;
   }

   public void load() throws IOException {
      String userHome = System.getProperty("user.home");
      File uh = new File(userHome);
      File propsFile = new File(uh, ".ejb2jsp.properties");
      String defaultWebApp = (new File(uh, "webapp")).getAbsolutePath();
      String defaultsrc = (new File(uh, "src")).getAbsolutePath();
      if (propsFile.exists()) {
         InputStream is = new FileInputStream(propsFile);
         Properties p = new Properties();

         try {
            p.load(is);
         } finally {
            is.close();
         }

         this.compiler = p.getProperty("compiler", "javac");
         this.webapp = p.getProperty("webapp", defaultWebApp);
         this.sourceDir = p.getProperty("sourceDir", defaultsrc);
      } else {
         this.compiler = "javac";
         this.webapp = defaultWebApp;
         this.sourceDir = defaultsrc;
      }

   }

   public void save() throws IOException {
      Properties p = new Properties();
      p.setProperty("compiler", this.compiler);
      p.setProperty("webapp", this.webapp);
      p.setProperty("sourceDir", this.sourceDir);
      String userHome = System.getProperty("user.home");
      File uh = new File(userHome);
      File propsFile = new File(uh, ".ejb2jsp.properties");
      OutputStream os = new FileOutputStream(propsFile);

      try {
         p.store(os, "ejb2jsp user preferences");
      } finally {
         os.close();
      }

   }
}
