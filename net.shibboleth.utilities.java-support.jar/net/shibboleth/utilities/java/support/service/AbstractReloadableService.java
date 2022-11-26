package net.shibboleth.utilities.java.support.service;

import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.UnmodifiableComponent;
import net.shibboleth.utilities.java.support.primitive.TimerSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractReloadableService extends AbstractIdentifiableInitializableComponent implements ReloadableService, UnmodifiableComponent {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractReloadableService.class);
   @Duration
   private long reloadCheckDelay = 0L;
   @Nullable
   private Timer reloadTaskTimer;
   @Nullable
   private Timer internalTaskTimer;
   @Nullable
   private ServiceReloadTask reloadTask;
   @Nullable
   private DateTime lastReloadInstant;
   @Nullable
   private DateTime lastSuccessfulReleaseInstant;
   @Nullable
   private Throwable reloadFailureCause;
   private boolean failFast;
   @Nullable
   private String logPrefix;

   @Duration
   public long getReloadCheckDelay() {
      return this.reloadCheckDelay;
   }

   @Duration
   public void setReloadCheckDelay(@Duration long delay) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.reloadCheckDelay = delay;
   }

   @Nullable
   public Timer getReloadTaskTimer() {
      return this.reloadTaskTimer;
   }

   public void setReloadTaskTimer(@Nullable Timer timer) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.reloadTaskTimer = timer;
   }

   @Nullable
   public DateTime getLastReloadAttemptInstant() {
      return this.lastReloadInstant;
   }

   @Nullable
   public DateTime getLastSuccessfulReloadInstant() {
      return this.lastSuccessfulReleaseInstant;
   }

   @Nullable
   public Throwable getReloadFailureCause() {
      return this.reloadFailureCause;
   }

   public boolean isFailFast() {
      return this.failFast;
   }

   public void setFailFast(boolean value) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.failFast = value;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      this.log.info("{} Performing initial load", this.getLogPrefix());

      try {
         this.lastReloadInstant = new DateTime(ISOChronology.getInstanceUTC());
         this.doReload();
         this.lastSuccessfulReleaseInstant = this.lastReloadInstant;
      } catch (ServiceException var2) {
         if (this.isFailFast()) {
            throw new ComponentInitializationException(this.getLogPrefix() + " could not perform initial load", var2);
         }

         this.log.error("{} Initial load failed", this.getLogPrefix(), var2);
         if (this.reloadCheckDelay > 0L) {
            this.log.info("{} Continuing to poll configuration", this.getLogPrefix());
         } else {
            this.log.error("{} No further attempts will be made to reload", this.getLogPrefix());
         }
      } catch (Exception var3) {
         throw new ComponentInitializationException(this.getLogPrefix() + " Unexpected error during initial load", var3);
      }

      if (this.reloadCheckDelay > 0L) {
         if (null == this.reloadTaskTimer) {
            this.log.debug("{} No reload task timer specified, creating default", this.getLogPrefix());
            this.internalTaskTimer = new Timer(TimerSupport.getTimerName(this), true);
         } else {
            this.internalTaskTimer = this.reloadTaskTimer;
         }

         this.log.info("{} Reload time set to: {}, starting refresh thread", this.getLogPrefix(), this.reloadCheckDelay);
         this.reloadTask = new ServiceReloadTask();
         this.internalTaskTimer.schedule(this.reloadTask, this.reloadCheckDelay, this.reloadCheckDelay);
      }

   }

   protected void doDestroy() {
      this.log.info("{} Starting shutdown", this.getLogPrefix());
      if (this.reloadTask != null) {
         this.reloadTask.cancel();
         this.reloadTask = null;
      }

      if (this.reloadTaskTimer == null && this.internalTaskTimer != null) {
         this.internalTaskTimer.cancel();
      }

      this.internalTaskTimer = null;
      this.log.info("{} Completing shutdown", this.getLogPrefix());
      super.doDestroy();
   }

   public final void reload() {
      DateTime now = new DateTime(ISOChronology.getInstanceUTC());
      this.lastReloadInstant = now;

      try {
         this.doReload();
         this.lastSuccessfulReleaseInstant = now;
         this.reloadFailureCause = null;
      } catch (ServiceException var3) {
         this.log.error("{} Reload for {} failed", new Object[]{this.getLogPrefix(), this.getId(), var3});
         this.reloadFailureCause = var3;
         throw var3;
      }
   }

   protected abstract boolean shouldReload();

   protected void doReload() {
      this.log.info("{} Reloading service configuration", this.getLogPrefix());
   }

   @Nonnull
   @NotEmpty
   protected String getLogPrefix() {
      String prefix = this.logPrefix;
      if (null == prefix) {
         if (this.getId() != null) {
            StringBuilder builder = (new StringBuilder("Service '")).append(this.getId()).append("':");
            prefix = builder.toString();
            if (null == this.logPrefix) {
               this.logPrefix = prefix;
            }
         } else {
            prefix = "Service:";
         }
      }

      return prefix;
   }

   protected class ServiceReloadTask extends TimerTask {
      public void run() {
         if (AbstractReloadableService.this.shouldReload()) {
            try {
               AbstractReloadableService.this.reload();
            } catch (ServiceException var2) {
               AbstractReloadableService.this.log.debug("{} Previously logged error during reload", AbstractReloadableService.this.getLogPrefix(), var2);
            } catch (Throwable var3) {
               AbstractReloadableService.this.log.error("{} Unexpected error during reload", AbstractReloadableService.this.getLogPrefix(), var3);
            }
         }

      }
   }
}
