package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;

public class MultiANewArrayInsnNode extends AbstractInsnNode {
   public String desc;
   public int dims;

   public MultiANewArrayInsnNode(String descriptor, int numDimensions) {
      super(197);
      this.desc = descriptor;
      this.dims = numDimensions;
   }

   public int getType() {
      return 13;
   }

   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitMultiANewArrayInsn(this.desc, this.dims);
      this.acceptAnnotations(methodVisitor);
   }

   public AbstractInsnNode clone(Map clonedLabels) {
      return (new MultiANewArrayInsnNode(this.desc, this.dims)).cloneAnnotations(this);
   }
}
