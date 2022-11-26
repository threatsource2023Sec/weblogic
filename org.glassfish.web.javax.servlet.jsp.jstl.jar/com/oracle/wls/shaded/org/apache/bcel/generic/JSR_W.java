package com.oracle.wls.shaded.org.apache.bcel.generic;

import com.oracle.wls.shaded.org.apache.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class JSR_W extends JsrInstruction {
   JSR_W() {
   }

   public JSR_W(InstructionHandle target) {
      super((short)201, target);
      super.length = 5;
   }

   public void dump(DataOutputStream out) throws IOException {
      super.index = this.getTargetOffset();
      out.writeByte(super.opcode);
      out.writeInt(super.index);
   }

   protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
      super.index = bytes.readInt();
      super.length = 5;
   }

   public void accept(Visitor v) {
      v.visitStackProducer(this);
      v.visitBranchInstruction(this);
      v.visitJsrInstruction(this);
      v.visitJSR_W(this);
   }
}
