package org.objectweb.asm.tree;

import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class TableSwitchInsnNode extends AbstractInsnNode {
   public int min;
   public int max;
   public LabelNode dflt;
   public List labels;

   public TableSwitchInsnNode(int min, int max, LabelNode dflt, LabelNode... labels) {
      super(170);
      this.min = min;
      this.max = max;
      this.dflt = dflt;
      this.labels = Util.asArrayList((Object[])labels);
   }

   public int getType() {
      return 11;
   }

   public void accept(MethodVisitor methodVisitor) {
      Label[] labelsArray = new Label[this.labels.size()];
      int i = 0;

      for(int n = labelsArray.length; i < n; ++i) {
         labelsArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
      }

      methodVisitor.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), labelsArray);
      this.acceptAnnotations(methodVisitor);
   }

   public AbstractInsnNode clone(Map clonedLabels) {
      return (new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, clonedLabels), clone(this.labels, clonedLabels))).cloneAnnotations(this);
   }
}
