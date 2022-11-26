package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

class CompilerState {
   Context cx;
   Scriptable scope;
   char[] source;
   int indexBegin;
   int index;
   int flags;
   int parenCount;
   int progLength;
   byte[] prog;

   CompilerState(String var1, int var2, Context var3, Scriptable var4) {
      this.source = var1.toCharArray();
      this.scope = var4;
      this.flags = var2;
      this.cx = var3;
   }
}
