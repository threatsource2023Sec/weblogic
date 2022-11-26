package weblogic.management.utils;

import java.io.File;

public class UserFileSystemUtils {
   public static boolean exists(String filePath) {
      return exists(new File(filePath));
   }

   public static boolean exists(File file) {
      return file.exists();
   }

   public static boolean mkdir(String dirPath) {
      return mkdir(new File(dirPath));
   }

   public static boolean mkdir(File dir) {
      return dir.mkdirs();
   }

   public static String[] list(String dirPath) {
      return list(new File(dirPath));
   }

   public static String[] list(File dir) {
      return dir.list();
   }

   public static boolean deleteFile(String filePath) {
      return deleteFile(new File(filePath));
   }

   public static boolean deleteFile(File file) {
      return file.delete();
   }

   public static boolean rmdir(String dirPath) {
      return deleteFile(new File(dirPath));
   }
}
