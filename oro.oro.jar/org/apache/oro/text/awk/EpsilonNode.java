package org.apache.oro.text.awk;

import java.util.BitSet;

final class EpsilonNode extends SyntaxNode {
   BitSet _positionSet = new BitSet(1);

   boolean _nullable() {
      return true;
   }

   BitSet _firstPosition() {
      return this._positionSet;
   }

   BitSet _lastPosition() {
      return this._positionSet;
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
   }

   SyntaxNode _clone(int[] var1) {
      return new EpsilonNode();
   }
}
