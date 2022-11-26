package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

public class LocalVariableAnnotationNode extends TypeAnnotationNode {
   public List start;
   public List end;
   public List index;

   public LocalVariableAnnotationNode(int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
      this(458752, typeRef, typePath, start, end, index, descriptor);
   }

   public LocalVariableAnnotationNode(int api, int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
      super(api, typeRef, typePath, descriptor);
      this.start = Util.asArrayList((Object[])start);
      this.end = Util.asArrayList((Object[])end);
      this.index = Util.asArrayList(index);
   }

   public void accept(MethodVisitor methodVisitor, boolean visible) {
      Label[] startLabels = new Label[this.start.size()];
      Label[] endLabels = new Label[this.end.size()];
      int[] indices = new int[this.index.size()];
      int i = 0;

      for(int n = startLabels.length; i < n; ++i) {
         startLabels[i] = ((LabelNode)this.start.get(i)).getLabel();
         endLabels[i] = ((LabelNode)this.end.get(i)).getLabel();
         indices[i] = (Integer)this.index.get(i);
      }

      this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, startLabels, endLabels, indices, this.desc, visible));
   }
}
