package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;

public class WideOp extends Op {
   private int inst;
   private int index;
   private int constant = -1;

   public WideOp(int op_code) {
      super(op_code);
   }

   public void read(DataInput in) throws IOException {
      this.inst = in.readUnsignedByte();
      this.index = in.readUnsignedShort();
      if (this.inst == 132) {
         this.constant = in.readShort();
      }

   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeByte(this.inst);
      out.writeShort(this.index);
      if (this.inst == 132) {
         out.writeShort(this.constant);
      }

   }

   public int length() {
      return this.inst == 132 ? 6 : 4;
   }
}
