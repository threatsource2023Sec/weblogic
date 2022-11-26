package weblogic.diagnostics.debugpatch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFDebugPatchTaskRuntimeMBean;
import weblogic.management.runtime.WLDFDebugPatchesRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class WLDFDebugPatchesRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFDebugPatchesRuntimeMBean {
   private static final String CLASS_SUFFIX = ".class";
   private static final String JAR_SUFFIX = ".jar";
   private static final String README = "README.TXT";
   private static final int CLASS_SUFFIX_LENGTH = ".class".length();
   private static final String[] EMPTY_LIST = new String[0];
   private static final String WILD_CARD = "*";
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDebugPatches");
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();
   private static final String AGENT_CLASSNAME = "weblogic.diagnostics.debugpatch.agent.DebugPatchAgent";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private File debugPatchesDir;
   private List activePatches = Collections.synchronizedList(new ArrayList());
   private Set redefinedClasses = new HashSet();
   private WorkManager workManager;
   private final Object mutex = new Object();
   private AtomicInteger seqNum = new AtomicInteger();
   private List tasksList = Collections.synchronizedList(new ArrayList());

   public WLDFDebugPatchesRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super("WLDFDebugPatchesRuntime", parent);
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      this.debugPatchesDir = new File(domainMBean.getDebugPatches().getDebugPatchDirectory());
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Using Debug Patch Directory: " + this.debugPatchesDir);
      }

      this.workManager = WorkManagerFactory.getInstance().getDefault();
   }

   public String[] getAvailableDebugPatches() {
      if (!this.debugPatchesDir.isDirectory()) {
         return EMPTY_LIST;
      } else {
         List patchList = new ArrayList();
         String[] var2 = this.debugPatchesDir.list();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String patch = var2[var4];
            if (patch.endsWith(".jar") && !this.isPatchActive(patch)) {
               patchList.add(patch);
            }
         }

         return (String[])patchList.toArray(new String[patchList.size()]);
      }
   }

   public String[] getActiveDebugPatches() {
      List list = new ArrayList();
      Iterator var2 = this.activePatches.iterator();

      while(var2.hasNext()) {
         DebugPatchInfo patchInfo = (DebugPatchInfo)var2.next();
         list.add(patchInfo.toString());
      }

      return (String[])list.toArray(new String[list.size()]);
   }

   public String showDebugPatchInfo(String patch) {
      File file = new File(this.debugPatchesDir, patch);
      if (!file.exists()) {
         return DTF.getDebugPatchDoesnotExist(patch);
      } else {
         InputStream in = null;
         StringBuffer buf = new StringBuffer();
         buf.append(patch).append(":\n");

         try {
            JarFile jarFile = new JarFile(file);
            Enumeration en = jarFile.entries();
            ZipEntry readme = null;

            while(en.hasMoreElements()) {
               JarEntry entry = (JarEntry)en.nextElement();
               String name = entry.getName();
               if (name.equals("README.TXT")) {
                  readme = entry;
               }

               if (name.endsWith(".class")) {
                  String className = name.substring(0, name.length() - CLASS_SUFFIX_LENGTH).replace('/', '.');
                  buf.append("    ").append(className).append("\n");
               }
            }

            buf.append("\n");
            if (readme == null) {
               buf.append(DTF.getDebugPatchInfoNotAvailable(patch)).append("\n");
            } else {
               buf.append(DTF.getDebugPatchAdditionalInfo());
               in = jarFile.getInputStream(readme);
               byte[] tmpBuf = new byte[4096];
               byte[] bytes = this.readBytes(in, tmpBuf);
               buf.append(new String(bytes));
            }
         } catch (Throwable var19) {
            var19.printStackTrace();
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var18) {
                  var18.printStackTrace();
               }
            }

         }

         return buf.toString();
      }
   }

   public WLDFDebugPatchTaskRuntimeMBean activateDebugPatch(String patch, String appName, String moduleName, String partitionName) throws ManagementException {
      File patchFile = this.getPatchFile(patch);
      if (!patchFile.exists()) {
         throw new IllegalArgumentException(DTF.getPatchActivationFileDoesNotExist(patch, this.debugPatchesDir.getAbsolutePath()));
      } else {
         return this.scheduleActivationTask(true, patch, appName, moduleName, partitionName);
      }
   }

   public WLDFDebugPatchTaskRuntimeMBean deactivateDebugPatches(String patches, String application, String module, String partitionName) throws ManagementException {
      return this.scheduleActivationTask(false, patches, application, module, partitionName);
   }

   public WLDFDebugPatchTaskRuntimeMBean deactivateAllDebugPatches() throws ManagementException {
      return this.scheduleActivationTask(false, "*", (String)null, (String)null, (String)null);
   }

   private boolean isPatchActive(String patch) {
      patch = patch + ":";
      Iterator var2 = this.activePatches.iterator();

      DebugPatchInfo active;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         active = (DebugPatchInfo)var2.next();
      } while(!active.getPatch().startsWith(patch));

      return true;
   }

   private File getPatchFile(String patch) {
      File dir = this.debugPatchesDir;
      File patchFile = new File(dir, patch);
      return patchFile;
   }

   private WLDFDebugPatchTaskRuntimeMBean scheduleActivationTask(boolean activate, String patch, String appName, String moduleName, String partitionName) throws ManagementException {
      if (patch == null) {
         throw new ManagementException(DTF.getNoPatchesSpecifiedWithDebugPatchActivation());
      } else if (!this.isRedefineClassesEnabled()) {
         throw new ManagementException(DTF.getInstrumentationAgentNotAvailable());
      } else {
         if (appName == null) {
            moduleName = null;
            partitionName = null;
         }

         String name = (activate ? "ACTIVATE_" : "DEACTIVATE_") + this.seqNum.incrementAndGet();
         WLDFDebugPatchTaskRuntimeMBeanImpl taskRuntime = new WLDFDebugPatchTaskRuntimeMBeanImpl(name, patch, appName, moduleName, partitionName, activate);
         ActivationTask task = new ActivationTask(activate, patch, appName, moduleName, partitionName, taskRuntime);
         this.tasksList.add(taskRuntime);
         this.workManager.schedule(task);
         return taskRuntime;
      }
   }

   private List getClassLoaders(String application, String module, String partition) {
      List loaders = null;
      if (application != null) {
         loaders = ((FindClassLoaders)LocatorUtilities.getService(FindClassLoaders.class)).findAppClassLoaders(partition, application, module);
      } else {
         loaders = new ArrayList();
         ((List)loaders).add(this.getClass().getClassLoader());
      }

      return (List)loaders;
   }

   private List loadClasses(List loaders, String className) throws ClassNotFoundException {
      List classes = new ArrayList();
      Iterator var4 = loaders.iterator();

      while(var4.hasNext()) {
         ClassLoader loader = (ClassLoader)var4.next();

         try {
            Class clz = loader.loadClass(className);
            classes.add(clz);
         } catch (Exception var7) {
         }
      }

      if (classes.size() == 0) {
         throw new ClassNotFoundException("Class " + className + " could not be loaded");
      } else {
         return classes;
      }
   }

   private void performActivation(String patch, String application, String module, String partitionName, WLDFDebugPatchTaskRuntimeMBeanImpl taskRuntime) throws Exception {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Activating debug patch: file=" + patch + ", application=" + application + ", module=" + module);
      }

      File dir = this.debugPatchesDir;
      File patchFile = new File(dir, patch);
      Set classes = new HashSet();
      JarFile jarFile = new JarFile(patchFile);
      Enumeration en = jarFile.entries();
      byte[] tmpBuf = new byte[4096];
      List loaders = this.getClassLoaders(application, module, partitionName);
      ClassLoader rootLoader = (ClassLoader)loaders.get(0);
      List classdefList = new ArrayList();
      StringBuffer buf = new StringBuffer();
      int missingCount = 0;

      while(!taskRuntime.isCancelled() && en.hasMoreElements()) {
         JarEntry entry = (JarEntry)en.nextElement();
         String name = entry.getName();
         if (name.endsWith(".class")) {
            String className = name.substring(0, name.length() - CLASS_SUFFIX_LENGTH).replace('/', '.');

            try {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("  className: " + className);
               }

               List classList = this.loadClasses(loaders, className);
               byte[] classBytes = this.readBytes(jarFile, entry, tmpBuf);
               if (classBytes != null) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("  Size: " + classBytes.length);
                  }

                  Iterator var22 = classList.iterator();

                  while(var22.hasNext()) {
                     Class clz = (Class)var22.next();
                     this.checkScope(clz, rootLoader);
                     this.preProcessClassbytes(clz, classBytes);
                     ClassDefinition classDef = new ClassDefinition(clz, classBytes);
                     classdefList.add(classDef);
                     classes.add(new WeakReference(clz));
                  }
               } else if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("  Classbytes could not be read");
               }
            } catch (ClassNotFoundException var26) {
               StringBuffer var10000 = buf.append("\n");
               ++missingCount;
               var10000.append(missingCount).append(": ").append(className);
            }
         }
      }

      if (missingCount > 0) {
         DiagnosticsLogger.logUnknownClassesInDebugPatch(patch, buf.toString());
         Loggable l = DiagnosticsLogger.logUnknownClassesInDebugPatchLoggable(patch, buf.toString());
         throw new ManagementException(l.getMessageBody());
      } else {
         if (!taskRuntime.isCancelled()) {
            ClassDefinition[] classDefs = (ClassDefinition[])classdefList.toArray(new ClassDefinition[classdefList.size()]);
            this.redefineClasses(classDefs);
         }

         this.redefinedClasses.addAll(classes);
         this.activePatches.add(new DebugPatchInfo(patch, application, module, partitionName));
      }
   }

   private void checkScope(Class clz, ClassLoader rootLoader) throws ClassNotFoundException {
      for(ClassLoader loader = clz.getClassLoader(); loader != null; loader = loader.getParent()) {
         if (loader == rootLoader) {
            return;
         }
      }

      throw new ClassNotFoundException(clz.getName());
   }

   private byte[] preProcessClassbytes(Class clz, byte[] classBytes) {
      ClassLoader loader = clz.getClassLoader();
      if (loader instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)loader;
         classBytes = gcl.doPreProcess(classBytes, clz.getName());
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("  Size after preProcessClassbytes: " + classBytes.length);
         }
      }

      return classBytes;
   }

   private Object redefineClasses(final ClassDefinition[] classDefs) throws Exception {
      Object retVal = SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
         public Object run() throws Exception {
            Class clz = Class.forName("weblogic.diagnostics.debugpatch.agent.DebugPatchAgent");
            Class[] argTypes = new Class[]{ClassDefinition[].class};
            Method method = clz.getMethod("redefineClasses", argTypes);
            Object[] args = new Object[]{classDefs};
            return method.invoke((Object)null, args);
         }
      });
      return retVal;
   }

   private boolean isRedefineClassesEnabled() {
      try {
         Class clz = Class.forName("weblogic.diagnostics.debugpatch.agent.DebugPatchAgent");
         Class[] argTypes = new Class[0];
         Method method = clz.getMethod("isRedefineClassesSupported", argTypes);
         Object[] args = new Object[0];
         Boolean retVal = (Boolean)method.invoke((Object)null, args);
         return retVal;
      } catch (Exception var6) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("isRedefineClassesEnabled failed", var6);
         }

         return false;
      }
   }

   private void performDeactivation(String patches, String appName, String moduleName, String partitionName, WLDFDebugPatchTaskRuntimeMBeanImpl taskRuntime) throws Exception {
      List oldActivePatches = new ArrayList();
      oldActivePatches.addAll(this.activePatches);
      this.performDeactivateAllDebugPatches();
      if (!patches.equals("*")) {
         String[] var7 = patches.split(",");
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String patch = var7[var9];
            DebugPatchInfo tmp = new DebugPatchInfo(patch.trim(), appName, moduleName, partitionName);
            if (!oldActivePatches.remove(tmp) && DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Attempt to deactivate " + patch + " which is not active");
            }
         }

         Iterator var12 = oldActivePatches.iterator();

         while(var12.hasNext()) {
            DebugPatchInfo patchInfo = (DebugPatchInfo)var12.next();
            if (taskRuntime.isCancelled()) {
               break;
            }

            this.performActivation(patchInfo.getPatch(), patchInfo.getAppName(), patchInfo.getModuleName(), patchInfo.getPartitionName(), taskRuntime);
         }

      }
   }

   private void performDeactivateAllDebugPatches() throws Exception {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Deactivating all debug patches");
      }

      List classdefList = new ArrayList();
      byte[] tmpBuf = new byte[4096];
      Iterator var3 = this.redefinedClasses.iterator();

      while(var3.hasNext()) {
         WeakReference ref = (WeakReference)var3.next();
         Class clz = (Class)ref.get();
         if (clz != null) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("performDeactivateAllDebugPatches: clz=" + clz.getName());
            }

            byte[] classBytes = this.readOriginalClassBytes(clz, tmpBuf);
            if (classBytes != null) {
               classBytes = this.preProcessClassbytes(clz, classBytes);
               classdefList.add(new ClassDefinition(clz, classBytes));
            }
         }
      }

      ClassDefinition[] classDefs = (ClassDefinition[])classdefList.toArray(new ClassDefinition[classdefList.size()]);
      this.redefineClasses(classDefs);
      this.redefinedClasses.clear();
      this.activePatches.clear();
   }

   public WLDFDebugPatchTaskRuntimeMBean[] getDebugPatchTasks() {
      return (WLDFDebugPatchTaskRuntimeMBean[])this.tasksList.toArray(new WLDFDebugPatchTaskRuntimeMBean[this.tasksList.size()]);
   }

   public WLDFDebugPatchTaskRuntimeMBean lookupDebugPatchTask(String name) throws ManagementException {
      Iterator var2 = this.tasksList.iterator();

      WLDFDebugPatchTaskRuntimeMBean task;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         task = (WLDFDebugPatchTaskRuntimeMBean)var2.next();
      } while(!name.equals(task.getName()));

      return task;
   }

   public void clearDebugPatchTasks() {
      List list = new ArrayList();
      Iterator var2 = this.tasksList.iterator();

      WLDFDebugPatchTaskRuntimeMBean task;
      while(var2.hasNext()) {
         task = (WLDFDebugPatchTaskRuntimeMBean)var2.next();
         String status = task.getStatus();
         if (!"SCHEDULED".equals(status) && !"RUNNING".equals(status)) {
            list.add(task);
         }
      }

      var2 = list.iterator();

      while(var2.hasNext()) {
         task = (WLDFDebugPatchTaskRuntimeMBean)var2.next();
         this.tasksList.remove(task);
      }

   }

   private byte[] readBytes(JarFile jarFile, JarEntry entry, byte[] tmpBuf) throws IOException {
      byte[] bytes = null;
      InputStream in = null;

      byte[] bytes;
      try {
         in = jarFile.getInputStream(entry);
         bytes = this.readBytes(in, tmpBuf);
      } finally {
         if (in != null) {
            in.close();
         }

      }

      return bytes;
   }

   private byte[] readBytes(InputStream in, byte[] tmpBuf) throws IOException {
      byte[] bytes = null;
      if (in == null) {
         return null;
      } else {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         try {
            int n = false;

            int n;
            while((n = in.read(tmpBuf)) >= 0) {
               if (n > 0) {
                  bos.write(tmpBuf, 0, n);
               }
            }

            bos.flush();
            byte[] bytes = bos.toByteArray();
            return bytes;
         } finally {
            if (bos != null) {
               bos.close();
            }

         }
      }
   }

   private byte[] readOriginalClassBytes(Class clz, byte[] tmpBuf) throws IOException {
      byte[] bytes = null;
      InputStream in = null;

      byte[] bytes;
      try {
         ClassLoader loader = clz.getClassLoader();
         String resName = clz.getName().replace(".", "/") + ".class";
         in = loader.getResourceAsStream(resName);
         bytes = this.readBytes(in, tmpBuf);
      } finally {
         if (in != null) {
            in.close();
         }

      }

      return bytes;
   }

   public class ActivationTask implements Runnable {
      private boolean activate;
      private String patch;
      private String appName;
      private String moduleName;
      private String partitionName;
      private WLDFDebugPatchTaskRuntimeMBeanImpl taskRuntime;

      ActivationTask(boolean activate, String patch, String appName, String moduleName, String partitionName, WLDFDebugPatchTaskRuntimeMBeanImpl taskRuntime) {
         this.activate = activate;
         this.patch = patch;
         this.appName = appName;
         this.moduleName = moduleName;
         this.partitionName = partitionName;
         this.taskRuntime = taskRuntime;
         taskRuntime.setStatus("SCHEDULED");
         taskRuntime.setBeginTime(-1L);
      }

      private void logStartActivating() {
         if (this.appName == null) {
            DiagnosticsLogger.logActivatingDebugPatchAtSystem(this.patch);
         } else if (this.partitionName != null) {
            if (this.moduleName != null) {
               DiagnosticsLogger.logActivatingDebugPatchAtModuleInApplicationInPartition(this.patch, this.appName, this.moduleName, this.partitionName);
            } else {
               DiagnosticsLogger.logActivatingDebugPatchAtApplicationInPartition(this.patch, this.appName, this.partitionName);
            }
         } else if (this.moduleName != null) {
            DiagnosticsLogger.logActivatingDebugPatchAtModuleInApplication(this.patch, this.appName, this.moduleName);
         } else {
            DiagnosticsLogger.logActivatingDebugPatchAtApplication(this.patch, this.appName);
         }

      }

      private void logStartDeactivating() {
         if (this.appName == null) {
            DiagnosticsLogger.logDeactivatingDebugPatchAtSystem(this.patch);
         } else if (this.partitionName != null) {
            if (this.moduleName != null) {
               DiagnosticsLogger.logDeactivatingDebugPatchAtModuleInApplicationInPartition(this.patch, this.appName, this.moduleName, this.partitionName);
            } else {
               DiagnosticsLogger.logDeactivatingDebugPatchAtApplicationInPartition(this.patch, this.appName, this.partitionName);
            }
         } else if (this.moduleName != null) {
            DiagnosticsLogger.logDeactivatingDebugPatchAtModuleInApplication(this.patch, this.appName, this.moduleName);
         } else {
            DiagnosticsLogger.logDeactivatingDebugPatchAtApplication(this.patch, this.appName);
         }

      }

      public void run() {
         synchronized(WLDFDebugPatchesRuntimeMBeanImpl.this.mutex) {
            if ("SCHEDULED".equals(this.taskRuntime.getStatus())) {
               if (WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.isDebugEnabled()) {
                  WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.debug((this.activate ? "Activating " : "Deactivating ") + this.patch + " appName=" + this.appName + " moduleName=" + this.moduleName);
               }

               this.taskRuntime.setStatus("RUNNING");
               this.taskRuntime.setBeginTime(System.currentTimeMillis());

               try {
                  if (this.activate) {
                     this.logStartActivating();
                     WLDFDebugPatchesRuntimeMBeanImpl.this.performActivation(this.patch, this.appName, this.moduleName, this.partitionName, this.taskRuntime);
                     DiagnosticsLogger.logActivatedDebugPatch(this.patch);
                  } else {
                     this.logStartDeactivating();
                     WLDFDebugPatchesRuntimeMBeanImpl.this.performDeactivation(this.patch, this.appName, this.moduleName, this.partitionName, this.taskRuntime);
                     DiagnosticsLogger.logDeactivatedDebugPatch(this.patch);
                  }

                  this.taskRuntime.setStatus("FINISHED");
               } catch (Exception var4) {
                  this.taskRuntime.setStatus("FAILED");
                  this.taskRuntime.setError(var4);
                  if (this.activate) {
                     DiagnosticsLogger.logDebugPatchActivationFailed(this.patch);
                  } else {
                     DiagnosticsLogger.logDebugPatchDeactivationFailed(this.patch);
                  }

                  if (WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.isDebugEnabled()) {
                     WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.debug(this.activate ? "Activation failed" : "Deactivation failed", var4);
                  }
               }

               this.taskRuntime.setEndTime(System.currentTimeMillis());
               if (WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.isDebugEnabled()) {
                  WLDFDebugPatchesRuntimeMBeanImpl.DEBUG_LOGGER.debug((this.activate ? "Activation of " : "Deactivation of ") + this.patch + " status=" + this.taskRuntime.getStatus());
               }
            }

         }
      }
   }
}
