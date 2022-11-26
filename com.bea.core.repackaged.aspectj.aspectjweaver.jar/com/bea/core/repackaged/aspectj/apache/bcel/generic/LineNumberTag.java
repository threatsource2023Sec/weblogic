package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public class LineNumberTag extends Tag {
   private final int lineNumber;

   public LineNumberTag(int lineNumber) {
      this.lineNumber = lineNumber;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public String toString() {
      return "line " + this.lineNumber;
   }

   public boolean equals(Object other) {
      if (!(other instanceof LineNumberTag)) {
         return false;
      } else {
         return this.lineNumber == ((LineNumberTag)other).lineNumber;
      }
   }

   public int hashCode() {
      return this.lineNumber;
   }
}
