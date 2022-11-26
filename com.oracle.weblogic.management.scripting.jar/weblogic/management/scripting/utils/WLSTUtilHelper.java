package weblogic.management.scripting.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import weblogic.management.scripting.WLST;
import weblogic.utils.StringUtils;

public class WLSTUtilHelper extends PyObject {
   protected static WLSTInterpreter theInterpreter = null;
   public static boolean wlstAsModule = true;
   private static final boolean debugInitEnabled = Boolean.getBoolean("wlst.debug.init");
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   private static final String[] easySyntaxCommandsArray = new String[]{"cd", "ls", "prompt", "reset", "debug", "help", "exit", "get", "man"};
   private static final String[] easySyntaxNoArgCommandsArray = new String[]{"config", "runtime", "reset", "dumpVariables", "disconnect", "adminConfig", "custom", "serverConfig", "serverRuntime", "domainRuntime", "domainConfig", "easeSyntax", "dumpStack", "debug", "pwd"};
   public static final String WLST_SCRIPT_DIR = "wlstScriptDir";
   public static final String WLST_SCRIPT_DIR_LIB = "wlstScriptDir/lib";
   private static final List easySyntaxCommands;
   private static final List easySyntaxNoArgCommands;

   public static boolean runningWLSTAsModule() {
      return wlstAsModule;
   }

   public static void debugInit(String msg) {
      if (debugInitEnabled) {
         System.out.println("<wlst-init> " + msg);
      }

   }

   public static void debugInit(String msg, Throwable t) {
      if (debugInitEnabled) {
         System.out.println("<wlst-init> " + msg);
         t.printStackTrace();
      }

   }

   public static void setProperties(String propertiesFile, InteractiveInterpreter interp) {
      if (propertiesFile != null && propertiesFile.length() > 0) {
         try {
            File file = new File(propertiesFile);
            Properties props = new Properties();
            FileReader freader = new FileReader(file);
            props.load(freader);
            Enumeration enumProps = props.keys();

            while(enumProps.hasMoreElements()) {
               String propertyName = (String)enumProps.nextElement();
               String value = props.getProperty(propertyName);
               interp.set(propertyName, value);
            }

            WLST.interp = (WLSTInterpreter)interp;
         } catch (FileNotFoundException var8) {
            System.out.println(txtFmt.getPropertiesFileNotFound(propertiesFile));
         } catch (IOException var9) {
            System.out.println(txtFmt.getPropertiesFileNotReadable(propertiesFile));
         }

      } else {
         System.out.println(txtFmt.getPropertiesFileNull());
      }
   }

   public static String convertEasySyntax(String code) {
      String[] cmdArgs = StringUtils.split(code, ' ');
      if (cmdArgs.length > 0) {
         String cmd = cmdArgs[0];
         String args = cmdArgs.length > 1 ? cmdArgs[1] : "";
         if (easySyntaxCommands.contains(cmd)) {
            return convertParamSignature(cmd, args);
         }

         if (easySyntaxNoArgCommands.contains(cmd)) {
            return convertNoParamSignature(cmd);
         }
      }

      return code;
   }

   private static String convertNoParamSignature(String cmd) {
      cmd = cmd + "()";
      return cmd;
   }

   private static String convertParamSignature(String cmd, String args) {
      if (args != null && !"".equals(args)) {
         String[] splitArgs = StringUtils.splitCompletely(args, " ");
         return splitArgs.length == 0 ? convertNoParamSignature(cmd) : cmd + "(\"" + splitArgs[0] + "\")";
      } else {
         return convertNoParamSignature(cmd);
      }
   }

   public static String getWLSTTempFile() {
      return getWLSTTempFile("wlstTemp");
   }

   public static String getWLSTTempFile(String wlstTempDirName) {
      boolean debug = false;
      String debugFlag = System.getProperty("wlst.debug.init", "false");
      if (debugFlag.equals("true")) {
         debug = true;
      }

      String tempDir = System.getProperty("java.io.tmpdir");
      debugInit("Temp Dir evaluated to " + tempDir);
      File f1 = new File(tempDir);
      String user = System.getProperty("user.name");
      if (user == null) {
         user = "nouser";
      }

      if (f1.canWrite()) {
         debugInit("Create a temp dir: " + tempDir + "/" + wlstTempDirName + user);
         f1 = new File(tempDir + "/" + wlstTempDirName + user);
         f1.mkdirs();
         if (f1.canWrite()) {
            return f1.getAbsolutePath();
         } else {
            debugInit("Could not write to " + f1 + ", hence we will create another temp file with no username: " + tempDir + "/" + wlstTempDirName + "nouser");
            f1 = new File(tempDir + "/" + wlstTempDirName + "nouser");

            try {
               f1.mkdirs();
               f1.createNewFile();
            } catch (IOException var8) {
               debugInit("IOException occurred: " + var8);
               return f1.getAbsolutePath();
            }

            f1.deleteOnExit();
            return f1.getAbsolutePath();
         }
      } else {
         debugInit("Could not write to " + tempDir + " hence we will create another temp file");
         File f = new File(tempDir + "WLSTTemp" + user);

         try {
            f.mkdirs();
            f.createNewFile();
         } catch (IOException var9) {
            debugInit("IOException occured: " + var9);
            return f.getAbsolutePath();
         }

         f.deleteOnExit();
         return f.getAbsolutePath();
      }
   }

   public static List getWLSTModules() {
      List modules = new ArrayList();

      try {
         String home = "";
         File wlstHome = new File(home);
         File[] files = wlstHome.listFiles();

         for(int i = 0; i < files.length; ++i) {
            if (!files[i].isDirectory() && files[i].getName().endsWith(".py")) {
               debugInit("Add WLST module " + files[i]);
               modules.add(files[i]);
            }
         }

         getModulesForHome(System.getProperty("weblogic.wlstHome", ""), modules);
         if (theInterpreter != null) {
            getModulesForHome(theInterpreter.getScriptDir(), modules);
         }
      } catch (Throwable var5) {
      }

      return modules;
   }

   public static List getWLSTJarModules() {
      List modules = new ArrayList();

      try {
         Enumeration dirURLs = WLSTUtilHelper.class.getClassLoader().getResources("wlstScriptDir");
         if (dirURLs == null || !dirURLs.hasMoreElements()) {
            debugInit("No WLST script dirs found in jars ");
            return modules;
         } else {
            while(true) {
               URL dirURL;
               String entry;
               do {
                  if (!dirURLs.hasMoreElements()) {
                     return modules;
                  }

                  dirURL = (URL)dirURLs.nextElement();
                  if (dirURL != null && dirURL.getProtocol().equals("file")) {
                     String[] files = (new File(dirURL.toURI())).list();
                     if (files != null && files.length > 0) {
                        String[] var4 = files;
                        int var5 = files.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                           entry = var4[var6];
                           File f = new File(entry);
                           if (!f.isDirectory() && f.getName().endsWith(".py")) {
                              debugInit("Add WLST jar module " + f);
                              modules.add(f.toURI().toURL());
                           }
                        }
                     }
                  }
               } while(!dirURL.getProtocol().equals("jar"));

               String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
               JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
               Enumeration entries = jar.entries();
               debugInit("Look in jar " + jarPath);

               while(entries.hasMoreElements()) {
                  String name = ((JarEntry)entries.nextElement()).getName();
                  if (name.startsWith("wlstScriptDir/")) {
                     entry = name.substring("wlstScriptDir".length() + 1);
                     if (entry.endsWith("/")) {
                        entry = entry.substring(0, entry.length() - 1);
                     }

                     if (entry.endsWith(".py") && entry.indexOf(47) == -1) {
                        debugInit("Add WLST jar module " + jarPath + "/" + entry);
                        modules.add(new URL(jarPath + "/" + entry));
                     }
                  }
               }
            }
         }
      } catch (Throwable var9) {
         return modules;
      }
   }

   public static void startProcess(Process process, String processName, boolean drain) {
      WLSTProcess.startIOThreads(process, processName, drain);
   }

   public static void startProcess(Process process, String processName, boolean drain, String serverLog) {
      WLSTProcess.startIOThreads(process, processName, drain, serverLog);
   }

   public static void destroyWLSTProcesses(String processName) {
      WLSTProcess.destroyProcesses(processName);
   }

   public static Process getWLSTProcess(String processName) {
      return WLSTProcess.getProcess(processName);
   }

   public static void getModulesForHome(String home, List modules) {
      if (home != null && home.length() != 0) {
         String[] homes = home.trim().split(File.pathSeparator);

         for(int i = 0; i < homes.length; ++i) {
            if (!StringUtils.isEmptyString(homes[i])) {
               File _wlstHome = new File(homes[i]);
               File[] _files = _wlstHome.listFiles();

               for(int j = 0; j < _files.length; ++j) {
                  if (!_files[j].isDirectory() && _files[j].getName().endsWith(".py")) {
                     debugInit("Add Home module " + _files[j]);
                     modules.add(_files[j]);
                  }
               }
            }
         }
      }

   }

   public static String logClassLoaderHierarchy(ClassLoader cl) {
      String result = "WLST ClassLoader is set to " + logClassLoader(cl);
      if (cl != null) {
         ClassLoader sysCl = ClassLoader.getSystemClassLoader();

         for(ClassLoader currentCl = cl.getParent(); currentCl != null; currentCl = currentCl.getParent()) {
            if (currentCl == sysCl) {
               result = result + "\nparent ClassLoader is the Java System ClassLoader";
               break;
            }

            result = result + "\n parent ClassLoader is " + logClassLoader(currentCl);
         }
      }

      return result;
   }

   public static String logClassLoader(ClassLoader cl) {
      if (cl == null) {
         return "null";
      } else {
         String result = cl.getClass().getName() + " (" + cl + ") ";
         if (cl instanceof URLClassLoader) {
            URL[] classpath = ((URLClassLoader)cl).getURLs();
            int len = 0;
            if (classpath != null) {
               len = classpath.length;
            }

            if (len > 0) {
               result = result + "\n\tClassLoader (" + cl + ") classpath contains " + len + " elements:";
            }

            for(int i = 0; i < len; ++i) {
               result = result + "\n\t\t" + i + ".) " + classpath[i].getPath();
            }
         }

         return result;
      }
   }

   static {
      easySyntaxCommands = Arrays.asList(easySyntaxCommandsArray);
      easySyntaxNoArgCommands = Arrays.asList(easySyntaxNoArgCommandsArray);
   }
}
