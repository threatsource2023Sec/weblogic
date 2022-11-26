package weblogic.utils.classloaders;

import java.io.File;
import weblogic.utils.FileUtils;
import weblogic.utils.PlatformConstants;
import weblogic.utils.io.ExtensionFilter;

public final class LibDirClassFinder extends ClasspathClassFinder2 {
   public LibDirClassFinder(File root, String classesPath, String libPath) {
      super(getClassPath(root, classesPath, libPath));
   }

   private static String getClassPath(File root, String classesPath, String libPath) {
      StringBuffer cp = new StringBuffer();
      if (root.exists()) {
         File classesRoot = new File(root, classesPath);
         if (classesRoot.exists() && classesRoot.isDirectory()) {
            appendToPath(classesRoot, cp);
         }

         File libRoot = new File(root, libPath);
         if (libRoot.exists() && libRoot.isDirectory()) {
            File[] jars = FileUtils.find(libRoot, new ExtensionFilter("jar"));

            for(int i = 0; i < jars.length; ++i) {
               appendToPath(jars[i], cp);
            }
         }
      }

      return cp.toString();
   }

   private static void appendToPath(File f, StringBuffer cp) {
      if (cp.length() > 0) {
         cp.append(PlatformConstants.PATH_SEP);
      }

      cp.append(f.getPath());
   }
}
