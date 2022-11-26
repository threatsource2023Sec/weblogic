package org.python.google.common.util.concurrent;

import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.collect.Sets;

@GwtCompatible(
   emulated = true
)
abstract class AggregateFutureState {
   private volatile Set seenExceptions = null;
   private volatile int remaining;
   private static final AtomicHelper ATOMIC_HELPER;
   private static final Logger log = Logger.getLogger(AggregateFutureState.class.getName());

   AggregateFutureState(int remainingFutures) {
      this.remaining = remainingFutures;
   }

   final Set getOrInitSeenExceptions() {
      Set seenExceptionsLocal = this.seenExceptions;
      if (seenExceptionsLocal == null) {
         seenExceptionsLocal = Sets.newConcurrentHashSet();
         this.addInitialException(seenExceptionsLocal);
         ATOMIC_HELPER.compareAndSetSeenExceptions(this, (Set)null, seenExceptionsLocal);
         seenExceptionsLocal = this.seenExceptions;
      }

      return seenExceptionsLocal;
   }

   abstract void addInitialException(Set var1);

   final int decrementRemainingAndGet() {
      return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
   }

   static {
      Object helper;
      try {
         helper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
      } catch (Throwable var2) {
         log.log(Level.SEVERE, "SafeAtomicHelper is broken!", var2);
         helper = new SynchronizedAtomicHelper();
      }

      ATOMIC_HELPER = (AtomicHelper)helper;
   }

   private static final class SynchronizedAtomicHelper extends AtomicHelper {
      private SynchronizedAtomicHelper() {
         super(null);
      }

      void compareAndSetSeenExceptions(AggregateFutureState state, Set expect, Set update) {
         synchronized(state) {
            if (state.seenExceptions == expect) {
               state.seenExceptions = update;
            }

         }
      }

      int decrementAndGetRemainingCount(AggregateFutureState state) {
         synchronized(state) {
            state.remaining--;
            return state.remaining;
         }
      }

      // $FF: synthetic method
      SynchronizedAtomicHelper(Object x0) {
         this();
      }
   }

   private static final class SafeAtomicHelper extends AtomicHelper {
      final AtomicReferenceFieldUpdater seenExceptionsUpdater;
      final AtomicIntegerFieldUpdater remainingCountUpdater;

      SafeAtomicHelper(AtomicReferenceFieldUpdater seenExceptionsUpdater, AtomicIntegerFieldUpdater remainingCountUpdater) {
         super(null);
         this.seenExceptionsUpdater = seenExceptionsUpdater;
         this.remainingCountUpdater = remainingCountUpdater;
      }

      void compareAndSetSeenExceptions(AggregateFutureState state, Set expect, Set update) {
         this.seenExceptionsUpdater.compareAndSet(state, expect, update);
      }

      int decrementAndGetRemainingCount(AggregateFutureState state) {
         return this.remainingCountUpdater.decrementAndGet(state);
      }
   }

   private abstract static class AtomicHelper {
      private AtomicHelper() {
      }

      abstract void compareAndSetSeenExceptions(AggregateFutureState var1, Set var2, Set var3);

      abstract int decrementAndGetRemainingCount(AggregateFutureState var1);

      // $FF: synthetic method
      AtomicHelper(Object x0) {
         this();
      }
   }
}
