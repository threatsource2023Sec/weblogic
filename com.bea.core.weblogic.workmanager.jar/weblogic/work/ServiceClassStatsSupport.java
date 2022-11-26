package weblogic.work;

public class ServiceClassStatsSupport {
   private final String name;
   long completedCount;
   private long previousCompletedCount;
   long totalThreadUse;
   long threadUseSquares;
   long threadUseNS;
   long threadUseHogNS;
   long lastThreadUseHogNS;
   private long totalCPU;
   long cpuSquares;

   public ServiceClassStatsSupport(String n) {
      this.name = n;
   }

   public final void workCompleted(long threadUse, long cpu) {
      ++this.completedCount;
      this.totalThreadUse += threadUse;
      this.totalCPU += cpu;
      this.cpuSquares += cpu * cpu;
      this.threadUseSquares += threadUse * threadUse;
   }

   public String getName() {
      return this.name;
   }

   public final long getCompleted() {
      return this.completedCount;
   }

   public final long getThreadUse() {
      return this.totalThreadUse;
   }

   public final long getThreadUseSquares() {
      return this.threadUseSquares;
   }

   public final long getCPU() {
      return this.totalCPU;
   }

   public final long getCPUSquares() {
      return this.cpuSquares;
   }

   private long getThreadAvg() {
      return div(this.totalThreadUse, this.completedCount);
   }

   private double getThreadStdev() {
      return stdev(this.totalThreadUse, this.threadUseSquares, this.completedCount);
   }

   public final long getCPUAvg() {
      return div(this.totalCPU, this.completedCount);
   }

   public final double getCPUStdev() {
      return stdev(this.totalCPU, this.cpuSquares, this.completedCount);
   }

   static long div(long a, long b) {
      return a == 0L ? 0L : (b == 0L ? Long.MAX_VALUE : (a + b / 2L) / b);
   }

   static double stdev(long sum, long squaresSum, long n) {
      double avg = (double)sum / (double)n;
      return Math.sqrt((double)squaresSum / (double)n - avg * avg);
   }

   synchronized long getAndResetThreadUseNS() {
      long retval = this.threadUseNS;
      this.threadUseNS = 0L;
      long threadUseHog = this.threadUseHogNS;
      this.lastThreadUseHogNS = threadUseHog;
      this.threadUseHogNS = 0L;
      long threadUseInMS = (retval + threadUseHog + 500000L) / 1000000L;
      this.totalThreadUse += threadUseInMS;
      return retval;
   }

   synchronized long getCompletedCountDelta() {
      long delta = this.completedCount - this.previousCompletedCount;
      this.previousCompletedCount = this.completedCount;
      return delta;
   }

   public final String toString() {
      return this.name + ": completed=" + this.completedCount + ", avg time=" + this.getThreadAvg() + "+/-" + this.getThreadStdev() + ", avg cpu=" + this.getCPUAvg() + "+/-" + this.getCPUStdev();
   }
}
