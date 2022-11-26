package com.bea.wls.redef;

import com.bea.wls.redef.i18n.FastSwapLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.Annotation;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class ClassRedefinitionRuntime {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private static final String WORKMANAGER_NAME = "ClassRedefWorkManager";
   private String name = "<Unknown>";
   private AtomicInteger _redefCount = new AtomicInteger();
   private AtomicInteger _failedRedefCount = new AtomicInteger();
   private AtomicInteger _processedClassesCount = new AtomicInteger();
   private AtomicLong _totalRedefTime = new AtomicLong();
   private List _loaders = Collections.synchronizedList(new ArrayList());
   private RedefinitionTask _currentTask;
   private int _redefinitionTaskLimit;
   private static WorkManager _workManager = WorkManagerFactory.getInstance().findOrCreate("ClassRedefWorkManager", 1, 1);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      if (name != null) {
         this.name = name;
      }

   }

   public int getClassRedefinitionCount() {
      return this._redefCount.get();
   }

   public int getFailedClassRedefinitionCount() {
      return this._failedRedefCount.get();
   }

   public int getProcessedClassesCount() {
      return this._processedClassesCount.get();
   }

   public long getTotalClassRedefinitionTime() {
      return this._totalRedefTime.get();
   }

   void updateProcessingTime(long incr) {
      this._totalRedefTime.addAndGet(incr);
   }

   void updateProcessedClassesCount(int incr) {
      this._processedClassesCount.addAndGet(incr);
      if (this._currentTask != null) {
         this._currentTask.updateProcessedClassesCount(incr);
      }

   }

   RedefinitionTask getCurrentTask() {
      return this._currentTask;
   }

   public void registerClassLoader(RedefiningClassLoader loader) {
      ClassLoader parent = loader.getParent();
      if (parent instanceof RedefiningClassLoader) {
         this.registerClassLoader((RedefiningClassLoader)parent);
      }

      Iterator var3 = this._loaders.iterator();

      RedefiningClassLoader ldr;
      do {
         if (!var3.hasNext()) {
            WeakReference ref = new WeakReference(loader);
            this._loaders.add(ref);
            loader.setRedefinitionRuntime(this);
            return;
         }

         WeakReference ref = (WeakReference)var3.next();
         ldr = (RedefiningClassLoader)ref.get();
      } while(loader != ldr);

   }

   public int getRedefinitionTaskLimit() {
      return this._redefinitionTaskLimit;
   }

   public void setRedefinitionTaskLimit(int limit) {
      this._redefinitionTaskLimit = limit;
   }

   public RedefinitionTask scheduleRedefineClasses(String moduleName, String[] classNames) {
      RedefinitionTask task = new RedefinitionTask(this, moduleName, classNames);
      _workManager.schedule(task);
      return task;
   }

   public synchronized void redefineClasses(String moduleName, Set candidates, RedefinitionTask task) throws ClassRedefinitionException {
      boolean success = false;
      FastSwapLogger.logFastSwapBegin(this.getName());
      ClassRedefinitionException ex = null;

      try {
         if (task.isRunnable()) {
            task.setStatus(RedefinitionTask.Status.RUNNING);
            task.setBeginTime(System.currentTimeMillis());
            long startTime = System.nanoTime();
            this._currentTask = task;
            List list = new LinkedList();
            Iterator var9 = this._loaders.iterator();

            label175:
            while(true) {
               RedefiningClassLoader loader;
               Annotation anno;
               do {
                  do {
                     if (!var9.hasNext()) {
                        long endTime = System.nanoTime();
                        this.updateProcessingTime(endTime - startTime);
                        Iterator var20 = list.iterator();

                        while(var20.hasNext()) {
                           CandidateSet set = (CandidateSet)var20.next();
                           this.redefineClasses(set.loader, set.sources);
                        }

                        success = true;
                        break label175;
                     }

                     WeakReference ref = (WeakReference)var9.next();
                     loader = (RedefiningClassLoader)ref.get();
                  } while(loader == null);

                  anno = loader.getAnnotation();
               } while(moduleName != null && anno != null && !moduleName.equals(anno.getModuleName()));

               Map sources = loader.scanForUpdates(candidates);
               if (!sources.isEmpty()) {
                  list.add(new CandidateSet(loader, sources));
                  task.updateCandidateClassesCount(sources.size());
               }
            }
         }
      } catch (Throwable var17) {
         ex = new ClassRedefinitionException(var17.getMessage(), var17);
      } finally {
         this._currentTask = null;
         this._redefCount.addAndGet(1);
         task.setEndTime(System.currentTimeMillis());
         if (task.isRunning()) {
            task.setStatus(success ? RedefinitionTask.Status.FINISHED : RedefinitionTask.Status.FAILED);
         }

         if (!success) {
            this._failedRedefCount.addAndGet(1);
         }

      }

      if (ex != null) {
         FastSwapLogger.logFastSwapFailure(this.getName(), ex);
         throw ex;
      } else {
         FastSwapLogger.logFastSwapEnd(this.getName(), "" + task.getStatus());
      }
   }

   private void redefineClasses(RedefiningClassLoader loader, Map sources) throws Exception {
      List classDefs = new ArrayList();
      Iterator var4 = sources.keySet().iterator();

      while(var4.hasNext()) {
         String className = (String)var4.next();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Redefining: " + className);
         }

         Class c = loader.loadClass(className);
         byte[] classBytes = class2bytes(c);
         classBytes = loader.doPreProcess(classBytes, className);
         classDefs.add(new ClassDefinition(c, classBytes));
      }

      loader.getClassRedefinitionAccess().redefineClasses(classDefs);
   }

   private static byte[] class2bytes(Class c) throws IOException, ClassRedefinitionException {
      ClassLoader cl = c.getClassLoader();
      InputStream is = null;

      byte[] var3;
      try {
         is = cl.getResourceAsStream(c.getName().replace('.', '/') + ".class");
         if (is == null) {
            throw new ClassRedefinitionException("Input stream for " + c.getName() + " could not be found");
         }

         var3 = getBytes(is);
      } finally {
         if (is != null) {
            is.close();
         }

      }

      return var3;
   }

   private static byte[] getBytes(InputStream is) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] b = new byte[512];
      int read = 0;

      while((read = is.read(b, read, b.length - read)) > 0) {
         baos.write(b, 0, read);
         if (read == b.length) {
            read = 0;
         }
      }

      return baos.toByteArray();
   }

   private static class CandidateSet {
      RedefiningClassLoader loader;
      Map sources;

      CandidateSet(RedefiningClassLoader loader, Map sources) {
         this.loader = loader;
         this.sources = sources;
      }
   }
}
