package org.stringtemplate.v4.gui;

import java.util.Iterator;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.StringRenderer;
import org.stringtemplate.v4.debug.EvalTemplateEvent;

public class JTreeSTModel implements TreeModel {
   public Interpreter interp;
   public Wrapper root;

   public JTreeSTModel(Interpreter interp, EvalTemplateEvent root) {
      this.interp = interp;
      this.root = new Wrapper(root);
   }

   public Object getChild(Object parent, int index) {
      EvalTemplateEvent e = ((Wrapper)parent).event;
      return new Wrapper((EvalTemplateEvent)e.scope.childEvalTemplateEvents.get(index));
   }

   public int getChildCount(Object parent) {
      EvalTemplateEvent e = ((Wrapper)parent).event;
      return e.scope.childEvalTemplateEvents.size();
   }

   public int getIndexOfChild(Object parent, Object child) {
      EvalTemplateEvent p = ((Wrapper)parent).event;
      EvalTemplateEvent c = ((Wrapper)parent).event;
      int i = 0;

      for(Iterator var6 = p.scope.childEvalTemplateEvents.iterator(); var6.hasNext(); ++i) {
         EvalTemplateEvent e = (EvalTemplateEvent)var6.next();
         if (e.scope.st == c.scope.st) {
            return i;
         }
      }

      return -1;
   }

   public boolean isLeaf(Object node) {
      return this.getChildCount(node) == 0;
   }

   public Object getRoot() {
      return this.root;
   }

   public void valueForPathChanged(TreePath treePath, Object o) {
   }

   public void addTreeModelListener(TreeModelListener treeModelListener) {
   }

   public void removeTreeModelListener(TreeModelListener treeModelListener) {
   }

   public static class Wrapper {
      EvalTemplateEvent event;

      public Wrapper(EvalTemplateEvent event) {
         this.event = event;
      }

      public int hashCode() {
         return this.event.hashCode();
      }

      public boolean equals(Object o) {
         return this.event == ((Wrapper)o).event;
      }

      public String toString() {
         ST st = this.event.scope.st;
         if (st.isAnonSubtemplate()) {
            return "{...}";
         } else if (st.debugState != null && st.debugState.newSTEvent != null) {
            String label = st.toString() + " @ " + st.debugState.newSTEvent.getFileName() + ":" + st.debugState.newSTEvent.getLine();
            return "<html><b>" + StringRenderer.escapeHTML(label) + "</b></html>";
         } else {
            return st.toString();
         }
      }
   }
}
