package org.apache.oro.text.awk;

import java.util.BitSet;

abstract class SyntaxNode {
   abstract boolean _nullable();

   abstract BitSet _firstPosition();

   abstract BitSet _lastPosition();

   abstract void _followPosition(BitSet[] var1, SyntaxNode[] var2);

   abstract SyntaxNode _clone(int[] var1);
}
