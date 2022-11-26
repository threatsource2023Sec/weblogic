package utils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WhichClass {
   private Hashtable m_zipCache = new Hashtable();
   private static String pathSep;

   public WhichClass(String clas) {
      String res = clas.replace('.', '/') + ".class";
      this.listResources(res);
   }

   public static void main(String[] args) {
      new WhichClass(args[0]);
   }

   protected void listResources(String name) {
      String res = null;
      String classPath = System.getProperty("java.class.path");
      int beginIndex = 0;
      int endIndex = classPath.indexOf(pathSep);

      while(true) {
         String element = "";
         if (endIndex == -1) {
            element = classPath.substring(beginIndex);
         } else {
            element = classPath.substring(beginIndex, endIndex);
         }

         res = this.openResource(name, element);
         if (res != null) {
            System.out.println(res);
         }

         if (endIndex == -1) {
            return;
         }

         beginIndex = endIndex + 1;
         endIndex = classPath.indexOf(pathSep, beginIndex);
      }
   }

   protected String openResource(String name, String path) {
      String result = null;

      try {
         String lPath = path.toLowerCase();
         if (!lPath.endsWith(".zip") && !lPath.endsWith(".jar")) {
            String fullName = path;
            if (!path.endsWith("\\") && !path.endsWith("/")) {
               fullName = path + "/";
            }

            fullName = fullName + name;
            File f = new File(fullName);
            if (f.exists() && f.isFile()) {
               result = fullName;
            }
         } else {
            result = this.openResourceFromJar(name, path);
         }
      } catch (Exception var7) {
      }

      return result;
   }

   protected String openResourceFromJar(String name, String jarFile) {
      String result = null;
      ZipFile zip = null;
      if ((zip = (ZipFile)this.m_zipCache.get(jarFile)) == null) {
         File f = new File(jarFile);
         if (f.exists() && f.isFile()) {
            try {
               zip = new ZipFile(f);
               this.m_zipCache.put(jarFile, zip);
            } catch (IOException var7) {
               return null;
            }
         }
      }

      if (zip != null) {
         ZipEntry entry = zip.getEntry(name);
         if (entry != null) {
            result = name;
         }
      }

      return result != null ? jarFile + "[" + result + "]" : null;
   }

   static {
      pathSep = File.pathSeparator;
   }
}
