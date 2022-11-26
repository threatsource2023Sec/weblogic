package weblogic.management.deploy.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.AppDeploymentBean;
import weblogic.j2ee.descriptor.wl.DeploymentConfigOverridesBean;
import weblogic.j2ee.descriptor.wl.LibraryBean;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.Work;
import weblogic.work.WorkManagerFactory;

public class MultiVersionConfiguration implements TimerListener {
   private boolean enabled = false;
   private DeploymentConfigOverridesBeanListener listener = null;
   private Descriptor descriptor = null;
   private DeploymentConfigOverridesBean rootBean = null;
   private File dco;
   private long lastModified;
   private long crc32;
   private final DescriptorManager dm = new DescriptorManager();
   private Timer pollerMonitorTimer = null;
   private long pollInterval;
   private ContextualTrace t = ContextualTrace.get("MSID");
   private Object lock = new Object();
   private ListenerThread listenerThread = null;
   private long lastListenerScheduled = -1L;
   private final SimpleDateFormat timeformat = new SimpleDateFormat("MM/dd/yy KK:mm:ss.SSS a z");
   static final String TIMER_MGR_NAME = "weblogic.deploy.MSIDPoller";
   private final TimerManager tm;

   MultiVersionConfiguration(String overridesDir, int overridesPollInterval, DeploymentConfigOverridesBeanListener listener) {
      this.t.record("MVC", "Ctr", "Args", overridesDir, overridesPollInterval);
      File dcoDir = new File(overridesDir);
      this.pollInterval = (long)overridesPollInterval * 1000L;
      this.dco = new File(dcoDir, "deployment-config-overrides.xml");
      this.tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.deploy.MSIDPoller", WorkManagerFactory.getInstance().getSystem());
      if (this.dco.exists()) {
         this.lastModified = this.dco.lastModified();

         try {
            this.crc32 = this.getCrc32(new FileInputStream(this.dco));
            this.descriptor = this.dm.createDescriptor(new FileInputStream(this.dco));
            this.rootBean = (DeploymentConfigOverridesBean)this.descriptor.getRootBean();
            if (listener == null) {
               throw new IllegalArgumentException("Listener may not be null");
            }

            this.listener = listener;
            this.enabled = true;
            this.t.record("MVC", "Ctr", "MVC-Enabled");
         } catch (IOException var6) {
            DeployerRuntimeExtendedLogger.failedToReadDeploymentConfigOverrides(this.dco.getAbsolutePath(), this.t.flush(), var6);
         }
      } else {
         this.t.record("MVC", "Ctr", "DCO-does-not-exist");
      }

   }

   Descriptor getDescriptor() {
      return this.descriptor;
   }

   DeploymentConfigOverridesBean getRootBean() {
      return this.rootBean;
   }

   public void teardown() {
      this.stopPolling();
      this.enabled = false;
      this.dco = null;
      this.descriptor = null;
      this.rootBean = null;
      this.listener = null;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public AppDeploymentBean getMultiVersionApplication(String configuredId) {
      return this.enabled ? this.rootBean.lookupAppDeployment(configuredId) : null;
   }

   public LibraryBean getMultiVersionLibrary(String configuredId) {
      return this.enabled ? this.rootBean.lookupLibrary(configuredId) : null;
   }

   void startPolling() {
      this.startPolling(this.pollInterval, this.pollInterval);
   }

   private void startPolling(long delay, long period) {
      if (this.pollerMonitorTimer == null) {
         this.t.record("MVC", "startPolling", delay, period);
         this.pollerMonitorTimer = this.tm.schedule(this, delay, period);
      }

   }

   void stopPolling() {
      if (this.pollerMonitorTimer != null) {
         this.t.record("MVC", "cancelPolling");
         this.pollerMonitorTimer.cancel();
         this.pollerMonitorTimer = null;
      }

   }

   void changePollInterval(int overridesPollInterval) {
      this.t.record("MVC", "changePollInterval", overridesPollInterval);
      this.stopPolling();
      this.pollInterval = (long)overridesPollInterval * 1000L;
      this.startPolling(0L, this.pollInterval);
   }

   void changeDir(String overridesDir) {
      this.t.record("MVC", "changeDir", overridesDir);
      this.stopPolling();
      File dcoDir = new File(overridesDir);
      this.dco = new File(dcoDir, "deployment-config-overrides.xml");
      if (this.dco.exists()) {
         this.enabled = true;
         this.t.record("MVC", "changeDir", "MVC-Enabled");
         this.startPolling(0L, this.pollInterval);
      } else {
         this.t.record("MVC", "changeDir", "DCO-does-not-exist");
         this.enabled = false;
      }

   }

   private long getCrc32(FileInputStream is) throws IOException {
      CRC32 msgDigest = new CRC32();

      int numRead;
      do {
         byte[] buffer = new byte[1024];
         numRead = is.read(buffer);
         if (numRead > 0) {
            buffer = this.filter(buffer);
            if (buffer.length > 0) {
               msgDigest.update(buffer, 0, buffer.length);
            }
         }
      } while(numRead != -1);

      is.close();
      return msgDigest.getValue();
   }

   private byte[] filter(byte[] other) {
      String s = new String(other);
      char[] c = s.toCharArray();
      char low = 32;
      char high = 126;
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < c.length; ++i) {
         if (c[i] >= low && c[i] <= high && c[i] != '\n' && c[i] != '\f') {
            sb.append(c[i]);
         }
      }

      return sb.toString().getBytes();
   }

   public void timerExpired(Timer timer) {
      long lastModified = this.dco.lastModified();
      if (lastModified != this.lastModified) {
         try {
            long crc32 = this.getCrc32(new FileInputStream(this.dco));
            if (crc32 != this.crc32) {
               DescriptorManager dm = new DescriptorManager();
               Descriptor descriptor = dm.createDescriptor(new FileInputStream(this.dco));
               DescriptorDiff diff = descriptor.computeDiff(this.descriptor);
               if (diff.size() > 0) {
                  Iterator iterator = diff.iterator();
                  this.t.record("MVC", "timerExpired", "Bean-Changes?", iterator.hasNext());
               }

               this.descriptor = descriptor;
               this.rootBean = (DeploymentConfigOverridesBean)descriptor.getRootBean();
               this.lastModified = lastModified;
               this.crc32 = crc32;
            } else {
               this.t.record("MVC", "timerExpired", "DCO-CRC32-unchanged", crc32, this.crc32);
            }
         } catch (FileNotFoundException var11) {
            DeployerRuntimeExtendedLogger.failedToReadDeploymentConfigOverrides(this.dco.getAbsolutePath(), this.t.flush(), var11);
         } catch (IOException var12) {
            DeployerRuntimeExtendedLogger.failedToReadDeploymentConfigOverrides(this.dco.getAbsolutePath(), this.t.flush(), var12);
         }
      }

      boolean scheduled = false;
      synchronized(this.lock) {
         if (this.listenerThread == null) {
            ListenerThread t = new ListenerThread(this.listener, this.rootBean);
            WorkManagerFactory.getInstance().getSystem().schedule(t);
            this.lastListenerScheduled = System.currentTimeMillis();
            this.listenerThread = t;
            scheduled = true;
         }
      }

      if (scheduled) {
         this.t.record("MVC", "timerExpired", "Scheduled-listener", this.rootBean.toString());
      } else {
         String sLastListenerScheduled = "Unset";
         if (this.lastListenerScheduled > 0L) {
            sLastListenerScheduled = this.timeformat.format(new Date(this.lastListenerScheduled));
         }

         this.t.record("MVC", "timerExpired", "Ignoring-poll", sLastListenerScheduled);
         DeployerRuntimeExtendedLogger.ignoringModifiedDCO(sLastListenerScheduled);
      }

   }

   interface DeploymentConfigOverridesBeanListener {
      void onValidateRuntime(DeploymentConfigOverridesBean var1);
   }

   private class ListenerThread implements Work {
      private final DeploymentConfigOverridesBeanListener listener;
      private final DeploymentConfigOverridesBean bean;
      private final AtomicBoolean firstCall;
      private final Runnable onFinish;

      private ListenerThread(DeploymentConfigOverridesBeanListener listener, DeploymentConfigOverridesBean bean) {
         this.firstCall = new AtomicBoolean(false);
         this.onFinish = new Runnable() {
            public void run() {
               if (!ListenerThread.this.firstCall.getAndSet(true)) {
                  synchronized(MultiVersionConfiguration.this.lock) {
                     MultiVersionConfiguration.this.listenerThread = null;
                  }
               }

            }
         };
         this.listener = listener;
         this.bean = bean;
      }

      public void run() {
         try {
            this.listener.onValidateRuntime(this.bean);
         } finally {
            this.onFinish.run();
         }

      }

      public Runnable overloadAction(String reason) {
         return this.onFinish;
      }

      public Runnable cancel(String reason) {
         return this.onFinish;
      }

      // $FF: synthetic method
      ListenerThread(DeploymentConfigOverridesBeanListener x1, DeploymentConfigOverridesBean x2, Object x3) {
         this(x1, x2);
      }
   }
}
