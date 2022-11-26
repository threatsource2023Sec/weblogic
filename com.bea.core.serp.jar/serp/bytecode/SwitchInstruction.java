package serp.bytecode;

import java.io.DataInput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class SwitchInstruction extends JumpInstruction {
   private List _cases = new LinkedList();

   public SwitchInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int[] getOffsets() {
      int bi = this.getByteIndex();
      int[] offsets = new int[this._cases.size()];

      for(int i = 0; i < offsets.length; ++i) {
         offsets[i] = ((InstructionPtrStrategy)this._cases.get(i)).getByteIndex() - bi;
      }

      return offsets;
   }

   public void setOffsets(int[] offsets) {
      int bi = this.getByteIndex();
      this._cases.clear();

      for(int i = 0; i < offsets.length; ++i) {
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setByteIndex(offsets[i] + bi);
         this._cases.add(next);
      }

   }

   public int countTargets() {
      return this._cases.size();
   }

   int getLength() {
      int length = 1;

      for(int byteIndex = this.getByteIndex() + 1; byteIndex % 4 != 0; ++length) {
         ++byteIndex;
      }

      return length;
   }

   public Instruction getDefaultTarget() {
      return this.getTarget();
   }

   public int getDefaultOffset() {
      return this.getOffset();
   }

   public SwitchInstruction setDefaultOffset(int offset) {
      this.setOffset(offset);
      return this;
   }

   public SwitchInstruction setDefaultTarget(Instruction ins) {
      return (SwitchInstruction)this.setTarget(ins);
   }

   public Instruction[] getTargets() {
      Instruction[] result = new Instruction[this._cases.size()];

      for(int i = 0; i < this._cases.size(); ++i) {
         result[i] = ((InstructionPtrStrategy)this._cases.get(i)).getTargetInstruction();
      }

      return result;
   }

   public SwitchInstruction setTargets(Instruction[] targets) {
      this._cases.clear();
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            this.addTarget(targets[i]);
         }
      }

      return this;
   }

   public SwitchInstruction addTarget(Instruction target) {
      this._cases.add(new InstructionPtrStrategy(this, target));
      return this;
   }

   public int getStackChange() {
      return -1;
   }

   public void updateTargets() {
      super.updateTargets();
      Iterator itr = this._cases.iterator();

      while(itr.hasNext()) {
         ((InstructionPtrStrategy)itr.next()).updateTargets();
      }

   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      super.replaceTarget(oldTarget, newTarget);
      Iterator itr = this._cases.iterator();

      while(itr.hasNext()) {
         ((InstructionPtrStrategy)itr.next()).replaceTarget(oldTarget, newTarget);
      }

   }

   void read(Instruction orig) {
      super.read(orig);
      SwitchInstruction ins = (SwitchInstruction)orig;
      this._cases.clear();
      Iterator itr = ins._cases.iterator();

      while(itr.hasNext()) {
         InstructionPtrStrategy incoming = (InstructionPtrStrategy)itr.next();
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setByteIndex(incoming.getByteIndex());
         this._cases.add(next);
      }

   }

   void clearTargets() {
      this._cases.clear();
   }

   void readTarget(DataInput in) throws IOException {
      InstructionPtrStrategy next = new InstructionPtrStrategy(this);
      next.setByteIndex(this.getByteIndex() + in.readInt());
      this._cases.add(next);
   }

   public SwitchInstruction setCases(int[] matches, Instruction[] targets) {
      this.setMatches(matches);
      this.setTargets(targets);
      return this;
   }

   public SwitchInstruction setMatches(int[] matches) {
      this.clearMatches();

      for(int i = 0; i < matches.length; ++i) {
         this.addMatch(matches[i]);
      }

      return this;
   }

   public SwitchInstruction addCase(int match, Instruction target) {
      this.addMatch(match);
      this.addTarget(target);
      return this;
   }

   public abstract SwitchInstruction addMatch(int var1);

   public abstract int[] getMatches();

   abstract void clearMatches();

   void calculateOpcode() {
   }
}
