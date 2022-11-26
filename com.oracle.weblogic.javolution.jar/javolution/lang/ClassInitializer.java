package javolution.lang;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javolution.util.StandardLog;

public class ClassInitializer {
   private ClassInitializer() {
   }

   public static void initializeAll() {
      initializeRuntime();
      initializeClassPath();
   }

   public static void initializeRuntime() {
      String var0 = System.getProperty("sun.boot.class.path");
      String var1 = System.getProperty("path.separator");
      if (var0 != null && var1 != null) {
         initialize(var0, var1);
         String var2 = System.getProperty("java.home");
         String var3 = System.getProperty("file.separator");
         if (var2 != null && var3 != null) {
            File var4 = new File(var2 + var3 + "lib" + var3 + "ext");
            if (!var4.getClass().getName().equals("java.io.File")) {
               StandardLog.warning("Extension classes initialization not supported for J2ME build");
            } else {
               if (var4.isDirectory()) {
                  File[] var5 = var4.listFiles();

                  for(int var6 = 0; var6 < var5.length; ++var6) {
                     String var7 = var5[var6].getPath();
                     if (var7.endsWith(".jar") || var7.endsWith(".zip")) {
                        initializeJar(var7);
                     }
                  }
               } else {
                  StandardLog.warning(var4 + " is not a directory");
               }

            }
         } else {
            StandardLog.warning("Cannot initialize extension library through system properties");
         }
      } else {
         StandardLog.warning("Cannot initialize boot path through system properties");
      }
   }

   public static void initializeClassPath() {
      String var0 = System.getProperty("java.class.path");
      String var1 = System.getProperty("path.separator");
      if (var0 != null && var1 != null) {
         initialize(var0, var1);
      } else {
         StandardLog.warning("Cannot initialize classpath through system properties");
      }
   }

   private static void initialize(String var0, String var1) {
      StandardLog.fine("Initialize classpath: " + var0);

      while(true) {
         while(var0.length() > 0) {
            int var3 = var0.indexOf(var1);
            String var2;
            if (var3 < 0) {
               var2 = var0;
               var0 = "";
            } else {
               var2 = var0.substring(0, var3);
               var0 = var0.substring(var3 + var1.length());
            }

            if (!var2.endsWith(".jar") && !var2.endsWith(".zip")) {
               initializeDir(var2);
            } else {
               initializeJar(var2);
            }
         }

         return;
      }
   }

   public static void initialize(Class var0) {
      Reflection.getClass(var0.getName());
   }

   public static void initialize(String var0) {
      Reflection.getClass(var0);
   }

   public static void initializeJar(String var0) {
      try {
         StandardLog.fine("Initialize Jar file: " + var0);
         ZipFile var1 = new ZipFile(var0);
         if (!var1.getClass().getName().equals("java.util.zip.ZipFile")) {
            StandardLog.warning("Initialization of classes in jar file not supported for J2ME build");
            return;
         }

         Enumeration var2 = var1.entries();

         while(var2.hasMoreElements()) {
            ZipEntry var3 = (ZipEntry)var2.nextElement();
            String var4 = var3.getName();
            if (var4.endsWith(".class")) {
               String var5 = var4.substring(0, var4.length() - 6);
               var5 = var5.replace('/', '.');
               Class var6 = Reflection.getClass(var5);
               if (var6 != null) {
                  StandardLog.finer(var5 + " initialized");
               }
            }
         }
      } catch (Exception var7) {
         StandardLog.error(var7);
      }

   }

   public static void initializeDir(String var0) {
      StandardLog.fine("Initialize Directory: " + var0);
      File var1 = new File(var0);
      if (!var1.getClass().getName().equals("java.io.File")) {
         StandardLog.warning("Initialization of classes in directory not supported for J2ME build");
      } else {
         if (var1.isDirectory()) {
            File[] var2 = var1.listFiles();

            for(int var3 = 0; var3 < var2.length; ++var3) {
               initialize("", var2[var3]);
            }
         } else {
            StandardLog.warning(var0 + " is not a directory");
         }

      }
   }

   private static void initialize(String var0, File var1) {
      String var2 = var1.getName();
      if (var1.isDirectory()) {
         File[] var3 = var1.listFiles();
         String var4 = var0.length() == 0 ? var2 : var0 + "." + var2;

         for(int var5 = 0; var5 < var3.length; ++var5) {
            initialize(var4, var3[var5]);
         }
      } else if (var2.endsWith(".class")) {
         String var6 = var0 + "." + var2.substring(0, var2.length() - 6);
         Class var7 = Reflection.getClass(var6);
         if (var7 != null) {
            StandardLog.finer(var6 + " initialized");
         }
      }

   }
}
