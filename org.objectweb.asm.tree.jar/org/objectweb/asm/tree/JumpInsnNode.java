package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;

public class JumpInsnNode extends AbstractInsnNode {
   public LabelNode label;

   public JumpInsnNode(int opcode, LabelNode label) {
      super(opcode);
      this.label = label;
   }

   public void setOpcode(int opcode) {
      this.opcode = opcode;
   }

   public int getType() {
      return 7;
   }

   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitJumpInsn(this.opcode, this.label.getLabel());
      this.acceptAnnotations(methodVisitor);
   }

   public AbstractInsnNode clone(Map clonedLabels) {
      return (new JumpInsnNode(this.opcode, clone(this.label, clonedLabels))).cloneAnnotations(this);
   }
}
