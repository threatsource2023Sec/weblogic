package weblogic.tools.ui;

import javax.swing.JComponent;

public abstract class BeanRowEditor extends ModelPanel implements IBeanRowEditor {
   private boolean m_isAutoCommit = true;

   public abstract void setEditingBean(Object var1);

   public abstract Object createNewBean();

   public IValidationFeedback getFeedback() {
      return null;
   }

   public JComponent getComponent() {
      return this;
   }

   public boolean isAutoCommit() {
      return this.m_isAutoCommit;
   }

   public void setAutoCommit(boolean f) {
      this.m_isAutoCommit = f;
   }
}
