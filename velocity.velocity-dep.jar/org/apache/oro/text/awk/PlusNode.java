package org.apache.oro.text.awk;

final class PlusNode extends StarNode {
   PlusNode(SyntaxNode var1) {
      super(var1);
   }

   SyntaxNode _clone(int[] var1) {
      return new PlusNode(super._left._clone(var1));
   }

   boolean _nullable() {
      return false;
   }
}
