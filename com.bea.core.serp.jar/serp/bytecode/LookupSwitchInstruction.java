package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Numbers;

public class LookupSwitchInstruction extends JumpInstruction {
   private List _matches = new LinkedList();
   private List _cases = new LinkedList();

   LookupSwitchInstruction(Code owner) {
      super(owner, 171);
   }

   int getLength() {
      int length = 1;

      for(int byteIndex = this.getByteIndex() + 1; byteIndex % 4 != 0; ++length) {
         ++byteIndex;
      }

      length += 8;
      length += 8 * this._matches.size();
      return length;
   }

   public int getStackChange() {
      return -1;
   }

   public Instruction getDefaultTarget() {
      return this.getTarget();
   }

   public LookupSwitchInstruction setDefaultTarget(Instruction ins) {
      return (LookupSwitchInstruction)this.setTarget(ins);
   }

   public int getDefaultOffset() {
      return this.getOffset();
   }

   public LookupSwitchInstruction setDefaultOffset(int offset) {
      this.setOffset(offset);
      return this;
   }

   public LookupSwitchInstruction setCases(int[] matches, Instruction[] targets) {
      this._matches.clear();
      this._cases.clear();

      int i;
      for(i = 0; i < matches.length; ++i) {
         this._matches.add(Numbers.valueOf(matches[i]));
      }

      for(i = 0; i < targets.length; ++i) {
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setTargetInstruction(targets[i]);
         this._cases.add(next);
      }

      this.invalidateByteIndexes();
      return this;
   }

   public int[] getOffsets() {
      int bi = this.getByteIndex();
      int[] offsets = new int[this._cases.size()];

      for(int i = 0; i < offsets.length; ++i) {
         offsets[i] = ((InstructionPtrStrategy)this._cases.get(i)).getByteIndex() - bi;
      }

      return offsets;
   }

   public int[] getMatches() {
      int[] matches = new int[this._matches.size()];
      Iterator itr = this._matches.iterator();

      for(int i = 0; i < matches.length; ++i) {
         matches[i] = (Integer)itr.next();
      }

      return matches;
   }

   public Instruction[] getTargets() {
      Instruction[] result = new Instruction[this._cases.size()];

      for(int i = 0; i < result.length; ++i) {
         result[i] = ((InstructionPtrStrategy)this._cases.get(i)).getTargetInstruction();
      }

      return result;
   }

   public LookupSwitchInstruction addCase(int match, Instruction target) {
      this._matches.add(Numbers.valueOf(match));
      this._cases.add(new InstructionPtrStrategy(this, target));
      this.invalidateByteIndexes();
      return this;
   }

   private Instruction findJumpPoint(int jumpByteIndex, List inss) {
      Iterator itr = inss.iterator();

      Instruction ins;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         ins = (Instruction)itr.next();
      } while(ins.getByteIndex() != jumpByteIndex);

      return ins;
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

   public void acceptVisit(BCVisitor visit) {
      visit.enterLookupSwitchInstruction(this);
      visit.exitLookupSwitchInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      LookupSwitchInstruction ins = (LookupSwitchInstruction)orig;
      this._matches = new LinkedList(ins._matches);
      this._cases.clear();
      Iterator itr = ins._cases.iterator();

      while(itr.hasNext()) {
         InstructionPtrStrategy origPtr = (InstructionPtrStrategy)itr.next();
         InstructionPtrStrategy newPtr = new InstructionPtrStrategy(this);
         newPtr.setByteIndex(origPtr.getByteIndex());
         this._cases.add(newPtr);
      }

      this.invalidateByteIndexes();
   }

   void read(DataInput in) throws IOException {
      int bi = this.getByteIndex();

      int i;
      for(i = bi + 1; i % 4 != 0; ++i) {
         in.readByte();
      }

      this.setOffset(in.readInt());
      this._matches.clear();
      this._cases.clear();
      i = 0;

      for(int pairCount = in.readInt(); i < pairCount; ++i) {
         this._matches.add(Numbers.valueOf(in.readInt()));
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setByteIndex(bi + in.readInt());
         this._cases.add(next);
      }

   }

   void write(DataOutput out) throws IOException {
      int bi = this.getByteIndex();

      int i;
      for(i = bi + 1; i % 4 != 0; ++i) {
         out.writeByte(0);
      }

      out.writeInt(this.getOffset());
      out.writeInt(this._matches.size());

      for(i = 0; i < this._matches.size(); ++i) {
         out.writeInt((Integer)this._matches.get(i));
         out.writeInt(((InstructionPtrStrategy)this._cases.get(i)).getByteIndex() - bi);
      }

   }
}
