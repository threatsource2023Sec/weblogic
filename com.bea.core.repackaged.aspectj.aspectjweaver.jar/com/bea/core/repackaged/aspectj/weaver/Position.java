package com.bea.core.repackaged.aspectj.weaver;

public class Position implements IHasPosition {
   private int start;
   private int end;

   public Position(int start, int end) {
      this.start = start;
      this.end = end;
   }

   public int getEnd() {
      return this.end;
   }

   public int getStart() {
      return this.start;
   }
}
