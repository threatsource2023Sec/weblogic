package org.apache.oro.text.awk;

import java.util.BitSet;

final class SyntaxTree {
   int _positions;
   SyntaxNode _root;
   LeafNode[] _nodes;
   BitSet[] _followSet;

   SyntaxTree(SyntaxNode var1, int var2) {
      this._root = var1;
      this._positions = var2;
   }

   void _computeFollowPositions() {
      this._followSet = new BitSet[this._positions];
      this._nodes = new LeafNode[this._positions];

      for(int var1 = this._positions; 0 < var1--; this._followSet[var1] = new BitSet(this._positions)) {
      }

      this._root._followPosition(this._followSet, this._nodes);
   }

   private void __addToFastMap(BitSet var1, boolean[] var2, boolean[] var3) {
      for(int var5 = 0; var5 < this._positions; ++var5) {
         if (var1.get(var5) && !var3[var5]) {
            var3[var5] = true;

            for(int var4 = 0; var4 < 256; ++var4) {
               if (!var2[var4]) {
                  var2[var4] = this._nodes[var5]._matches((char)var4);
               }
            }
         }
      }

   }

   boolean[] createFastMap() {
      boolean[] var1 = new boolean[256];
      boolean[] var2 = new boolean[this._positions];
      this.__addToFastMap(this._root._firstPosition(), var1, var2);
      return var1;
   }
}
