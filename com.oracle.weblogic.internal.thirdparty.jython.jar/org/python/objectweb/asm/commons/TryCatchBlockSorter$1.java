package org.python.objectweb.asm.commons;

import java.util.Comparator;
import org.python.objectweb.asm.tree.TryCatchBlockNode;

class TryCatchBlockSorter$1 implements Comparator {
   // $FF: synthetic field
   final TryCatchBlockSorter this$0;

   TryCatchBlockSorter$1(TryCatchBlockSorter var1) {
      this.this$0 = var1;
   }

   public int compare(TryCatchBlockNode var1, TryCatchBlockNode var2) {
      int var3 = this.blockLength(var1);
      int var4 = this.blockLength(var2);
      return var3 - var4;
   }

   private int blockLength(TryCatchBlockNode var1) {
      int var2 = this.this$0.instructions.indexOf(var1.start);
      int var3 = this.this$0.instructions.indexOf(var1.end);
      return var3 - var2;
   }
}
