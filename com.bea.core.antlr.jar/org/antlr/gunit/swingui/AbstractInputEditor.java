package org.antlr.gunit.swingui;

import java.awt.event.ActionListener;
import javax.swing.JComponent;
import org.antlr.gunit.swingui.model.ITestCaseInput;

public abstract class AbstractInputEditor {
   protected ITestCaseInput input;
   protected JComponent comp;

   public void setInput(ITestCaseInput input) {
      this.input = input;
   }

   public JComponent getControl() {
      return this.comp;
   }

   public abstract void addActionListener(ActionListener var1);
}
