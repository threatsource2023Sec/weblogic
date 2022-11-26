package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public abstract class JumpInstruction extends Instruction implements InstructionPtr {
   private InstructionPtrStrategy _target = new InstructionPtrStrategy(this);

   JumpInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public Instruction getTarget() {
      return this._target.getTargetInstruction();
   }

   public JumpInstruction setTarget(Instruction instruction) {
      this._target.setTargetInstruction(instruction);
      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         Instruction target = ((JumpInstruction)other).getTarget();
         return target == null || this.getTarget() == null || target == this.getTarget();
      }
   }

   public void updateTargets() {
      this._target.updateTargets();
   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      this._target.replaceTarget(oldTarget, newTarget);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterJumpInstruction(this);
      visit.exitJumpInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      this._target.setByteIndex(((JumpInstruction)orig)._target.getByteIndex());
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      switch (this.getOpcode()) {
         case 200:
         case 201:
            this._target.setByteIndex(this.getByteIndex() + in.readInt());
            break;
         default:
            this._target.setByteIndex(this.getByteIndex() + in.readShort());
      }

   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      switch (this.getOpcode()) {
         case 200:
         case 201:
            out.writeInt(this._target.getByteIndex() - this.getByteIndex());
            break;
         default:
            out.writeShort(this._target.getByteIndex() - this.getByteIndex());
      }

   }

   public void setOffset(int offset) {
      this._target.setByteIndex(this.getByteIndex() + offset);
   }

   public int getOffset() {
      return this._target.getByteIndex() - this.getByteIndex();
   }
}
