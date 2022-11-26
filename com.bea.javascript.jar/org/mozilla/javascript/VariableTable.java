package org.mozilla.javascript;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class VariableTable {
   protected Vector itsVariables = new Vector();
   protected Hashtable itsVariableNames = new Hashtable(11);
   protected int varStart;

   public void addLocal(String var1) {
      Integer var2 = (Integer)this.itsVariableNames.get(var1);
      if (var2 == null) {
         int var3 = this.itsVariables.size();
         LocalVariable var4 = this.createLocalVariable(var1, false);
         this.itsVariables.addElement(var4);
         this.itsVariableNames.put(var1, new Integer(var3));
      }
   }

   public void addParameter(String var1) {
      Integer var2 = (Integer)this.itsVariableNames.get(var1);
      if (var2 != null) {
         LocalVariable var3 = (LocalVariable)this.itsVariables.elementAt(var2);
         if (var3.isParameter()) {
            String var4 = Context.getMessage1("msg.dup.parms", var1);
            Context.reportWarning(var4, (String)null, 0, (String)null, 0);
         } else {
            this.itsVariables.removeElementAt(var2);
         }
      }

      int var5 = this.varStart++;
      LocalVariable var6 = this.createLocalVariable(var1, true);
      this.itsVariables.insertElementAt(var6, var5);
      this.itsVariableNames.put(var1, new Integer(var5));
   }

   public LocalVariable createLocalVariable(String var1, boolean var2) {
      return new LocalVariable(var1, var2);
   }

   public void establishIndices() {
      for(int var1 = 0; var1 < this.itsVariables.size(); ++var1) {
         LocalVariable var2 = (LocalVariable)this.itsVariables.elementAt(var1);
         var2.setIndex(var1);
      }

   }

   public String[] getAllNames() {
      int var1 = this.size();
      String[] var2 = null;
      if (var1 != 0) {
         var2 = new String[var1];

         for(int var3 = 0; var3 != var1; ++var3) {
            var2[var3] = this.getName(var3);
         }
      }

      return var2;
   }

   public String getName(int var1) {
      return ((LocalVariable)this.itsVariables.elementAt(var1)).getName();
   }

   public int getOrdinal(String var1) {
      Integer var2 = (Integer)this.itsVariableNames.get(var1);
      return var2 != null ? var2 : -1;
   }

   public int getParameterCount() {
      return this.varStart;
   }

   public LocalVariable getVariable(int var1) {
      return (LocalVariable)this.itsVariables.elementAt(var1);
   }

   public LocalVariable getVariable(String var1) {
      Integer var2 = (Integer)this.itsVariableNames.get(var1);
      return var2 != null ? (LocalVariable)this.itsVariables.elementAt(var2) : null;
   }

   public void removeLocal(String var1) {
      Integer var2 = (Integer)this.itsVariableNames.get(var1);
      if (var2 != null) {
         this.itsVariables.removeElementAt(var2);
         this.itsVariableNames.remove(var1);
         Hashtable var3 = new Hashtable(11);

         Object var5;
         Integer var6;
         for(Enumeration var4 = this.itsVariableNames.keys(); var4.hasMoreElements(); var3.put(var5, var6)) {
            var5 = var4.nextElement();
            var6 = (Integer)this.itsVariableNames.get(var5);
            int var7 = var6;
            if (var7 > var2) {
               var6 = new Integer(var7 - 1);
            }
         }

         this.itsVariableNames = var3;
      }

   }

   public int size() {
      return this.itsVariables.size();
   }
}
