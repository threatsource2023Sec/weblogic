package weblogic.work;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import weblogic.kernel.KernelStatus;
import weblogic.utils.PlatformConstants;
import weblogic.utils.collections.MaybeMapper;

public abstract class WorkAdapter implements Work {
   static final MaybeMapper CLAIM_VERSION = new MaybeMapper() {
      public WorkAdapter unbox(WorkAdapter w, long v) {
         return w.claimVersion(v) ? w : null;
      }
   };
   static final MaybeMapper CHECK_EXPIRED_WORK = new MaybeMapper() {
      public WorkAdapter unbox(WorkAdapter w, long v) {
         return v != -1L && w.getVersion() > v ? w : null;
      }
   };
   static final MaybeMapper CHECK_STALE = new MaybeMapper() {
      public WorkAdapter unbox(WorkAdapter w, long v) {
         return w.isCurrentVersion(v) ? w : null;
      }
   };
   SelfTuningWorkManagerImpl wm;
   RequestClass requestClass;
   long creationTimeStamp = System.currentTimeMillis();
   private volatile boolean started;
   private long startedTimeStamp;
   private boolean scheduled;
   private volatile long version = 0L;
   private static final AtomicLongFieldUpdater version_updater;
   static final long SKIP_VERSION_CHECKING = -1L;

   public long getVersion() {
      return this.version;
   }

   boolean isCurrentVersion(long v) {
      return v == -1L || this.getVersion() == v;
   }

   private boolean claimVersion(long v) {
      return v == -1L || this.getVersion() == v && (version_updater == null || version_updater.compareAndSet(this, v, v + 1L));
   }

   void returnForReuse() {
      this.started = false;
      this.creationTimeStamp = -1L;
      this.scheduled = false;
   }

   public Runnable overloadAction(String reason) {
      return null;
   }

   public Runnable cancel(String reason) {
      return null;
   }

   public void release() {
   }

   public boolean isAdminChannelRequest() {
      return false;
   }

   public boolean isTransactional() {
      return false;
   }

   final boolean setScheduled() {
      if (this.scheduled) {
         return false;
      } else {
         synchronized(this) {
            if (this.scheduled) {
               return false;
            } else {
               this.scheduled = true;
               if (this.creationTimeStamp <= 0L) {
                  this.creationTimeStamp = System.currentTimeMillis();
               }

               return true;
            }
         }
      }
   }

   void setWorkManager(SelfTuningWorkManagerImpl wm) {
      if (wm != null) {
         this.requestClass = wm.getRequestClass();
         this.wm = wm;
      }
   }

   final SelfTuningWorkManagerImpl getWorkManager() {
      return this.wm;
   }

   final MinThreadsConstraint getMinThreadsConstraint() {
      return this.wm == null ? null : this.wm.getMinThreadsConstraint();
   }

   final MaxThreadsConstraint getMaxThreadsConstraint() {
      return this.wm == null ? null : this.wm.getMaxThreadsConstraint();
   }

   protected final boolean isStarted() {
      return this.started;
   }

   final void started() {
      this.started = true;
      this.startedTimeStamp = System.currentTimeMillis();
   }

   final Runnable getWork() {
      if (this.wm != null && this.wm.isShutdown()) {
         Runnable cancelTask = this.cancel(this.wm.getCancelMessage());
         if (cancelTask != null) {
            return cancelTask;
         }
      }

      return this;
   }

   public String getDescription() {
      return null;
   }

   final String dump() {
      StringBuffer sb = new StringBuffer();
      sb.append("Workmanager: " + (this.wm != null ? this.wm.getName() : "") + ", ");
      sb.append("Version: " + this.getVersion() + ", ");
      sb.append("Scheduled=" + this.scheduled + ", Started=" + this.started + ", ");
      if (!this.started) {
         sb.append("Wait time: " + (System.currentTimeMillis() - this.creationTimeStamp) + " ms");
      } else {
         sb.append("Started time: " + (System.currentTimeMillis() - this.startedTimeStamp) + " ms");
      }

      sb.append(PlatformConstants.EOL);
      return sb.toString();
   }

   public String toString() {
      return this.dump();
   }

   static {
      AtomicLongFieldUpdater updater = null;
      if (!KernelStatus.isApplet()) {
         updater = AtomicLongFieldUpdater.newUpdater(WorkAdapter.class, "version");
      }

      version_updater = updater;
   }
}
