package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Op;

public class BranchOp extends Op implements Resolvable {
   public Op target;
   public int offset;

   public BranchOp(int op_code) {
      super(op_code);
   }

   public BranchOp(int op_code, int offset) {
      super(op_code);
      this.offset = offset;
   }

   public BranchOp(int op_code, Label target) {
      super(op_code);
      this.target = target;
   }

   public String toString() {
      return super.toString() + " offset = " + this.offset;
   }

   public boolean resolve(Bytecodes code) throws BadBytecodesException {
      int pc = code.pcForOp(this);
      if (this.target == null) {
         this.target = code.opAtPC(pc + this.offset);
      } else {
         this.offset = code.pcForOp(this.target) - pc;
      }

      if (this.op_code == 200 && !this.wideOffset()) {
         this.op_code = 167;
         return true;
      } else if (this.op_code == 201 && !this.wideOffset()) {
         this.op_code = 168;
         return true;
      } else {
         return false;
      }
   }

   private boolean wideOffset() {
      return this.offset > 32767 || this.offset < -32768;
   }

   public void read(DataInput in) throws IOException {
      if (this.op_code != 200 && this.op_code != 201) {
         this.offset = in.readShort();
      } else {
         this.offset = in.readInt();
      }

   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
      if (this.op_code != 200 && this.op_code != 201) {
         out.writeShort(this.offset);
      } else {
         out.writeInt(this.offset);
      }

   }
}
