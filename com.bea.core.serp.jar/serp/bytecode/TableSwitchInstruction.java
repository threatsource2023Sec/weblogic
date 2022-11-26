package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import serp.bytecode.visitor.BCVisitor;

public class TableSwitchInstruction extends JumpInstruction {
   private int _low = 0;
   private int _high = 0;
   private List _cases = new LinkedList();

   TableSwitchInstruction(Code owner) {
      super(owner, 170);
   }

   public int[] getOffsets() {
      int bi = this.getByteIndex();
      int[] offsets = new int[this._cases.size()];

      for(int i = 0; i < this._cases.size(); ++i) {
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

      this.invalidateByteIndexes();
   }

   int getLength() {
      int length = 1;

      for(int byteIndex = this.getByteIndex() + 1; byteIndex % 4 != 0; ++length) {
         ++byteIndex;
      }

      length += 12;
      length += 4 * this._cases.size();
      return length;
   }

   public Instruction getDefaultTarget() {
      return this.getTarget();
   }

   public TableSwitchInstruction setDefaultTarget(Instruction ins) {
      return (TableSwitchInstruction)this.setTarget(ins);
   }

   public int getDefaultOffset() {
      return this.getOffset();
   }

   public TableSwitchInstruction setDefaultOffset(int offset) {
      this.setOffset(offset);
      return this;
   }

   public int getLow() {
      return this._low;
   }

   public TableSwitchInstruction setLow(int low) {
      this._low = low;
      return this;
   }

   public int getHigh() {
      return this._high;
   }

   public TableSwitchInstruction setHigh(int high) {
      this._high = high;
      return this;
   }

   public Instruction[] getTargets() {
      Instruction[] result = new Instruction[this._cases.size()];

      for(int i = 0; i < this._cases.size(); ++i) {
         result[i] = ((InstructionPtrStrategy)this._cases.get(i)).getTargetInstruction();
      }

      return result;
   }

   public TableSwitchInstruction setTargets(Instruction[] targets) {
      this._cases.clear();
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            this.addTarget(targets[i]);
         }
      }

      return this;
   }

   public TableSwitchInstruction addTarget(Instruction target) {
      this._cases.add(new InstructionPtrStrategy(this, target));
      this.invalidateByteIndexes();
      return this;
   }

   public int getStackChange() {
      return -1;
   }

   private Instruction findTarget(int jumpByteIndex, List inss) {
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
      visit.enterTableSwitchInstruction(this);
      visit.exitTableSwitchInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      TableSwitchInstruction ins = (TableSwitchInstruction)orig;
      this.setLow(ins.getLow());
      this.setHigh(ins.getHigh());
      Iterator itr = ins._cases.iterator();

      while(itr.hasNext()) {
         InstructionPtrStrategy incoming = (InstructionPtrStrategy)itr.next();
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setByteIndex(incoming.getByteIndex());
         this._cases.add(next);
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
      this.setLow(in.readInt());
      this.setHigh(in.readInt());
      this._cases.clear();

      for(i = 0; i < this._high - this._low + 1; ++i) {
         InstructionPtrStrategy next = new InstructionPtrStrategy(this);
         next.setByteIndex(bi + in.readInt());
         this._cases.add(next);
      }

   }

   void write(DataOutput out) throws IOException {
      int bi = this.getByteIndex();

      for(int byteIndex = bi + 1; byteIndex % 4 != 0; ++byteIndex) {
         out.writeByte(0);
      }

      out.writeInt(this.getOffset());
      out.writeInt(this.getLow());
      out.writeInt(this.getHigh());
      Iterator itr = this._cases.iterator();

      while(itr.hasNext()) {
         out.writeInt(((InstructionPtrStrategy)itr.next()).getByteIndex() - bi);
      }

   }
}
