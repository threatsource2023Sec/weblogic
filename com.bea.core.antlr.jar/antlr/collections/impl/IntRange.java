package antlr.collections.impl;

public class IntRange {
   int begin;
   int end;

   public IntRange(int var1, int var2) {
      this.begin = var1;
      this.end = var2;
   }

   public String toString() {
      return this.begin + ".." + this.end;
   }
}
