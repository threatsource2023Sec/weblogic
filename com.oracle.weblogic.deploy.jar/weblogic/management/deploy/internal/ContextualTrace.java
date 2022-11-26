package weblogic.management.deploy.internal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;

public class ContextualTrace {
   private final String name;
   private final DebugLogger debugger;
   private final CircularFifoBuffer circularBuffer;
   private final char newLine = '\n';
   private final char space = ' ';
   private final String separator = "----";
   private static final int DEFAULT_BUFFER_SIZE = 50;
   private static Map registry = null;
   private static final SimpleDateFormat dateHeaderFormat = new SimpleDateFormat("MM/dd/yy a z");
   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("KK:mm:ss.SSS");
   private static final String EMPTY_TIME = "            ";

   public static synchronized ContextualTrace get(String name) {
      return get(name, 50);
   }

   private static ContextualTrace get(String name, int size) {
      ContextualTrace trace = null;
      if (registry == null) {
         registry = new HashMap();
      } else {
         trace = (ContextualTrace)registry.get(name);
      }

      if (trace == null) {
         trace = new ContextualTrace(name, size);
         registry.put(name, trace);
      }

      return trace;
   }

   private ContextualTrace(String name, int size) {
      this.name = name;
      this.debugger = DebugLogger.getDebugLogger("Debug" + name);
      this.circularBuffer = new CircularFifoBuffer(Record.class, size);
   }

   public synchronized void record(String clz, String method, Object... tokens) {
      Record r = new Record(clz, method, tokens);
      if (this.debugger.isDebugEnabled()) {
         r.writeToDebugger(this.debugger);
      } else {
         this.circularBuffer.add(r);
      }

   }

   public synchronized String flush() {
      if (this.debugger.isDebugEnabled()) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         long prevTimestamp = -1L;
         String prevDateHeader = "";
         char lastThreadNameCreated = 0;
         Map threadLegend = new HashMap();

         String threadName;
         for(Record r = (Record)this.circularBuffer.remove(); r != null; r = (Record)this.circularBuffer.remove()) {
            Date d = new Date(r.timestamp);
            String timestamp = r.timestamp == prevTimestamp ? "            " : timestampFormat.format(d);
            prevTimestamp = r.timestamp;
            threadName = dateHeaderFormat.format(d);
            if (!threadName.equals(prevDateHeader)) {
               sb.append(threadName).append('\n');
               prevDateHeader = threadName;
            }

            char threadName;
            if (threadLegend.isEmpty()) {
               threadName = 'A';
               lastThreadNameCreated = threadName;
               threadLegend.put(r.thread, threadName);
            } else if (threadLegend.containsKey(r.thread)) {
               threadName = (Character)threadLegend.get(r.thread);
            } else {
               ++lastThreadNameCreated;
               threadName = lastThreadNameCreated;
               threadLegend.put(r.thread, lastThreadNameCreated);
            }

            r.writeTo(sb, true, timestamp, true, threadName + "");
            sb.append('\n');
         }

         sb.append("----").append('\n');
         StringBuilder legend = (new StringBuilder()).append("Thread Legend").append('\n');
         Iterator var13 = threadLegend.keySet().iterator();

         while(var13.hasNext()) {
            threadName = (String)var13.next();
            legend.append(threadLegend.get(threadName)).append("   ").append(threadName).append('\n');
         }

         legend.append("----").append('\n');
         return this.newHeader().append(legend).append(sb).toString();
      }
   }

   private StringBuilder newHeader() {
      return (new StringBuilder()).append('\n').append("----").append('\n').append(this.name).append('\n').append("----").append('\n');
   }

   private class Record {
      private final String thread;
      private final long timestamp;
      private final String clz;
      private final String method;
      private final Object[] tokens;

      private Record(String clz, String method, Object... tokens) {
         this.thread = Thread.currentThread().toString();
         this.timestamp = System.currentTimeMillis();
         this.clz = clz;
         this.method = method;
         this.tokens = tokens;
      }

      private void writeTo(StringBuilder sb, boolean includeTimestamp, String timestamp, boolean includeThread, String thread) {
         if (includeTimestamp) {
            this.writeTokensTo(sb, timestamp);
         }

         if (includeThread) {
            this.writeTokensTo(sb, thread);
         }

         this.writeTokensTo(sb, this.clz, this.method);
         this.writeTokensTo(sb, this.tokens);
      }

      public void writeToDebugger(DebugLogger debugger) {
         StringBuilder sb = new StringBuilder();
         this.writeTo(sb, false, (String)null, false, (String)null);
         debugger.debug(sb.toString());
      }

      private void writeTokensTo(StringBuilder sb, Object... tokens) {
         Object[] var3 = tokens;
         int var4 = tokens.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object token = var3[var5];
            sb.append(token).append(' ');
         }

      }

      // $FF: synthetic method
      Record(String x1, String x2, Object[] x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
