package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;

public class InsnNode extends AbstractInsnNode {
   public InsnNode(int opcode) {
      super(opcode);
   }

   public int getType() {
      return 0;
   }

   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitInsn(this.opcode);
      this.acceptAnnotations(methodVisitor);
   }

   public AbstractInsnNode clone(Map clonedLabels) {
      return (new InsnNode(this.opcode)).cloneAnnotations(this);
   }
}
