package org.stringtemplate.v4.misc;

public class Coordinate {
   public int line;
   public int charPosition;

   public Coordinate(int a, int b) {
      this.line = a;
      this.charPosition = b;
   }

   public String toString() {
      return this.line + ":" + this.charPosition;
   }
}
