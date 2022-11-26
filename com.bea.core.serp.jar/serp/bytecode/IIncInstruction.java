package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class IIncInstruction extends LocalVariableInstruction {
   private int _inc = 0;

   IIncInstruction(Code owner) {
      super(owner, 132);
   }

   int getLength() {
      return super.getLength() + 2;
   }

   public int getIncrement() {
      return this._inc;
   }

   public IIncInstruction setIncrement(int val) {
      this._inc = val;
      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof IIncInstruction)) {
         return false;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         IIncInstruction ins = (IIncInstruction)other;
         return this.getIncrement() == 0 || ins.getIncrement() == 0 || this.getIncrement() == ins.getIncrement();
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterIIncInstruction(this);
      visit.exitIIncInstruction(this);
   }

   void read(Instruction other) {
      super.read(other);
      this._inc = ((IIncInstruction)other).getIncrement();
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this.setLocal(in.readUnsignedByte());
      this._inc = in.readByte();
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeByte(this.getLocal());
      out.writeByte(this._inc);
   }
}
