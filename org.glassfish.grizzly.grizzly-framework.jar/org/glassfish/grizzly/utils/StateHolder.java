package org.glassfish.grizzly.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.conditions.Condition;

public final class StateHolder {
   private static final Logger _logger = Grizzly.logger(StateHolder.class);
   private volatile Object state;
   private final ReentrantReadWriteLock readWriteLock;
   private final Collection conditionListeners;

   public StateHolder() {
      this((Object)null);
   }

   public StateHolder(Object initialState) {
      this.state = initialState;
      this.readWriteLock = new ReentrantReadWriteLock();
      this.conditionListeners = new ConcurrentLinkedQueue();
   }

   public Object getState() {
      return this.state;
   }

   public void setState(Object state) {
      this.readWriteLock.writeLock().lock();

      try {
         this.state = state;
         this.readWriteLock.readLock().lock();
         this.readWriteLock.writeLock().unlock();
         this.notifyConditionListeners();
      } finally {
         this.readWriteLock.readLock().unlock();
      }

   }

   public ReentrantReadWriteLock getStateLocker() {
      return this.readWriteLock;
   }

   public Future notifyWhenStateIsEqual(final Object state, CompletionHandler completionHandler) {
      return this.notifyWhenConditionMatchState(new Condition() {
         public boolean check() {
            return state == StateHolder.this.state;
         }
      }, completionHandler);
   }

   public Future notifyWhenStateIsNotEqual(final Object state, CompletionHandler completionHandler) {
      return this.notifyWhenConditionMatchState(new Condition() {
         public boolean check() {
            return state != StateHolder.this.state;
         }
      }, completionHandler);
   }

   public Future notifyWhenConditionMatchState(Condition condition, CompletionHandler completionHandler) {
      this.readWriteLock.readLock().lock();

      Object resultFuture;
      try {
         if (condition.check()) {
            if (completionHandler != null) {
               completionHandler.completed(this.state);
            }

            resultFuture = ReadyFutureImpl.create(this.state);
         } else {
            FutureImpl future = SafeFutureImpl.create();
            ConditionElement elem = new ConditionElement(condition, future, completionHandler);
            this.conditionListeners.add(elem);
            resultFuture = future;
         }
      } finally {
         this.readWriteLock.readLock().unlock();
      }

      return (Future)resultFuture;
   }

   protected void notifyConditionListeners() {
      Iterator it = this.conditionListeners.iterator();

      while(it.hasNext()) {
         ConditionElement element = (ConditionElement)it.next();

         try {
            if (element.condition.check()) {
               it.remove();
               if (element.completionHandler != null) {
                  element.completionHandler.completed(this.state);
               }

               element.future.result(this.state);
            }
         } catch (Exception var4) {
            _logger.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_STATE_HOLDER_CALLING_CONDITIONLISTENER_EXCEPTION(), var4);
         }
      }

   }

   protected static final class ConditionElement {
      private final Condition condition;
      private final FutureImpl future;
      private final CompletionHandler completionHandler;

      public ConditionElement(Condition condition, FutureImpl future, CompletionHandler completionHandler) {
         this.condition = condition;
         this.future = future;
         this.completionHandler = completionHandler;
      }

      public CompletionHandler getCompletionHandler() {
         return this.completionHandler;
      }

      public Condition getCondition() {
         return this.condition;
      }

      public FutureImpl getFuture() {
         return this.future;
      }
   }
}
