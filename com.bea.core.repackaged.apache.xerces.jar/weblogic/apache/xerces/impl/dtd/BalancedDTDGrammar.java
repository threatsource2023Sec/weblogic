package weblogic.apache.xerces.impl.dtd;

import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.XNIException;

final class BalancedDTDGrammar extends DTDGrammar {
   private boolean fMixed;
   private int fDepth = 0;
   private short[] fOpStack = null;
   private int[][] fGroupIndexStack;
   private int[] fGroupIndexStackSizes;

   public BalancedDTDGrammar(SymbolTable var1, XMLDTDDescription var2) {
      super(var1, var2);
   }

   public final void startContentModel(String var1, Augmentations var2) throws XNIException {
      this.fDepth = 0;
      this.initializeContentModelStacks();
      super.startContentModel(var1, var2);
   }

   public final void startGroup(Augmentations var1) throws XNIException {
      ++this.fDepth;
      this.initializeContentModelStacks();
      this.fMixed = false;
   }

   public final void pcdata(Augmentations var1) throws XNIException {
      this.fMixed = true;
   }

   public final void element(String var1, Augmentations var2) throws XNIException {
      this.addToCurrentGroup(this.addUniqueLeafNode(var1));
   }

   public final void separator(short var1, Augmentations var2) throws XNIException {
      if (var1 == 0) {
         this.fOpStack[this.fDepth] = 4;
      } else if (var1 == 1) {
         this.fOpStack[this.fDepth] = 5;
      }

   }

   public final void occurrence(short var1, Augmentations var2) throws XNIException {
      if (!this.fMixed) {
         int var3 = this.fGroupIndexStackSizes[this.fDepth] - 1;
         if (var1 == 2) {
            this.fGroupIndexStack[this.fDepth][var3] = this.addContentSpecNode((short)1, this.fGroupIndexStack[this.fDepth][var3], -1);
         } else if (var1 == 3) {
            this.fGroupIndexStack[this.fDepth][var3] = this.addContentSpecNode((short)2, this.fGroupIndexStack[this.fDepth][var3], -1);
         } else if (var1 == 4) {
            this.fGroupIndexStack[this.fDepth][var3] = this.addContentSpecNode((short)3, this.fGroupIndexStack[this.fDepth][var3], -1);
         }
      }

   }

   public final void endGroup(Augmentations var1) throws XNIException {
      int var2 = this.fGroupIndexStackSizes[this.fDepth];
      int var3 = var2 > 0 ? this.addContentSpecNodes(0, var2 - 1) : this.addUniqueLeafNode((String)null);
      --this.fDepth;
      this.addToCurrentGroup(var3);
   }

   public final void endDTD(Augmentations var1) throws XNIException {
      super.endDTD(var1);
      this.fOpStack = null;
      this.fGroupIndexStack = null;
      this.fGroupIndexStackSizes = null;
   }

   protected final void addContentSpecToElement(XMLElementDecl var1) {
      int var2 = this.fGroupIndexStackSizes[0] > 0 ? this.fGroupIndexStack[0][0] : -1;
      this.setContentSpecIndex(this.fCurrentElementIndex, var2);
   }

   private int addContentSpecNodes(int var1, int var2) {
      if (var1 == var2) {
         return this.fGroupIndexStack[this.fDepth][var1];
      } else {
         int var3 = var1 + var2 >>> 1;
         return this.addContentSpecNode(this.fOpStack[this.fDepth], this.addContentSpecNodes(var1, var3), this.addContentSpecNodes(var3 + 1, var2));
      }
   }

   private void initializeContentModelStacks() {
      if (this.fOpStack == null) {
         this.fOpStack = new short[8];
         this.fGroupIndexStack = new int[8][];
         this.fGroupIndexStackSizes = new int[8];
      } else if (this.fDepth == this.fOpStack.length) {
         short[] var1 = new short[this.fDepth * 2];
         System.arraycopy(this.fOpStack, 0, var1, 0, this.fDepth);
         this.fOpStack = var1;
         int[][] var2 = new int[this.fDepth * 2][];
         System.arraycopy(this.fGroupIndexStack, 0, var2, 0, this.fDepth);
         this.fGroupIndexStack = var2;
         int[] var3 = new int[this.fDepth * 2];
         System.arraycopy(this.fGroupIndexStackSizes, 0, var3, 0, this.fDepth);
         this.fGroupIndexStackSizes = var3;
      }

      this.fOpStack[this.fDepth] = -1;
      this.fGroupIndexStackSizes[this.fDepth] = 0;
   }

   private void addToCurrentGroup(int var1) {
      int[] var2 = this.fGroupIndexStack[this.fDepth];
      int var3 = this.fGroupIndexStackSizes[this.fDepth]++;
      if (var2 == null) {
         var2 = new int[8];
         this.fGroupIndexStack[this.fDepth] = var2;
      } else if (var3 == var2.length) {
         int[] var4 = new int[var2.length * 2];
         System.arraycopy(var2, 0, var4, 0, var2.length);
         var2 = var4;
         this.fGroupIndexStack[this.fDepth] = var4;
      }

      var2[var3] = var1;
   }
}
