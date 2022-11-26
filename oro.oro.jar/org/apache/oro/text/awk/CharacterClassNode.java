package org.apache.oro.text.awk;

import java.util.BitSet;

class CharacterClassNode extends LeafNode {
   BitSet _characterSet = new BitSet(257);

   CharacterClassNode(int var1) {
      super(var1);
   }

   void _addToken(int var1) {
      this._characterSet.set(var1);
   }

   void _addTokenRange(int var1, int var2) {
      while(var1 <= var2) {
         this._characterSet.set(var1++);
      }

   }

   boolean _matches(char var1) {
      return this._characterSet.get(var1);
   }

   SyntaxNode _clone(int[] var1) {
      int var10005 = var1[0];
      int var10002 = var1[0];
      var1[0] = var10005 + 1;
      CharacterClassNode var2 = new CharacterClassNode(var10002);
      var2._characterSet = (BitSet)this._characterSet.clone();
      return var2;
   }
}
