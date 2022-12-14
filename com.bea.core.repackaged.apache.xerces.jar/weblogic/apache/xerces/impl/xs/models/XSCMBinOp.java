package weblogic.apache.xerces.impl.xs.models;

import weblogic.apache.xerces.impl.dtd.models.CMNode;
import weblogic.apache.xerces.impl.dtd.models.CMStateSet;

public class XSCMBinOp extends CMNode {
   private CMNode fLeftChild;
   private CMNode fRightChild;

   public XSCMBinOp(int var1, CMNode var2, CMNode var3) {
      super(var1);
      if (this.type() != 101 && this.type() != 102) {
         throw new RuntimeException("ImplementationMessages.VAL_BST");
      } else {
         this.fLeftChild = var2;
         this.fRightChild = var3;
      }
   }

   final CMNode getLeft() {
      return this.fLeftChild;
   }

   final CMNode getRight() {
      return this.fRightChild;
   }

   public boolean isNullable() {
      if (this.type() == 101) {
         return this.fLeftChild.isNullable() || this.fRightChild.isNullable();
      } else if (this.type() != 102) {
         throw new RuntimeException("ImplementationMessages.VAL_BST");
      } else {
         return this.fLeftChild.isNullable() && this.fRightChild.isNullable();
      }
   }

   protected void calcFirstPos(CMStateSet var1) {
      if (this.type() == 101) {
         var1.setTo(this.fLeftChild.firstPos());
         var1.union(this.fRightChild.firstPos());
      } else {
         if (this.type() != 102) {
            throw new RuntimeException("ImplementationMessages.VAL_BST");
         }

         var1.setTo(this.fLeftChild.firstPos());
         if (this.fLeftChild.isNullable()) {
            var1.union(this.fRightChild.firstPos());
         }
      }

   }

   protected void calcLastPos(CMStateSet var1) {
      if (this.type() == 101) {
         var1.setTo(this.fLeftChild.lastPos());
         var1.union(this.fRightChild.lastPos());
      } else {
         if (this.type() != 102) {
            throw new RuntimeException("ImplementationMessages.VAL_BST");
         }

         var1.setTo(this.fRightChild.lastPos());
         if (this.fRightChild.isNullable()) {
            var1.union(this.fLeftChild.lastPos());
         }
      }

   }
}
