package weblogic.scheduler.ejb.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.TargetUtils;
import weblogic.j2ee.ApplicationManager;
import weblogic.scheduler.ApplicationNotFoundException;
import weblogic.scheduler.GlobalResourceGroupApplicationNotFoundException;
import weblogic.scheduler.TimerCreationCallback;
import weblogic.scheduler.TimerListenerExtension;
import weblogic.scheduler.ejb.ClusteredTimerListener;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.Annotation;

public class ClusteredTimerListenerWrapper implements TimerListener, TimerCreationCallback, TimerListenerExtension, Externalizable {
   private ClusteredTimerListener ejbListener;
   private String annotation;
   private String dispatchPolicy;
   private static final boolean DEBUG_JOBSCHEDULER = Debug.getCategory("weblogic.JobScheduler").isEnabled();

   ClusteredTimerListenerWrapper(String annotation, ClusteredTimerListener clusteredTimerListener, String dispatchPolicy) {
      this.ejbListener = clusteredTimerListener;
      this.annotation = annotation;
      if (dispatchPolicy != null) {
         this.dispatchPolicy = dispatchPolicy;
      } else {
         this.dispatchPolicy = "default";
      }

   }

   public ClusteredTimerListenerWrapper() {
   }

   public void timerExpired(Timer timer) {
      this.executeTimer(timer);
   }

   private void executeTimer(Timer timer) {
      ClassLoader cl = ApplicationManager.getApplicationClassLoader(new Annotation(this.annotation));
      ClassLoader saveLoader = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(cl);
         this.ejbListener.timerExpired(timer);
      } finally {
         Thread.currentThread().setContextClassLoader(saveLoader);
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.annotation);
      out.writeObject(this.ejbListener);
      out.writeUTF(this.dispatchPolicy);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.annotation = in.readUTF();
      Annotation ann = new Annotation(this.annotation);
      AppClassLoaderManager mgr = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new java.lang.annotation.Annotation[0]);
      ClassLoader cl = mgr.findLoader(ann);
      ApplicationContextInternal aci = ApplicationAccess.getApplicationAccess().getApplicationContext(ann.getApplicationName());
      boolean isDeployedLocally = aci != null && TargetUtils.isDeployedLocally(aci.getBasicDeploymentMBean().getTargets());
      if (!isDeployedLocally) {
         if (DEBUG_JOBSCHEDULER && ClusteredTimerManagerUtility.isApplicationFromGlobalDomainResourceGroup(ann.getApplicationName())) {
            throw new GlobalResourceGroupApplicationNotFoundException(ann.getApplicationName());
         } else {
            throw new ApplicationNotFoundException(ann.getApplicationName());
         }
      } else {
         ClassLoader saveLoader = Thread.currentThread().getContextClassLoader();

         try {
            Thread.currentThread().setContextClassLoader(cl);
            this.ejbListener = (ClusteredTimerListener)in.readObject();
         } finally {
            Thread.currentThread().setContextClassLoader(saveLoader);
         }

         this.dispatchPolicy = in.readUTF();
      }
   }

   public String getTimerId(String key) {
      String modifiedKey = key;
      if (this.ejbListener.getGroupName() != null) {
         modifiedKey = this.ejbListener.getGroupName() + "@@" + key;
      }

      return modifiedKey;
   }

   public ClusteredTimerListener getEJBTimerListener() {
      return this.ejbListener;
   }

   public String getDispatchPolicy() {
      return this.dispatchPolicy;
   }

   public boolean isTransactional() {
      return false;
   }

   public String getApplicationName() {
      return (new Annotation(this.annotation)).getApplicationName();
   }

   public String getModuleName() {
      return (new Annotation(this.annotation)).getModuleName();
   }
}
