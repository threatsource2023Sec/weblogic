package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class MultiANewArrayInstruction extends ClassInstruction {
   private int _dims = -1;

   MultiANewArrayInstruction(Code owner) {
      super(owner, 197);
   }

   int getLength() {
      return super.getLength() + 1;
   }

   public int getStackChange() {
      return -this._dims + 1;
   }

   public int getDimensions() {
      return this._dims;
   }

   public MultiANewArrayInstruction setDimensions(int dims) {
      this._dims = dims;
      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof MultiANewArrayInstruction)) {
         return false;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         MultiANewArrayInstruction ins = (MultiANewArrayInstruction)other;
         int dims = this.getDimensions();
         int otherDims = ins.getDimensions();
         return dims == -1 || otherDims == -1 || dims == otherDims;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMultiANewArrayInstruction(this);
      visit.exitMultiANewArrayInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      this._dims = ((MultiANewArrayInstruction)orig).getDimensions();
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this._dims = in.readUnsignedByte();
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeByte(this._dims);
   }
}
