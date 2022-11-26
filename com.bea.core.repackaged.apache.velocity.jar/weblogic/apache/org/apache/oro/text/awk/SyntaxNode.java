package weblogic.apache.org.apache.oro.text.awk;

import java.util.BitSet;

abstract class SyntaxNode {
   abstract SyntaxNode _clone(int[] var1);

   abstract BitSet _firstPosition();

   abstract void _followPosition(BitSet[] var1, SyntaxNode[] var2);

   abstract BitSet _lastPosition();

   abstract boolean _nullable();
}
