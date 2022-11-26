package weblogic.application.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.ErrorCollectionException;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class StateMachineDriver {
   private static final boolean DEBUG_TIMINGS = Boolean.getBoolean("weblogic.DEBUG_TIMINGS");
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public void nextState(StateChange change, Object[] objs) throws StateChangeException {
      for(int i = 0; i < objs.length; ++i) {
         try {
            long time1 = System.nanoTime();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Driving " + objs[i] + " using next transition of change " + change);
            }

            change.next(objs[i]);
            if (DEBUG_TIMINGS) {
               long time2 = System.nanoTime();
               System.out.println("JEEINST:application.StateMachine.nextState:" + (time2 - time1) + ":" + change + ":" + objs[i]);
               time1 = System.nanoTime();
            }
         } catch (Throwable var9) {
            try {
               this.previousState(change, objs, i);
            } catch (StateChangeException var8) {
               change.logRollbackError(var8);
            }

            throw new StateChangeException(var9);
         }
      }

   }

   public void nextStateInParallel(StateChange change, Object[] objs) throws StateChangeException {
      if (objs != null && objs.length != 0) {
         final CountDownLatch doneSignal = new CountDownLatch(objs.length - 1);
         ParallelChange[] changes = new ParallelChange[objs.length];
         WorkManagerFactory fact = WorkManagerFactory.getInstance();
         String wmName = "wls-internal-parallel-" + change.toString() + ":" + UUID.randomUUID().toString();
         boolean wmCreated = false;

         int i;
         ParallelChange pc;
         try {
            int last = objs.length - 1;
            WorkManager wm = null;
            if (last > 0) {
               wm = fact.findOrCreate(wmName, 2, -1);
               wmCreated = true;
            }

            for(i = 0; i < last; ++i) {
               final AtomicBoolean firstCall = new AtomicBoolean(false);
               Runnable onFinish = new Runnable() {
                  public void run() {
                     if (!firstCall.getAndSet(true)) {
                        doneSignal.countDown();
                     }

                  }
               };
               changes[i] = new ParallelChange(objs[i], change, onFinish);
               ContextWrap wrappedWork = new ContextWrap(changes[i], onFinish, onFinish);
               wm.schedule(wrappedWork);
            }

            try {
               pc = new ParallelChange(objs[last], change, (Runnable)null);
               changes[last] = pc;
               pc.run();
               if (last > 0) {
                  doneSignal.await();
               }
            } catch (InterruptedException var19) {
               throw new StateChangeException(var19);
            }
         } finally {
            if (wmCreated) {
               fact.remove(wmName);
            }

         }

         List badResults = new ArrayList();
         ParallelChange[] var22 = changes;
         i = changes.length;

         for(int var26 = 0; var26 < i; ++var26) {
            ParallelChange pc = var22[var26];
            if (pc.result != null) {
               badResults.add(pc.result);
            }
         }

         if (!badResults.isEmpty()) {
            for(int i = 0; i < changes.length; ++i) {
               pc = changes[i];
               if (pc.result == null) {
                  try {
                     this.previousState(change, objs, i);
                  } catch (StateChangeException var18) {
                     change.logRollbackError(var18);
                  }
               }
            }

            ErrorCollectionException ece = new ErrorCollectionException();
            Iterator var27 = badResults.iterator();

            while(var27.hasNext()) {
               Throwable badResult = (Throwable)var27.next();
               ece.add(badResult);
            }

            throw new StateChangeException(ece);
         }
      }
   }

   public void previousState(StateChange change, Object[] objs) throws StateChangeException {
      this.previousState(change, objs, objs.length);
   }

   private void previousState(StateChange change, Object[] objs, int N) throws StateChangeException {
      ErrorCollectionException e = null;

      for(int i = N - 1; i >= 0; --i) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Reversing " + objs[i] + " using previous transition of change " + change);
            }

            change.previous(objs[i]);
         } catch (Throwable var7) {
            if (e == null) {
               e = new ErrorCollectionException();
            }

            e.addError(var7);
         }
      }

      if (e != null) {
         throw new StateChangeException(e);
      }
   }

   static class ParallelChange implements Runnable {
      final Object target;
      final StateChange change;
      Runnable onFinish;
      Throwable result;

      ParallelChange(Object o, StateChange c, Runnable onf) {
         this.target = o;
         this.change = c;
         this.onFinish = onf;
      }

      public void run() {
         try {
            long time1 = System.nanoTime();
            if (StateMachineDriver.debugLogger.isDebugEnabled()) {
               StateMachineDriver.debugLogger.debug("Driving " + this.target + " (in parallel) using next transition of change " + this.change);
            }

            this.change.next(this.target);
            if (StateMachineDriver.DEBUG_TIMINGS) {
               long time2 = System.nanoTime();
               System.out.println("JEEINST:application.StateMachine.nextState(ParallelChange):" + (time2 - time1) + ":" + this.change + ":" + this.target);
               time1 = System.nanoTime();
            }
         } catch (Throwable var8) {
            this.result = var8;
         } finally {
            if (this.onFinish != null) {
               this.onFinish.run();
            }

         }

      }
   }
}
