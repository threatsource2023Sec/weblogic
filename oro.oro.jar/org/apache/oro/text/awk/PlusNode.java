package org.apache.oro.text.awk;

final class PlusNode extends StarNode {
   PlusNode(SyntaxNode var1) {
      super(var1);
   }

   boolean _nullable() {
      return false;
   }

   SyntaxNode _clone(int[] var1) {
      return new PlusNode(this._left._clone(var1));
   }
}
