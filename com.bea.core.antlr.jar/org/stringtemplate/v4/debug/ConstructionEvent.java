package org.stringtemplate.v4.debug;

public class ConstructionEvent {
   public Throwable stack = new Throwable();

   public String getFileName() {
      return this.getSTEntryPoint().getFileName();
   }

   public int getLine() {
      return this.getSTEntryPoint().getLineNumber();
   }

   public StackTraceElement getSTEntryPoint() {
      StackTraceElement[] trace = this.stack.getStackTrace();
      StackTraceElement[] var2 = trace;
      int var3 = trace.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement e = var2[var4];
         String name = e.toString();
         if (!name.startsWith("org.stringtemplate.v4")) {
            return e;
         }
      }

      return trace[0];
   }
}
