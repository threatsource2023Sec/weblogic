package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class TABLESWITCH extends InstructionSelect {
   public TABLESWITCH(int[] match, InstructionHandle[] targets, InstructionHandle target) {
      super((short)170, match, targets, target);
      this.length = (short)(13 + this.matchLength * 4);
      this.fixedLength = this.length;
   }

   public void dump(DataOutputStream out) throws IOException {
      super.dump(out);
      int low = this.matchLength > 0 ? this.match[0] : 0;
      out.writeInt(low);
      int high = this.matchLength > 0 ? this.match[this.matchLength - 1] : 0;
      out.writeInt(high);

      for(int i = 0; i < this.matchLength; ++i) {
         out.writeInt(this.indices[i] = this.getTargetOffset(this.targets[i]));
      }

   }

   public TABLESWITCH(ByteSequence bytes) throws IOException {
      super((short)170, bytes);
      int low = bytes.readInt();
      int high = bytes.readInt();
      this.matchLength = high - low + 1;
      this.fixedLength = (short)(13 + this.matchLength * 4);
      this.length = (short)(this.fixedLength + this.padding);
      this.match = new int[this.matchLength];
      this.indices = new int[this.matchLength];
      this.targets = new InstructionHandle[this.matchLength];

      int i;
      for(i = low; i <= high; this.match[i - low] = i++) {
      }

      for(i = 0; i < this.matchLength; ++i) {
         this.indices[i] = bytes.readInt();
      }

   }
}
