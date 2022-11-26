package org.objectweb.asm.commons;

import java.util.Collections;
import java.util.Comparator;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class TryCatchBlockSorter extends MethodNode {
   public TryCatchBlockSorter(MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
      this(458752, methodVisitor, access, name, descriptor, signature, exceptions);
      if (this.getClass() != TryCatchBlockSorter.class) {
         throw new IllegalStateException();
      }
   }

   protected TryCatchBlockSorter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
      super(api, access, name, descriptor, signature, exceptions);
      this.mv = methodVisitor;
   }

   public void visitEnd() {
      Collections.sort(this.tryCatchBlocks, new Comparator() {
         public int compare(TryCatchBlockNode tryCatchBlockNode1, TryCatchBlockNode tryCatchBlockNode2) {
            return this.blockLength(tryCatchBlockNode1) - this.blockLength(tryCatchBlockNode2);
         }

         private int blockLength(TryCatchBlockNode tryCatchBlockNode) {
            int startIndex = TryCatchBlockSorter.this.instructions.indexOf(tryCatchBlockNode.start);
            int endIndex = TryCatchBlockSorter.this.instructions.indexOf(tryCatchBlockNode.end);
            return endIndex - startIndex;
         }
      });

      for(int i = 0; i < this.tryCatchBlocks.size(); ++i) {
         ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).updateIndex(i);
      }

      if (this.mv != null) {
         this.accept(this.mv);
      }

   }
}
