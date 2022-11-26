package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.Op;

public class TableswitchOp extends SwitchOp {
   public int low;
   public int high;
   public int[] offsets;
   public Op[] targets;

   public TableswitchOp(int op_code, int pc) {
      super(op_code, pc);
   }

   public void read(DataInput in) throws IOException {
      super.read(in);
      this.low = in.readInt();
      this.high = in.readInt();
      int numTargets = this.high - this.low + 1;
      this.offsets = new int[numTargets];
      this.targets = new Op[numTargets];
      this.length = 1 + this.padding + 12 + numTargets * 4;

      for(int i = 0; i < this.offsets.length; ++i) {
         this.offsets[i] = in.readInt();
      }

   }

   public void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeInt(this.low);
      out.writeInt(this.high);

      for(int i = 0; i < this.offsets.length; ++i) {
         out.writeInt(this.offsets[i]);
      }

   }

   public int length() {
      return 15 + (this.high - this.low + 1) * 4;
   }

   public boolean resolve(Bytecodes code) throws BadBytecodesException {
      super.resolve(code);
      int i = 0;

      for(int len = this.offsets.length; i < len; ++i) {
         if (this.targets[i] == null) {
            this.targets[i] = code.opAtPC(this.pc + this.offsets[i]);
         } else {
            this.offsets[i] = code.pcForOp(this.targets[i]) - this.pc;
         }
      }

      return false;
   }
}
