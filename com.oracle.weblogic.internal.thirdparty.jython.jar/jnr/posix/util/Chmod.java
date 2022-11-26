package jnr.posix.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Chmod {
   private static final boolean CHMOD_API_AVAILABLE;
   private static final Method setWritable;
   private static final Method setReadable;
   private static final Method setExecutable;

   public static int chmod(File file, String mode) {
      if (CHMOD_API_AVAILABLE) {
         char other = '0';
         if (mode.length() >= 1) {
            other = mode.charAt(mode.length() - 1);
         }

         char user = '0';
         if (mode.length() >= 3) {
            user = mode.charAt(mode.length() - 3);
         }

         if (!setPermissions(file, other, false)) {
            return -1;
         } else {
            return !setPermissions(file, user, true) ? -1 : 0;
         }
      } else {
         try {
            Process chmod = Runtime.getRuntime().exec("/bin/chmod " + mode + " " + file.getAbsolutePath());
            chmod.waitFor();
            return chmod.exitValue();
         } catch (IOException var4) {
         } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
         }

         return -1;
      }
   }

   private static boolean setPermissions(File file, char permChar, boolean userOnly) {
      int permValue = Character.digit(permChar, 8);

      try {
         if ((permValue & 1) != 0) {
            setExecutable.invoke(file, Boolean.TRUE, userOnly);
         } else {
            setExecutable.invoke(file, Boolean.FALSE, userOnly);
         }

         if ((permValue & 2) != 0) {
            setWritable.invoke(file, Boolean.TRUE, userOnly);
         } else {
            setWritable.invoke(file, Boolean.FALSE, userOnly);
         }

         if ((permValue & 4) != 0) {
            setReadable.invoke(file, Boolean.TRUE, userOnly);
         } else {
            setReadable.invoke(file, Boolean.FALSE, userOnly);
         }

         return true;
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
      }

      return false;
   }

   static {
      boolean apiAvailable = false;
      Method setWritableVar = null;
      Method setReadableVar = null;
      Method setExecutableVar = null;

      try {
         setWritableVar = File.class.getMethod("setWritable", Boolean.TYPE, Boolean.TYPE);
         setReadableVar = File.class.getMethod("setReadable", Boolean.TYPE, Boolean.TYPE);
         setExecutableVar = File.class.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE);
         apiAvailable = true;
      } catch (Exception var5) {
      }

      setWritable = setWritableVar;
      setReadable = setReadableVar;
      setExecutable = setExecutableVar;
      CHMOD_API_AVAILABLE = apiAvailable;
   }
}
