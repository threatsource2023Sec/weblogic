package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.Source;

public final class verboseToZip {
   private static ZipOutputStream zipArchive = null;
   private static ZipEntry zipEntry = null;
   private static InputStream classToBeArchived = null;
   private static FileOutputStream archiveToCreate = null;
   private static FileInputStream logToScan = null;
   private static Hashtable classList = null;
   private static String zipname = null;
   private static JarFile weblogicJar = null;
   private static final int bufsize = 8192;
   private static final String CLASSPATH_PROP = "java.class.path";
   private static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";

   public static void main(String[] argv) {
      if (argv.length != 2) {
         System.out.println("Usage: java utils.verboseToZip verboseFileToScan zipArchiveToBeCreated\nHelp at: @DOCSWEBROOT");
      } else {
         zipname = argv[1];

         try {
            File logFile = new File(argv[0]);
            if (!logFile.exists() || !logFile.isFile() || !logFile.canRead()) {
               System.out.println("\nCannot find a readable file named");
               System.out.println("'" + argv[0] + "' to scan. Check path and permissions.\n");
               return;
            }

            logToScan = new FileInputStream(logFile);
            File archiveFile = new File(zipname);

            try {
               archiveToCreate = new FileOutputStream(archiveFile);
            } catch (FileNotFoundException var4) {
               System.out.println("Cannot create zip file " + archiveFile + "\n");
               System.exit(0);
            }

            zipArchive = new ZipOutputStream(archiveToCreate);
            zipArchive.setMethod(0);
         } catch (Exception var5) {
            var5.printStackTrace();
            System.exit(0);
         }

         if (!createListOfClasses()) {
            System.out.println("\n'" + argv[0] + "' doesn't have a list of classes like the output of java -verbose.\nThis utility will make a zip file from an input file containing lines\nof the form:\n\n[Loaded /weblogic/classes/weblogic/jdbc/dblib/Driver.class]\n\nNo zip archive created.");
         } else {
            getMiscFiles();
            fillArchive();
         }
      }
   }

   private static boolean createListOfClasses() {
      classList = new Hashtable();
      boolean returnValue = false;

      String className;
      while((className = getNextClassName()) != null) {
         returnValue = true;
         if (className.indexOf("i18n") == -1) {
            classList.put(className, "");
         }
      }

      return returnValue;
   }

   private static void getMiscFiles() {
      String classpath = System.getProperty("java.class.path");
      StringTokenizer classPaths = new StringTokenizer(classpath, File.pathSeparator);

      while(classPaths.hasMoreTokens()) {
         String aClassPath = classPaths.nextToken();
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
         Enumeration e = weblogicJar.entries();

         while(e.hasMoreElements()) {
            ZipEntry ze = (ZipEntry)e.nextElement();
            String result = ze.getName();
            if (!ze.isDirectory() && result.indexOf("i18n") != -1) {
               classList.put(result, "");
            }
         }
      }

      classList.put("META-INF/MANIFEST.MF", "");
   }

   private static String getNextClassName() {
      while(true) {
         String nextLine = readLineFromLogFile();
         if (nextLine == null) {
            return null;
         }

         if (!nextLine.startsWith("[Open") && !nextLine.startsWith("[Loaded [") && !nextLine.startsWith("[Loaded SymantecJITCompilationThread") && !nextLine.startsWith("[Loaded $Proxy") && nextLine.indexOf("_WLSkel") == -1 && nextLine.indexOf("_WLStub") == -1 && nextLine.indexOf("DispatcherImpl_WLSkel") == -1) {
            String className = getClassNameFrom(nextLine);
            if (!className.equals("")) {
               return className;
            }
         }
      }
   }

   private static String readLineFromLogFile() {
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

   private static String getClassNameFrom(String fileLine) {
      StringTokenizer lineTokens = new StringTokenizer(fileLine);
      int tokenCount = lineTokens.countTokens();
      if (tokenCount < 2) {
         return "";
      } else {
         String first = lineTokens.nextToken();
         if (!first.equals("[Loaded")) {
            return "";
         } else {
            String className;
            String source;
            if (tokenCount == 2) {
               source = lineTokens.nextToken();
               className = source.substring(0, source.length() - 1);
               if (!className.endsWith(".class")) {
                  className = className.replace('.', '/') + ".class";
               }
            } else {
               className = lineTokens.nextToken();
               lineTokens.nextToken();
               source = lineTokens.nextToken();
               if (source.indexOf(".zip]") != -1) {
                  className = "";
               }

               if (source.indexOf(".jar]") != -1) {
                  className = "";
               }
            }

            return className;
         }
      }
   }

   private static String RemoveClassPathRootFrom(String className) {
      String nClassName = normalizePath(className);
      Object o = System.getProperty("java.class.path");
      String pathString = (String)o;
      StringTokenizer classPaths = new StringTokenizer(pathString, File.pathSeparator);

      String aClassPath;
      do {
         if (!classPaths.hasMoreTokens()) {
            return className;
         }

         aClassPath = classPaths.nextToken();
      } while(aClassPath.endsWith(".zip") || aClassPath.endsWith(".jar") || !nClassName.startsWith(aClassPath));

      return nClassName.substring(aClassPath.length() + 1);
   }

   private static void fillArchive() {
      int cnt = 0;
      String classpath = System.getProperty("java.class.path");
      Enumeration e = classList.keys();

      try {
         String goodGrammar;
         for(ClassFinder finder = new ClasspathClassFinder2(classpath); e.hasMoreElements(); ++cnt) {
            goodGrammar = (String)e.nextElement();
            String className = RemoveClassPathRootFrom(goodGrammar);
            Source source = finder.getSource(className);
            if (source == null) {
               source = finder.getClassSource(className);
            }

            if (source == null) {
               System.out.println("Unable to find class file named '" + className + "'.");
               return;
            }

            classToBeArchived = source.getInputStream();
            System.out.println("[" + className + "]");
            zipEntry = new ZipEntry(className);
            if (className.endsWith("META-INF/MANIFEST.MF")) {
               classToBeArchived = weblogicJar.getInputStream(zipEntry);
            }

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
         }

         zipArchive.close();
         goodGrammar = "files.";
         if (cnt == 1) {
            goodGrammar = "file. (Only one!?)";
         }

         System.out.println("Created zip file: " + zipname + " containing " + cnt + " class " + goodGrammar);
      } catch (Exception var16) {
         var16.printStackTrace();
         System.exit(0);
      }

   }

   private static String normalizePath(String path) {
      if (path.indexOf(92) == -1) {
         return path;
      } else {
         StringBuffer nPath = new StringBuffer(path);

         for(int i = 0; i < nPath.length(); ++i) {
            if (nPath.charAt(i) == '\\') {
               nPath.setCharAt(i, '/');
            }
         }

         return nPath.toString();
      }
   }
}
