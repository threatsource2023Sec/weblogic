package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.Op;

public class SwitchOp extends Op implements Resolvable {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   protected int padding;
   public int default_offset;
   public Op default_target;
   protected int pc;
   protected int length;

   public SwitchOp(int op_code, int pc) {
      super(op_code);
      this.pc = pc;
   }

   public void read(DataInput in) throws IOException {
      this.padding = 3 - this.pc % 4;

      for(int i = 0; i < this.padding; ++i) {
         in.readByte();
      }

      this.default_offset = in.readInt();
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);

      for(int i = 0; i < this.padding; ++i) {
         out.writeByte(0);
      }

      out.writeInt(this.default_offset);
   }

   public boolean resolve(Bytecodes code) throws BadBytecodesException {
      this.pc = code.pcForOp(this);
      this.padding = 3 - this.pc % 4;
      if (this.default_target == null) {
         this.default_target = code.opAtPC(this.pc + this.default_offset);
      } else {
         this.default_offset = code.pcForOp(this.default_target) - this.pc;
      }

      return false;
   }

   public int length() {
      return this.length;
   }
}
