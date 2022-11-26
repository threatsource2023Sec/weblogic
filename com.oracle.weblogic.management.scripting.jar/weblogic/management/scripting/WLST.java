package weblogic.management.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyInstance;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.__builtin__;
import weblogic.management.scripting.plugin.WLSTPluginsList;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtilHelper;
import weblogic.management.scripting.utils.WLSTUtilWrapperFactory;
import weblogic.utils.StringUtils;

public class WLST {
   private static final String PYTHON_VERBOSE = "python.verbose";
   private static final List PYTHON_VERBOSE_OPTIONS = new ArrayList(Arrays.asList("error", "warning", "message", "comment", "debug"));
   private static final String PYTHON_WARNING = "warning";
   private static final String PYTHON_CACHEDIR_SKIP = "python.cachedir.skip";
   private static final String EMPTY_STRING = "";
   private static final String COMMA = ",";
   private static final String MYPS1 = "myps1";
   private static final String SYS_ARGV_EQUALS = "sys.argv=";
   public static WLSTInterpreter interp = null;
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   private static List wlstOptions = null;
   private static PyList sysArgs = null;
   static boolean isStandalone = false;
   private static final String WLST_UTIL_WRAPPER_CLASS = "weblogic.management.scripting.utils.WLSTUtilWrapper";
   private static final String WLST_CORE_UTIL_WRAPPER_CLASS = "weblogic.management.scripting.core.utils.WLSTCoreUtilWrapper";
   private static WLSTUtilWrapperFactory wlstUtilWrapper = null;
   private static boolean scriptFileExecuted = false;
   private static JLineWrapper jLineWrapper = null;
   private static final String JLINE_OPTION = "-enableJLine";

   public static void main(String[] args) throws Throwable {
      ifHelpExit(args);

      try {
         wlstOptions = getWLSTOptions();
         print(txtFmt.getInitializingWLST());
         String var = System.getProperty("python.verbose");
         if (var != null && !var.equals("null")) {
            if (!PYTHON_VERBOSE_OPTIONS.contains(var)) {
               throw new IllegalArgumentException(txtFmt.getInvalidVerboseOption(var));
            }
         } else {
            System.setProperty("python.verbose", "warning");
         }

         boolean skipWLSModuleScanning = ifSkipWLSModuleScanning(args);
         if (!skipWLSModuleScanning) {
            String cacheDir = System.getProperty("python.cachedir");
            if (cacheDir == null) {
               cacheDir = WLSTUtilHelper.getWLSTTempFile();
               System.setProperty("python.cachedir", cacheDir);
            }

            File packagesDir = new File(cacheDir, "packages");
            if (!packagesDir.exists()) {
               print(txtFmt.getScanningPackage());
            } else if (!packagesDir.canWrite()) {
               cacheDir = WLSTUtilHelper.getWLSTTempFile("WlstTemp");
               System.setProperty("python.cachedir", cacheDir);
               packagesDir = new File(cacheDir, "packages");
               packagesDir.mkdir();
               packagesDir.createNewFile();
               if (!packagesDir.exists()) {
                  print(txtFmt.getScanningPackage());
               }
            }
         }

         setWlstUtilWrapper();
         Hashtable h = new Hashtable();
         if (skipWLSModuleScanning) {
            h.put("skipWLSModuleScanning", new Boolean(true));
         }

         if (args != null && args.length > 0) {
            h.put("enableScriptMode", new Boolean(true));
            if (ifJLineEnabled(args)) {
               initializeJLine();
            }
         } else {
            initializeJLine();
         }

         interp = (new WLSTInterpreter(h)).getWLInterpreter();
         if (!isStandalone) {
            initializeScriptPlugins((String[])WLSTPluginsList.SUBSYSTEM_LIST.toArray(new String[WLSTPluginsList.SUBSYSTEM_LIST.size()]));
         }

         print(txtFmt.getWelcome());
         print(txtFmt.getHelpInfo());
         boolean scriptFilesPassed = false;
         if (sysArgs == null) {
            sysArgs = new PyList();
         }

         args = checkOptions(args, interp);
         String codeString;
         if (args != null && args.length > 0 && !scriptFileExecuted) {
            codeString = "";
            int k = 0;

            while(true) {
               if (k >= args.length) {
                  interp.exec("sys.argv=" + sysArgs);
                  File f = new File(codeString);
                  FileInputStream is = null;
                  if (!f.isFile() || !f.canRead()) {
                     if (codeString.indexOf("-") == 0) {
                        System.out.println(txtFmt.getCommandLineHelp());
                        System.exit(0);
                     }

                     throw new FileNotFoundException(codeString);
                  }

                  is = new FileInputStream(f.getCanonicalFile());
                  interp.set("WLST_FILE_NAME_LAST_EXECUTED", codeString);
                  interp.execfile(is, f.getCanonicalPath());
                  break;
               }

               if (k == 0) {
                  codeString = args[k];
                  sysArgs.append(new PyString(codeString));
                  scriptFilesPassed = true;
               } else {
                  sysArgs.append(new PyString(args[k]));
               }

               ++k;
            }
         }

         codeString = "";
         String ps1 = interp.get("myps1").toString();
         PyObject promt = new PyString(ps1);
         PyObject dotPromt = new PyString("...");
         StringBuilder sb = new StringBuilder();

         while(!scriptFilesPassed) {
            if (interp.getDisconnected()) {
               interp.exec("init()");
               interp.exec("evaluatePrompt()");
            } else {
               interp.exec("restoreDisplay()");
            }

            if (!ps1.equals(interp.get("myps1").toString())) {
               ps1 = interp.get("myps1").toString();
               promt = new PyString(ps1);
            }

            try {
               codeString = __builtin__.raw_input(promt);
            } catch (PyException var17) {
               if (var17.toString().indexOf("EOFError: raw_input()") != -1 && var17.traceback.tb_lineno == 0) {
                  return;
               }

               throw var17;
            }

            if (codeString != null) {
               if (interp.getEaseSyntax()) {
                  codeString = WLSTUtilHelper.convertEasySyntax(codeString);
               }

               if (interp.getRecordAll()) {
                  wlstUtilWrapper.getInfoHandler().write(codeString);
               }

               sb.delete(0, sb.length());
               sb.trimToSize();
               sb.append(codeString);
               interp.exec("originalErr=sys.stderr");

               String s;
               for(; interp.runsource(sb.toString(), "<console>", jLineWrapper); sb.append("\n").append(s)) {
                  s = __builtin__.raw_input(dotPromt);
                  if (interp.getRecordAll() && !wlstUtilWrapper.usingCommand("stopRecording")) {
                     wlstUtilWrapper.getInfoHandler().write("  " + s);
                  }
               }

               interp.exec("sys.stderr=originalErr");
            } else {
               if (interp.getRecordingInProgress()) {
                  interp.exec("stopRecording()");
               }

               if (isStandalone || !wlstUtilWrapper.isEditSessionInProgress()) {
                  System.out.println(txtFmt.getExitingWLST());
                  return;
               }

               wlstUtilWrapper.println(txtFmt.getExitEdit());
               interp.exec("stopEdit('y')");
            }
         }

      } catch (PyException var18) {
         PyException pe = var18;

         try {
            exitIfSystemExit(pe);
         } catch (Throwable var16) {
            var16.printStackTrace();
         }

         throw var18;
      } finally {
         if (jLineWrapper != null) {
            jLineWrapper.cleanup();
         }

      }
   }

   private static boolean ifJLineEnabled(String[] args) {
      if (args != null && args.length != 0) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("-enableJLine")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static void setWlstUtilWrapper() {
      if (WLST.class.getClassLoader().getResource("weblogic.management.scripting.utils.WLSTUtilWrapper".replace('.', '/') + ".class") == null) {
         isStandalone = true;
      }

      Class clz;
      try {
         if (!isStandalone) {
            clz = Class.forName("weblogic.management.scripting.utils.WLSTUtilWrapper", true, Thread.currentThread().getContextClassLoader());
         } else {
            clz = Class.forName("weblogic.management.scripting.core.utils.WLSTCoreUtilWrapper", true, Thread.currentThread().getContextClassLoader());
         }
      } catch (ClassNotFoundException var3) {
         throw new IllegalStateException(var3);
      }

      try {
         Method method = clz.getMethod("getInstance");
         wlstUtilWrapper = (WLSTUtilWrapperFactory)method.invoke((Object)null, (Object[])null);
      } catch (Exception var2) {
         throw new IllegalStateException(var2);
      }
   }

   private static void print(String s) {
      System.out.println(s);
   }

   private static void ifHelpExit(String[] args) {
      if (args != null && args.length == 1 && args[0].equals("-help")) {
         System.out.println(txtFmt.getCommandLineHelp());
         System.exit(0);
      }

   }

   private static boolean ifSkipWLSModuleScanning(String[] args) {
      String skipCacheDir = System.getProperty("python.cachedir.skip");
      if (skipCacheDir != null && skipCacheDir.equalsIgnoreCase("true")) {
         return true;
      } else if (args != null && args.length != 0) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("-skipWLSModuleScanning")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static void exitIfSystemExit(PyException exc) {
      if (exc != null) {
         exc.printStackTrace();
      }

      if (Py.matchException(exc, Py.SystemExit)) {
         PyObject value = exc.value;
         if (value instanceof PyInstance) {
            PyObject tmp = value.__findattr__("code");
            if (tmp != null) {
               value = tmp;
            }
         }

         Py.getSystemState().callExitFunc();
         if (value instanceof PyInteger) {
            System.exit(((PyInteger)value).getValue());
         } else {
            if (value != Py.None) {
               try {
                  Py.println(value);
                  System.exit(1);
               } catch (Throwable var3) {
               }
            }

            System.exit(0);
         }
      }

   }

   private static void initializeScriptPlugins(String[] classList) {
      StringBuffer subSystemBuffer = new StringBuffer();

      for(int count = 0; count < classList.length; ++count) {
         subSystemBuffer.append("import " + classList[count] + "\n");
         subSystemBuffer.append("from " + classList[count] + " import *\n");
         subSystemBuffer.append(classList[count] + ".setInterpreter(theInterpreter)\n");
         subSystemBuffer.append(classList[count] + ".initialize()\n");
      }

      interp.exec(subSystemBuffer.toString());
   }

   private static List getWLSTOptions() {
      wlstOptions = new ArrayList();
      wlstOptions.add("-easeSyntax");
      wlstOptions.add("-loadProperties");
      wlstOptions.add("-online");
      wlstOptions.add("-offline");
      wlstOptions.add("-i");
      wlstOptions.add("-skipWLSModuleScanning");
      wlstOptions.add("-enableJLine");
      return wlstOptions;
   }

   private static String[] checkOptions(String[] args, WLSTInterpreter interp) {
      if (args != null && args.length != 0 && (args.length != 1 || args[0] == null || !args[0].equals(""))) {
         List options = getWLSTOptions();
         String newArgs = null;
         List all = new ArrayList();

         for(int k = 0; k < args.length; ++k) {
            all.add(args[k]);
         }

         boolean changedArgs = false;

         for(int i = 0; i < args.length; ++i) {
            if (options.contains(args[i])) {
               if (args[i].equals("-easeSyntax")) {
                  interp.setEaseSyntax(true);
                  System.out.println(txtFmt.getEaseSyntaxEnabled());
                  all.remove(args[i]);
               } else {
                  String fileName;
                  if (args[i].equals("-loadProperties")) {
                     try {
                        fileName = args[i + 1];
                        WLSTUtilHelper.setProperties(fileName, interp);
                        all.remove(args[i]);
                        all.remove(args[i + 1]);
                     } catch (ArrayIndexOutOfBoundsException var13) {
                        System.out.println(txtFmt.getSpecifyPropertiesLocation());
                        all.remove(args[i]);
                     }
                  } else if (args[i].equals("-skipWLSModuleScanning")) {
                     all.remove(args[i]);
                  } else if (args[i].equals("-enableJLine")) {
                     all.remove(args[i]);
                  } else if (args[i].equals("-i")) {
                     try {
                        fileName = args[i + 1];
                        List all_ = new ArrayList();

                        int k;
                        for(k = 0; k < args.length; ++k) {
                           all_.add(args[k]);
                        }

                        for(k = 0; k < args.length; ++k) {
                           if (options.contains(args[k])) {
                              int ind;
                              if (args[k].equals("-skipWLSModuleScanning")) {
                                 ind = all_.indexOf("-skipWLSModuleScanning");
                                 all_.remove(ind);
                              }

                              if (args[k].equals("-easeSyntax")) {
                                 ind = all_.indexOf("-easeSyntax");
                                 all_.remove(ind);
                              } else if (args[k].equals("-loadProperties")) {
                                 ind = all_.indexOf("-loadProperties");
                                 all_.remove(ind);
                                 all_.remove(ind);
                              } else if (args[k].equals("-i")) {
                                 ind = all_.indexOf("-i");
                                 all_.remove(ind);
                                 all_.remove(ind);
                              }
                           }
                        }

                        String[] args_ = new String[all_.size()];
                        Iterator iter_ = all_.iterator();
                        int h = false;
                        sysArgs = new PyList();
                        sysArgs.append(new PyString(fileName));

                        while(iter_.hasNext()) {
                           PyString s = new PyString((String)iter_.next());
                           sysArgs.append(s);
                        }

                        interp.exec("sys.argv=" + sysArgs);
                        interp.execfile(fileName);
                        scriptFileExecuted = true;
                        all.remove(args[i]);
                        all.remove(args[i + 1]);
                     } catch (ArrayIndexOutOfBoundsException var14) {
                        System.out.println(txtFmt.getNoFileNameSpecified());
                        all.remove(args[i]);
                     }
                  }
               }

               changedArgs = true;
            }
         }

         if (!changedArgs) {
            return args;
         } else {
            Iterator iter = all.iterator();

            while(iter.hasNext()) {
               if (newArgs != null) {
                  newArgs = newArgs + (String)iter.next() + ",";
               } else {
                  newArgs = (String)iter.next() + ",";
               }
            }

            String[] news = null;
            if (newArgs != null) {
               news = StringUtils.splitCompletely(newArgs, ",");
               if (news.length == 0) {
                  news = new String[]{newArgs};
               }
            }

            return news;
         }
      } else {
         return null;
      }
   }

   private static void initializeJLine() {
      if (!isStandalone && !Boolean.getBoolean("wlst.nojline") && File.pathSeparatorChar != ';') {
         try {
            Class homeClass = Class.forName("weblogic.Home");
            Method getFileMethod = homeClass.getMethod("getMiddlewareHomePath", (Class[])null);
            String homePath = (String)getFileMethod.invoke((Object)null, (Object[])null);
            String jlinePath = (new File(homePath)).getAbsolutePath() + File.separator + "coherence" + File.separator + "lib" + File.separator + "jline.jar";
            File jlineJar = new File(jlinePath);
            if (!jlineJar.exists()) {
               return;
            }

            URL jlineURL = jlineJar.toURI().toURL();
            URL[] urls = new URL[]{jlineURL};
            ClassLoader parent = Thread.currentThread().getContextClassLoader();
            if (parent == null) {
               parent = WLST.class.getClassLoader();
            }

            ClassLoader jlineCls = new URLClassLoader(urls, parent);
            File historyFile = new File(System.getProperty("user.home"), ".jline-WLST.history");
            jLineWrapper = new JLineWrapper(jlineCls, historyFile);
            Set filePerms = new HashSet();
            filePerms.add(PosixFilePermission.OWNER_READ);
            filePerms.add(PosixFilePermission.OWNER_WRITE);
            Files.setPosixFilePermissions(Paths.get(historyFile.getAbsolutePath()), filePerms);
         } catch (Throwable var11) {
            if (Boolean.getBoolean("wlst.debug.init")) {
               var11.printStackTrace();
            }
         }

      }
   }

   public static boolean isJLineInitialized() {
      return jLineWrapper != null;
   }

   public static String promptForPassword() {
      return jLineWrapper != null ? jLineWrapper.readPassword() : null;
   }
}
