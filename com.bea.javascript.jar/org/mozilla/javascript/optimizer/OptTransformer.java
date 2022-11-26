package org.mozilla.javascript.optimizer;

import java.util.Hashtable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.NodeTransformer;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TokenStream;
import org.mozilla.javascript.VariableTable;

class OptTransformer extends NodeTransformer {
   private Hashtable theFnClassNameList;

   OptTransformer(Hashtable var1) {
      this.theFnClassNameList = var1;
   }

   void collectContainedFunctions(Node var1) {
      for(Node var2 = var1; var2 != null; var2 = var2.getNextSibling()) {
         if (var2.getType() == 110) {
            FunctionNode var3 = (FunctionNode)var2.getProp(5);
            if (var3.getFunctionName().length() != 0) {
               String var4 = var3.getFunctionName();
               Object var5 = this.theFnClassNameList.get(var4);
               if (var5 == var3) {
                  return;
               }

               this.theFnClassNameList.put(var4, var3);
            }

            this.addParameters(var3);
         }
      }

   }

   public IRFactory createIRFactory(TokenStream var1, Scriptable var2) {
      return new IRFactory(var1, var2);
   }

   protected VariableTable createVariableTable() {
      return new OptVariableTable();
   }

   private int detectDirectCall(Node var1, Node var2) {
      Context var3 = Context.getCurrentContext();
      int var4 = var3.getOptimizationLevel();
      Node var5 = var1.getFirstChild();
      int var6 = 0;

      for(Node var7 = var5.getNextSibling(); var7 != null; ++var6) {
         var7 = var7.getNextSibling();
      }

      if (var2.getType() == 110 && var4 > 0) {
         if (var5.getType() == 44) {
            this.markDirectCall(var2, var1, var6, var5.getString());
         } else if (var5.getType() == 39) {
            Node var8 = var5.getFirstChild().getNextSibling();
            this.markDirectCall(var2, var1, var6, var8.getString());
         }
      }

      return var6;
   }

   void markDirectCall(Node var1, Node var2, int var3, String var4) {
      OptFunctionNode var5 = (OptFunctionNode)this.theFnClassNameList.get(var4);
      if (var5 != null) {
         VariableTable var6 = var5.getVariableTable();
         if (var6.getParameterCount() > 32) {
            return;
         }

         if (var3 == var6.getParameterCount()) {
            var2.putProp(27, var5);
            ((OptFunctionNode)var1).addDirectCallTarget(var5);
            var5.setIsTargetOfDirectCall();
         }
      }

   }

   public NodeTransformer newInstance() {
      return new OptTransformer((Hashtable)this.theFnClassNameList.clone());
   }

   public Node transform(Node var1, Node var2, TokenStream var3, Scriptable var4) {
      this.collectContainedFunctions(var1.getFirstChild());
      return super.transform(var1, var2, var3, var4);
   }

   protected void visitCall(Node var1, Node var2) {
      int var3 = this.detectDirectCall(var1, var2);
      if (super.inFunction && var3 == 0) {
         ((OptFunctionNode)var2).setContainsCalls(var3);
      }

      super.visitCall(var1, var2);
   }

   protected void visitNew(Node var1, Node var2) {
      this.detectDirectCall(var1, var2);
      super.visitNew(var1, var2);
   }
}
