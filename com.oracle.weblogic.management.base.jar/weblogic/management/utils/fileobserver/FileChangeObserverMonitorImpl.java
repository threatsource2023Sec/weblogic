package weblogic.management.utils.fileobserver;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

@Service
public class FileChangeObserverMonitorImpl implements FileChangeObserverMonitor {
   private long pollInterval;
   private boolean running;
   private boolean destroyed;
   private Timer observeTimer;
   private boolean observersInitialized;
   private final List observers;

   public FileChangeObserverMonitorImpl() {
      this(5000L, (FileChangeObserver)null);
   }

   public FileChangeObserverMonitorImpl(long pollInterval) {
      this(pollInterval, (FileChangeObserver)null);
   }

   public FileChangeObserverMonitorImpl(long pollInterval, FileChangeObserver... fileChangeObservers) {
      this.running = false;
      this.destroyed = false;
      this.observersInitialized = false;
      this.observers = new CopyOnWriteArrayList();
      if (pollInterval < 1000L) {
         pollInterval = 1000L;
      }

      this.pollInterval = pollInterval;
      if (fileChangeObservers != null) {
         FileChangeObserver[] var4 = fileChangeObservers;
         int var5 = fileChangeObservers.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            FileChangeObserver observer = var4[var6];
            this.addFileChangeObserver(observer);
         }
      }

   }

   public void addFileChangeObserver(FileChangeObserver fileChangeObserver) {
      if (fileChangeObserver != null) {
         this.observers.add(fileChangeObserver);
      }

   }

   public void removeFileChangeObserver(FileChangeObserver fileChangeObserver) {
      if (fileChangeObserver != null) {
         while(true) {
            if (this.observers.remove(fileChangeObserver)) {
               continue;
            }
         }
      }

   }

   public Collection getFileChangeObservers() {
      return this.observers;
   }

   public long getPollInterval() {
      return this.pollInterval;
   }

   public synchronized void setPollInterval(long pollInterval) throws Exception {
      this.pollInterval = pollInterval;
      if (this.running) {
         this.stop();
         this.start();
      }

   }

   public synchronized void start() throws ServiceFailureException {
      try {
         if (this.destroyed) {
            throw new IllegalStateException("The monitor has been destroyed.");
         } else if (this.running) {
            throw new IllegalStateException("The monitor is already running.");
         } else {
            if (!this.observersInitialized) {
               Iterator var1 = this.observers.iterator();

               while(var1.hasNext()) {
                  FileChangeObserver observer = (FileChangeObserver)var1.next();
                  observer.initialize();
               }
            }

            this.running = true;
            this.observeTimer = this.getObserveTimer();
            this.observersInitialized = true;
         }
      } catch (Exception var3) {
         throw new ServiceFailureException("Failure", var3);
      }
   }

   public synchronized void stop() {
      this.running = false;
      this.observeTimer.cancel();
   }

   public synchronized void destroy() throws Exception {
      this.stop();
      Iterator var1 = this.observers.iterator();

      while(var1.hasNext()) {
         FileChangeObserver observer = (FileChangeObserver)var1.next();
         observer.destroy();
         this.removeFileChangeObserver(observer);
      }

      this.destroyed = true;
   }

   public void observeChanges() {
      Iterator var1 = this.observers.iterator();

      while(var1.hasNext()) {
         FileChangeObserver observer = (FileChangeObserver)var1.next();
         observer.observeChanges();
      }

   }

   public boolean isRunning() {
      return this.running;
   }

   Timer getObserveTimer() {
      FileChangeObserverTimerListener timerListener = new FileChangeObserverTimerListener(this);
      TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      return timerManager.schedule(timerListener, this.pollInterval, this.pollInterval);
   }
}
