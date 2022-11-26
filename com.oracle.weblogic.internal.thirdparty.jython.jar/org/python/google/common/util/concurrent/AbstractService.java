package org.python.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.ForOverride;

@Beta
@GwtIncompatible
public abstract class AbstractService implements Service {
   private static final ListenerCallQueue.Event STARTING_EVENT = new ListenerCallQueue.Event() {
      public void call(Service.Listener listener) {
         listener.starting();
      }

      public String toString() {
         return "starting()";
      }
   };
   private static final ListenerCallQueue.Event RUNNING_EVENT = new ListenerCallQueue.Event() {
      public void call(Service.Listener listener) {
         listener.running();
      }

      public String toString() {
         return "running()";
      }
   };
   private static final ListenerCallQueue.Event STOPPING_FROM_STARTING_EVENT;
   private static final ListenerCallQueue.Event STOPPING_FROM_RUNNING_EVENT;
   private static final ListenerCallQueue.Event TERMINATED_FROM_NEW_EVENT;
   private static final ListenerCallQueue.Event TERMINATED_FROM_RUNNING_EVENT;
   private static final ListenerCallQueue.Event TERMINATED_FROM_STOPPING_EVENT;
   private final Monitor monitor = new Monitor();
   private final Monitor.Guard isStartable = new IsStartableGuard();
   private final Monitor.Guard isStoppable = new IsStoppableGuard();
   private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard();
   private final Monitor.Guard isStopped = new IsStoppedGuard();
   private final ListenerCallQueue listeners = new ListenerCallQueue();
   private volatile StateSnapshot snapshot;

   private static ListenerCallQueue.Event terminatedEvent(final Service.State from) {
      return new ListenerCallQueue.Event() {
         public void call(Service.Listener listener) {
            listener.terminated(from);
         }

         public String toString() {
            return "terminated({from = " + from + "})";
         }
      };
   }

   private static ListenerCallQueue.Event stoppingEvent(final Service.State from) {
      return new ListenerCallQueue.Event() {
         public void call(Service.Listener listener) {
            listener.stopping(from);
         }

         public String toString() {
            return "stopping({from = " + from + "})";
         }
      };
   }

   protected AbstractService() {
      this.snapshot = new StateSnapshot(Service.State.NEW);
   }

   @ForOverride
   protected abstract void doStart();

   @ForOverride
   protected abstract void doStop();

   @CanIgnoreReturnValue
   public final Service startAsync() {
      if (this.monitor.enterIf(this.isStartable)) {
         try {
            this.snapshot = new StateSnapshot(Service.State.STARTING);
            this.enqueueStartingEvent();
            this.doStart();
         } catch (Throwable var5) {
            this.notifyFailed(var5);
         } finally {
            this.monitor.leave();
            this.dispatchListenerEvents();
         }

         return this;
      } else {
         throw new IllegalStateException("Service " + this + " has already been started");
      }
   }

   @CanIgnoreReturnValue
   public final Service stopAsync() {
      if (this.monitor.enterIf(this.isStoppable)) {
         try {
            Service.State previous = this.state();
            switch (previous) {
               case NEW:
                  this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                  this.enqueueTerminatedEvent(Service.State.NEW);
                  break;
               case STARTING:
                  this.snapshot = new StateSnapshot(Service.State.STARTING, true, (Throwable)null);
                  this.enqueueStoppingEvent(Service.State.STARTING);
                  break;
               case RUNNING:
                  this.snapshot = new StateSnapshot(Service.State.STOPPING);
                  this.enqueueStoppingEvent(Service.State.RUNNING);
                  this.doStop();
                  break;
               case STOPPING:
               case TERMINATED:
               case FAILED:
                  throw new AssertionError("isStoppable is incorrectly implemented, saw: " + previous);
               default:
                  throw new AssertionError("Unexpected state: " + previous);
            }
         } catch (Throwable var5) {
            this.notifyFailed(var5);
         } finally {
            this.monitor.leave();
            this.dispatchListenerEvents();
         }
      }

      return this;
   }

   public final void awaitRunning() {
      this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);

      try {
         this.checkCurrentState(Service.State.RUNNING);
      } finally {
         this.monitor.leave();
      }

   }

   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
         try {
            this.checkCurrentState(Service.State.RUNNING);
         } finally {
            this.monitor.leave();
         }

      } else {
         throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state.");
      }
   }

   public final void awaitTerminated() {
      this.monitor.enterWhenUninterruptibly(this.isStopped);

      try {
         this.checkCurrentState(Service.State.TERMINATED);
      } finally {
         this.monitor.leave();
      }

   }

   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
         try {
            this.checkCurrentState(Service.State.TERMINATED);
         } finally {
            this.monitor.leave();
         }

      } else {
         throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. Current state: " + this.state());
      }
   }

   @GuardedBy("monitor")
   private void checkCurrentState(Service.State expected) {
      Service.State actual = this.state();
      if (actual != expected) {
         if (actual == Service.State.FAILED) {
            throw new IllegalStateException("Expected the service " + this + " to be " + expected + ", but the service has FAILED", this.failureCause());
         } else {
            throw new IllegalStateException("Expected the service " + this + " to be " + expected + ", but was " + actual);
         }
      }
   }

   protected final void notifyStarted() {
      this.monitor.enter();

      try {
         if (this.snapshot.state != Service.State.STARTING) {
            IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
            this.notifyFailed(failure);
            throw failure;
         }

         if (this.snapshot.shutdownWhenStartupFinishes) {
            this.snapshot = new StateSnapshot(Service.State.STOPPING);
            this.doStop();
         } else {
            this.snapshot = new StateSnapshot(Service.State.RUNNING);
            this.enqueueRunningEvent();
         }
      } finally {
         this.monitor.leave();
         this.dispatchListenerEvents();
      }

   }

   protected final void notifyStopped() {
      this.monitor.enter();

      try {
         Service.State previous = this.snapshot.state;
         if (previous != Service.State.STOPPING && previous != Service.State.RUNNING) {
            IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + previous);
            this.notifyFailed(failure);
            throw failure;
         }

         this.snapshot = new StateSnapshot(Service.State.TERMINATED);
         this.enqueueTerminatedEvent(previous);
      } finally {
         this.monitor.leave();
         this.dispatchListenerEvents();
      }

   }

   protected final void notifyFailed(Throwable cause) {
      Preconditions.checkNotNull(cause);
      this.monitor.enter();

      try {
         Service.State previous = this.state();
         switch (previous) {
            case NEW:
            case TERMINATED:
               throw new IllegalStateException("Failed while in state:" + previous, cause);
            case STARTING:
            case RUNNING:
            case STOPPING:
               this.snapshot = new StateSnapshot(Service.State.FAILED, false, cause);
               this.enqueueFailedEvent(previous, cause);
            case FAILED:
               break;
            default:
               throw new AssertionError("Unexpected state: " + previous);
         }
      } finally {
         this.monitor.leave();
         this.dispatchListenerEvents();
      }

   }

   public final boolean isRunning() {
      return this.state() == Service.State.RUNNING;
   }

   public final Service.State state() {
      return this.snapshot.externalState();
   }

   public final Throwable failureCause() {
      return this.snapshot.failureCause();
   }

   public final void addListener(Service.Listener listener, Executor executor) {
      this.listeners.addListener(listener, executor);
   }

   public String toString() {
      return this.getClass().getSimpleName() + " [" + this.state() + "]";
   }

   private void dispatchListenerEvents() {
      if (!this.monitor.isOccupiedByCurrentThread()) {
         this.listeners.dispatch();
      }

   }

   private void enqueueStartingEvent() {
      this.listeners.enqueue(STARTING_EVENT);
   }

   private void enqueueRunningEvent() {
      this.listeners.enqueue(RUNNING_EVENT);
   }

   private void enqueueStoppingEvent(Service.State from) {
      if (from == Service.State.STARTING) {
         this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
      } else {
         if (from != Service.State.RUNNING) {
            throw new AssertionError();
         }

         this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
      }

   }

   private void enqueueTerminatedEvent(Service.State from) {
      switch (from) {
         case NEW:
            this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
            break;
         case STARTING:
         case TERMINATED:
         case FAILED:
         default:
            throw new AssertionError();
         case RUNNING:
            this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
            break;
         case STOPPING:
            this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
      }

   }

   private void enqueueFailedEvent(final Service.State from, final Throwable cause) {
      this.listeners.enqueue(new ListenerCallQueue.Event() {
         public void call(Service.Listener listener) {
            listener.failed(from, cause);
         }

         public String toString() {
            return "failed({from = " + from + ", cause = " + cause + "})";
         }
      });
   }

   static {
      STOPPING_FROM_STARTING_EVENT = stoppingEvent(Service.State.STARTING);
      STOPPING_FROM_RUNNING_EVENT = stoppingEvent(Service.State.RUNNING);
      TERMINATED_FROM_NEW_EVENT = terminatedEvent(Service.State.NEW);
      TERMINATED_FROM_RUNNING_EVENT = terminatedEvent(Service.State.RUNNING);
      TERMINATED_FROM_STOPPING_EVENT = terminatedEvent(Service.State.STOPPING);
   }

   @Immutable
   private static final class StateSnapshot {
      final Service.State state;
      final boolean shutdownWhenStartupFinishes;
      @Nullable
      final Throwable failure;

      StateSnapshot(Service.State internalState) {
         this(internalState, false, (Throwable)null);
      }

      StateSnapshot(Service.State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
         Preconditions.checkArgument(!shutdownWhenStartupFinishes || internalState == Service.State.STARTING, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object)internalState);
         Preconditions.checkArgument(!(failure != null ^ internalState == Service.State.FAILED), "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", internalState, failure);
         this.state = internalState;
         this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
         this.failure = failure;
      }

      Service.State externalState() {
         return this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING ? Service.State.STOPPING : this.state;
      }

      Throwable failureCause() {
         Preconditions.checkState(this.state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", (Object)this.state);
         return this.failure;
      }
   }

   private final class IsStoppedGuard extends Monitor.Guard {
      IsStoppedGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         return AbstractService.this.state().isTerminal();
      }
   }

   private final class HasReachedRunningGuard extends Monitor.Guard {
      HasReachedRunningGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
      }
   }

   private final class IsStoppableGuard extends Monitor.Guard {
      IsStoppableGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
      }
   }

   private final class IsStartableGuard extends Monitor.Guard {
      IsStartableGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         return AbstractService.this.state() == Service.State.NEW;
      }
   }
}
