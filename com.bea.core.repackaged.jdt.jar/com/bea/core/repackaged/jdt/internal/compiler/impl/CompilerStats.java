package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class CompilerStats implements Comparable {
   public long startTime;
   public long endTime;
   public long lineCount;
   public long parseTime;
   public long resolveTime;
   public long analyzeTime;
   public long generateTime;

   public long elapsedTime() {
      return this.endTime - this.startTime;
   }

   public int compareTo(Object o) {
      CompilerStats otherStats = (CompilerStats)o;
      long time1 = this.elapsedTime();
      long time2 = otherStats.elapsedTime();
      return time1 < time2 ? -1 : (time1 == time2 ? 0 : 1);
   }
}
