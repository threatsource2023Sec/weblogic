package org.apache.openjpa.event;

import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;

public class TransactionEventManager extends AbstractConcurrentEventManager {
   private int _begin = 0;
   private int _flush = 0;
   private int _end = 0;

   public void addListener(Object listener) {
      super.addListener(listener);
      if (listener instanceof BeginTransactionListener) {
         ++this._begin;
      }

      if (listener instanceof FlushTransactionListener) {
         ++this._flush;
      }

      if (listener instanceof EndTransactionListener) {
         ++this._end;
      }

   }

   public boolean removeListener(Object listener) {
      if (!super.removeListener(listener)) {
         return false;
      } else {
         if (listener instanceof BeginTransactionListener) {
            --this._begin;
         }

         if (listener instanceof FlushTransactionListener) {
            --this._flush;
         }

         if (listener instanceof EndTransactionListener) {
            --this._end;
         }

         return true;
      }
   }

   public boolean hasBeginListeners() {
      return this._begin > 0;
   }

   public boolean hasFlushListeners() {
      return this._flush > 0;
   }

   public boolean hasEndListeners() {
      return this._end > 0;
   }

   protected void fireEvent(Object event, Object listener) {
      TransactionEvent ev = (TransactionEvent)event;
      switch (ev.getType()) {
         case 0:
            if (listener instanceof BeginTransactionListener) {
               ((BeginTransactionListener)listener).afterBegin(ev);
            }
            break;
         case 1:
            if (listener instanceof FlushTransactionListener) {
               ((FlushTransactionListener)listener).beforeFlush(ev);
            }
            break;
         case 2:
            if (listener instanceof FlushTransactionListener) {
               ((FlushTransactionListener)listener).afterFlush(ev);
            }
            break;
         case 3:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).beforeCommit(ev);
            }
            break;
         case 4:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).afterCommit(ev);
            }
            break;
         case 5:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).afterRollback(ev);
            }
            break;
         case 6:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).afterStateTransitions(ev);
            }
            break;
         case 7:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).afterCommitComplete(ev);
            }
            break;
         case 8:
            if (listener instanceof EndTransactionListener) {
               ((EndTransactionListener)listener).afterRollbackComplete(ev);
            }
      }

   }
}
