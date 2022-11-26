package com.oracle.wls.shaded.org.apache.bcel.generic;

import com.oracle.wls.shaded.org.apache.bcel.util.ByteSequence;
import java.io.IOException;

public class LDC_W extends LDC {
   LDC_W() {
   }

   public LDC_W(int index) {
      super(index);
   }

   protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
      this.setIndex(bytes.readUnsignedShort());
      super.length = 3;
   }
}
