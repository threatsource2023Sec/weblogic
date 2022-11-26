package weblogic.management.scripting.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import org.python.util.PythonInterpreter;
import weblogic.Home;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.management.scripting.InformationHandler;
import weblogic.management.scripting.WLScriptContext;
import weblogic.management.scripting.core.utils.WLSTCoreUtil;
import weblogic.management.utils.PDevHelper;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;

public class WLSTUtil extends WLSTCoreUtil {
   private static ClassLoader pdevCls = null;
   private static ClassLoader pluginCls = null;
   private static ArrayList sysProps = new ArrayList();
   private static ArrayList jvmArgs = new ArrayList();
   private static final String NULL = "<null>";
   private static final String STARTNODEMANAGER_SH = "startNodeManager.sh";
   private static final String STARTNODEMANAGER_WIN = "startNodeManager.cmd";
   private static final String STOPNODEMANAGER_SH = "stopNodeManager.sh";
   private static final String STOPNODEMANAGER_WIN = "stopNodeManager.cmd";

   private static InputStream getWLSTScript() {
      return WLSTUtil.class.getResourceAsStream("wlst.py");
   }

   private static InputStream getWLSTCommonScript() {
      return WLSTUtil.class.getResourceAsStream("wlst_common.py");
   }

   private static InputStream getWLSTCoreScript() {
      return WLSTCoreUtil.class.getResourceAsStream("wlst_core.py");
   }

   private static InputStream getWLSTNMScript() {
      return WLSTCoreUtil.class.getResourceAsStream("wlst_nm.py");
   }

   public static void writeWLSTAsModule(String path) throws Throwable {
      InputStream is = WLSTUtil.class.getResourceAsStream("modules/wlstModule.py");
      File file = new File(path);
      FileUtils.writeToFile(is, file);
   }

   public static String getWLSTScriptPath() throws IOException {
      InputStream is = WLSTUtil.class.getResourceAsStream("wlst.py");
      File file = File.createTempFile("wlst_module1", ".py");
      file.deleteOnExit();
      FileUtils.writeToFile(is, file);
      return file.getAbsolutePath();
   }

   public static String getWLSTCommonModulePath() throws IOException {
      InputStream is = WLSTUtil.class.getResourceAsStream("wlstCommonModule.py");
      File file = File.createTempFile("wlst_module2", ".py");
      file.deleteOnExit();
      FileUtils.writeToFile(is, file);
      return file.getAbsolutePath();
   }

   public static String getOfflineWLSTScriptPath() {
      ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

      String var8;
      try {
         Class var1 = WLSTUtil.class;
         synchronized(WLSTUtil.class) {
            if (pdevCls == null) {
               pdevCls = PDevHelper.getPDevClassLoader(WLSTUtil.class.getClassLoader());
            }
         }

         Thread.currentThread().setContextClassLoader(pdevCls);
         var8 = getOfflineWLSTScriptPathInternal();
      } finally {
         Thread.currentThread().setContextClassLoader(prevCls);
      }

      return var8;
   }

   public static String getOfflineWLSTScriptPathInternal() {
      String path = null;
      String debugFlag = System.getProperty("wlst.debug.init", "false");
      boolean debug = false;
      if (debugFlag.equals("true")) {
         debug = true;
      }

      try {
         Class clz = Class.forName("com.oracle.cie.domain.script.jython.WLST_offline", true, Thread.currentThread().getContextClassLoader());
         Method mthd = clz.getMethod("getWLSTOfflineInitFilePath", (Class[])null);
         path = (String)mthd.invoke(clz, (Object[])null);
         debugInit("The WLST Offline script path has been evaluated to " + path);
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException("Could not find the OffLine WLST class ", var5);
      } catch (NoSuchMethodException var6) {
         throw new RuntimeException("Could not find the method while getting the WLSTOffLineScript path", var6);
      } catch (IllegalAccessException var7) {
         throw new RuntimeException("Illegal Access while getting the WLSTOffLineScript path", var7);
      } catch (InvocationTargetException var8) {
         debugInit("Encountered java.lang.reflect.InvocationTargetException, calling retriedClassPath");
         path = retriedClassPath();
         if (path == null) {
            debugInit("Re-attempt to get the offline path failed");
            throw new RuntimeException("Invocation Target exception while getting the WLSTOffLineScript path", var8);
         }
      }

      return path;
   }

   private static String retriedClassPath() {
      debugInit("re-attempt to get the offline path");
      int retryAttemps = Integer.parseInt(System.getProperty("wlst.offline.retry", "3"));
      long retrySleeptime = Long.parseLong(System.getProperty("wlst.offline.retrysleeptime", "2000"));
      debugInit("retryAttemps == " + retryAttemps);
      debugInit("retrySleeptime == " + retrySleeptime);
      boolean isRetrySucceed = true;
      String path = null;

      for(int attempt = 1; attempt <= retryAttemps; ++attempt) {
         try {
            debugInit("Retry, attempt == " + attempt);
            Class clz = Class.forName("com.oracle.cie.domain.script.jython.WLST_offline", true, Thread.currentThread().getContextClassLoader());
            Method mthd = clz.getMethod("getWLSTOfflineInitFilePath", (Class[])null);
            path = (String)mthd.invoke(clz, (Object[])null);
         } catch (Exception var9) {
            if (var9 instanceof InvocationTargetException) {
               isRetrySucceed = false;
            }
         }

         if (isRetrySucceed || attempt == retryAttemps) {
            break;
         }

         try {
            Thread.sleep(retrySleeptime);
         } catch (InterruptedException var8) {
         }
      }

      return path;
   }

   public static String writeKshFile() {
      String weblogic90Location = (new File(Home.getFile().getParentFile().getAbsolutePath())).getAbsolutePath();
      String wlscontrol = weblogic90Location + File.separator + "common" + File.separator + "bin" + File.separator + "wlscontrol.sh";
      wlscontrol = StringUtils.replaceGlobal(wlscontrol, File.separator, "/");
      return wlscontrol;
   }

   public static String getOfflineWLSTScriptForModule() {
      String s = "";
      s = s + "\nWLS.setup(1)";
      s = s + "\n";
      return s;
   }

   private static InputStream getNonSupportedOnlineWLSTScript() {
      return WLSTUtil.class.getResourceAsStream("online_nonsupported.py");
   }

   private static InputStream getNonSupportedOfflineWLSTScript() {
      return WLSTUtil.class.getResourceAsStream("offline_nonsupported.py");
   }

   private static InputStream getOnlineAppendedWLSTScript() {
      return WLSTUtil.class.getResourceAsStream("online_append.py");
   }

   private static InputStream getNonSupportedPartitionWLSTScript() {
      return WLSTUtil.class.getResourceAsStream("partition_nonsupported.py");
   }

   public static synchronized void initializeOnlineWLST(PyObject[] args, String[] kw) throws Throwable {
      ArgParser ap = new ArgParser("connect", args, kw, "username", "password", "url");
      WLSTInterpreter interp = (WLSTInterpreter)Py.tojava(ap.getPyObject(3), "weblogic.management.scripting.utils.WLSTInterpreter");
      System.setProperty("weblogic.classloader.ThreadContextClassLoader", "true");
      setupOnline(interp);
      WLScriptContext ctx = (WLScriptContext)interp.get("WLS_ON");
      ctx.connect(args, kw);
      if (ctx.getPartitionName() != null && !ctx.getPartitionName().equals("DOMAIN")) {
         InputStream is = getNonSupportedPartitionWLSTScript();
         if (is != null) {
            interp.execfile(is);
         }
      }

      interp.exec("updateGlobals()");
      interp.exec("print ''");
   }

   public static WLSTInterpreter ensureInterpreter() {
      if (theInterpreter == null) {
         theInterpreter = (new WLSTInterpreter()).getWLInterpreter();
      }

      return theInterpreter;
   }

   private static void execWLSTScript(WLSTInterpreter interp, boolean online) {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();

      WLScriptContext ctx;
      PyObject offlineCtx;
      try {
         ctx = ensureWLCtx(interp);
         offlineCtx = interp.get("WLS");
         interp.setOfflineCMO(interp.get("cmo"));
         interp.setOfflinePrompt(interp.get("myps1"));
         InputStream is;
         if (online) {
            is = getWLSTScript();
            if (is != null) {
               interp.execfile(is);
            }
         } else {
            is = getWLSTCoreScript();
            if (is != null) {
               interp.execfile(is);
            }

            is = getWLSTNMScript();
            if (is != null) {
               interp.execfile(is);
            }

            is = getWLSTCommonScript();
            if (is != null) {
               interp.execfile(is);
            }
         }
      } finally {
         Thread.currentThread().setContextClassLoader(tccl);
      }

      if (ctx != null) {
         interp.set("WLS_ON", ctx);
         interp.set("WLS_CORE", ctx);
      }

      if (offlineCtx != null) {
         interp.set("WLS", offlineCtx);
      }

      interp.set("home", (PyObject)null);
      interp.set("adminHome", (PyObject)null);
      interp.set("CMO", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getCmo());
      interp.set("cmo", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getCmo());
      interp.set("mbs", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getMBeanServer());
      interp.set("cmgr", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getConfigManager());
      interp.set("domainRuntimeService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getDomainRuntimeServiceMBean());
      interp.set("runtimeService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getRuntimeServiceMBean());
      interp.set("editService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getEditServiceMBean());
      interp.set("typeService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getMBeanTypeService());
      interp.set("wlstGlobals._editService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getEditService());
      interp.set("nmService", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getNodeManagerService());
      interp.set("scriptMode", ((WLScriptContext)WLScriptContext.class.cast(ctx)).getScriptMode());
   }

   public static WLScriptContext ensureWLCtx(WLSTInterpreter interp) {
      Object o = interp.get("WLS_ON");
      if (o instanceof WLScriptContext) {
         return (WLScriptContext)o;
      } else {
         WLScriptContext ctx = new WLScriptContext();
         interp.set("WLS_ON", ctx);
         interp.set("WLS_CORE", ctx);
         ctx.setWLSTInterpreter(interp);
         return ctx;
      }
   }

   public static void setupOnline(WLSTInterpreter interp) {
      execWLSTScript(interp, true);
      InputStream is = getNonSupportedOfflineWLSTScript();
      if (is != null) {
         interp.execfile(is);
      }

   }

   public static void setupOffline(WLSTInterpreter interp) {
      System.setProperty("weblogic.classloader.ThreadContextClassLoader", "true");
      ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

      List pyJarList;
      Class cl;
      try {
         cl = WLSTUtil.class;
         synchronized(WLSTUtil.class) {
            if (pdevCls == null) {
               pdevCls = PDevHelper.getPDevClassLoader(WLSTUtil.class.getClassLoader());
            }
         }

         Thread.currentThread().setContextClassLoader(pdevCls);
         if (interp != null) {
            interp.setClassLoader(pdevCls);
         }

         pyJarList = setupOfflineInternal(interp);
      } finally {
         Thread.currentThread().setContextClassLoader(prevCls);
      }

      if (interp != null && !WLSTInterpreter.isRunningInServer()) {
         cl = null;
         ClassLoader cl;
         if (!interp.getDisablePluginJarLoadingMode()) {
            Class var4 = WLSTUtil.class;
            synchronized(WLSTUtil.class) {
               if (pluginCls == null) {
                  pluginCls = getPluginClassLoader(pyJarList);
               }
            }

            cl = pluginCls;
            interp.setClassLoader(cl);
            Thread.currentThread().setContextClassLoader(cl);
            GlobalServiceLocator.getServiceLocator();
         } else {
            cl = WLSTUtil.class.getClassLoader();
            interp.setClassLoader(cl);
         }
      }

   }

   private static ClassLoader getPluginClassLoader(List pyJarList) {
      URL[] pyJarURLs = new URL[pyJarList.size()];

      for(int i = 0; i < pyJarList.size(); ++i) {
         try {
            File pyJar = (File)pyJarList.get(i);
            debugInit("Adding jar file: " + pyJar.getAbsolutePath());
            pyJarURLs[i] = pyJar.toURI().toURL();
         } catch (MalformedURLException var4) {
            throw new RuntimeException(var4);
         }
      }

      ClassLoader cl = DescriptorClassLoader.getClassLoader();
      if (cl == null) {
         cl = WLSTUtil.class.getClassLoader();
      }

      if (pyJarURLs.length != 0) {
         cl = new URLClassLoader(pyJarURLs, (ClassLoader)cl);
      }

      debugInit(WLSTUtilHelper.logClassLoaderHierarchy((ClassLoader)cl));
      return (ClassLoader)cl;
   }

   private static List setupOfflineInternal(WLSTInterpreter interp) {
      theInterpreter = interp;
      PyObject ctx = interp.get("WLS_ON");
      PyObject ns = interp.get("nmService");
      PyObject es = interp.get("wlstGlobals._editService");
      PyObject offlineCtx = interp.get("WLS");
      execWLSTScript(interp, false);
      if (ctx != null) {
         interp.set("WLS_ON", ctx);
         interp.set("WLS_CORE", ctx);
      }

      if (ns != null) {
         interp.set("nmService", ns);
      }

      if (es != null) {
         interp.set("wlstGlobals._editService", es);
      }

      interp.execfile(getOfflineWLSTScriptPathInternal());
      InputStream is = getOnlineAppendedWLSTCoreScript();
      if (is != null) {
         interp.execfile(is);
      }

      is = getOnlineAppendedWLSTScript();
      if (is != null) {
         interp.execfile(is);
      }

      if (offlineCtx != null) {
         interp.set("WLS", offlineCtx);
      }

      if (interp.getOfflineCMO() != null) {
         interp.set("cmo", interp.getOfflineCMO());
      }

      if (interp.getOfflinePrompt() != null) {
         interp.set("myps1", interp.getOfflinePrompt());
      }

      is = getNonSupportedOnlineWLSTScript();
      if (is != null) {
         interp.execfile(is);
      }

      return initOfflineContext(interp, "WLS");
   }

   public static List initOfflineContext(WLSTInterpreter interp, String key) {
      try {
         Class clz = Class.forName("com.oracle.cie.domain.script.jython.WLST_offline", true, Thread.currentThread().getContextClassLoader());
         Class[] params = new Class[]{PythonInterpreter.class, Boolean.TYPE, Boolean.TYPE, String.class};
         Method mthd = clz.getMethod("setupContext", params);
         Object[] args = new Object[]{interp, new Boolean(interp.getScriptMode()), new Boolean(interp.getOnlineOnlyMode()), key};
         return (List)mthd.invoke(clz, args);
      } catch (Throwable var6) {
         var6.printStackTrace();
         return new ArrayList();
      }
   }

   private static void unsupportPyMethod(InteractiveInterpreter interp, String sig) {
      String msg = "This command is not supported while connected to a running server";
      String action = "def " + sig + ":\n  print ''\n  print '" + msg + "'\n  print ''\n  return\n";
      interp.exec(action);
   }

   public static void addJvmArg(String s) {
      jvmArgs.add(s);
   }

   /** @deprecated */
   @Deprecated
   public static void addJvmArgs(String s) {
      String[] args = StringUtils.splitCompletely(s, " ");

      for(int i = 0; i < args.length; ++i) {
         addJvmArg(args[i]);
      }

   }

   public static void addSystemProp(String k, String v) {
      if (!v.equals("<null>")) {
         sysProps.add(k + '=' + v);
      } else {
         sysProps.add(k);
      }

   }

   public static void addSystemProps(Properties p) {
      Enumeration e = p.keys();

      while(e.hasMoreElements()) {
         String k = (String)e.nextElement();
         String v = (String)p.get(k);
         addSystemProp(k, v);
      }

   }

   public static Process startNodeManagerScript(File rootDir) throws IOException {
      return startNodeManagerScript(rootDir, (Map)null);
   }

   public static Process startNodeManagerScript(File rootDir, Map envVarsToChange) throws IOException {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      String cmd = null;
      if (isWinSystem()) {
         cmd = "startNodeManager.cmd";
      } else {
         cmd = "startNodeManager.sh";
      }

      File scriptDir = new File(rootDir.getAbsolutePath() + File.separator + "bin" + File.separator);
      File scriptFile = new File(scriptDir, cmd);
      if (!scriptFile.exists()) {
         ctx.println(ctx.getWLSTMsgFormatter().getErrorFileNotExist(scriptFile.getAbsolutePath()));
         return null;
      } else {
         ProcessBuilder pb = isWinSystem() ? new ProcessBuilder(new String[]{scriptFile.getAbsolutePath()}) : new ProcessBuilder(new String[]{"/bin/sh", scriptFile.getAbsolutePath()});
         Map env = pb.environment();
         ArrayList strV = new ArrayList();
         String dashD = "-D";
         Iterator i = sysProps.iterator();

         while(i.hasNext()) {
            String s = (String)i.next();
            strV.add(dashD + s);
         }

         StringBuffer javaSysProps = new StringBuffer();
         Iterator i = strV.iterator();

         while(i.hasNext()) {
            javaSysProps.append((String)i.next());
            javaSysProps.append(" ");
         }

         StringBuffer javaVmArgs = new StringBuffer();
         Iterator i = jvmArgs.iterator();

         while(i.hasNext()) {
            javaVmArgs.append((String)i.next());
            javaVmArgs.append(" ");
         }

         env.put("JAVA_PROPERTIES", javaVmArgs.toString());
         env.put("JAVA_OPTIONS", javaSysProps.toString());
         if (envVarsToChange != null) {
            i = envVarsToChange.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry mEntry = (Map.Entry)i.next();
               env.put(mEntry.getKey(), mEntry.getValue());
            }
         }

         ctx.println(ctx.getWLSTMsgFormatter().getRunningNMScriptMessage(cmd, scriptDir.getAbsolutePath()));
         return pb.start();
      }
   }

   public static Process stopNodeManagerScript(File rootDir, Map envVarsToChange) throws Exception {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      String cmd = null;
      if (isWinSystem()) {
         cmd = "stopNodeManager.cmd";
      } else {
         cmd = "stopNodeManager.sh";
      }

      File scriptDir = new File(rootDir.getAbsolutePath() + File.separator + "bin" + File.separator);
      File scriptFile = new File(scriptDir, cmd);
      if (!scriptFile.exists()) {
         ctx.println(ctx.getWLSTMsgFormatter().getErrorFileNotExist(scriptFile.getAbsolutePath()));
         return null;
      } else {
         ProcessBuilder pb = isWinSystem() ? new ProcessBuilder(new String[]{scriptFile.getAbsolutePath()}) : new ProcessBuilder(new String[]{"/bin/sh", scriptFile.getAbsolutePath()});
         Map env = pb.environment();
         if (envVarsToChange != null) {
            Iterator var8 = envVarsToChange.entrySet().iterator();

            while(var8.hasNext()) {
               Map.Entry mEntry = (Map.Entry)var8.next();
               env.put(mEntry.getKey(), mEntry.getValue());
            }
         }

         ctx.println(ctx.getWLSTMsgFormatter().getRunningNMScriptMessage(cmd, scriptDir.getAbsolutePath()));
         return pb.start();
      }
   }

   public static File findWLHome() {
      File wlHome = null;
      String wlstHome = System.getProperty("weblogic.wlstHome");
      debugInit("weblogic.wlstHome is " + wlstHome + ", File.pathSeparator = " + File.pathSeparator);
      int moduleIndex;
      if (wlstHome != null) {
         String[] pathEntries = wlstHome.split(File.pathSeparator);

         for(int i = 0; i < pathEntries.length; ++i) {
            debugInit("pathEntries[" + i + "] = " + pathEntries[i]);
            moduleIndex = pathEntries[i].indexOf("/wlserver/common/wlst");
            if (moduleIndex > -1) {
               wlHome = new File(pathEntries[i].substring(0, moduleIndex) + "/wlserver/server");
               break;
            }
         }
      }

      if (wlHome == null) {
         String classLocation = WLSTUtil.class.getName().replace('.', '/') + ".class";
         String path = WLSTUtil.class.getClassLoader().getResource(classLocation).getPath();
         debugInit("path to " + classLocation + " is " + path);
         if (path != null) {
            int moduleIndex = true;
            int fileIndex = path.indexOf("file:");
            if (fileIndex == -1) {
               moduleIndex = path.lastIndexOf("/wlserver/server/");
               if (moduleIndex > -1) {
                  wlHome = new File(path.substring(0, moduleIndex) + "/wlserver/server");
               }
            } else {
               moduleIndex = path.lastIndexOf("/wlserver/modules/");
               if (moduleIndex > -1) {
                  wlHome = new File(path.substring((new String("file:")).length(), moduleIndex) + "/wlserver/server");
               } else if (moduleIndex == -1) {
                  moduleIndex = path.lastIndexOf("/wlserver/server/");
                  if (moduleIndex > -1) {
                     wlHome = new File(path.substring((new String("file:")).length(), moduleIndex) + "/wlserver/server");
                  }
               }
            }
         }
      }

      return wlHome;
   }

   private static boolean isWinSystem() {
      return File.pathSeparatorChar == ';';
   }

   public static void println(String s, Exception ex) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.println(ctx.getWLSTMsgFormatter().getPythonExecError(s, ex));
   }

   public static InformationHandler getInfoHandler() {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      return ctx.getInfoHandler();
   }

   public static boolean usingCommand(String command) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      return ctx.getCommandType().equals(command);
   }

   public static boolean isEditSessionInProgress() {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      return ctx.isEditSessionInProgress;
   }

   public static void setErrOutputMedium(Writer writer) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setErrOutputMedium(writer);
   }

   public static void setErrOutputMedium(OutputStream stream) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setErrOutputMedium(stream);
   }

   public static void setStdOutputMedium(Writer writer) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setStdOutputMedium(writer);
   }

   public static void setStdOutputMedium(OutputStream stream) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setStdOutputMedium(stream);
   }

   public static void setlogToStandardOut(boolean value) {
      WLScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setlogToStandardOut(value);
   }
}
