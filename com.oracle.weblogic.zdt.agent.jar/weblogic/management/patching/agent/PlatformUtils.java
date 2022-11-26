package weblogic.management.patching.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PlatformUtils {
   public static final String WINDOWS_SCRIPT_EXT = ".cmd";
   public static final String UNIX_SCRIPT_EXT = ".sh";
   public static boolean isUnix;
   public static boolean isWindows;
   public static final String MW_HOME_ENV = "MW_HOME";
   public static final String JAVA_HOME_ENV = "JAVA_HOME";
   private static String JAVA_HOME;
   private static String MW_HOME;
   public static final String fileSeparator;
   public static final String UNIX = "unix";
   public static final String WINDOWS = "windows";

   public static String getScriptExtension() {
      String ext;
      if (isWindows) {
         ext = ".cmd";
      } else {
         ext = ".sh";
      }

      return ext;
   }

   public static String getMWHomePath() {
      return MW_HOME;
   }

   public static String getJavaHomePath() {
      return JAVA_HOME;
   }

   public static String getPlatformString() {
      String result = null;
      if (isUnix) {
         result = "unix";
      } else if (isWindows) {
         result = "windows";
      }

      return result;
   }

   public static String getJavaBinaryPath() {
      return JAVA_HOME + File.separator + "bin" + File.separator + "java" + (isWindows ? ".exe" : "");
   }

   public static void extractJar(Path baseDir, String jarLocation, String destinationPath) throws Exception {
      Path jarPath = baseDir.resolve(jarLocation);
      if (!jarPath.toFile().exists()) {
         throw new Exception("Jar File " + jarPath.toString() + " does not exist");
      } else {
         File jar = new File(jarPath.toString());
         JarFile jarFile = new JarFile(jar);
         Throwable var6 = null;

         try {
            Enumeration enums = jarFile.entries();

            while(true) {
               JarEntry entry;
               String fileName;
               File f;
               do {
                  do {
                     if (!enums.hasMoreElements()) {
                        return;
                     }

                     entry = (JarEntry)enums.nextElement();
                     fileName = destinationPath + fileSeparator + entry.getName();
                     f = new File(fileName);
                  } while(fileName.endsWith(fileSeparator));
               } while(!f.getParent().endsWith(getPlatformString()));

               String fName = destinationPath + fileSeparator + f.getName();
               File file = new File(fName);

               try {
                  InputStream inputStream = jarFile.getInputStream(entry);
                  Throwable var14 = null;

                  try {
                     FileOutputStream fileOutputStream = new FileOutputStream(file);
                     Throwable var16 = null;

                     try {
                        while(inputStream.available() > 0) {
                           fileOutputStream.write(inputStream.read());
                        }

                        file.setExecutable(true);
                        file.setReadable(true);
                     } catch (Throwable var63) {
                        var16 = var63;
                        throw var63;
                     } finally {
                        if (fileOutputStream != null) {
                           if (var16 != null) {
                              try {
                                 fileOutputStream.close();
                              } catch (Throwable var62) {
                                 var16.addSuppressed(var62);
                              }
                           } else {
                              fileOutputStream.close();
                           }
                        }

                     }
                  } catch (Throwable var65) {
                     var14 = var65;
                     throw var65;
                  } finally {
                     if (inputStream != null) {
                        if (var14 != null) {
                           try {
                              inputStream.close();
                           } catch (Throwable var61) {
                              var14.addSuppressed(var61);
                           }
                        } else {
                           inputStream.close();
                        }
                     }

                  }
               } catch (IOException var67) {
                  throw var67;
               }
            }
         } catch (Throwable var68) {
            var6 = var68;
            throw var68;
         } finally {
            if (jarFile != null) {
               if (var6 != null) {
                  try {
                     jarFile.close();
                  } catch (Throwable var60) {
                     var6.addSuppressed(var60);
                  }
               } else {
                  jarFile.close();
               }
            }

         }
      }
   }

   public static void deleteExtensionScripts(Path baseDir, String jarLocation, String destinationPath, ZdtAgentOutputHandler outputHandler) throws Exception {
      Path jarPath = baseDir.resolve(jarLocation);
      if (!jarPath.toFile().exists()) {
         throw new Exception("Jar File " + jarPath.toString() + " does not exist");
      } else {
         File jar = new File(jarPath.toString());
         JarFile jarFile = new JarFile(jar);
         Throwable var7 = null;

         try {
            Enumeration enums = jarFile.entries();

            while(enums.hasMoreElements()) {
               JarEntry entry = (JarEntry)enums.nextElement();
               String fileName = destinationPath + fileSeparator + entry.getName();
               File f = new File(fileName);
               if (!fileName.endsWith(fileSeparator) && f.getParent().endsWith(getPlatformString())) {
                  String fName = destinationPath + fileSeparator + f.getName();

                  try {
                     File file = new File(fName);
                     if (file.exists()) {
                        outputHandler.info("Deleting file : " + fName);
                        file.delete();
                     }
                  } catch (Exception var22) {
                     throw var22;
                  }
               }
            }
         } catch (Throwable var23) {
            var7 = var23;
            throw var23;
         } finally {
            if (jarFile != null) {
               if (var7 != null) {
                  try {
                     jarFile.close();
                  } catch (Throwable var21) {
                     var7.addSuppressed(var21);
                  }
               } else {
                  jarFile.close();
               }
            }

         }

      }
   }

   static {
      isUnix = File.separatorChar == '/';
      isWindows = File.separatorChar == '\\';
      JAVA_HOME = System.getenv("JAVA_HOME");
      MW_HOME = System.getenv("MW_HOME");
      fileSeparator = File.separator;
   }
}
