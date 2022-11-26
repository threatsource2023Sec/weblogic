package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.ClassNameHelper;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TokenStream;

public class OptIRFactory extends IRFactory {
   private ClassNameHelper nameHelper;

   public OptIRFactory(TokenStream var1, ClassNameHelper var2, Scriptable var3) {
      super(var1, var3);
      this.nameHelper = var2;
   }

   public Object createFunctionNode(String var1, Object var2, Object var3) {
      if (var1 == null) {
         var1 = "";
      }

      OptFunctionNode var4 = new OptFunctionNode(var1, (Node)var2, (Node)var3, this.nameHelper);
      return var4;
   }
}
