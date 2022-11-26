package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;

public class LocalVarOp extends Op {
   int index;

   public LocalVarOp(int op_code) {
      super(op_code);
   }

   public LocalVarOp(int op_code, int index) {
      super(op_code);
      this.index = index;
   }

   public void read(DataInput in) throws IOException {
      this.index = in.readUnsignedByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeByte(this.index);
   }

   public String toString() {
      return super.toString() + " " + this.index;
   }
}
