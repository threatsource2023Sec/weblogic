package org.apache.oro.text.awk;

import java.util.BitSet;

final class CatNode extends SyntaxNode {
   SyntaxNode _left;
   SyntaxNode _right;

   boolean _nullable() {
      return this._left._nullable() && this._right._nullable();
   }

   BitSet _firstPosition() {
      if (this._left._nullable()) {
         BitSet var1 = this._left._firstPosition();
         BitSet var2 = this._right._firstPosition();
         BitSet var3 = new BitSet(Math.max(var1.size(), var2.size()));
         var3.or(var2);
         var3.or(var1);
         return var3;
      } else {
         return this._left._firstPosition();
      }
   }

   BitSet _lastPosition() {
      if (this._right._nullable()) {
         BitSet var1 = this._left._lastPosition();
         BitSet var2 = this._right._lastPosition();
         BitSet var3 = new BitSet(Math.max(var1.size(), var2.size()));
         var3.or(var2);
         var3.or(var1);
         return var3;
      } else {
         return this._right._lastPosition();
      }
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
      this._left._followPosition(var1, var2);
      this._right._followPosition(var1, var2);
      BitSet var4 = this._left._lastPosition();
      BitSet var5 = this._right._firstPosition();
      int var3 = var4.size();

      while(0 < var3--) {
         if (var4.get(var3)) {
            var1[var3].or(var5);
         }
      }

   }

   SyntaxNode _clone(int[] var1) {
      CatNode var2 = new CatNode();
      var2._left = this._left._clone(var1);
      var2._right = this._right._clone(var1);
      return var2;
   }
}
