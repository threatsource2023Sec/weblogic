package weblogic.management.scripting.core.utils;

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
import java.util.List;
import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import org.python.util.PythonInterpreter;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.management.scripting.WLCoreScriptContext;
import weblogic.management.scripting.core.InformationCoreHandler;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTUtilHelper;
import weblogic.utils.FileUtils;

public class WLSTCoreUtil extends WLSTUtilHelper {
   private static boolean isStandalone = false;
   private static ClassLoader pluginCls = null;
   private static final String WL_SCRIPT_CONTEXT_CLASS = "weblogic.management.scripting.WLScriptContext";
   private static final String WL_CORE_SCRIPT_CONTEXT_CLASS = "weblogic.management.scripting.WLCoreScriptContext";

   private static InputStream getWLSTCoreScript() {
      return WLSTCoreUtil.class.getResourceAsStream("wlst_core.py");
   }

   private static InputStream getWLSTNMScript() {
      return WLSTCoreUtil.class.getResourceAsStream("wlst_nm.py");
   }

   public static void writeWLSTAsModule(String path) throws Throwable {
      InputStream is = WLSTCoreUtil.class.getResourceAsStream("modules/wlstModule_core.py");
      File file = new File(path);
      FileUtils.writeToFile(is, file);
   }

   public static String getWLSTCoreScriptPath() throws IOException {
      InputStream is = WLSTCoreUtil.class.getResourceAsStream("wlst_core.py");
      File file = File.createTempFile("wlst_module3", ".py");
      file.deleteOnExit();
      FileUtils.writeToFile(is, file);
      return file.getAbsolutePath();
   }

   public static String getWLSTNMScriptPath() throws IOException {
      InputStream is = WLSTCoreUtil.class.getResourceAsStream("wlst_nm.py");
      File file = File.createTempFile("wlst_module4", ".py");
      file.deleteOnExit();
      FileUtils.writeToFile(is, file);
      return file.getAbsolutePath();
   }

   public static String getOfflineWLSTScriptPath() {
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
         return path;
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException("Could not find the OffLine WLST class ", var5);
      } catch (NoSuchMethodException var6) {
         throw new RuntimeException("Could not find the method while getting the WLSTOffLineScript path", var6);
      } catch (IllegalAccessException var7) {
         throw new RuntimeException("Illegal Access while getting the WLSTOffLineScript path", var7);
      } catch (InvocationTargetException var8) {
         throw new RuntimeException("Invocation Target exception while getting the WLSTOffLineScript path", var8);
      }
   }

   public static String getOfflineWLSTScriptForModule() {
      String s = "";
      s = s + "\nWLS.setup(1)";
      s = s + "\n";
      return s;
   }

   public static InputStream getOnlineAppendedWLSTCoreScript() {
      return WLSTCoreUtil.class.getResourceAsStream("online_append_core.py");
   }

   public static WLSTInterpreter ensureInterpreter() {
      if (theInterpreter == null) {
         theInterpreter = (new WLSTInterpreter()).getWLInterpreter();
      }

      return theInterpreter;
   }

   public static void setupOnline(WLSTInterpreter interp) {
      execWLSTScript(interp, true);
   }

   private static void execWLSTScript(WLSTInterpreter interp, boolean online) {
      WLCoreScriptContext ctx = ensureWLCtx(interp);
      PyObject offlineCtx = interp.get("WLS");
      interp.setOfflineCMO(interp.get("cmo"));
      interp.setOfflinePrompt(interp.get("myps1"));
      InputStream is = getWLSTCoreScript();
      if (is != null) {
         interp.execfile(is);
      }

      is = getWLSTNMScript();
      if (is != null) {
         interp.execfile(is);
      }

      if (isStandalone) {
         interp.set("WLS_CORE", ctx);
      } else {
         interp.set("WLS_ON", ctx);
      }

      if (offlineCtx != null) {
         interp.set("WLS", offlineCtx);
      }

      interp.set("nmService", ctx.getNodeManagerService());
   }

   public static WLCoreScriptContext ensureWLCtx(WLSTInterpreter interp) {
      Object o = interp.get("WLS_CORE");
      if (o instanceof WLCoreScriptContext) {
         return (WLCoreScriptContext)o;
      } else {
         WLCoreScriptContext ctx = null;
         if (WLSTUtilHelper.class.getClassLoader().getResource("weblogic.management.scripting.WLScriptContext".replace('.', '/') + ".class") == null) {
            isStandalone = true;
         }

         Class clz;
         try {
            if (isStandalone) {
               clz = Class.forName("weblogic.management.scripting.WLCoreScriptContext", true, Thread.currentThread().getContextClassLoader());
            } else {
               clz = Class.forName("weblogic.management.scripting.WLScriptContext", true, Thread.currentThread().getContextClassLoader());
            }
         } catch (ClassNotFoundException var6) {
            throw new IllegalStateException(var6);
         }

         try {
            if (isStandalone) {
               ctx = new WLCoreScriptContext();
               interp.set("WLS_CORE", ctx);
            } else {
               ctx = (WLCoreScriptContext)clz.newInstance();
               interp.set("WLS_ON", clz.newInstance());
            }
         } catch (Exception var5) {
            throw new IllegalStateException(var5);
         }

         ctx.setWLSTInterpreter(interp);
         return ctx;
      }
   }

   public static void setupOffline(WLSTInterpreter interp) {
      theInterpreter = interp;
      PyObject ctx = interp.get("WLS_CORE");
      PyObject ns = interp.get("nmService");
      PyObject offlineCtx = interp.get("WLS");
      execWLSTScript(interp, false);
      if (ctx != null) {
         if (isStandalone) {
            interp.set("WLS_CORE", ctx);
         } else {
            interp.set("WLS_ON", ctx);
         }
      }

      if (ns != null) {
         interp.set("nmService", ns);
      }

      interp.execfile(getOfflineWLSTScriptPath());
      InputStream is = getOnlineAppendedWLSTCoreScript();
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

      List pyJarList = initOfflineContext(interp, "WLS");
      ClassLoader cl = null;
      if (!interp.getDisablePluginJarLoadingMode()) {
         Class var7 = WLSTCoreUtil.class;
         synchronized(WLSTCoreUtil.class) {
            if (pluginCls == null) {
               pluginCls = getPluginClassLoader(pyJarList);
            }
         }

         cl = pluginCls;
      } else {
         cl = WLSTCoreUtil.class.getClassLoader();
      }

      interp.setClassLoader(cl);
      Thread.currentThread().setContextClassLoader(cl);
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
         cl = WLSTCoreUtil.class.getClassLoader();
      }

      if (pyJarURLs.length != 0) {
         cl = new URLClassLoader(pyJarURLs, (ClassLoader)cl);
      }

      debugInit(WLSTUtilHelper.logClassLoaderHierarchy((ClassLoader)cl));
      return (ClassLoader)cl;
   }

   public static List initOfflineContext(WLSTInterpreter interp, String key) {
      try {
         Class clz = Class.forName("com.oracle.cie.domain.script.jython.WLST_offline", true, Thread.currentThread().getContextClassLoader());
         Class[] params = new Class[]{PythonInterpreter.class, Boolean.TYPE, String.class};
         Method mthd = clz.getMethod("setupContext", params);
         Object[] args = new Object[]{interp, new Boolean(interp.getScriptMode()), key};
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

   public static void println(String s, Exception ex) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.println(ctx.getWLSTMsgFormatter().getPythonExecError(s, ex));
   }

   public static boolean usingCommand(String command) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      return ctx.getCommandType().equals(command);
   }

   public static boolean isEditSessionInProgress() {
      return false;
   }

   public static InformationCoreHandler getInfoHandler() {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      return ctx.getInfoHandler();
   }

   public static void setErrOutputMedium(Writer writer) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setErrOutputMedium(writer);
   }

   public static void setErrOutputMedium(OutputStream stream) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setErrOutputMedium(stream);
   }

   public static void setStdOutputMedium(Writer writer) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setStdOutputMedium(writer);
   }

   public static void setStdOutputMedium(OutputStream stream) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setStdOutputMedium(stream);
   }

   public static void setlogToStandardOut(boolean value) {
      WLCoreScriptContext ctx = ensureWLCtx(ensureInterpreter());
      ctx.setlogToStandardOut(value);
   }
}
