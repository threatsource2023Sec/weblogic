package weblogic.utils.classloaders.debug;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import weblogic.diagnostics.debug.DebugLogger;

class RecordTrace {
   private static final DebugLogger ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
   private ArrayBlockingQueue queue;
   private String bufferCapacityStringValue = null;
   private int bufferCapacity;
   private final int defaultBufferCapacity = 50;

   RecordTrace() {
      Map params = ctDebugLogger.getDebugParameters();
      if (params.containsKey("BufferCapacity")) {
         this.bufferCapacity = Integer.parseInt((String)params.get("BufferCapacity"));
      } else {
         this.bufferCapacity = 50;
      }

      this.queue = new ArrayBlockingQueue(this.bufferCapacity);
      if (ctDebugLogger.isDebugEnabled()) {
         ctDebugLogger.debug("Contextual Trace BufferCapacity = " + this.bufferCapacity);
      }

   }

   synchronized boolean add(Record r) {
      this.checkAndSetCapacity();
      if (this.queue.remainingCapacity() == 0) {
         try {
            this.queue.take();
         } catch (InterruptedException var3) {
            var3.printStackTrace();
            return false;
         }
      }

      return this.queue.add(r);
   }

   private synchronized void checkAndSetCapacity() {
      String paramValue = (String)ctDebugLogger.getDebugParameters().get("BufferCapacity");
      ArrayList tempBuffer;
      if (paramValue == null && this.bufferCapacityStringValue != null) {
         this.bufferCapacity = 50;
         this.bufferCapacityStringValue = null;
         tempBuffer = new ArrayList(this.queue.size());
         this.queue.drainTo(tempBuffer);
         this.queue = new ArrayBlockingQueue(this.bufferCapacity, true, this.queue);
         ctDebugLogger.debug("Contextual Trace BufferCapacity = " + this.bufferCapacity);
      } else if (paramValue != null && (this.bufferCapacityStringValue == null || !paramValue.equals(this.bufferCapacityStringValue))) {
         this.bufferCapacity = Integer.parseInt(paramValue);
         this.bufferCapacityStringValue = paramValue;
         tempBuffer = new ArrayList(this.queue.size());
         this.queue.drainTo(tempBuffer);
         this.queue = new ArrayBlockingQueue(this.bufferCapacity, true, this.queue);
         ctDebugLogger.debug("Contextual Trace BufferCapacity = " + this.bufferCapacity);
      }

   }

   public synchronized int size() {
      return this.queue.size();
   }

   synchronized void dump(Thread thread, StringBuilder builder, String triggerMessage, StackTraceElement[] triggerStackTrace, int triggerStackTraceOffset) {
      ArrayList tempBuffer = new ArrayList(this.queue.size());
      this.queue.drainTo(tempBuffer);
      builder.append('\n');
      builder.append("---Dumping and clearing contextual trace data---");
      builder.append('\n');
      builder.append("Trigger: ").append(triggerMessage).append('\n');
      builder.append("Trigger point: ");
      builder.append(triggerStackTrace[triggerStackTraceOffset].toString());
      builder.append('\n');

      int index;
      for(index = triggerStackTraceOffset + 1; index < triggerStackTrace.length; ++index) {
         builder.append("  at ").append(triggerStackTrace[index].toString()).append('\n');
      }

      builder.append("------------------------------------------------");
      builder.append('\n');

      for(index = tempBuffer.size() - 1; index >= 0; --index) {
         Record r = (Record)tempBuffer.get(index);
         if (r.getThread() == thread) {
            r.toString(builder);
            builder.append('\n');
         }
      }

      builder.append("------------------------------------------------");
   }
}
