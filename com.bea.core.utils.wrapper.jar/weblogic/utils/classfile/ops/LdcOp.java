package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.cp.CPFloat;
import weblogic.utils.classfile.cp.CPInfo;
import weblogic.utils.classfile.cp.CPInteger;
import weblogic.utils.classfile.cp.CPString;
import weblogic.utils.classfile.cp.ConstantPool;

public class LdcOp extends Op implements Resolvable {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static final int MAX_UNSIGNED_BYTE = 255;
   ConstantPool constant_pool;
   CPInfo cp_entry;

   public LdcOp(int op_code, ConstantPool constant_pool) {
      super(op_code);
      this.constant_pool = constant_pool;
   }

   public LdcOp(int op_code, ConstantPool constant_pool, CPInfo cp_entry) {
      this(op_code, constant_pool);
      this.cp_entry = cp_entry;
   }

   public boolean isString() {
      return this.cp_entry instanceof CPString;
   }

   public boolean isInteger() {
      return this.cp_entry instanceof CPInteger;
   }

   public boolean isFloat() {
      return this.cp_entry instanceof CPFloat;
   }

   public String theString() {
      try {
         return ((CPString)this.cp_entry).toString();
      } catch (ClassCastException var2) {
         throw new Error("LDC arg not a String.");
      }
   }

   public void read(DataInput in) throws IOException {
      if (this.op_code == 18) {
         this.cp_entry = this.constant_pool.cpInfoAt(in.readUnsignedByte());
      } else {
         this.cp_entry = this.constant_pool.cpInfoAt(in.readUnsignedShort());
      }

   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      if (this.op_code == 19) {
         out.writeShort(this.cp_entry.getIndex());
      } else {
         out.writeByte(this.cp_entry.getIndex());
      }

   }

   public boolean resolve(Bytecodes code) {
      return false;
   }

   public String toString() {
      return super.toString() + " " + this.cp_entry;
   }
}
