package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.cp.CPInfo;
import weblogic.utils.classfile.cp.ConstantPool;

public class ConstPoolOp extends Op {
   ConstantPool constant_pool;
   CPInfo cp_entry;

   public ConstPoolOp(int op_code, ConstantPool constant_pool) {
      super(op_code);
      this.constant_pool = constant_pool;
   }

   public ConstPoolOp(int op_code, ConstantPool constant_pool, CPInfo cp_entry) {
      this(op_code, constant_pool);
      this.cp_entry = cp_entry;
   }

   public void read(DataInput in) throws IOException {
      this.cp_entry = this.constant_pool.cpInfoAt(in.readUnsignedShort());
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      out.writeShort(this.cp_entry.getIndex());
   }

   public String toString() {
      return super.toString() + " (" + this.cp_entry + ")";
   }
}
