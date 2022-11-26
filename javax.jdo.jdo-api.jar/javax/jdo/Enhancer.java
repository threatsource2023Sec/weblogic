package javax.jdo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.jdo.spi.I18NHelper;

public class Enhancer {
   private static final I18NHelper msg = I18NHelper.getInstance("javax.jdo.Bundle");
   private char NL = '\n';
   private String JAR_FILE_SUFFIX = ".jar";
   private String JDO_FILE_SUFFIX = ".jdo";
   private String CLASS_FILE_SUFFIX = ".class";
   private boolean error = false;
   private boolean printAndExit = false;
   private List persistenceUnitNames = new ArrayList();
   private String directoryName = null;
   private ClassLoader loader = null;
   private String classPath = null;
   private boolean checkOnly = false;
   private boolean verbose = false;
   private boolean recurse = false;
   private StringBuilder errorBuffer = new StringBuilder();
   private StringBuilder verboseBuffer = new StringBuilder();
   private List fileNames = new ArrayList();
   private List classFileNames = new ArrayList();
   private List jdoFileNames = new ArrayList();
   private List jarFileNames = new ArrayList();
   private int numberOfValidatedClasses = 0;
   private int numberOfEnhancedClasses = 0;
   private Properties properties;

   public static void main(String[] args) {
      Enhancer enhancerMain = new Enhancer();
      enhancerMain.run(args);
   }

   private void run(String[] args) {
      this.processArgs(args);
      JDOEnhancer enhancer = null;

      try {
         enhancer = JDOHelper.getEnhancer();
      } catch (JDOException var9) {
         var9.printStackTrace();
         this.exit(2);
      }

      try {
         this.properties = enhancer.getProperties();
         this.addVerboseMessage("MSG_EnhancerClass", enhancer.getClass().getName());
         this.addVerboseMessage("MSG_EnhancerProperty", "VendorName", this.properties.getProperty("VendorName"));
         this.addVerboseMessage("MSG_EnhancerProperty", "VersionNumber", this.properties.getProperty("VersionNumber"));
         Set props = this.properties.entrySet();
         Iterator entries = props.iterator();

         while(entries.hasNext()) {
            Map.Entry entry = (Map.Entry)entries.next();
            if (!"VendorName".equals(entry.getKey()) && !"VersionNumber".equals(entry.getKey())) {
               this.addVerboseMessage("MSG_EnhancerProperty", (String)entry.getKey(), (String)entry.getValue());
            }
         }

         enhancer.setVerbose(this.verbose);
         if (this.loader != null) {
            enhancer.setClassLoader(this.loader);
         }

         int numberOfClasses = this.classFileNames.size();
         if (numberOfClasses != 0) {
            enhancer.addClasses((String[])this.classFileNames.toArray(new String[numberOfClasses]));
         }

         int numberOfFiles = this.jdoFileNames.size();
         if (numberOfFiles != 0) {
            enhancer.addFiles((String[])this.jdoFileNames.toArray(new String[numberOfFiles]));
         }

         Iterator i$;
         String persistenceUnitName;
         if (0 < this.jarFileNames.size()) {
            i$ = this.jarFileNames.iterator();

            while(i$.hasNext()) {
               persistenceUnitName = (String)i$.next();
               enhancer.addJar(persistenceUnitName);
            }
         }

         if (this.persistenceUnitNames != null) {
            i$ = this.persistenceUnitNames.iterator();

            while(i$.hasNext()) {
               persistenceUnitName = (String)i$.next();
               enhancer.addPersistenceUnit(persistenceUnitName);
            }
         }

         if (this.directoryName != null) {
            enhancer.setOutputDirectory(this.directoryName);
         }

         if (this.checkOnly) {
            this.numberOfValidatedClasses = enhancer.validate();
            this.addVerboseMessage("MSG_EnhancerValidatedClasses", this.numberOfValidatedClasses);
         } else {
            this.numberOfEnhancedClasses = enhancer.enhance();
            this.addVerboseMessage("MSG_EnhancerEnhancedClasses", this.numberOfEnhancedClasses);
         }

         this.exit(0);
      } catch (Exception var10) {
         var10.printStackTrace();
         this.exit(1);
      }

   }

   private void processArgs(String[] args) {
      this.parseArgs(args);
      this.parseFiles((String[])this.fileNames.toArray(new String[this.fileNames.size()]), true, this.recurse);
      this.loader = this.prepareClassLoader(this.classPath);
      if (this.error) {
         this.addErrorMessage(msg.msg("MSG_EnhancerUsage"));
         this.exit(3);
      }

      if (this.printAndExit) {
         this.addVerboseMessage("MSG_EnhancerUsage");
         this.exit(0);
      }

   }

   private void parseArgs(String[] args) {
      boolean doneWithOptions = false;
      this.fileNames = new ArrayList();

      for(int i = 0; i < args.length; ++i) {
         String arg = args[i];
         if ("?".equals(arg)) {
            this.printAndExit = true;
            return;
         }

         if (!doneWithOptions) {
            if (arg.startsWith("-")) {
               String option = arg.substring(1);
               if ("help".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-help");
                  this.setPrintAndExit();
               } else if ("h".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-h");
                  this.setPrintAndExit();
               } else if ("v".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-v");
                  this.verbose = true;
               } else if ("verbose".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-verbose");
                  this.verbose = true;
               } else if ("pu".equals(option)) {
                  if (this.hasNextArgument("MSG_EnhancerProcessing", "-pu", i, args.length)) {
                     ++i;
                     String puName = args[i];
                     this.addVerboseMessage("MSG_EnhancerPersistenceUnitName", puName);
                     this.persistenceUnitNames.add(puName);
                  } else {
                     this.setError();
                  }
               } else if ("cp".equals(option)) {
                  if (this.hasNextArgument("MSG_EnhancerProcessing", "-cp", i, args.length)) {
                     ++i;
                     this.classPath = args[i];
                     this.addVerboseMessage("MSG_EnhancerClassPath", this.classPath);
                  } else {
                     this.setError();
                  }
               } else if ("d".equals(option)) {
                  if (this.hasNextArgument("MSG_EnhancerProcessing", "-d", i, args.length)) {
                     ++i;
                     this.directoryName = args[i];
                     this.addVerboseMessage("MSG_EnhancerOutputDirectory", this.directoryName);
                  } else {
                     this.setError();
                  }
               } else if ("checkonly".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-checkonly");
                  this.checkOnly = true;
               } else if ("r".equals(option)) {
                  this.addVerboseMessage("MSG_EnhancerProcessing", "-r");
                  this.recurse = true;
               } else {
                  this.setError();
                  this.addErrorMessage(msg.msg("ERR_EnhancerUnrecognizedOption", (Object)option));
               }
            } else {
               doneWithOptions = true;
               this.fileNames.add(arg);
            }
         } else {
            this.fileNames.add(arg);
         }
      }

   }

   private boolean hasNextArgument(String msgId, String where, int i, int length) {
      if (i + 1 >= length) {
         this.setError();
         this.addErrorMessage(msg.msg(msgId, (Object)where));
         this.addErrorMessage(msg.msg("ERR_EnhancerRequiredArgumentMissing"));
         return false;
      } else {
         return true;
      }
   }

   private void parseFiles(String[] fileNames, boolean search, boolean recurse) {
      String[] arr$ = fileNames;
      int len$ = fileNames.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String fileName = arr$[i$];
         if (fileName.endsWith(this.JAR_FILE_SUFFIX)) {
            this.jarFileNames.add(fileName);
            this.addVerboseMessage("MSG_EnhancerJarFileName", fileName);
         } else if (fileName.endsWith(this.JDO_FILE_SUFFIX)) {
            this.jdoFileNames.add(fileName);
            this.addVerboseMessage("MSG_EnhancerJDOFileName", fileName);
         } else if (fileName.endsWith(this.CLASS_FILE_SUFFIX)) {
            this.classFileNames.add(fileName);
            this.addVerboseMessage("MSG_EnhancerClassFileName", fileName);
         } else {
            File directoryFile = new File(fileName);
            if (directoryFile.isDirectory() && search) {
               String directoryPath = directoryFile.getAbsolutePath();
               String[] files = directoryFile.list();
               String[] pathName = new String[1];
               if (files != null) {
                  String[] arr$ = files;
                  int len$ = files.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     String file = arr$[i$];
                     pathName[0] = directoryPath + '/' + file;
                     this.parseFiles(pathName, recurse, recurse);
                  }
               }
            }
         }
      }

   }

   private ClassLoader prepareClassLoader(String classPath) {
      if (classPath == null) {
         return null;
      } else {
         ClassLoader result = null;
         String separator = System.getProperty("path.separator");
         String[] paths = classPath.split(separator);
         List urls = new ArrayList();
         String[] arr$ = paths;
         int len$ = paths.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String path = arr$[i$];
            File file = new File(path);
            URI uri = file.toURI();

            try {
               URL url = uri.toURL();
               this.addVerboseMessage("MSG_EnhancerClassPath", url.toString());
               urls.add(url);
            } catch (MalformedURLException var13) {
               this.setError();
               this.addErrorMessage(msg.msg("ERR_EnhancerBadClassPath", (Object)file));
            }
         }

         result = new URLClassLoader((URL[])urls.toArray(new URL[urls.size()]), (ClassLoader)null);
         return result;
      }
   }

   private void addErrorMessage(String message) {
      this.errorBuffer.append(message);
      this.errorBuffer.append(this.NL);
   }

   private void setError() {
      this.error = true;
   }

   private void setPrintAndExit() {
      this.printAndExit = true;
   }

   private void exit(int exitValue) {
      System.out.print(this.verboseBuffer.toString());
      System.err.print(this.errorBuffer.toString());
      System.exit(exitValue);
   }

   private void addVerboseMessage(String msgId, String... where) {
      this.verboseBuffer.append(msg.msg(msgId, (Object[])where));
      this.verboseBuffer.append(this.NL);
   }

   private void addVerboseMessage(String msgId, String where) {
      this.verboseBuffer.append(msg.msg(msgId, (Object)where));
      this.verboseBuffer.append(this.NL);
   }

   private void addVerboseMessage(String msgId) {
      this.verboseBuffer.append(msg.msg(msgId));
      this.verboseBuffer.append(this.NL);
   }

   private void addVerboseMessage(String msgId, int where) {
      this.addVerboseMessage(msgId, String.valueOf(where));
   }
}
