package org.jboss.classfilewriter.code;

public class ExceptionHandler {
   private final int start;
   private final int exceptionIndex;
   private final String exceptionType;
   private final StackFrame frame;
   private int end;
   private int handler;

   ExceptionHandler(int start, int exceptionIndex, String exceptionType, StackFrame frame) {
      this.start = start;
      this.exceptionIndex = exceptionIndex;
      this.exceptionType = exceptionType;
      this.frame = frame;
   }

   int getEnd() {
      return this.end;
   }

   void setEnd(int end) {
      this.end = end;
   }

   int getHandler() {
      return this.handler;
   }

   void setHandler(int handler) {
      this.handler = handler;
   }

   int getStart() {
      return this.start;
   }

   int getExceptionIndex() {
      return this.exceptionIndex;
   }

   StackFrame getFrame() {
      return this.frame;
   }

   public String getExceptionType() {
      return this.exceptionType;
   }
}
