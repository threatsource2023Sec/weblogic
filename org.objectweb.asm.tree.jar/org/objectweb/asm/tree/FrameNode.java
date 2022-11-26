package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.MethodVisitor;

public class FrameNode extends AbstractInsnNode {
   public int type;
   public List local;
   public List stack;

   private FrameNode() {
      super(-1);
   }

   public FrameNode(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      super(-1);
      this.type = type;
      switch (type) {
         case -1:
         case 0:
            this.local = Util.asArrayList(numLocal, local);
            this.stack = Util.asArrayList(numStack, stack);
            break;
         case 1:
            this.local = Util.asArrayList(numLocal, local);
            break;
         case 2:
            this.local = Util.asArrayList(numLocal);
         case 3:
            break;
         case 4:
            this.stack = Util.asArrayList(1, stack);
            break;
         default:
            throw new IllegalArgumentException();
      }

   }

   public int getType() {
      return 14;
   }

   public void accept(MethodVisitor methodVisitor) {
      switch (this.type) {
         case -1:
         case 0:
            methodVisitor.visitFrame(this.type, this.local.size(), asArray(this.local), this.stack.size(), asArray(this.stack));
            break;
         case 1:
            methodVisitor.visitFrame(this.type, this.local.size(), asArray(this.local), 0, (Object[])null);
            break;
         case 2:
            methodVisitor.visitFrame(this.type, this.local.size(), (Object[])null, 0, (Object[])null);
            break;
         case 3:
            methodVisitor.visitFrame(this.type, 0, (Object[])null, 0, (Object[])null);
            break;
         case 4:
            methodVisitor.visitFrame(this.type, 0, (Object[])null, 1, asArray(this.stack));
            break;
         default:
            throw new IllegalArgumentException();
      }

   }

   public AbstractInsnNode clone(Map clonedLabels) {
      FrameNode clone = new FrameNode();
      clone.type = this.type;
      int i;
      int n;
      Object stackElement;
      if (this.local != null) {
         clone.local = new ArrayList();
         i = 0;

         for(n = this.local.size(); i < n; ++i) {
            stackElement = this.local.get(i);
            if (stackElement instanceof LabelNode) {
               stackElement = clonedLabels.get(stackElement);
            }

            clone.local.add(stackElement);
         }
      }

      if (this.stack != null) {
         clone.stack = new ArrayList();
         i = 0;

         for(n = this.stack.size(); i < n; ++i) {
            stackElement = this.stack.get(i);
            if (stackElement instanceof LabelNode) {
               stackElement = clonedLabels.get(stackElement);
            }

            clone.stack.add(stackElement);
         }
      }

      return clone;
   }

   private static Object[] asArray(List list) {
      Object[] array = new Object[list.size()];
      int i = 0;

      for(int n = array.length; i < n; ++i) {
         Object o = list.get(i);
         if (o instanceof LabelNode) {
            o = ((LabelNode)o).getLabel();
         }

         array[i] = o;
      }

      return array;
   }
}
