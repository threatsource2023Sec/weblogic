package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;

public class BipushOp extends Op {
   private int value;

   public BipushOp(int op_code) {
      super(op_code);
   }

   public BipushOp(int op_code, int value) {
      super(op_code);
      this.value = value;
   }

   public void read(DataInput in) throws IOException {
      this.value = in.readByte();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeByte(this.value);
   }

   public String toString() {
      return super.toString() + " " + this.value;
   }
}
