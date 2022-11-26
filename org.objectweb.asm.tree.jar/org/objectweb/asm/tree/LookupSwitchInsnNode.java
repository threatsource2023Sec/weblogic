package org.objectweb.asm.tree;

import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class LookupSwitchInsnNode extends AbstractInsnNode {
   public LabelNode dflt;
   public List keys;
   public List labels;

   public LookupSwitchInsnNode(LabelNode dflt, int[] keys, LabelNode[] labels) {
      super(171);
      this.dflt = dflt;
      this.keys = Util.asArrayList(keys);
      this.labels = Util.asArrayList((Object[])labels);
   }

   public int getType() {
      return 12;
   }

   public void accept(MethodVisitor methodVisitor) {
      int[] keysArray = new int[this.keys.size()];
      int i = 0;

      int i;
      for(i = keysArray.length; i < i; ++i) {
         keysArray[i] = (Integer)this.keys.get(i);
      }

      Label[] labelsArray = new Label[this.labels.size()];
      i = 0;

      for(int n = labelsArray.length; i < n; ++i) {
         labelsArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
      }

      methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), keysArray, labelsArray);
      this.acceptAnnotations(methodVisitor);
   }

   public AbstractInsnNode clone(Map clonedLabels) {
      LookupSwitchInsnNode clone = new LookupSwitchInsnNode(clone(this.dflt, clonedLabels), (int[])null, clone(this.labels, clonedLabels));
      clone.keys.addAll(this.keys);
      return clone.cloneAnnotations(this);
   }
}
