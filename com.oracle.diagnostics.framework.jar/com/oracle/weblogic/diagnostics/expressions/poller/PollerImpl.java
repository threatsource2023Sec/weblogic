package com.oracle.weblogic.diagnostics.expressions.poller;

import com.oracle.weblogic.diagnostics.expressions.NotEnoughDataException;
import com.oracle.weblogic.diagnostics.expressions.TrackedValue;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.timerservice.TimerListener;
import com.oracle.weblogic.diagnostics.timerservice.TimerServiceFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.inject.Inject;
import weblogic.diagnostics.debug.DebugLogger;

public class PollerImpl implements Poller, TimerListener {
   private static final CircularValuesBuffer EMPTY_ROW = new CircularValuesBuffer(0);
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionPoller");
   private final Map valuesMap = new ConcurrentHashMap();
   private CircularValuesBuffer rowTimestamps = null;
   private long cyclesCompleted = 0L;
   private final int frequency;
   private final int sampleWindowSizeMin;
   private final TrackedValueSource spec;
   private String key;
   private boolean polling;
   private AtomicBoolean timerCallbackInProgress = new AtomicBoolean(false);
   private Throwable pollingException;
   @Inject
   private TimerServiceFactory timerFactory;
   private int referenceCount = 0;

   public PollerImpl(String key, TrackedValueSource spec, int frequency, int size) {
      this.key = key;
      this.spec = spec;
      this.frequency = frequency;
      this.sampleWindowSizeMin = size;
      this.rowTimestamps = new CircularValuesBuffer(this.sampleWindowSizeMin);
   }

   public void startPolling() {
      if (!this.polling) {
         this.timerFactory.getTimerService().registerListener(this);
         this.polling = true;
      }

   }

   public void stopPolling() {
      if (this.polling) {
         this.timerFactory.getTimerService().unregisterListener(this);
         this.polling = false;
      }

   }

   public boolean isPolling() {
      return this.polling;
   }

   public String getKey() {
      if (this.key == null) {
         StringBuffer keyBuf = new StringBuffer();
         keyBuf.append(this.spec.getKey()).append("_").append(Integer.toString(this.frequency)).append("_").append(Integer.toString(this.sampleWindowSizeMin));
         this.key = keyBuf.toString();
      }

      return this.key;
   }

   public int hashCode() {
      return this.getKey().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof PollerImpl) {
         PollerImpl rhs = (PollerImpl)obj;
         return this.getKey().equals(rhs.getKey());
      } else {
         return super.equals(obj);
      }
   }

   public Iterator iterator() {
      if (this.pollingException != null) {
         throw new RuntimeException(this.pollingException);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Iterator requested for[" + this.spec.getKey() + "]: desired sample depth: " + this.sampleWindowSizeMin + ", values: " + this.printValues());
         }

         return new RowIterator();
      }
   }

   public int getFrequency() {
      return this.frequency;
   }

   public void timerExpired() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Poller(" + this.getKey() + "): Timer expired!");
      }

      boolean canContinue = this.timerCallbackInProgress.compareAndSet(false, true);
      if (!canContinue) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("timerExpired(): already in progress, exiting");
         }

      } else {
         try {
            long timestamp = System.currentTimeMillis();
            this.rowTimestamps.push(timestamp);
            HashSet notFoundThisCycle = new HashSet(this.valuesMap.keySet());

            TrackedValue value;
            CircularValuesBuffer valuesList;
            for(Iterator it = this.spec.iterator(); it.hasNext(); valuesList.push(value)) {
               value = (TrackedValue)it.next();
               notFoundThisCycle.remove(value.getKey());
               valuesList = (CircularValuesBuffer)this.valuesMap.get(value.getKey());
               if (valuesList == null) {
                  valuesList = new CircularValuesBuffer(this.sampleWindowSizeMin);
                  this.valuesMap.put(value.getKey(), valuesList);
               }
            }

            if (notFoundThisCycle.size() > 0) {
               String missingInstance;
               for(Iterator var13 = notFoundThisCycle.iterator(); var13.hasNext(); this.valuesMap.remove(missingInstance)) {
                  missingInstance = (String)var13.next();
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("removing missing instance " + missingInstance);
                  }
               }

            }
         } catch (Throwable var11) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unexpected exception during polling operation, stopping poller " + this.getKey() + ":  " + var11.getMessage());
            }

            this.pollingException = var11;
            this.stopPolling();
            throw var11;
         } finally {
            ++this.cyclesCompleted;
            this.timerCallbackInProgress.set(false);
         }
      }
   }

   public void clear() {
      this.reset();
   }

   public int size() {
      return this.valuesMap.keySet().size();
   }

   public void reset() {
      this.valuesMap.clear();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public Collection getResolvedValues() {
      return this.valuesMap.values();
   }

   public Collection collection() {
      return this.getResolvedValues();
   }

   public Collection flatten() {
      return (Collection)this.getResolvedValues().stream().flatMap((v) -> {
         return v.stream();
      }).collect(Collectors.toList());
   }

   int incrementRefCount() {
      return ++this.referenceCount;
   }

   int decrementRefCount() {
      if (this.referenceCount > 0) {
         --this.referenceCount;
      }

      return this.referenceCount;
   }

   int refCount() {
      return this.referenceCount;
   }

   private String printValues() {
      StringBuffer buf = new StringBuffer();
      Iterator it = this.valuesMap.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         buf.append("{ ");
         String key = (String)entry.getKey();
         CircularValuesBuffer stack = (CircularValuesBuffer)entry.getValue();
         buf.append("key: ").append(key).append(", depth: ").append(stack.size()).append(", values: ").append(stack.toString());
         buf.append(" }");
         if (it.hasNext()) {
            buf.append(',');
         }
      }

      return buf.toString();
   }

   private class RowIterator implements Iterator {
      private final Iterator delegate;

      public RowIterator() {
         if (PollerImpl.this.cyclesCompleted < (long)PollerImpl.this.sampleWindowSizeMin) {
            throw new NotEnoughDataException(PollerImpl.this.getKey());
         } else {
            this.delegate = PollerImpl.this.valuesMap.entrySet().iterator();
         }
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public CircularValuesBuffer next() {
         CircularValuesBuffer nextVal = this.findNext();
         return nextVal;
      }

      private CircularValuesBuffer findNext() {
         Map.Entry entry = (Map.Entry)this.delegate.next();
         CircularValuesBuffer row = (CircularValuesBuffer)entry.getValue();
         boolean rowHasEnoughSamples = row.size() >= PollerImpl.this.sampleWindowSizeMin;
         if (rowHasEnoughSamples) {
            return row;
         } else {
            if (PollerImpl.debugLogger.isDebugEnabled()) {
               PollerImpl.debugLogger.debug("Using empty row for [" + (String)entry.getKey() + "], not enough samples collected yet");
            }

            return PollerImpl.EMPTY_ROW;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
