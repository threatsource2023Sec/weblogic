package org.mozilla.javascript.tools.debugger;

import java.util.EventObject;
import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

public class AbstractCellEditor implements CellEditor {
   protected EventListenerList listenerList = new EventListenerList();
   // $FF: synthetic field
   static Class class$javax$swing$event$CellEditorListener;

   public void addCellEditorListener(CellEditorListener var1) {
      this.listenerList.add(class$javax$swing$event$CellEditorListener != null ? class$javax$swing$event$CellEditorListener : (class$javax$swing$event$CellEditorListener = class$("javax.swing.event.CellEditorListener")), var1);
   }

   public void cancelCellEditing() {
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected void fireEditingCanceled() {
      Object[] var1 = this.listenerList.getListenerList();

      for(int var2 = var1.length - 2; var2 >= 0; var2 -= 2) {
         if (var1[var2] == (class$javax$swing$event$CellEditorListener != null ? class$javax$swing$event$CellEditorListener : (class$javax$swing$event$CellEditorListener = class$("javax.swing.event.CellEditorListener")))) {
            ((CellEditorListener)var1[var2 + 1]).editingCanceled(new ChangeEvent(this));
         }
      }

   }

   protected void fireEditingStopped() {
      Object[] var1 = this.listenerList.getListenerList();

      for(int var2 = var1.length - 2; var2 >= 0; var2 -= 2) {
         if (var1[var2] == (class$javax$swing$event$CellEditorListener != null ? class$javax$swing$event$CellEditorListener : (class$javax$swing$event$CellEditorListener = class$("javax.swing.event.CellEditorListener")))) {
            ((CellEditorListener)var1[var2 + 1]).editingStopped(new ChangeEvent(this));
         }
      }

   }

   public Object getCellEditorValue() {
      return null;
   }

   public boolean isCellEditable(EventObject var1) {
      return true;
   }

   public void removeCellEditorListener(CellEditorListener var1) {
      this.listenerList.remove(class$javax$swing$event$CellEditorListener != null ? class$javax$swing$event$CellEditorListener : (class$javax$swing$event$CellEditorListener = class$("javax.swing.event.CellEditorListener")), var1);
   }

   public boolean shouldSelectCell(EventObject var1) {
      return false;
   }

   public boolean stopCellEditing() {
      return true;
   }
}
