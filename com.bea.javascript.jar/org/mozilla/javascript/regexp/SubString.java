package org.mozilla.javascript.regexp;

class SubString {
   static final SubString emptySubString = new SubString();
   char[] charArray;
   int index;
   int length;

   public SubString() {
   }

   public SubString(String var1) {
      this.index = 0;
      this.charArray = var1.toCharArray();
      this.length = var1.length();
   }

   public String toString() {
      return this.charArray == null ? "" : new String(this.charArray, this.index, this.length);
   }
}
