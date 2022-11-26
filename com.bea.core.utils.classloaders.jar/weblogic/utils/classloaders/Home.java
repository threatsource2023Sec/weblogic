package weblogic.utils.classloaders;

import java.io.File;

public class Home {
   private final String path;

   public Home() {
      String p = System.getProperty("weblogic.home");
      String version = null;
      if (p == null) {
         String file = this.getFileSource("weblogic.utils.classloaders.Home");
         if (file != null) {
            if (file.endsWith(".jar")) {
               File f = (new File(file)).getParentFile().getParentFile().getParentFile();
               p = f.getPath();
               p = p.replace('\\', '/');
               if (p.endsWith("/wlserver")) {
                  p = p + "/server";
               } else {
                  p = p + "/wlserver/server";
               }
            }

            file = null;
         }

         if (file != null) {
            file = file.replace('\\', '/');
            int fileIndex = file.lastIndexOf("/wlserver/modules/");
            if (fileIndex > -1) {
               p = file.substring(0, fileIndex) + "/wlserver/server";
            }
         }
      }

      this.path = p;
   }

   private String getClassPath() {
      return System.getProperty("java.class.path");
   }

   private String getFileSource(String name) {
      ClasspathClassFinder2 finder = null;

      String var4;
      try {
         finder = new ClasspathClassFinder2(this.getClassPath());
         Object source = finder.getClassSource(name);
         if (source instanceof FileSource) {
            var4 = ((FileSource)source).getFile().toString().replace(File.separatorChar, '/');
            return var4;
         }

         if (!(source instanceof ZipSource)) {
            var4 = null;
            return var4;
         }

         var4 = ((ZipSource)source).getFile().getName().replace(File.separatorChar, '/');
      } finally {
         if (finder != null) {
            finder.close();
         }

      }

      return var4;
   }

   private static Home getInstance() {
      Home home = Home.HomeSingleton.SINGLETON;
      if (home.path == null) {
         throw new RuntimeException("error in finding weblogic.Home");
      } else {
         return home;
      }
   }

   public static String getPath() {
      Home home = getInstance();
      return home.path;
   }

   public static File getFile() {
      return new File(getPath());
   }

   public static String getMiddlewareHomePath() {
      return BeaHomeHolder.getBeaHome();
   }

   public String toString() {
      return getPath();
   }

   public static void main(String[] argv) {
      System.out.println(getPath());
   }

   private static final class HomeSingleton {
      private static final Home SINGLETON = new Home();
   }
}
