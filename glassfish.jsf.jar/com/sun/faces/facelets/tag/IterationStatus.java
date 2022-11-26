package com.sun.faces.facelets.tag;

import java.io.Serializable;

public class IterationStatus implements Serializable {
   private static final long serialVersionUID = 1L;
   private final int index;
   private final boolean first;
   private final boolean last;
   private final Integer begin;
   private final Integer end;
   private final Integer step;
   private final boolean even;
   private final Object current;
   private final int iterationCount;

   public IterationStatus(boolean first, boolean last, int index, Integer begin, Integer end, Integer step) {
      this(first, last, index, begin, end, step, (Object)null, 0);
   }

   public IterationStatus(boolean first, boolean last, int index, Integer begin, Integer end, Integer step, Object current, int iterationCount) {
      this.index = index;
      this.begin = begin;
      this.end = end;
      this.step = step;
      this.first = first;
      this.last = last;
      this.current = current;
      int iBegin = begin != null ? begin : 0;
      int iStep = step != null ? step : 1;
      this.even = (index - iBegin) / iStep % 2 == 0;
      this.iterationCount = iterationCount;
   }

   public boolean isFirst() {
      return this.first;
   }

   public boolean isLast() {
      return this.last;
   }

   public Integer getBegin() {
      return this.begin;
   }

   public Integer getEnd() {
      return this.end;
   }

   public int getIndex() {
      return this.index;
   }

   public Integer getStep() {
      return this.step;
   }

   public Object getCurrent() {
      return this.current;
   }

   public int getCount() {
      return this.iterationCount;
   }

   public boolean isEven() {
      return this.even;
   }

   public boolean isOdd() {
      return !this.even;
   }

   public String toString() {
      return "IterationStatus{index=" + this.index + ", first=" + this.first + ", last=" + this.last + ", begin=" + this.begin + ", end=" + this.end + ", step=" + this.step + ", even=" + this.even + ", current=" + this.current + ", iterationCount=" + this.iterationCount + '}';
   }
}
