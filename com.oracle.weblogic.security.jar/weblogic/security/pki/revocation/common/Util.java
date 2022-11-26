package weblogic.security.pki.revocation.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

final class Util {
   private static final Locale FILE_EXTENSION_LOCALE;
   private static final String CRL_FILE_EXTENSION = ".crl";
   public static final CrlFilesOnlyFilter CRL_FILES_ONLY_FILTER;

   public static void checkNotNull(String name, Object test) {
      if (null == test) {
         throw new IllegalArgumentException("Expected non-null \"" + name + "\".");
      }
   }

   public static void checkRange(String valueName, Long testValue, Long minValue, Long maxValue) {
      if (null != testValue) {
         if (null != minValue || null != maxValue) {
            if (null != minValue && testValue < minValue) {
               throw new IllegalArgumentException("Value " + formatValueName(valueName) + "minimum is " + minValue + ", was " + testValue + ".");
            } else if (null != maxValue && testValue > maxValue) {
               throw new IllegalArgumentException("Value " + formatValueName(valueName) + "maximum is " + maxValue + ", was " + testValue + ".");
            }
         }
      }
   }

   public static void checkTimeTolerance(int timeTolerance) {
      checkRange("timeTolerance", (long)timeTolerance, 0L, (Long)null);
   }

   public static void checkRefreshPeriodPercent(int refreshPeriodPercent) {
      checkRange("refreshPeriodPercent", (long)refreshPeriodPercent, 1L, 100L);
   }

   public static void backgroundTaskSleep() {
      try {
         Thread.currentThread();
         Thread.sleep(5000L);
      } catch (InterruptedException var1) {
      }

   }

   public static byte[] readAll(InputStream inputStream) throws IOException {
      checkNotNull("InputStream", inputStream);
      InputStream is = new BufferedInputStream(inputStream);
      ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
      baos.reset();
      byte[] transferBuffer = new byte[8192];

      int n;
      while((n = is.read(transferBuffer, 0, transferBuffer.length)) != -1) {
         baos.write(transferBuffer, 0, n);
      }

      byte[] result = baos.toByteArray();
      return result;
   }

   private static String formatValueName(String valueName) {
      return null == valueName ? "" : valueName + " ";
   }

   static {
      FILE_EXTENSION_LOCALE = Locale.US;
      CRL_FILES_ONLY_FILTER = new CrlFilesOnlyFilter();
   }

   private static final class CrlFilesOnlyFilter implements FileFilter {
      private CrlFilesOnlyFilter() {
      }

      public boolean accept(File pathname) {
         if (pathname != null && !pathname.isDirectory()) {
            String name = pathname.getName();
            if (null != name) {
               String lcname = name.toLowerCase(Util.FILE_EXTENSION_LOCALE);
               return lcname.endsWith(".crl");
            }
         }

         return false;
      }

      // $FF: synthetic method
      CrlFilesOnlyFilter(Object x0) {
         this();
      }
   }
}
