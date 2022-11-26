package org.mozilla.javascript.optimizer;

import java.util.Vector;
import org.mozilla.javascript.ClassNameHelper;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Node;

public class OptFunctionNode extends FunctionNode {
   private String itsClassName;
   private boolean itsIsTargetOfDirectCall;
   private boolean itsContainsCalls;
   private boolean[] itsContainsCallsCount = new boolean[4];
   private boolean itsParameterNumberContext;
   private Vector itsDirectCallTargets;

   public OptFunctionNode(String var1, Node var2, Node var3, ClassNameHelper var4) {
      super(var1, var2, var3);
      super.itsVariableTable = new OptVariableTable();
      OptClassNameHelper var5 = (OptClassNameHelper)var4;
      this.itsClassName = var5.getJavaScriptClassName(var1, false);
   }

   public void addDirectCallTarget(FunctionNode var1) {
      if (this.itsDirectCallTargets == null) {
         this.itsDirectCallTargets = new Vector();
      }

      for(int var2 = 0; var2 < this.itsDirectCallTargets.size(); ++var2) {
         if ((FunctionNode)this.itsDirectCallTargets.elementAt(var2) == var1) {
            return;
         }
      }

      this.itsDirectCallTargets.addElement(var1);
   }

   public boolean containsCalls(int var1) {
      return var1 < this.itsContainsCallsCount.length && var1 >= 0 ? this.itsContainsCallsCount[var1] : this.itsContainsCalls;
   }

   public String getClassName() {
      return this.itsClassName;
   }

   public String getDirectCallParameterSignature() {
      StringBuffer var1 = new StringBuffer("(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;");
      int var2 = super.itsVariableTable.getParameterCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.append("Ljava/lang/Object;D");
      }

      var1.append("[Ljava/lang/Object;)");
      return var1.toString();
   }

   public Vector getDirectCallTargets() {
      return this.itsDirectCallTargets;
   }

   public boolean getParameterNumberContext() {
      return this.itsParameterNumberContext;
   }

   public void incrementLocalCount() {
      Integer var1 = (Integer)this.getProp(22);
      if (var1 == null) {
         this.putProp(22, new Integer(1));
      } else {
         this.putProp(22, new Integer(var1 + 1));
      }

   }

   public boolean isTargetOfDirectCall() {
      return this.itsIsTargetOfDirectCall;
   }

   public void setContainsCalls(int var1) {
      if (var1 < this.itsContainsCallsCount.length) {
         this.itsContainsCallsCount[var1] = true;
      }

      this.itsContainsCalls = true;
   }

   public void setIsTargetOfDirectCall() {
      this.itsIsTargetOfDirectCall = true;
   }

   public void setParameterNumberContext(boolean var1) {
      this.itsParameterNumberContext = var1;
   }
}
