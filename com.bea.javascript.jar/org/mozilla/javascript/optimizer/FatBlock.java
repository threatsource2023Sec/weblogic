package org.mozilla.javascript.optimizer;

import java.util.Enumeration;
import java.util.Hashtable;
import org.mozilla.javascript.Node;

public class FatBlock {
   private Hashtable itsSuccessors = new Hashtable(4);
   private Hashtable itsPredecessors = new Hashtable(4);
   private Block itsShadowOfFormerSelf;

   public FatBlock(int var1, int var2, Node[] var3) {
      this.itsShadowOfFormerSelf = new Block(var1, var2, var3);
   }

   public void addPredecessor(FatBlock var1) {
      this.itsPredecessors.put(var1, var1);
   }

   public void addSuccessor(FatBlock var1) {
      this.itsSuccessors.put(var1, var1);
   }

   Block diet() {
      this.itsShadowOfFormerSelf.setSuccessorList(this.reduceToArray(this.itsSuccessors));
      this.itsShadowOfFormerSelf.setPredecessorList(this.reduceToArray(this.itsPredecessors));
      return this.itsShadowOfFormerSelf;
   }

   public Node getEndNode() {
      return this.itsShadowOfFormerSelf.getEndNode();
   }

   public Block getSlimmerSelf() {
      return this.itsShadowOfFormerSelf;
   }

   private Block[] reduceToArray(Hashtable var1) {
      Block[] var2 = null;
      if (!var1.isEmpty()) {
         var2 = new Block[var1.size()];
         Enumeration var3 = var1.elements();

         FatBlock var5;
         for(int var4 = 0; var3.hasMoreElements(); var2[var4++] = var5.itsShadowOfFormerSelf) {
            var5 = (FatBlock)var3.nextElement();
         }
      }

      return var2;
   }
}
