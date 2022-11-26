package org.mozilla.javascript.tools.debugger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class VariableModel extends AbstractTreeTableModel implements TreeTableModel {
   protected static String[] cNames = new String[]{" Name", " Value"};
   protected static Class[] cTypes;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$debugger$TreeTableModel;
   // $FF: synthetic field
   static Class class$java$lang$String;

   static {
      cTypes = new Class[]{class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")), class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = class$("java.lang.String"))};
   }

   public VariableModel(Scriptable var1) {
      super(var1 == null ? null : new VariableNode(var1, "this"));
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public Object getChild(Object var1, int var2) {
      return this.getChildren(var1)[var2];
   }

   public int getChildCount(Object var1) {
      Object[] var2 = this.getChildren(var1);
      return var2 == null ? 0 : var2.length;
   }

   protected Object[] getChildren(Object var1) {
      VariableNode var2 = (VariableNode)var1;
      return var2.getChildren();
   }

   public Class getColumnClass(int var1) {
      return cTypes[var1];
   }

   public int getColumnCount() {
      return cNames.length;
   }

   public String getColumnName(int var1) {
      return cNames[var1];
   }

   protected Object getObject(Object var1) {
      VariableNode var2 = (VariableNode)var1;
      return var2 == null ? null : var2.getObject();
   }

   public Object getValueAt(Object var1, int var2) {
      Object var3 = this.getObject(var1);
      Context var4 = Context.enter();

      try {
         try {
            String var5;
            switch (var2) {
               case 0:
                  VariableNode var8 = (VariableNode)var1;
                  String var9 = "";
                  if (var8.name != null) {
                     var5 = var9 + var8.name;
                     return var5;
                  }

                  var5 = var9 + "[" + var8.index + "]";
                  return var5;
               case 1:
                  if (var3 != Undefined.instance && var3 != Scriptable.NOT_FOUND) {
                     if (var3 != null) {
                        if (var3 instanceof NativeCall) {
                           var5 = "[object Call]";
                           return var5;
                        }

                        String var10;
                        try {
                           var10 = Context.toString(var3);
                        } catch (Exception var18) {
                           var10 = var3.toString();
                        }

                        StringBuffer var11 = new StringBuffer();
                        int var12 = var10.length();

                        for(int var13 = 0; var13 < var12; ++var13) {
                           char var14 = var10.charAt(var13);
                           if (Character.isISOControl(var14)) {
                              var14 = ' ';
                           }

                           var11.append(var14);
                        }

                        var5 = var11.toString();
                        return var5;
                     }

                     var5 = "null";
                     return var5;
                  }

                  var5 = "undefined";
                  return var5;
            }
         } catch (Exception var19) {
         }

         return null;
      } finally {
         Context.exit();
      }
   }

   public boolean isCellEditable(Object var1, int var2) {
      return var2 == 0;
   }

   public boolean isLeaf(Object var1) {
      if (var1 == null) {
         return true;
      } else {
         VariableNode var2 = (VariableNode)var1;
         Object[] var3 = var2.getChildren();
         return var3 == null || var3.length <= 0;
      }
   }

   public void setScope(Scriptable var1) {
      VariableNode var2 = (VariableNode)super.root;
      var2.scope = var1;
      this.fireTreeNodesChanged(this, new Object[]{super.root}, (int[])null, new Object[]{super.root});
   }
}
