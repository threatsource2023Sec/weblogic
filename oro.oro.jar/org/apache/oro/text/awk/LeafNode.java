package org.apache.oro.text.awk;

import java.util.BitSet;

abstract class LeafNode extends SyntaxNode {
   static final int _NUM_TOKENS = 256;
   static final int _END_MARKER_TOKEN = 256;
   protected int _position;
   protected BitSet _positionSet;

   LeafNode(int var1) {
      this._position = var1;
      this._positionSet = new BitSet(var1 + 1);
      this._positionSet.set(var1);
   }

   abstract boolean _matches(char var1);

   final boolean _nullable() {
      return false;
   }

   final BitSet _firstPosition() {
      return this._positionSet;
   }

   final BitSet _lastPosition() {
      return this._positionSet;
   }

   final void _followPosition(BitSet[] var1, SyntaxNode[] var2) {
      var2[this._position] = this;
   }
}
