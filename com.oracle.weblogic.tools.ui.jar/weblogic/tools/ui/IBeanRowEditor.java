package weblogic.tools.ui;

import javax.swing.JComponent;

public interface IBeanRowEditor {
   void setEditingBean(Object var1);

   Object createNewBean();

   IValidationFeedback getFeedback();

   void uiToModel();

   JComponent getComponent();

   boolean isAutoCommit();

   void setAutoCommit(boolean var1);

   JComponent getFirstFocusComponent();
}
