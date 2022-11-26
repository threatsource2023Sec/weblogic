package weblogic.apache.org.apache.oro.text.awk;

import java.util.BitSet;

final class NegativeCharacterClassNode extends CharacterClassNode {
   NegativeCharacterClassNode(int var1) {
      super(var1);
      super._characterSet.set(256);
   }

   SyntaxNode _clone(int[] var1) {
      int var10005 = var1[0];
      int var10002 = var1[0];
      var1[0] = var10005 + 1;
      NegativeCharacterClassNode var2 = new NegativeCharacterClassNode(var10002);
      var2._characterSet = (BitSet)super._characterSet.clone();
      return var2;
   }

   boolean _matches(char var1) {
      return super._characterSet.get(var1) ^ true;
   }
}
