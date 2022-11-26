package com.bea.core.repackaged.jdt.internal.compiler.parser;

public class NLSTag {
   public int start;
   public int end;
   public int lineNumber;
   public int index;

   public NLSTag(int start, int end, int lineNumber, int index) {
      this.start = start;
      this.end = end;
      this.lineNumber = lineNumber;
      this.index = index;
   }

   public String toString() {
      return "NLSTag(" + this.start + "," + this.end + "," + this.lineNumber + ")";
   }
}
