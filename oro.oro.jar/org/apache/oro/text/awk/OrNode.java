package org.apache.oro.text.awk;

import java.util.BitSet;

class OrNode extends SyntaxNode {
   SyntaxNode _left;
   SyntaxNode _right;

   OrNode(SyntaxNode var1, SyntaxNode var2) {
      this._left = var1;
      this._right = var2;
   }

   boolean _nullable() {
      return this._left._nullable() || this._right._nullable();
   }

   BitSet _firstPosition() {
      BitSet var1 = this._left._firstPosition();
      BitSet var2 = this._right._firstPosition();
      BitSet var3 = new BitSet(Math.max(var1.size(), var2.size()));
      var3.or(var2);
      var3.or(var1);
      return var3;
   }

   BitSet _lastPosition() {
      BitSet var1 = this._left._lastPosition();
      BitSet var2 = this._right._lastPosition();
      BitSet var3 = new BitSet(Math.max(var1.size(), var2.size()));
      var3.or(var2);
      var3.or(var1);
      return var3;
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
      this._left._followPosition(var1, var2);
      this._right._followPosition(var1, var2);
   }

   SyntaxNode _clone(int[] var1) {
      return new OrNode(this._left._clone(var1), this._right._clone(var1));
   }
}
