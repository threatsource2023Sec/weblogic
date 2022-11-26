package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class logToZip {
   static ZipOutputStream zipArchive = null;
   static ZipEntry zipEntry = null;
   static BufferedInputStream classToBeArchived = null;
   static FileOutputStream archiveToCreate = null;
   static FileInputStream logToScan = null;
   static Hashtable classList = null;
   static String codeBase = null;
   static String zipname = null;
   static boolean i18n = false;
   static JarFile weblogicJar = null;
   private static final int bufsize = 8192;
   public static final String CLASSPATH_PROP = "java.class.path";
   public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";

   public static void main(String[] argv) {
      if (argv.length != 3) {
         System.out.println("Usage: java utils.logToZip httplogfile codebase_directory zipfile\nExample: java utils.logToZip /usr/http/logs/access classes myapplet.zip\nHelp at: @DOCSWEBROOT");
         System.exit(1);
      } else {
         zipname = argv[2];

         try {
            File logFile = new File(argv[0]);
            if (!logFile.exists() || !logFile.isFile() || !logFile.canRead()) {
               System.out.println("\nCannot find a readable log file named");
               System.out.println("'" + argv[0] + "' to scan. Check path and permissions.\n");
               System.out.println("Note: This should be run from the document root of your HTTP server");
               return;
            }

            logToScan = new FileInputStream(logFile);
            codeBase = argv[1];
            if (codeBase.length() > 0 && codeBase.charAt(0) == '/') {
               if (codeBase.length() > 1) {
                  codeBase = codeBase.substring(1);
               } else {
                  codeBase = "";
               }
            }

            File archiveFile = new File(zipname);

            try {
               archiveToCreate = new FileOutputStream(archiveFile);
            } catch (FileNotFoundException var4) {
               System.out.println("Cannot create file " + archiveFile + "\n");
               System.exit(1);
            }

            zipArchive = new ZipOutputStream(archiveToCreate);
            zipArchive.setMethod(0);
         } catch (Exception var5) {
            var5.printStackTrace();
            System.exit(1);
         }

         if (!createListOfClasses()) {
            System.out.println("No class files found in log. No zip archive created.\nRun this from the document root directory of your HTTP server.");
            System.exit(1);
         }

         getMiscFiles();
         fillArchive();
         System.exit(0);
      }
   }

   public static boolean createListOfClasses() {
      classList = new Hashtable();
      String className = null;
      boolean ret_val = false;

      while((className = getNextClassName()) != null) {
         ret_val = true;
         if (className.indexOf("i18n") != -1) {
            i18n = true;
         } else {
            classList.put(className, "");
         }
      }

      return ret_val;
   }

   public static void getMiscFiles() {
      String classpath = System.getProperty("java.class.path");
      String aClassPath = null;
      String result = null;
      StringTokenizer classPaths = new StringTokenizer(classpath, File.pathSeparator);

      while(classPaths.hasMoreTokens()) {
         aClassPath = classPaths.nextToken();
         if (aClassPath.endsWith("weblogic.jar")) {
            try {
               weblogicJar = new JarFile(aClassPath);
            } catch (Exception var6) {
               System.out.println("Unable to open weblogic.jar");
            }
            break;
         }
      }

      if (weblogicJar != null) {
         Enumeration enum_ = weblogicJar.entries();

         while(enum_.hasMoreElements()) {
            ZipEntry e = (ZipEntry)enum_.nextElement();
            result = e.getName();
            if (!e.isDirectory() && i18n && result.indexOf("i18n") != -1) {
               classList.put(result, "");
            }
         }
      }

      classList.put("META-INF/MANIFEST.MF", "");
   }

   public static String getNextClassName() {
      String nextLine = null;
      String className = null;

      while(true) {
         nextLine = readLineFromLogFile();
         if (nextLine == null) {
            return null;
         }

         if (!nextLine.startsWith("[Open") && !nextLine.startsWith("[Loaded [") && !nextLine.startsWith("[Loaded SymantecJITCompilationThread") && !nextLine.startsWith("[Loaded $Proxy") && nextLine.indexOf("DispatcherImpl_WLSkel") == -1 && nextLine.indexOf("DGCServerImpl_WLSkel") == -1) {
            className = getClassNameFrom(nextLine);
            if (!className.equals("")) {
               return className;
            }
         }
      }
   }

   public static String readLineFromLogFile() {
      StringBuffer line = new StringBuffer();
      int ch = -1;

      try {
         for(ch = logToScan.read(); ch != 10 && ch != -1; ch = logToScan.read()) {
            line.append((char)ch);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         System.exit(0);
      }

      if (ch == 10) {
         if (line.length() == 0) {
            return "";
         }
      } else if (ch == -1 && line.length() == 0) {
         return null;
      }

      return line.toString();
   }

   public static String getClassNameFrom(String fileLine) {
      StringTokenizer lineTokens = new StringTokenizer(fileLine);
      String returnName = "";

      String className;
      do {
         if (!lineTokens.hasMoreElements()) {
            return returnName;
         }

         className = lineTokens.nextToken();
      } while(!className.endsWith(".class"));

      returnName = className.substring(1);
      if (codeBase.length() > 0) {
         if (!returnName.startsWith(codeBase)) {
            System.out.print("codebase_directory argument '" + codeBase + "' ");
            System.out.print("doesn't match the ");
            System.out.println("prefix of '" + returnName + "'");
            System.exit(0);
         }

         return returnName.substring(codeBase.length() + 1);
      } else {
         return returnName;
      }
   }

   public static void fillArchive() {
      Enumeration E = classList.keys();
      String className = null;
      String codebaseName = null;
      int cnt = 0;

      while(true) {
         try {
            if (E.hasMoreElements()) {
               className = (String)E.nextElement();
               if (codeBase.length() > 0) {
                  codebaseName = codeBase + "/" + className;
               } else {
                  codebaseName = className;
               }

               File classFile = new File(codebaseName);
               if (classFile.exists() && classFile.isFile() && classFile.canRead()) {
                  System.out.println("[" + className + "]");
                  classToBeArchived = new BufferedInputStream(new FileInputStream(classFile));
                  zipEntry = new ZipEntry(className);
                  Vector byteBuffer = new Vector();
                  Vector byteLength = new Vector();
                  long numBytes = 0L;
                  byte[] bytes = new byte[8192];
                  CRC32 crc = new CRC32();
                  crc.reset();

                  int numread;
                  for(; (numread = classToBeArchived.read(bytes, 0, bytes.length)) != -1; bytes = new byte[8192]) {
                     if (bytes.length > 0) {
                        crc.update(bytes, 0, numread);
                        byteBuffer.addElement(bytes);
                        byteLength.addElement(new Integer(numread));
                        numBytes += (long)numread;
                     }
                  }

                  classToBeArchived.close();
                  zipEntry.setSize(numBytes);
                  zipEntry.setCrc(crc.getValue());
                  zipArchive.putNextEntry(zipEntry);
                  Enumeration e1 = byteBuffer.elements();
                  Enumeration e2 = byteLength.elements();

                  while(e1.hasMoreElements()) {
                     bytes = (byte[])((byte[])e1.nextElement());
                     numread = (Integer)e2.nextElement();
                     zipArchive.write(bytes, 0, numread);
                  }

                  zipArchive.closeEntry();
                  ++cnt;
                  continue;
               }

               System.out.println("Unable to find class file named '" + codebaseName + "'.\nAre you running this from from the document root of the HTTP server?");
               return;
            }

            zipArchive.close();
            System.out.println("Created zip file: " + zipname + " with " + cnt + " class files");
         } catch (Exception var15) {
            var15.printStackTrace();
            System.exit(0);
         }

         return;
      }
   }
}
