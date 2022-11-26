package weblogic.diagnostics.instrumentation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstrumentationStatistics {
   private static final long NANOS_PER_MILLI = 1000000L;
   private int inspectedClassesCount;
   private int modifiedClassesCount;
   private long minWeavingTime;
   private long maxWeavingTime;
   private long totalWeavingTime;
   private int executionJoinpointCount;
   private int callJoinpointCount;
   private int catchJoinpointCount;
   private int classweaveAbortCount;
   private Map methodInvocationStatistics = new ConcurrentHashMap();
   private Map methodMemoryAllocationStatistics = new ConcurrentHashMap();

   public int getInspectedClassesCount() {
      return this.inspectedClassesCount;
   }

   public int getModifiedClassesCount() {
      return this.modifiedClassesCount;
   }

   public long getMinWeavingTime() {
      return this.minWeavingTime;
   }

   public long getMaxWeavingTime() {
      return this.maxWeavingTime;
   }

   public long getTotalWeavingTime() {
      return this.totalWeavingTime;
   }

   public synchronized void incrementWeavingTime(long weavingTime, boolean modified) {
      if (this.minWeavingTime == 0L || weavingTime < this.minWeavingTime) {
         this.minWeavingTime = weavingTime;
      }

      if (weavingTime > this.maxWeavingTime) {
         this.maxWeavingTime = weavingTime;
      }

      if (modified) {
         ++this.modifiedClassesCount;
      }

      this.totalWeavingTime += weavingTime;
      ++this.inspectedClassesCount;
   }

   public int getExecutionJoinpointCount() {
      return this.executionJoinpointCount;
   }

   public synchronized void incrementExecutionJoinpointCount(int count) {
      this.executionJoinpointCount += count;
   }

   public int getCallJoinpointCount() {
      return this.callJoinpointCount;
   }

   public synchronized void incrementCallJoinpointCount(int count) {
      this.callJoinpointCount += count;
   }

   public int getCatchJoinpointCount() {
      return this.catchJoinpointCount;
   }

   public synchronized void incrementCatchJoinpointCount(int count) {
      this.catchJoinpointCount += count;
   }

   public int getClassweaveAbortCount() {
      return this.classweaveAbortCount;
   }

   public synchronized void incrementClassweaveAbortCount(int incr) {
      this.classweaveAbortCount += incr;
   }

   public synchronized void add(InstrumentationStatistics stats) {
      this.inspectedClassesCount += stats.inspectedClassesCount;
      this.modifiedClassesCount += stats.modifiedClassesCount;
      this.totalWeavingTime += stats.totalWeavingTime;
      this.executionJoinpointCount += stats.executionJoinpointCount;
      this.callJoinpointCount += stats.callJoinpointCount;
      this.catchJoinpointCount += stats.catchJoinpointCount;
      this.classweaveAbortCount += stats.classweaveAbortCount;
      if (this.minWeavingTime == 0L || stats.minWeavingTime < this.minWeavingTime) {
         this.minWeavingTime = stats.minWeavingTime;
      }

      if (stats.maxWeavingTime > this.maxWeavingTime) {
         this.maxWeavingTime = stats.maxWeavingTime;
      }

   }

   public Map getMethodInvocationStatistics() {
      return this.methodInvocationStatistics;
   }

   public Map getMethodMemoryAllocationStatistics() {
      return this.methodMemoryAllocationStatistics;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("InstrumentationStatistics {");
      buf.append("\n  inspectedClassesCount   = ");
      buf.append(this.inspectedClassesCount);
      buf.append("\n  modifiedClassesCount    = ");
      buf.append(this.modifiedClassesCount);
      buf.append("\n  minWeavingTime          = ");
      buf.append(this.minWeavingTime / 1000000L);
      buf.append("\n  maxWeavingTime          = ");
      buf.append(this.maxWeavingTime / 1000000L);
      buf.append("\n  totalWeavingTime        = ");
      buf.append(this.totalWeavingTime / 1000000L);
      buf.append("\n  executionJoinpointCount = ");
      buf.append(this.executionJoinpointCount);
      buf.append("\n  callJoinpointCount      = ");
      buf.append(this.callJoinpointCount);
      buf.append("\n  catchJoinpointCount     = ");
      buf.append(this.catchJoinpointCount);
      buf.append("\n  classweaveAbortCount    = ");
      buf.append(this.classweaveAbortCount);
      buf.append("\n}");
      return new String(buf);
   }
}
