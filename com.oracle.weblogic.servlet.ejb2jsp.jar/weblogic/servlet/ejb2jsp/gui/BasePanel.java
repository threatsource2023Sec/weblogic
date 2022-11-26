package weblogic.servlet.ejb2jsp.gui;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
   protected boolean dirty;

   public void setDirty(boolean b) {
      this.dirty = b;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public abstract void bean2fields() throws Exception;

   public abstract void fields2bean() throws Exception;
}
