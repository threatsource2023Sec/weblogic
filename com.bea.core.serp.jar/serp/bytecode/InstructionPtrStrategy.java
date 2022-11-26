package serp.bytecode;

class InstructionPtrStrategy implements InstructionPtr {
   private InstructionPtr _pointer;
   private Instruction _target;
   private int _byteIndex;

   public InstructionPtrStrategy(InstructionPtr pointer) {
      this._target = null;
      this._byteIndex = -1;
      this._pointer = pointer;
   }

   public InstructionPtrStrategy(InstructionPtr pointer, Instruction target) {
      this(pointer);
      this.setTargetInstruction(target);
   }

   public void setByteIndex(int index) {
      if (index < -1) {
         throw new IllegalArgumentException(String.valueOf(index));
      } else {
         this._byteIndex = index;
         this._target = null;
      }
   }

   public void setTargetInstruction(Instruction ins) {
      if (ins.getCode() != this.getCode()) {
         throw new IllegalArgumentException("Instruction pointers and targets must be part of the same code block.");
      } else {
         this._target = ins;
         this._byteIndex = -1;
      }
   }

   public Instruction getTargetInstruction() {
      return this._target != null ? this._target : this.getCode().getInstruction(this._byteIndex);
   }

   public int getByteIndex() {
      return this._target == null ? this._byteIndex : this._target.getByteIndex();
   }

   public void updateTargets() {
      if (this._target == null) {
         this._target = this.getCode().getInstruction(this._byteIndex);
      }

   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      if (this.getTargetInstruction() == oldTarget) {
         this.setTargetInstruction(newTarget);
      }

   }

   public Code getCode() {
      return this._pointer.getCode();
   }
}
