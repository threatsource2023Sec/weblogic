package org.mozilla.javascript.tools.idswitch;

public class IdValuePair {
   public final int idLength;
   public final String id;
   public final String value;
   private int lineNumber;

   public IdValuePair(String var1, String var2) {
      this.idLength = var1.length();
      this.id = var1;
      this.value = var2;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public void setLineNumber(int var1) {
      this.lineNumber = var1;
   }
}
