package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class RetInstruction extends LocalVariableInstruction {
   RetInstruction(Code owner) {
      super(owner, 169);
   }

   int getLength() {
      return super.getLength() + 1;
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof RetInstruction) ? false : super.equalsInstruction(other);
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterRetInstruction(this);
      visit.exitRetInstruction(this);
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this.setLocal(in.readUnsignedByte());
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeByte(this.getLocal());
   }
}
