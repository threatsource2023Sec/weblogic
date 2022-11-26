package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.ActionListener;

public interface ActionSource {
   /** @deprecated */
   MethodBinding getAction();

   /** @deprecated */
   void setAction(MethodBinding var1);

   /** @deprecated */
   MethodBinding getActionListener();

   /** @deprecated */
   void setActionListener(MethodBinding var1);

   boolean isImmediate();

   void setImmediate(boolean var1);

   void addActionListener(ActionListener var1);

   ActionListener[] getActionListeners();

   void removeActionListener(ActionListener var1);
}
