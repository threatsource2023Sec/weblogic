package weblogic.utils.classfile.ops;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.Op;

public class LookupswitchOp extends SwitchOp {
   private pair[] pairs;

   public LookupswitchOp(int op_code, int pc) {
      super(op_code, pc);
   }

   public void read(DataInput in) throws IOException {
      super.read(in);
      int npairs = in.readInt();
      this.length = 1 + this.padding + 8 + npairs * 8;
      this.pairs = new pair[npairs];

      for(int i = 0; i < npairs; ++i) {
         int match = in.readInt();
         int offset = in.readInt();
         this.pairs[i] = new pair(match, offset);
      }

   }

   public void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeInt(this.pairs.length);
      int i = 0;

      for(int len = this.pairs.length; i < len; ++i) {
         pair p = this.pairs[i];
         out.writeInt(p.match);
         out.writeInt(p.offset);
      }

   }

   public boolean resolve(Bytecodes code) throws BadBytecodesException {
      super.resolve(code);
      int i = 0;

      for(int len = this.pairs.length; i < len; ++i) {
         pair p = this.pairs[i];
         if (p.target == null) {
            p.target = code.opAtPC(this.pc + p.offset);
         } else {
            p.offset = code.pcForOp(p.target) - this.pc;
         }
      }

      return false;
   }

   class pair {
      int match;
      int offset;
      Op target;

      pair(int match, int offset) {
         this.match = match;
         this.offset = offset;
      }
   }
}
