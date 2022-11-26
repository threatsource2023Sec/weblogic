package org.mozilla.javascript.optimizer;

import java.io.PrintWriter;
import org.mozilla.javascript.LocalVariable;
import org.mozilla.javascript.VariableTable;

public class OptVariableTable extends VariableTable {
   public void assignParameterJRegs() {
      short var1 = 4;

      for(int var2 = 0; var2 < super.varStart; ++var2) {
         OptLocalVariable var3 = (OptLocalVariable)super.itsVariables.elementAt(var2);
         var3.assignJRegister(var1);
         var1 = (short)(var1 + 3);
      }

   }

   public LocalVariable createLocalVariable(String var1, boolean var2) {
      return new OptLocalVariable(var1, var2);
   }

   public void print(PrintWriter var1) {
      System.out.println("Variable Table, size = " + super.itsVariables.size());

      for(int var2 = 0; var2 < super.itsVariables.size(); ++var2) {
         LocalVariable var3 = (LocalVariable)super.itsVariables.elementAt(var2);
         var1.println(var3.toString());
      }

   }
}
