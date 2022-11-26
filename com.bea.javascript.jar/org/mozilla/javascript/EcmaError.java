package org.mozilla.javascript;

public class EcmaError extends RuntimeException {
   private NativeError errorObject;
   private String sourceName;
   private int lineNumber;
   private int columnNumber;
   private String lineSource;

   public EcmaError(NativeError var1, String var2, int var3, int var4, String var5) {
      super("EcmaError");
      this.errorObject = var1;
      this.sourceName = var2;
      this.lineNumber = var3;
      this.columnNumber = var4;
      this.lineSource = var5;
   }

   public int getColumnNumber() {
      return this.columnNumber;
   }

   public Scriptable getErrorObject() {
      return this.errorObject;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public String getLineSource() {
      return this.lineSource;
   }

   public String getMessage() {
      return this.errorObject.getMessage();
   }

   public String getName() {
      return this.errorObject.getName();
   }

   public String getSourceName() {
      return this.sourceName;
   }

   public String toString() {
      return this.sourceName != null && this.lineNumber > 0 ? this.errorObject.toString() + " (" + this.sourceName + "; line " + this.lineNumber + ")" : this.errorObject.toString();
   }
}
