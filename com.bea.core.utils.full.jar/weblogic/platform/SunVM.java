package weblogic.platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public final class SunVM extends VM {
   private static boolean stackdumpLibsOk = false;
   private static boolean writeToStdin = false;
   private static final String TEMP_FILE = "debugMagicThreadDumpFile";
   private static String unsupportedVMMessage = "**Unexpected error occurred in ThreadDump Initialization***";

   public String getName() {
      return "weblogic.platform.SunVM";
   }

   private static void isVMSupported() {
      String javaVersion = System.getProperty("java.version");
      if (javaVersion == null) {
         javaVersion = "(undefined)";
      }

      javaVersion = javaVersion.toLowerCase();
      String osname = System.getProperty("os.name");
      if (osname == null) {
         osname = "(undefined)";
      }

      osname = osname.toLowerCase();
      String vendorURL = System.getProperty("java.vendor.url");
      if (vendorURL == null) {
         vendorURL = "(undefined)";
      }

      vendorURL = vendorURL.toLowerCase();
      String archDataModel = System.getProperty("sun.arch.data.model");
      if (archDataModel == null) {
         archDataModel = "(undefined)";
      }

      boolean tryToLoad = false;
      if (osname.indexOf("linux") >= 0 && (vendorURL.indexOf("sun.com") >= 0 || vendorURL.indexOf("blackdown.org") >= 0 || vendorURL.indexOf("oracle.com") >= 0 || vendorURL.indexOf("openjdk.java.net") >= 0)) {
         tryToLoad = true;
      }

      if (osname.indexOf("windows") >= 0 && (vendorURL.indexOf("sun.com") >= 0 || vendorURL.indexOf("oracle.com") >= 0)) {
         if (isJavaVersionGreaterThanOrEqualTo("1.5", javaVersion)) {
            tryToLoad = true;
         } else {
            tryToLoad = false;
         }
      }

      if (osname.indexOf("mac os x") >= 0) {
         tryToLoad = false;
      }

      if (osname.indexOf("sunos") >= 0 || osname.indexOf("solaris") >= 0) {
         if (vendorURL.indexOf("sun.com") < 0 && vendorURL.indexOf("oracle.com") < 0) {
            tryToLoad = false;
         } else if (isJavaVersionGreaterThanOrEqualTo("1.6", javaVersion) || archDataModel.indexOf("64") == -1 && isJavaVersionGreaterThanOrEqualTo("1.5", javaVersion)) {
            tryToLoad = true;
         }
      }

      if (tryToLoad) {
         try {
            System.loadLibrary("stackdump");
            stackdumpLibsOk = true;
         } catch (UnsatisfiedLinkError var6) {
            unsupportedVMMessage = "cannot load libary 'stackdump': " + var6.toString();
         }
      } else {
         unsupportedVMMessage = "cannot dump threads on this VM:\njava.version='" + javaVersion + "'\nos.name='" + osname + "'\njava.vendor.url='" + vendorURL + "'\nIt either hasn't been tested, or is known not to work.";
      }

   }

   public void threadDump() {
      if (stackdumpLibsOk) {
         this.threadDump0();
      } else {
         System.err.println(unsupportedVMMessage);
      }

   }

   public void threadDump(String filename) throws IOException {
      if (stackdumpLibsOk) {
         this.fileThreadDump0(filename);
      } else {
         PrintStream pos = new PrintStream(new FileOutputStream(filename));
         pos.println(unsupportedVMMessage);
         pos.close();
      }

   }

   public void threadDump(FileDescriptor fd) throws IOException {
      if (stackdumpLibsOk) {
         this.fdThreadDump0(fd);
      } else {
         PrintStream pos = new PrintStream(new FileOutputStream(fd));
         pos.println(unsupportedVMMessage);
         pos.close();
      }

   }

   public void threadDump(File f) throws IOException {
      FileOutputStream fos = new FileOutputStream(f);
      this.threadDump(fos.getFD());
      fos.close();
   }

   public void threadDump(PrintWriter writer) {
      try {
         this.threadDump("debugMagicThreadDumpFile");
         BufferedReader reader = new BufferedReader(new FileReader("debugMagicThreadDumpFile"));

         String line;
         while((line = reader.readLine()) != null) {
            writer.println(line);
         }

         reader.close();
         writer.flush();
      } catch (IOException var4) {
         writer.println("Error : Unable to dump threads");
      }

   }

   private static boolean isJavaVersionGreaterThanOrEqualTo(String greaterThanVersion, String javaVersion) {
      String[] greaterThanVersionComponents = greaterThanVersion.split("\\.");
      String[] javaVersionComponents = javaVersion.split("\\.");
      if (javaVersionComponents.length > 1 && greaterThanVersionComponents.length > 1) {
         if (Integer.parseInt(javaVersionComponents[0]) > Integer.parseInt(greaterThanVersionComponents[0])) {
            return true;
         } else {
            return Integer.parseInt(javaVersionComponents[0]) == Integer.parseInt(greaterThanVersionComponents[0]) && Integer.parseInt(javaVersionComponents[1]) >= Integer.parseInt(greaterThanVersionComponents[1]);
         }
      } else {
         return false;
      }
   }

   private synchronized native void threadDump0();

   private synchronized native void fileThreadDump0(String var1) throws IOException;

   private synchronized native void fdThreadDump0(FileDescriptor var1) throws IOException;

   public static void main(String[] a) throws Exception {
      if (a.length == 2) {
         System.err.println('"' + a[0] + "\".compareTo(\"" + a[1] + "\") - " + a[0].compareTo(a[1]));
      }

      int lim = 5;
      if (a.length == 1) {
         lim = Integer.parseInt(a[0]);
      }

      for(int i = 0; i < lim; ++i) {
         FileOutputStream fout = new FileOutputStream("testDump" + i + ".txt");
         FileDescriptor fd = fout.getFD();
         VM.getVM().threadDump(fd);
         if (i != lim - 1) {
            System.err.println("\n\n*******Next thread dump in ten seconds********\n");
            Thread.sleep(10000L);
         }
      }

   }

   static {
      isVMSupported();
   }
}
