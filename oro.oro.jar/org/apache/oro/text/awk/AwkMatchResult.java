package org.apache.oro.text.awk;

import org.apache.oro.text.regex.MatchResult;

final class AwkMatchResult implements MatchResult {
   private int __matchBeginOffset;
   private int __length;
   private String __match;

   AwkMatchResult(String var1, int var2) {
      this.__match = var1;
      this.__length = var1.length();
      this.__matchBeginOffset = var2;
   }

   void _incrementMatchBeginOffset(int var1) {
      this.__matchBeginOffset += var1;
   }

   public int length() {
      return this.__length;
   }

   public int groups() {
      return 1;
   }

   public String group(int var1) {
      return var1 == 0 ? this.__match : null;
   }

   public int begin(int var1) {
      return var1 == 0 ? 0 : -1;
   }

   public int end(int var1) {
      return var1 == 0 ? this.__length : -1;
   }

   public int beginOffset(int var1) {
      return var1 == 0 ? this.__matchBeginOffset : -1;
   }

   public int endOffset(int var1) {
      return var1 == 0 ? this.__matchBeginOffset + this.__length : -1;
   }

   public String toString() {
      return this.group(0);
   }
}
