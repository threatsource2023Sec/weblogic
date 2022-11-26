package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.ClosedByInterruptException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class JRTUtil {
   public static final String JAVA_BASE = "java.base";
   public static final char[] JAVA_BASE_CHAR = "java.base".toCharArray();
   static final String MODULES_SUBDIR = "/modules";
   static final String[] DEFAULT_MODULE = new String[]{"java.base"};
   static final String[] NO_MODULE = new String[0];
   static final String MULTIPLE = "MU";
   static final String DEFAULT_PACKAGE = "";
   static String MODULE_TO_LOAD = null;
   public static final String JRT_FS_JAR = "jrt-fs.jar";
   static URI JRT_URI = URI.create("jrt:/");
   public static int NOTIFY_FILES = 1;
   public static int NOTIFY_PACKAGES = 2;
   public static int NOTIFY_MODULES = 4;
   public static int NOTIFY_ALL;
   private static Map images;
   private static final Object lock;

   static {
      NOTIFY_ALL = NOTIFY_FILES | NOTIFY_PACKAGES | NOTIFY_MODULES;
      images = null;
      lock = new Object();
   }

   public static JrtFileSystem getJrtSystem(File image) {
      return getJrtSystem(image, (String)null);
   }

   public static JrtFileSystem getJrtSystem(File image, String release) {
      Map i = images;
      if (images == null) {
         synchronized(lock) {
            i = images;
            if (i == null) {
               images = (Map)(i = new HashMap());
            }
         }
      }

      JrtFileSystem system = null;
      String key = image.toString();
      if (release != null) {
         key = key + "|" + release;
      }

      synchronized(i) {
         if ((system = (JrtFileSystem)images.get(key)) == null) {
            try {
               images.put(key, system = JrtFileSystem.getNewJrtFileSystem(image, release));
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }

         return system;
      }
   }

   public static void reset() {
      images = null;
      MODULE_TO_LOAD = System.getProperty("modules.to.load");
   }

   public static void walkModuleImage(File image, JrtFileVisitor visitor, int notify) throws IOException {
      getJrtSystem(image, (String)null).walkModuleImage(visitor, notify);
   }

   public static void walkModuleImage(File image, String release, JrtFileVisitor visitor, int notify) throws IOException {
      getJrtSystem(image, release).walkModuleImage(visitor, notify);
   }

   public static InputStream getContentFromJrt(File jrt, String fileName, String module) throws IOException {
      return getJrtSystem(jrt).getContentFromJrt(fileName, module);
   }

   public static byte[] getClassfileContent(File jrt, String fileName, String module) throws IOException, ClassFormatException {
      return getJrtSystem(jrt).getClassfileContent(fileName, module);
   }

   public static ClassFileReader getClassfile(File jrt, String fileName, IModule module) throws IOException, ClassFormatException {
      return getJrtSystem(jrt).getClassfile(fileName, module);
   }

   public static ClassFileReader getClassfile(File jrt, String fileName, String module, Predicate moduleNameFilter) throws IOException, ClassFormatException {
      return getJrtSystem(jrt).getClassfile(fileName, module, moduleNameFilter);
   }

   public static List getModulesDeclaringPackage(File jrt, String qName, String moduleName) {
      return getJrtSystem(jrt).getModulesDeclaringPackage(qName, moduleName);
   }

   public static boolean hasCompilationUnit(File jrt, String qualifiedPackageName, String moduleName) {
      return getJrtSystem(jrt).hasClassFile(qualifiedPackageName, moduleName);
   }

   public static String sanitizedFileName(Path path) {
      String p = path.getFileName().toString();
      return p.length() > 1 && p.charAt(p.length() - 1) == '/' ? p.substring(0, p.length() - 1) : p;
   }

   public static byte[] safeReadBytes(Path path) throws IOException {
      try {
         return Files.readAllBytes(path);
      } catch (ClosedByInterruptException var1) {
         return null;
      } catch (NoSuchFileException var2) {
         return null;
      }
   }

   abstract static class AbstractFileVisitor implements FileVisitor {
      public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
         return FileVisitResult.CONTINUE;
      }
   }

   public interface JrtFileVisitor {
      FileVisitResult visitPackage(Object var1, Object var2, BasicFileAttributes var3) throws IOException;

      FileVisitResult visitFile(Object var1, Object var2, BasicFileAttributes var3) throws IOException;

      FileVisitResult visitModule(Object var1, String var2) throws IOException;
   }
}
