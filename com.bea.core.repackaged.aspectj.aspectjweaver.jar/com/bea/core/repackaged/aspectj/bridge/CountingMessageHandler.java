package com.bea.core.repackaged.aspectj.bridge;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class CountingMessageHandler implements IMessageHandler {
   public final IMessageHandler delegate;
   public final CountingMessageHandler proxy;
   private final Hashtable counters;

   public static CountingMessageHandler makeCountingMessageHandler(IMessageHandler handler) {
      return handler instanceof CountingMessageHandler ? (CountingMessageHandler)handler : new CountingMessageHandler(handler);
   }

   public CountingMessageHandler(IMessageHandler delegate) {
      LangUtil.throwIaxIfNull(delegate, "delegate");
      this.delegate = delegate;
      this.counters = new Hashtable();
      this.proxy = delegate instanceof CountingMessageHandler ? (CountingMessageHandler)delegate : null;
   }

   public boolean handleMessage(IMessage message) throws AbortException {
      if (null != this.proxy) {
         return this.proxy.handleMessage(message);
      } else {
         if (null != message) {
            IMessage.Kind kind = message.getKind();
            if (!this.isIgnoring(kind)) {
               this.increment(kind);
            }
         }

         return this.delegate.handleMessage(message);
      }
   }

   public boolean isIgnoring(IMessage.Kind kind) {
      return this.delegate.isIgnoring(kind);
   }

   public void dontIgnore(IMessage.Kind kind) {
      this.delegate.dontIgnore(kind);
   }

   public void ignore(IMessage.Kind kind) {
      this.delegate.ignore(kind);
   }

   public String toString() {
      return this.delegate.toString();
   }

   public int numMessages(IMessage.Kind kind, boolean orGreater) {
      if (null != this.proxy) {
         return this.proxy.numMessages(kind, orGreater);
      } else {
         int result = 0;
         if (null == kind) {
            for(Enumeration enu = this.counters.elements(); enu.hasMoreElements(); result += ((IntHolder)enu.nextElement()).count) {
            }
         } else if (!orGreater) {
            result = this.numMessages(kind);
         } else {
            Iterator i$ = IMessage.KINDS.iterator();

            while(i$.hasNext()) {
               IMessage.Kind k = (IMessage.Kind)i$.next();
               if (kind.isSameOrLessThan(k)) {
                  result += this.numMessages(k);
               }
            }
         }

         return result;
      }
   }

   public boolean hasErrors() {
      return 0 < this.numMessages(IMessage.ERROR, true);
   }

   private int numMessages(IMessage.Kind kind) {
      if (null != this.proxy) {
         return this.proxy.numMessages(kind);
      } else {
         IntHolder counter = (IntHolder)this.counters.get(kind);
         return null == counter ? 0 : counter.count;
      }
   }

   private void increment(IMessage.Kind kind) {
      if (null != this.proxy) {
         throw new IllegalStateException("not called when proxying");
      } else {
         IntHolder counter = (IntHolder)this.counters.get(kind);
         if (null == counter) {
            counter = new IntHolder();
            this.counters.put(kind, counter);
         }

         ++counter.count;
      }
   }

   public void reset() {
      if (this.proxy != null) {
         this.proxy.reset();
      }

      this.counters.clear();
   }

   private static class IntHolder {
      int count;

      private IntHolder() {
      }

      // $FF: synthetic method
      IntHolder(Object x0) {
         this();
      }
   }
}
