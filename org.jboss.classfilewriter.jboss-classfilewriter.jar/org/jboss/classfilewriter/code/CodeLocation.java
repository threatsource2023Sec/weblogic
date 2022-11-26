package org.jboss.classfilewriter.code;

public class CodeLocation {
   private final int location;
   private final StackFrame stackFrame;

   CodeLocation(int location, StackFrame stackFrame) {
      this.location = location;
      this.stackFrame = stackFrame;
   }

   int getLocation() {
      return this.location;
   }

   StackFrame getStackFrame() {
      return this.stackFrame;
   }
}
