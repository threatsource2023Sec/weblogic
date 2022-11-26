package com.bea.wls.redef.runtime;

import com.bea.wls.redef.ClassRedefinitionRuntime;
import com.bea.wls.redef.RedefiningClassLoader;
import com.bea.wls.redef.RedefinitionTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.classloaders.GenericClassLoader;

public class ClassRedefinitionRuntimeImpl extends RuntimeMBeanDelegate implements ClassRedefinitionRuntimeMBean {
   private ClassRedefinitionRuntime _redefRuntime;
   private AtomicInteger _sequenceNum = new AtomicInteger();
   private List _tasksList = Collections.synchronizedList(new ArrayList());

   public ClassRedefinitionRuntimeImpl(RuntimeMBean parent, GenericClassLoader classLoader) throws ManagementException {
      super(getParentName(parent), parent);
      this._redefRuntime = ((RedefiningClassLoader)classLoader).getRedefinitionRuntime();
      this._redefRuntime.setName(this.getName());
   }

   public void registerClassLoader(RedefiningClassLoader loader) {
      this._redefRuntime.registerClassLoader(loader);
   }

   private static String getParentName(RuntimeMBean parent) {
      return parent != null ? parent.getName() : "NULL";
   }

   public int getClassRedefinitionCount() {
      return this._redefRuntime.getClassRedefinitionCount();
   }

   public int getFailedClassRedefinitionCount() {
      return this._redefRuntime.getFailedClassRedefinitionCount();
   }

   public int getProcessedClassesCount() {
      return this._redefRuntime.getProcessedClassesCount();
   }

   public long getTotalClassRedefinitionTime() {
      return this._redefRuntime.getTotalClassRedefinitionTime();
   }

   public ClassRedefinitionTaskRuntimeMBean redefineClasses() throws ManagementException {
      return this.redefineClasses((String)null, (String[])null);
   }

   public ClassRedefinitionTaskRuntimeMBean redefineClasses(String moduleName, String[] classNames) throws ManagementException {
      RedefinitionTask task = this._redefRuntime.scheduleRedefineClasses(moduleName, classNames);
      String taskName = this.getName() + "_" + this._sequenceNum.incrementAndGet();
      ClassRedefinitionTaskRuntimeImpl taskRuntime = new ClassRedefinitionTaskRuntimeImpl(taskName, this, task);
      this.ensureSpace();
      this._tasksList.add(taskRuntime);
      return taskRuntime;
   }

   private void ensureSpace() {
      int maxTasks = this._redefRuntime.getRedefinitionTaskLimit();
      int count = this._tasksList.size() - maxTasks + 1;

      while(count > 0 && this._tasksList.size() > 0) {
         --count;
         ClassRedefinitionTaskRuntimeImpl task = (ClassRedefinitionTaskRuntimeImpl)this._tasksList.remove(0);

         try {
            if (task.isRunning()) {
               task.cancel();
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         try {
            task.unregister();
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   public ClassRedefinitionTaskRuntimeMBean[] getClassRedefinitionTasks() {
      ClassRedefinitionTaskRuntimeMBean[] arr = new ClassRedefinitionTaskRuntimeMBean[0];
      return (ClassRedefinitionTaskRuntimeMBean[])this._tasksList.toArray(arr);
   }
}
