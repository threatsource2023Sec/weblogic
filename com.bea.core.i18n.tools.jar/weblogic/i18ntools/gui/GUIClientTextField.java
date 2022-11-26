package weblogic.i18ntools.gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class GUIClientTextField extends JTextField implements FocusListener {
   public GUIClientTextField(String str, int cols) {
      super(str, cols);
      this.addFocusListener(this);
   }

   public void focusGained(FocusEvent ev) {
      this.selectAll();
   }

   public void focusLost(FocusEvent ev) {
      this.setSelectionStart(0);
      this.setSelectionEnd(0);
   }

   public boolean isFocusTraversable() {
      return this.isEditable();
   }
}
