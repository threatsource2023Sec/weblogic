package org.apache.oro.text.awk;

import java.util.BitSet;

class StarNode extends SyntaxNode {
   SyntaxNode _left;

   StarNode(SyntaxNode var1) {
      this._left = var1;
   }

   boolean _nullable() {
      return true;
   }

   BitSet _firstPosition() {
      return this._left._firstPosition();
   }

   BitSet _lastPosition() {
      return this._left._lastPosition();
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
      this._left._followPosition(var1, var2);
      BitSet var3 = this._lastPosition();
      BitSet var4 = this._firstPosition();
      int var5 = var3.size();

      while(0 < var5--) {
         if (var3.get(var5)) {
            var1[var5].or(var4);
         }
      }

   }

   SyntaxNode _clone(int[] var1) {
      return new StarNode(this._left._clone(var1));
   }
}
