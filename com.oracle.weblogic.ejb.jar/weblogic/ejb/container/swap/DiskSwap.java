package weblogic.ejb.container.swap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.NoSuchObjectException;
import java.util.EventListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJBObject;
import javax.ejb.NoSuchEJBException;
import weblogic.cluster.replication.ROID;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.deployer.StatefulTimeoutConfiguration;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.SessionEJBContextImpl;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.collections.NumericValueHashtable;

public final class DiskSwap implements EJBSwap {
   protected static final DebugLogger debugLogger;
   private final StatefulSessionManager beanManager;
   private final DiskScrubber scrubber;
   private final List brListeners = new LinkedList();
   private final NumericValueHashtable fileToOIDTable = new NumericValueHashtable();
   private final Map fileToPKTable = new ConcurrentHashMap();
   private final File swapDir;
   private final StatefulSessionBeanInfo ssbi;
   private StorageHandler storageHandler;

   public DiskSwap(File dir, StatefulSessionManager beanManager, StatefulSessionBeanInfo sbi) {
      this.swapDir = dir;
      this.ssbi = sbi;
      this.beanManager = beanManager;
      if (sbi.isStatefulTimeoutConfigured()) {
         this.scrubber = new StatefulTimeoutScrubber(sbi.getStatefulTimeoutConfiguration());
      } else {
         this.scrubber = new IdleTimeoutScrubber(sbi.getIdleTimeoutMS(), sbi.getSessionTimeoutMS());
      }

      this.scrubber.start();
   }

   private synchronized StorageHandler getStorageHandler() {
      if (this.storageHandler == null) {
         this.storageHandler = (StorageHandler)(this.ssbi.isPassivationCapable() ? new DiskBasedHandler(this.ssbi.getClassLoader(), this.swapDir) : new InMemoryHandler());
      }

      return this.storageHandler;
   }

   void addBeanRemovalListener(BeanRemovalListener bl) {
      this.brListeners.add(bl);
   }

   void removeBeanRemovalListener(BeanRemovalListener bl) {
      this.brListeners.remove(bl);
   }

   private void fireBeanRemoval(ROID pk) {
      Iterator var2 = this.brListeners.iterator();

      while(var2.hasNext()) {
         BeanRemovalListener brl = (BeanRemovalListener)var2.next();
         brl.beanRemovalOccured(pk);
      }

   }

   private String keyAsString(Object key) {
      return key.toString() + ".db";
   }

   public void destroy() {
      this.scrubber.stop();
      this.getStorageHandler().destroy();
   }

   public void remove(Object key) {
      if (debugLogger.isDebugEnabled()) {
         debug("Removing swapped bean for key: " + key);
      }

      this.getStorageHandler().remove(key);
      String asStringKey = this.keyAsString(key);
      this.fileToOIDTable.remove(asStringKey);
      if (!this.brListeners.isEmpty()) {
         this.fileToPKTable.remove(asStringKey);
      }

   }

   public Object read(Object key, Class iface) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Swapping in bean for key: " + key);
      }

      boolean var8 = false;

      Object var3;
      try {
         var8 = true;
         var3 = this.getStorageHandler().read(key, iface);
         var8 = false;
      } finally {
         if (var8) {
            String asStringKey = this.keyAsString(key);
            this.fileToOIDTable.remove(asStringKey);
            if (!this.brListeners.isEmpty()) {
               this.fileToPKTable.remove(asStringKey);
            }

            this.scrubber.unregisterBean(key);
         }
      }

      String asStringKey = this.keyAsString(key);
      this.fileToOIDTable.remove(asStringKey);
      if (!this.brListeners.isEmpty()) {
         this.fileToPKTable.remove(asStringKey);
      }

      this.scrubber.unregisterBean(key);
      return var3;
   }

   public void write(Object pk, Object bean, long timeLastTouched) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Writing bean to swap for key: " + pk);
      }

      this.getStorageHandler().write(pk, bean);
      this.scrubber.registerBean(pk, timeLastTouched);
      String asStringKey = this.keyAsString(pk);
      this.addToFileToOidTable(asStringKey, (WLEnterpriseBean)bean);
      if (!this.brListeners.isEmpty()) {
         this.fileToPKTable.put(asStringKey, pk);
      }

   }

   private void addToFileToOidTable(String key, WLEnterpriseBean bean) {
      int oldState = bean.__WL_getMethodState();
      bean.__WL_setMethodState(128);
      EJBObject eo = null;

      label59: {
         try {
            eo = ((SessionEJBContextImpl)bean.__WL_getEJBContext()).getEJBObject();
            break label59;
         } catch (IllegalStateException var11) {
         } finally {
            bean.__WL_setMethodState(oldState);
         }

         return;
      }

      if (eo != null && this.beanManager.isInMemoryReplication() && !(eo instanceof Activatable)) {
         try {
            this.fileToOIDTable.put(key, (long)ServerHelper.getObjectId(eo));
         } catch (NoSuchObjectException var10) {
         }
      }

   }

   private TimerManager getScrubberTimerManager() {
      return TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   }

   private void deleteInstancesInactiveFor(long sessionTimeoutMS) {
      this.getStorageHandler().deleteInstancesInactiveFor(sessionTimeoutMS);
   }

   private void deleteInstance(Object key) {
      this.getStorageHandler().deleteInstance(key);
   }

   private void processDeletedEntry(String fileName) {
      int oid = (int)this.fileToOIDTable.remove(fileName);
      if (oid != 0) {
         try {
            ServerHelper.unexportObject(oid);
         } catch (NoSuchObjectException var4) {
            if (debugLogger.isDebugEnabled()) {
               debug("Ignoring error that occured during unexporting: " + oid + ". " + StackTraceUtilsClient.throwable2StackTrace(var4));
            }
         }
      }

      Object pk = this.fileToPKTable.remove(fileName);
      if (pk != null && pk instanceof ROID) {
         this.fireBeanRemoval((ROID)pk);
      }

   }

   public void updateClassLoader(ClassLoader cl) {
      this.getStorageHandler().updateClassLoader(cl);
   }

   public void updateIdleTimeoutMS(long ms) {
      this.scrubber.updateIdleTimeoutMS(ms);
   }

   private static void debug(String s) {
      debugLogger.debug("[DiskSwap] " + s);
   }

   static {
      debugLogger = EJBDebugService.swappingLogger;
   }

   protected interface BeanRemovalListener extends EventListener {
      void beanRemovalOccured(ROID var1);
   }

   private final class StatefulTimeoutScrubber implements DiskScrubber, TimerListener {
      private final StatefulTimeoutConfiguration config;
      private final long statefulTimeoutMS;
      private Timer timer;
      private final Map map = new ConcurrentHashMap();

      StatefulTimeoutScrubber(StatefulTimeoutConfiguration config) {
         this.config = config;
         this.statefulTimeoutMS = config.getStatefulTimeout(TimeUnit.MILLISECONDS);
      }

      public void start() {
         if (this.statefulTimeoutMS >= 0L) {
            this.timer = DiskSwap.this.getScrubberTimerManager().scheduleAtFixedRate(this, this.statefulTimeoutMS, this.config.getScrubberDelay(TimeUnit.MILLISECONDS));
         }
      }

      public void stop() {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

         this.map.clear();
      }

      public void registerBean(Object pk, long timeLastTouched) {
         if (this.statefulTimeoutMS >= 0L) {
            this.map.put(pk, timeLastTouched + this.statefulTimeoutMS);
         }
      }

      public void unregisterBean(Object pk) {
         this.map.remove(pk);
      }

      public void timerExpired(Timer timer) {
         long currentTime = System.currentTimeMillis();
         Set entries = this.map.entrySet();
         Iterator var5 = entries.iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            if (currentTime >= (Long)entry.getValue()) {
               entries.remove(entry);
               DiskSwap.this.deleteInstance(entry.getKey());
            }
         }

      }

      public void updateIdleTimeoutMS(long ms) {
      }
   }

   private final class IdleTimeoutScrubber implements DiskScrubber, TimerListener {
      private long idleTimeoutMS;
      private long sessionTimeoutMS;
      private Timer timer;

      IdleTimeoutScrubber(long idleTimeoutMS, long sessionTimeoutMS) {
         if (DiskSwap.debugLogger.isDebugEnabled()) {
            DiskSwap.debug("IdleTimeoutMS: " + idleTimeoutMS + ", SessionTimeoutMS: " + sessionTimeoutMS);
         }

         this.idleTimeoutMS = idleTimeoutMS;
         this.sessionTimeoutMS = sessionTimeoutMS;
         if (this.sessionTimeoutMS < this.idleTimeoutMS) {
            this.sessionTimeoutMS = idleTimeoutMS;
         }

      }

      public void start() {
         this.start(this.idleTimeoutMS);
      }

      private void start(long delay) {
         if (this.idleTimeoutMS > 0L) {
            this.timer = DiskSwap.this.getScrubberTimerManager().scheduleAtFixedRate(this, this.idleTimeoutMS, delay);
         }

      }

      public void stop() {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

      }

      public final void registerBean(Object pk, long timeLastTouched) {
      }

      public final void unregisterBean(Object pk) {
      }

      public void updateIdleTimeoutMS(long ms) {
         long delay = 0L;
         if (this.timer != null) {
            long nextTimeout = this.timer.getTimeout();
            this.stop();
            delay = nextTimeout - System.currentTimeMillis();
            if (delay < 0L) {
               delay = 0L;
            }

            if (ms < delay) {
               delay = ms;
            }
         } else {
            delay = ms;
         }

         this.idleTimeoutMS = ms;
         if (this.sessionTimeoutMS < this.idleTimeoutMS) {
            this.sessionTimeoutMS = this.idleTimeoutMS;
         }

         this.start(delay);
      }

      public void timerExpired(Timer timer) {
         DiskSwap.this.deleteInstancesInactiveFor(this.sessionTimeoutMS);
      }
   }

   interface DiskScrubber {
      void start();

      void stop();

      void registerBean(Object var1, long var2);

      void unregisterBean(Object var1);

      void updateIdleTimeoutMS(long var1);
   }

   final class InMemoryHandler implements StorageHandler {
      private final Map store = new ConcurrentHashMap();

      public Object read(Object key, Class iface) throws InternalException {
         return this.store.remove(key);
      }

      public void write(Object pk, Object bean) throws InternalException {
         this.store.put(pk, bean);
      }

      public void remove(Object key) {
         this.store.remove(key);
      }

      public void deleteInstancesInactiveFor(long sessionTimeoutMS) {
      }

      public void deleteInstance(Object key) {
         this.store.remove(key);
         DiskSwap.this.processDeletedEntry(DiskSwap.this.keyAsString(key));
      }

      public void updateClassLoader(ClassLoader cl) {
      }

      public void destroy() {
         this.store.clear();
      }
   }

   final class DiskBasedHandler implements StorageHandler {
      private final File dir;
      private final PassivationUtils passivationUtils;

      DiskBasedHandler(ClassLoader cl, File dir) {
         this.passivationUtils = new PassivationUtils(cl);
         this.dir = dir;
         if (!dir.exists()) {
            if (!dir.mkdirs()) {
               throw new RuntimeException("Failed to create Stateful Session persistence directory: " + dir.getAbsolutePath());
            }
         } else {
            FileUtils.remove(dir, FileUtils.STAR);
         }

      }

      public Object read(Object key, Class iface) throws InternalException {
         File f = this.getFileFor(key);
         InputStream bis = null;

         Object var5;
         try {
            bis = new BufferedInputStream(new FileInputStream(f));
            var5 = this.passivationUtils.read(DiskSwap.this.beanManager, bis, key, iface);
         } catch (IOException var9) {
            if (DiskSwap.debugLogger.isDebugEnabled()) {
               DiskSwap.debug("Key not found in swap:" + key);
               EJBLogger.logStackTraceAndMessage(var9.getMessage(), var9);
            }

            EJBRuntimeUtils.throwInternalException("Error during read.", new NoSuchEJBException("Bean has been deleted."));
            throw new AssertionError("Should not reach.");
         } finally {
            this.closeQuietly(bis);
            f.delete();
         }

         return var5;
      }

      public void write(Object key, Object bean) throws InternalException {
         OutputStream bos = null;

         try {
            bos = new BufferedOutputStream(new FileOutputStream(this.getFileFor(key)));
            this.passivationUtils.write(DiskSwap.this.beanManager, bos, bean);
         } catch (FileNotFoundException var8) {
            EJBRuntimeUtils.throwInternalException("Error in write.", var8);
         } finally {
            this.closeQuietly(bos);
         }

      }

      public void remove(Object key) {
         this.getFileFor(key).delete();
      }

      public void deleteInstancesInactiveFor(long sessionTimeoutMS) {
         File[] files = this.dir.listFiles();
         if (files != null) {
            long currTime = System.currentTimeMillis();
            File[] var6 = files;
            int var7 = files.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               File f = var6[var8];
               if (Math.abs(f.lastModified() - currTime) > sessionTimeoutMS) {
                  f.delete();
                  DiskSwap.this.processDeletedEntry(f.getName());
               }
            }

         }
      }

      public void deleteInstance(Object key) {
         File f = this.getFileFor(key);
         f.delete();
         DiskSwap.this.processDeletedEntry(f.getName());
      }

      public void updateClassLoader(ClassLoader cl) {
         this.passivationUtils.updateClassLoader(cl);
      }

      public void destroy() {
         if (this.dir.exists()) {
            FileUtils.remove(this.dir);
         }

      }

      private File getFileFor(Object key) {
         return new File(this.dir, DiskSwap.this.keyAsString(key));
      }

      private void closeQuietly(Closeable c) {
         if (c != null) {
            try {
               c.close();
            } catch (IOException var3) {
            }

         }
      }
   }

   interface StorageHandler {
      Object read(Object var1, Class var2) throws InternalException;

      void deleteInstance(Object var1);

      void deleteInstancesInactiveFor(long var1);

      void write(Object var1, Object var2) throws InternalException;

      void remove(Object var1);

      void updateClassLoader(ClassLoader var1);

      void destroy();
   }
}
