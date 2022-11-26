package com.bea.core.repackaged.aspectj.weaver.tools.cache;

public class CacheStatistics {
   private volatile int hits;
   private volatile int misses;
   private volatile int weaved;
   private volatile int generated;
   private volatile int ignored;
   private volatile int puts;
   private volatile int puts_ignored;

   public void hit() {
      ++this.hits;
   }

   public void miss() {
      ++this.misses;
   }

   public void weaved() {
      ++this.weaved;
   }

   public void generated() {
      ++this.generated;
   }

   public void ignored() {
      ++this.ignored;
   }

   public void put() {
      ++this.puts;
   }

   public void putIgnored() {
      ++this.puts_ignored;
   }

   public int getHits() {
      return this.hits;
   }

   public int getMisses() {
      return this.misses;
   }

   public int getWeaved() {
      return this.weaved;
   }

   public int getGenerated() {
      return this.generated;
   }

   public int getIgnored() {
      return this.ignored;
   }

   public int getPuts() {
      return this.puts;
   }

   public int getPutsIgnored() {
      return this.puts_ignored;
   }

   public void reset() {
      this.hits = 0;
      this.misses = 0;
      this.weaved = 0;
      this.generated = 0;
      this.ignored = 0;
      this.puts = 0;
      this.puts_ignored = 0;
   }

   public String toString() {
      return "CacheStatistics{hits=" + this.hits + ", misses=" + this.misses + ", weaved=" + this.weaved + ", generated=" + this.generated + ", ignored=" + this.ignored + ", puts=" + this.puts + ", puts_ignored=" + this.puts_ignored + '}';
   }
}
