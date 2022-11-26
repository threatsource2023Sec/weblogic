package weblogic.tools.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public abstract class ModelTabPanel extends BeanRowEditor {
   JTabbedPane jtp = new JTabbedPane();

   public ModelTabPanel() {
      this.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = 1;
      gbc.weightx = gbc.weighty = 1.0;
      this.add(this.jtp, gbc);
   }

   public JTabbedPane getTabPane() {
      return this.jtp;
   }

   public void add(String title, JComponent comp) {
      int borderSize = 2;
      comp.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
      this.jtp.add(title, comp);
   }

   public JComponent getFirstFocusComponent() {
      JComponent ret = null;
      int ct = this.jtp.getTabCount();
      int i = false;

      for(int i = 0; ret == null && i < ct; ++i) {
         Object o = this.jtp.getComponentAt(i);
         if (o instanceof IBeanRowEditor) {
            ret = ((IBeanRowEditor)o).getFirstFocusComponent();
            this.jtp.setSelectedIndex(i);
         }
      }

      if (ret == null) {
         this.jtp.setSelectedIndex(0);
      }

      return ret;
   }

   public IValidationFeedback getFeedback() {
      IValidationFeedback ret = super.getFeedback();
      if (ret != null) {
         return ret;
      } else {
         int ct = this.jtp.getTabCount();

         for(int i = 0; i < ct; ++i) {
            Object o = this.jtp.getComponentAt(i);
            if (o instanceof IBeanRowEditor) {
               IBeanRowEditor ibre = (IBeanRowEditor)o;
               ret = ibre.getFeedback();
               if (ret != null) {
                  this.jtp.setSelectedIndex(i);
                  break;
               }
            }
         }

         return ret;
      }
   }

   public void setAutoCommit(boolean b) {
      super.setAutoCommit(b);
      int ct = this.jtp.getTabCount();

      for(int i = 0; i < ct; ++i) {
         Object o = this.jtp.getComponentAt(i);
         if (o instanceof IBeanRowEditor) {
            ((IBeanRowEditor)o).setAutoCommit(b);
         }
      }

   }

   public void modelToUI() {
      int ct = this.jtp.getTabCount();

      for(int i = 0; i < ct; ++i) {
         Object o = this.jtp.getComponentAt(i);
         if (o instanceof ModelPanel) {
            ModelPanel mp = (ModelPanel)o;
            mp.modelToUI();
         }
      }

   }

   public void uiToModel() {
      int ct = this.jtp.getTabCount();

      for(int i = 0; i < ct; ++i) {
         Object o = this.jtp.getComponentAt(i);
         if (o instanceof ModelPanel) {
            ModelPanel mp = (ModelPanel)o;
            mp.uiToModel();
         } else if (o instanceof IBeanRowEditor) {
            ((IBeanRowEditor)o).uiToModel();
         }
      }

   }

   public abstract void setEditingBean(Object var1);

   public abstract Object createNewBean();

   private static void p(String s) {
      System.err.println("[ModelTabPanel]: " + s);
   }
}
