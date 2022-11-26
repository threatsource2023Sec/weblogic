package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;

public class IndexAndConstOp extends Op {
   private int index;
   private int constant;

   public IndexAndConstOp(int op_code) {
      super(op_code);
   }

   public IndexAndConstOp(int op_code, int index, int constant) {
      super(op_code);
      this.index = index;
      this.constant = constant;
   }

   public void read(DataInput in) throws IOException {
      this.index = in.readUnsignedByte();
      this.constant = in.readByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeByte(this.index);
      out.writeByte(this.constant);
   }

   public String toString() {
      return super.toString() + " index = " + this.index + " constant = " + this.constant;
   }
}
