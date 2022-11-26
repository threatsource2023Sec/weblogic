package org.jboss.classfilewriter.code;

public class BranchEnd {
   private final int branchLocation;
   private final int offsetLocation;
   private final StackFrame stackFrame;
   private final boolean jump32Bit;

   BranchEnd(int branchLocation, StackFrame stackFrame, int offsetLocation) {
      this.branchLocation = branchLocation;
      this.offsetLocation = offsetLocation;
      this.stackFrame = stackFrame.createFull();
      this.jump32Bit = false;
   }

   public BranchEnd(int branchLocation, StackFrame stackFrame, boolean jump32Bit, int offsetLocation) {
      this.branchLocation = branchLocation;
      this.stackFrame = stackFrame.createFull();
      this.jump32Bit = jump32Bit;
      this.offsetLocation = offsetLocation;
   }

   int getBranchLocation() {
      return this.branchLocation;
   }

   StackFrame getStackFrame() {
      return this.stackFrame;
   }

   boolean isJump32Bit() {
      return this.jump32Bit;
   }

   int getOffsetLocation() {
      return this.offsetLocation;
   }
}
