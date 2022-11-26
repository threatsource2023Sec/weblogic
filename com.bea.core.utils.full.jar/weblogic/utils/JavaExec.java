package weblogic.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.platform.JDK;

public final class JavaExec {
   private static final boolean DEBUG = false;
   private static final Logger LOGGER = Logger.getLogger("weblogic.utils.JavaExec");
   private static final boolean isMSVM = isMicrosoftVM();
   private static boolean isJre = false;
   private static boolean useLocalJre = false;
   private static boolean isWinSys = false;
   private static boolean quoteStrings = false;
   private static String exeName;
   private static String jdkLibDir = null;
   private static String[] defaultClasspath;
   private static final String NULL = "<null>";
   private static int majorVersion = -1;
   private static int minorVersion = -1;
   public static final String JDK_LIB = "JDK_LIB_PLACEHOLDER";
   public static final String JDK_LIB_DIR = "JDK_LIB_DIR";
   private String mainClass;
   private ArrayList sysProps;
   private ArrayList classPath;
   private ArrayList args;
   private ArrayList jvmArgs;
   private boolean forceMSVM;

   public JavaExec(String clazz) {
      this(clazz, false);
   }

   public JavaExec(String clazz, boolean forceMSVM) {
      this.sysProps = new ArrayList();
      this.classPath = new ArrayList();
      this.args = new ArrayList();
      this.jvmArgs = new ArrayList();
      this.forceMSVM = false;
      this.mainClass = clazz;
      if (isWinSys && forceMSVM) {
         this.forceMSVM = true;
      }

      if (isMSVM || forceMSVM) {
         exeName = "jview.exe";
      }

   }

   public static JavaExec createCommand(String cmdLine) {
      ArrayList strV = new ArrayList();
      String[] strs = StringUtils.splitCompletely(cmdLine, " \"", true);

      for(int i = 0; i < strs.length; ++i) {
         if (!strs[i].equals("\"")) {
            if (!strs[i].equals(" ")) {
               strV.add(strs[i]);
            }
         } else {
            String quotedStr = "";
            ++i;

            while(i < strs.length && !strs[i].equals("\"")) {
               quotedStr = quotedStr + strs[i++];
            }

            strV.add(quotedStr);
         }
      }

      strs = new String[strV.size()];
      strV.toArray(strs);
      return new JavaExec(strs);
   }

   public static JavaExec createCommand(String[] cmdLine) {
      return new JavaExec(cmdLine);
   }

   private JavaExec(String[] cmdLine) {
      this.sysProps = new ArrayList();
      this.classPath = new ArrayList();
      this.args = new ArrayList();
      this.jvmArgs = new ArrayList();
      this.forceMSVM = false;
      this.mainClass = null;

      for(int i = 0; i < cmdLine.length; ++i) {
         String arg = cmdLine[i];
         if (arg != null) {
            String[] paths;
            if (this.mainClass == null && arg.startsWith("-classpath")) {
               paths = null;
               if (arg.equals("-classpath")) {
                  ++i;
                  if (i < cmdLine.length) {
                     arg = cmdLine[i];
                     if (arg.charAt(0) == '"') {
                        arg = arg.substring(1, arg.length() - 1);
                     }

                     paths = StringUtils.splitCompletely(arg, File.pathSeparator, false);
                  }
               } else {
                  arg = arg.substring(11);
                  if (arg.charAt(0) == '"') {
                     arg = arg.substring(1, arg.length() - 1);
                  }

                  paths = StringUtils.splitCompletely(arg, File.pathSeparator, false);
               }

               this.saveClassPath(paths);
            } else {
               int jdkLibIdx;
               if (arg.startsWith("-Dweblogic.class.path")) {
                  if (isMSVM) {
                     paths = null;
                     arg = arg.substring(22);
                     if (arg.charAt(0) == '"') {
                        arg = arg.substring(1, arg.length() - 1);
                     }

                     paths = StringUtils.splitCompletely(arg, File.pathSeparator, false);
                     this.saveClassPath(paths);
                  } else {
                     jdkLibIdx = arg.indexOf("JDK_LIB_PLACEHOLDER");
                     int idx;
                     if (jdkLibIdx >= 0) {
                        idx = "JDK_LIB_PLACEHOLDER".length();
                        String head = arg.substring(0, jdkLibIdx);
                        String tail = null;
                        if (jdkLibIdx + idx <= arg.length()) {
                           tail = arg.substring(jdkLibIdx + idx + 1);
                        }

                        arg = head;
                        if (tail != null) {
                           arg = head + tail;
                        }
                     }

                     idx = arg.indexOf(61);
                     if (idx > 0) {
                        this.addSystemProp(arg.substring(2, idx), arg.substring(idx + 1));
                     } else {
                        this.addSystemProp(arg.substring(2), "<null>");
                     }
                  }
               } else if (arg.startsWith("-D")) {
                  jdkLibIdx = arg.indexOf(61);
                  if (jdkLibIdx > 0) {
                     this.addSystemProp(arg.substring(2, jdkLibIdx), arg.substring(jdkLibIdx + 1));
                  } else {
                     this.addSystemProp(arg.substring(2), "<null>");
                  }
               } else if (arg.startsWith("-") && this.mainClass == null) {
                  this.jvmArgs.add(arg);
               } else if (this.mainClass == null) {
                  this.mainClass = arg;
               } else {
                  this.addArg(arg);
               }
            }
         }
      }

   }

   private void saveClassPath(String[] paths) {
      if (paths != null) {
         int jdkLibIdx = -1;

         int pathIdx;
         for(pathIdx = 0; pathIdx < paths.length; ++pathIdx) {
            if (paths[pathIdx].equals("JDK_LIB_PLACEHOLDER")) {
               jdkLibIdx = pathIdx;
               break;
            }
         }

         pathIdx = 0;
         File pathFile;
         if (jdkLibIdx >= 0) {
            ArrayList newClassPath = new ArrayList();

            while(pathIdx <= jdkLibIdx) {
               pathFile = new File(paths[pathIdx++]);
               newClassPath.add(pathFile);
            }

            for(int i = 0; i < this.classPath.size(); ++i) {
               newClassPath.add(this.classPath.get(i));
            }

            this.classPath = newClassPath;
         }

         while(pathIdx < paths.length) {
            pathFile = new File(paths[pathIdx++]);
            this.addClassPath(pathFile);
         }
      }

   }

   public void addSystemProp(String k, String v) {
      if (!v.equals("<null>")) {
         this.sysProps.add(k + '=' + v);
      } else {
         this.sysProps.add(k);
      }

   }

   public void addSystemProps(Properties p) {
      Enumeration e = p.keys();

      while(e.hasMoreElements()) {
         String k = (String)e.nextElement();
         String v = (String)p.get(k);
         this.addSystemProp(k, v);
      }

   }

   public void addClassPath(File f) {
      this.classPath.add(f);
   }

   public void addDefaultClassPath() {
      String enviroClasspath = System.getProperty("java.class.path");
      File[] enviroClasspathFiles = FileUtils.splitPath(enviroClasspath);

      for(int ii = 0; ii < enviroClasspathFiles.length; ++ii) {
         this.addClassPath(enviroClasspathFiles[ii]);
      }

   }

   public void addArg(String s) {
      this.args.add(s);
   }

   public static void useLocalJre() {
      useLocalJre = true;
   }

   public void addJvmArg(String s) {
      this.jvmArgs.add(s);
   }

   /** @deprecated */
   @Deprecated
   public void addJvmArgs(String s) {
      String[] args = StringUtils.splitCompletely(s, " ");

      for(int i = 0; i < args.length; ++i) {
         this.addJvmArg(args[i]);
      }

   }

   public Process getProcess() throws IOException {
      String[] cmd = this.getCommandArray();
      Process process = Runtime.getRuntime().exec(cmd);
      return process;
   }

   public Process getProcess(File workingDir) throws IOException {
      String[] cmd = this.getCommandArray();
      Process process = Runtime.getRuntime().exec(cmd, (String[])null, workingDir);
      return process;
   }

   public String getExeName() {
      return exeName;
   }

   public String getCommand() {
      StringBuffer sb = new StringBuffer();
      String[] args = this.getCommandArray();

      for(int i = 0; i < args.length; ++i) {
         if (i > 0) {
            sb.append(' ');
         }

         sb.append(args[i]);
      }

      return sb.toString();
   }

   public String[] getCommandArray() {
      ArrayList strV = new ArrayList();
      strV.add(exeName);
      ArrayList orgClassPath = (ArrayList)this.classPath.clone();
      StringBuffer classpathStr;
      Iterator i;
      boolean hasMore;
      if (!isMSVM && !this.forceMSVM) {
         if (this.jvmArgs != null) {
            Iterator i = this.jvmArgs.iterator();

            while(i.hasNext()) {
               strV.add(i.next());
            }
         }

         strV.add("-classpath");
         classpathStr = new StringBuffer();
         if (quoteStrings) {
            classpathStr.append('"');
         }

         if (!this.classPath.contains("JDK_LIB_PLACEHOLDER")) {
            for(int i = 0; i < defaultClasspath.length; ++i) {
               classpathStr.append(defaultClasspath[i]);
               if (i != defaultClasspath.length - 1 || this.classPath.size() > 0) {
                  classpathStr.append(File.pathSeparatorChar);
               }
            }
         }

         i = this.classPath.iterator();
         hasMore = i.hasNext();

         while(true) {
            while(hasMore) {
               Object pathObj = i.next();
               hasMore = i.hasNext();
               if (pathObj.equals("JDK_LIB_PLACEHOLDER")) {
                  for(int j = 0; j < defaultClasspath.length; ++j) {
                     classpathStr.append(defaultClasspath[j]);
                     if (j != defaultClasspath.length - 1 || hasMore) {
                        classpathStr.append(File.pathSeparatorChar);
                     }
                  }
               } else {
                  classpathStr.append(((File)pathObj).getAbsolutePath());
                  if (hasMore) {
                     classpathStr.append(File.pathSeparatorChar);
                  }
               }
            }

            if (quoteStrings) {
               classpathStr.append('"');
            }

            strV.add(classpathStr.toString());
            break;
         }
      } else if (this.classPath.size() > 0) {
         strV.add("/cp");
         classpathStr = new StringBuffer();
         classpathStr.append('"');
         i = this.classPath.iterator();
         hasMore = i.hasNext();

         while(hasMore) {
            File f = (File)i.next();
            hasMore = i.hasNext();
            String pathElement = f.getAbsolutePath();
            if (pathElement.endsWith("\\")) {
               pathElement = pathElement.substring(0, pathElement.length() - 1);
            }

            String relPath = f.getPath();
            if (relPath.equals("JDK_LIB_PLACEHOLDER")) {
               pathElement = "%windir%\\Java\\Classes\\classes.zip";
            } else if (relPath.startsWith("JDK_LIB_DIR")) {
               pathElement = jdkLibDir + relPath.substring("JDK_LIB_DIR".length());
            }

            classpathStr.append(pathElement);
            if (hasMore) {
               classpathStr.append(';');
            }
         }

         classpathStr.append('"');
         strV.add(classpathStr.toString());
      }

      String dashD = "-D";
      if (isMSVM || this.forceMSVM) {
         dashD = "/d:";
      }

      i = this.sysProps.iterator();

      while(i.hasNext()) {
         String s = (String)i.next();
         strV.add(dashD + s);
      }

      strV.add(this.mainClass);
      i = this.args.iterator();

      while(i.hasNext()) {
         strV.add(adaptStringForEnvironment((String)i.next()));
      }

      if (orgClassPath != null) {
         this.classPath = orgClassPath;
      }

      String[] strs = new String[strV.size()];
      strV.toArray(strs);
      return strs;
   }

   private static String adaptStringForEnvironment(String str) {
      if (quoteStrings) {
         str = '"' + str + '"';
      }

      return str;
   }

   private static void initDefaults() {
      JDK jdk = JDK.getJDK();
      majorVersion = jdk.getMajorVersion();
      minorVersion = jdk.getMinorVersion();
      if (isWinSystem()) {
         isWinSys = true;
         quoteStrings = true;
      } else {
         quoteStrings = false;
      }

      if (isMicrosoftVM()) {
         exeName = "jview.exe";
         defaultClasspath = new String[0];
         jdkLibDir = "%windir%";
      } else {
         File dir = new File(System.getProperty("java.home").replace('/', File.separatorChar));
         exeName = getExeName(dir);
         jdkLibDir = dir.getAbsolutePath();
         if (!isJre && (majorVersion < 1 || minorVersion < 2)) {
            defaultClasspath = new String[1];
            defaultClasspath[0] = (new File(dir, "lib" + File.separatorChar + "classes.zip")).getAbsolutePath();
         } else {
            defaultClasspath = new String[2];
            defaultClasspath[0] = (new File(dir, "lib" + File.separatorChar + "rt.jar")).getAbsolutePath();
            defaultClasspath[1] = (new File(dir, "lib" + File.separatorChar + "i18n.jar")).getAbsolutePath();
         }

      }
   }

   private static boolean isMicrosoftVM() {
      String vend = System.getProperty("java.vendor");
      if (vend == null) {
         return false;
      } else {
         return vend.toLowerCase().indexOf("icrosoft") != -1;
      }
   }

   private static boolean isWinSystem() {
      return File.pathSeparatorChar == ';';
   }

   private static String getExeName(File jh) {
      File f;
      if (useLocalJre) {
         f = new File(jh, "bin" + File.separatorChar + "jre");
         if (f.exists()) {
            return f.getAbsolutePath();
         }

         f = new File(jh, "bin" + File.separatorChar + "jre.exe");
         if (f.exists()) {
            return f.getAbsolutePath();
         }
      }

      f = new File(jh, "bin" + File.separatorChar + "java");
      if (f.exists()) {
         return f.getAbsolutePath();
      } else {
         f = new File(jh, "bin" + File.separatorChar + "javaw.exe");
         if (f.exists()) {
            return f.getAbsolutePath();
         } else {
            f = new File(jh, "bin" + File.separatorChar + "java.exe");
            if (f.exists()) {
               return f.getAbsolutePath();
            } else {
               f = new File(jh, "bin" + File.separatorChar + "jre");
               if (f.exists()) {
                  isJre = true;
                  return f.getAbsolutePath();
               } else {
                  f = new File(jh, "bin" + File.separatorChar + "jre.exe");
                  if (f.exists()) {
                     isJre = true;
                     return f.getAbsolutePath();
                  } else {
                     throw new Error("cannot find a suitable java executable");
                  }
               }
            }
         }
      }
   }

   public static void startReaderThreads(Process newVMProc) {
      Thread outthread = new Thread(new JaXSubProcess(false, newVMProc.getInputStream()));
      outthread.setDaemon(true);
      outthread.start();
      Thread errthread = new Thread(new JaXSubProcess(true, newVMProc.getErrorStream()));
      errthread.setDaemon(true);
      errthread.start();
   }

   static {
      initDefaults();
   }

   static final class JaXSubProcess implements Runnable {
      private static final Logger LOGGER = Logger.getLogger("weblogic.utils.JavaExec.JaXSubProcess");
      boolean isErr;
      String subname;
      InputStream isem;

      JaXSubProcess(boolean iser, InputStream is) {
         this.isErr = iser;
         if (this.isErr) {
            this.subname = "child stderr";
         } else {
            this.subname = "child stdout";
         }

         this.isem = is;
      }

      public void run() {
         DataInputStream dis = new DataInputStream(this.isem);

         try {
            String line;
            try {
               while((line = dis.readLine()) != null) {
                  if (this.isErr) {
                     LOGGER.warning(line);
                  } else {
                     LOGGER.info(line);
                  }
               }
            } catch (IOException var7) {
               LOGGER.log(Level.WARNING, "IOException when running JaXSubProcess", var7);
            }
         } finally {
            LOGGER.fine("Stopped draining " + this.subname);
         }

      }
   }
}
