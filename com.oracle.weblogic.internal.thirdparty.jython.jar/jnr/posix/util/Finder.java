package jnr.posix.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import jnr.posix.FileStat;
import jnr.posix.POSIX;

public class Finder {
   private static final Collection EXECUTABLE_EXTENSIONS = Collections.unmodifiableSet(new HashSet(Arrays.asList(".exe", ".com", ".cmd", ".bat")));

   public static String findFileInPath(POSIX posix, String name, String path) {
      if (path == null || path.length() == 0) {
         path = System.getenv("PATH");
      }

      return path != null && path.length() != 0 ? findFileCommon(posix, name, path, true) : name;
   }

   public static String findFileCommon(POSIX posix, String name, String path, boolean executableOnly) {
      if (name != null && name.length() != 0) {
         int length = name.length();
         boolean isAbsolute = false;
         boolean isPath = false;
         int i = 0;
         if (Platform.IS_WINDOWS) {
            if (length > 1 && Character.isLetter(name.charAt(0)) && name.charAt(1) == ':') {
               i = 2;
               isAbsolute = true;
            }

            int extensionIndex = -1;
            char c = name.charAt(i);
            if (i == 47 || i == 92) {
               ++i;
               c = name.charAt(i);
               isAbsolute = true;
            }

            while(i < length) {
               switch (c) {
                  case '.':
                     extensionIndex = i - 1;
                     break;
                  case '/':
                  case '\\':
                     isPath = true;
                     extensionIndex = -1;
               }

               c = name.charAt(i);
               ++i;
            }

            if (extensionIndex >= 0 && !EXECUTABLE_EXTENSIONS.contains(name.substring(extensionIndex).toLowerCase())) {
               extensionIndex = -1;
            }

            if (!executableOnly) {
               if (isAbsolute) {
                  return name;
               }
            } else if (isPath) {
               if (extensionIndex >= 0) {
                  return name;
               }

               if (executableOnly) {
                  return addExtension(name);
               }

               if ((new File(name)).exists()) {
                  return name;
               }

               return null;
            }

            String[] paths = path.split(File.pathSeparator);

            for(int p = 0; p < paths.length; ++p) {
               String currentPath = paths[p];
               int currentPathLength = currentPath.length();
               if (currentPath != null && currentPathLength != 0) {
                  String filename;
                  if (currentPath.charAt(0) == '~' && (currentPathLength == 1 || currentPathLength > 1 && (currentPath.charAt(1) == '/' || currentPath.charAt(1) == '\\'))) {
                     filename = System.getenv("HOME");
                     if (filename != null) {
                        currentPath = filename + (currentPathLength == 1 ? "" : currentPath.substring(1));
                     }
                  }

                  if (!currentPath.endsWith("/") && !currentPath.endsWith("\\")) {
                     currentPath = currentPath + "\\";
                  }

                  filename = currentPath + name;
                  if (Platform.IS_WINDOWS) {
                     filename = filename.replace('/', '\\');
                  }

                  if (Platform.IS_WINDOWS && executableOnly && extensionIndex == -1) {
                     String extendedFilename = addExtension(filename);
                     if (extendedFilename != null) {
                        return extendedFilename;
                     }
                  } else {
                     try {
                        FileStat stat = posix.stat(filename);
                        if (!executableOnly || !stat.isDirectory() && stat.isExecutable()) {
                           return filename;
                        }
                     } catch (Throwable var16) {
                     }
                  }
               }
            }
         }

         return null;
      } else {
         return name;
      }
   }

   public static String addExtension(String path) {
      Iterator var1 = EXECUTABLE_EXTENSIONS.iterator();

      String newPath;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         String extension = (String)var1.next();
         newPath = path + extension;
      } while(!(new File(newPath)).exists());

      return newPath;
   }
}
