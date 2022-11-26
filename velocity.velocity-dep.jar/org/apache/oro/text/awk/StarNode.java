package org.apache.oro.text.awk;

import java.util.BitSet;

class StarNode extends SyntaxNode {
   SyntaxNode _left;

   StarNode(SyntaxNode var1) {
      this._left = var1;
   }

   SyntaxNode _clone(int[] var1) {
      return new StarNode(this._left._clone(var1));
   }

   BitSet _firstPosition() {
      return this._left._firstPosition();
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
      this._left._followPosition(var1, var2);
      BitSet var3 = this._lastPosition();
      BitSet var4 = this._firstPosition();
      int var5 = var3.size();

      while(var5-- > 0) {
         if (var3.get(var5)) {
            var1[var5].or(var4);
         }
      }

   }

   BitSet _lastPosition() {
      return this._left._lastPosition();
   }

   boolean _nullable() {
      return true;
   }
}
