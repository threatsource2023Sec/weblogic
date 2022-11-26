package org.apache.oro.text.awk;

class TokenNode extends LeafNode {
   char _token;

   TokenNode(char var1, int var2) {
      super(var2);
      this._token = var1;
   }

   boolean _matches(char var1) {
      return this._token == var1;
   }

   SyntaxNode _clone(int[] var1) {
      int var10006 = var1[0];
      int var10003 = var1[0];
      var1[0] = var10006 + 1;
      return new TokenNode(this._token, var10003);
   }
}
