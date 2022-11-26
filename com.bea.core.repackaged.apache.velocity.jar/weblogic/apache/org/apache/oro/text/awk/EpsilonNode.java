package weblogic.apache.org.apache.oro.text.awk;

import java.util.BitSet;

final class EpsilonNode extends SyntaxNode {
   BitSet _positionSet = new BitSet(1);

   SyntaxNode _clone(int[] var1) {
      return new EpsilonNode();
   }

   BitSet _firstPosition() {
      return this._positionSet;
   }

   void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
   }

   BitSet _lastPosition() {
      return this._positionSet;
   }

   boolean _nullable() {
      return true;
   }
}
