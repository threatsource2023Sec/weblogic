package org.stringtemplate.v4.misc;

public class Interval {
   public int a;
   public int b;

   public Interval(int a, int b) {
      this.a = a;
      this.b = b;
   }

   public String toString() {
      return this.a + ".." + this.b;
   }
}
