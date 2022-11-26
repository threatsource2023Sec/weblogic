package weblogic.security.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.security.SecurityLogger;

public final class FileUtils {
   private static final boolean DEBUG = false;

   private static void debug(String msg) {
   }

   private static void error(String msg, Throwable t) throws FileUtilsException {
      throw new FileUtilsException(msg, t);
   }

   private static void error(String msg) throws FileUtilsException {
      throw new FileUtilsException(msg);
   }

   private static void writeFile(File f, FileWriter writer) throws FileUtilsException {
      String methodName = null;

      try {
         boolean ok = false;
         FileOutputStream os = new FileOutputStream(f);

         try {
            writer.write(os);
            ok = true;
         } finally {
            try {
               os.close();
            } catch (IOException var13) {
            }

            if (!ok) {
               f.delete();
            }

         }
      } catch (FileNotFoundException var15) {
         error(SecurityLogger.getErrorCreatingFile(f.getAbsolutePath()), var15);
      } catch (IOException var16) {
         error(SecurityLogger.getErrorWritingRealmContents(f.getAbsolutePath()), var16);
      }

   }

   public static void replace(String pathName, FileWriter writer) throws FileUtilsException {
      String methodName = null;
      File realFileForInfo = new File(pathName);
      String fileName = realFileForInfo.getName();
      File directory = realFileForInfo.getAbsoluteFile().getParentFile();
      File tempFileForNewInfo = null;

      try {
         tempFileForNewInfo = File.createTempFile(fileName, ".new", directory);
      } catch (IOException var10) {
         error(SecurityLogger.getCouldNotCreateTempFileNew(fileName, directory.getAbsolutePath()), var10);
      }

      writeFile(tempFileForNewInfo, writer);
      File tempFileForOldInfo = null;
      if (realFileForInfo.exists()) {
         try {
            tempFileForOldInfo = File.createTempFile(fileName, ".old", directory);
         } catch (IOException var9) {
            error(SecurityLogger.getCouldNotCreateTempFileOld(fileName, directory.getAbsolutePath()), var9);
         }

         if (!tempFileForOldInfo.delete()) {
            error(SecurityLogger.getCouldNotClearTempFile(tempFileForOldInfo.getAbsolutePath()));
         }
      }

      if (tempFileForOldInfo != null && !realFileForInfo.renameTo(tempFileForOldInfo)) {
         error(SecurityLogger.getCouldNotRenameTempFile(realFileForInfo.getAbsolutePath(), tempFileForOldInfo.getAbsolutePath()));
      }

      if (!tempFileForNewInfo.renameTo(realFileForInfo)) {
         error(SecurityLogger.getCouldNotRenameTempFile(tempFileForNewInfo.getAbsolutePath(), realFileForInfo.getAbsolutePath()));
      }

      if (tempFileForOldInfo != null && !tempFileForOldInfo.delete()) {
         error(SecurityLogger.getCouldNotDeleteTempFile("Couldn't delete " + tempFileForOldInfo.getAbsolutePath()));
      }

   }
}
