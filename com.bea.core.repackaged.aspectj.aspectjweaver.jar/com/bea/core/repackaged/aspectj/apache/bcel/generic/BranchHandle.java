package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public final class BranchHandle extends InstructionHandle {
   private InstructionBranch bi;

   private BranchHandle(InstructionBranch i) {
      super(i);
      this.bi = i;
   }

   static final BranchHandle getBranchHandle(InstructionBranch i) {
      return new BranchHandle(i);
   }

   public int getPosition() {
      return this.bi.positionOfThisInstruction;
   }

   void setPosition(int pos) {
      this.pos = this.bi.positionOfThisInstruction = pos;
   }

   protected int updatePosition(int offset, int max_offset) {
      int x = this.bi.updatePosition(offset, max_offset);
      this.pos = this.bi.positionOfThisInstruction;
      return x;
   }

   public void setTarget(InstructionHandle ih) {
      this.bi.setTarget(ih);
   }

   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
      this.bi.updateTarget(old_ih, new_ih);
   }

   public InstructionHandle getTarget() {
      return this.bi.getTarget();
   }

   public void setInstruction(Instruction i) {
      super.setInstruction(i);
      this.bi = (InstructionBranch)i;
   }
}
