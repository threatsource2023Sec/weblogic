package com.bea.wls.redef;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;

public class LocalVariableTable {
   private List localVariables = new ArrayList();

   public LocalVariableTable(serp.bytecode.LocalVariableTable table) {
      serp.bytecode.LocalVariable[] var2 = table.getLocalVariables();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         serp.bytecode.LocalVariable lv = var2[var4];
         this.localVariables.add(new LocalVariable(lv));
      }

   }

   public boolean updateScope(int start, int previousEnd, int newEnd) {
      boolean updates = false;

      LocalVariable localVariable;
      for(Iterator var5 = this.localVariables.iterator(); var5.hasNext(); updates |= localVariable.updateScope(start, previousEnd, newEnd)) {
         localVariable = (LocalVariable)var5.next();
      }

      return updates;
   }

   public boolean insert(BCMethod method) {
      Code code = method.getCode(false);
      if (code == null) {
         return false;
      } else if (this.localVariables.isEmpty()) {
         return false;
      } else {
         code.removeLocalVariableTables();
         serp.bytecode.LocalVariableTable table = code.getLocalVariableTable(true);
         Iterator var4 = this.localVariables.iterator();

         while(var4.hasNext()) {
            LocalVariable lv = (LocalVariable)var4.next();
            lv.insert(table);
         }

         return true;
      }
   }
}
