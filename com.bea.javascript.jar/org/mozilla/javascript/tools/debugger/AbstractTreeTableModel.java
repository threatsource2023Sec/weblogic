package org.mozilla.javascript.tools.debugger;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public abstract class AbstractTreeTableModel implements TreeTableModel {
   protected Object root;
   protected EventListenerList listenerList = new EventListenerList();
   // $FF: synthetic field
   static Class class$javax$swing$event$TreeModelListener;
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$debugger$TreeTableModel;

   public AbstractTreeTableModel(Object var1) {
      this.root = var1;
   }

   public void addTreeModelListener(TreeModelListener var1) {
      this.listenerList.add(class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")), var1);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected void fireTreeNodesChanged(Object var1, Object[] var2, int[] var3, Object[] var4) {
      Object[] var5 = this.listenerList.getListenerList();
      TreeModelEvent var6 = null;

      for(int var7 = var5.length - 2; var7 >= 0; var7 -= 2) {
         if (var5[var7] == (class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")))) {
            if (var6 == null) {
               var6 = new TreeModelEvent(var1, var2, var3, var4);
            }

            ((TreeModelListener)var5[var7 + 1]).treeNodesChanged(var6);
         }
      }

   }

   protected void fireTreeNodesInserted(Object var1, Object[] var2, int[] var3, Object[] var4) {
      Object[] var5 = this.listenerList.getListenerList();
      TreeModelEvent var6 = null;

      for(int var7 = var5.length - 2; var7 >= 0; var7 -= 2) {
         if (var5[var7] == (class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")))) {
            if (var6 == null) {
               var6 = new TreeModelEvent(var1, var2, var3, var4);
            }

            ((TreeModelListener)var5[var7 + 1]).treeNodesInserted(var6);
         }
      }

   }

   protected void fireTreeNodesRemoved(Object var1, Object[] var2, int[] var3, Object[] var4) {
      Object[] var5 = this.listenerList.getListenerList();
      TreeModelEvent var6 = null;

      for(int var7 = var5.length - 2; var7 >= 0; var7 -= 2) {
         if (var5[var7] == (class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")))) {
            if (var6 == null) {
               var6 = new TreeModelEvent(var1, var2, var3, var4);
            }

            ((TreeModelListener)var5[var7 + 1]).treeNodesRemoved(var6);
         }
      }

   }

   protected void fireTreeStructureChanged(Object var1, Object[] var2, int[] var3, Object[] var4) {
      Object[] var5 = this.listenerList.getListenerList();
      TreeModelEvent var6 = null;

      for(int var7 = var5.length - 2; var7 >= 0; var7 -= 2) {
         if (var5[var7] == (class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")))) {
            if (var6 == null) {
               var6 = new TreeModelEvent(var1, var2, var3, var4);
            }

            ((TreeModelListener)var5[var7 + 1]).treeStructureChanged(var6);
         }
      }

   }

   public abstract Object getChild(Object var1, int var2);

   public abstract int getChildCount(Object var1);

   public Class getColumnClass(int var1) {
      return class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object"));
   }

   public abstract int getColumnCount();

   public abstract String getColumnName(int var1);

   public int getIndexOfChild(Object var1, Object var2) {
      for(int var3 = 0; var3 < this.getChildCount(var1); ++var3) {
         if (this.getChild(var1, var3).equals(var2)) {
            return var3;
         }
      }

      return -1;
   }

   public Object getRoot() {
      return this.root;
   }

   public abstract Object getValueAt(Object var1, int var2);

   public boolean isCellEditable(Object var1, int var2) {
      return this.getColumnClass(var2) == (class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")));
   }

   public boolean isLeaf(Object var1) {
      return this.getChildCount(var1) == 0;
   }

   public void removeTreeModelListener(TreeModelListener var1) {
      this.listenerList.remove(class$javax$swing$event$TreeModelListener != null ? class$javax$swing$event$TreeModelListener : (class$javax$swing$event$TreeModelListener = class$("javax.swing.event.TreeModelListener")), var1);
   }

   public void setValueAt(Object var1, Object var2, int var3) {
   }

   public void valueForPathChanged(TreePath var1, Object var2) {
   }
}
