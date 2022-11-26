package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;

public class NewarrayOp extends Op {
   int atype;

   public NewarrayOp(int op_code) {
      super(op_code);
   }

   public NewarrayOp(int op_code, int atype) {
      super(op_code);
      this.atype = atype;
   }

   public void read(DataInput in) throws IOException {
      this.atype = in.readUnsignedByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeByte(this.atype);
   }

   public String toString() {
      return super.toString() + " atype = " + this.atype;
   }
}
