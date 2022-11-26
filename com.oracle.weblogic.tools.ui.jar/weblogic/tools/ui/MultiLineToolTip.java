package weblogic.tools.ui;

import javax.swing.JToolTip;

public class MultiLineToolTip extends JToolTip {
   public MultiLineToolTip() {
      this.setUI(new MultiLineToolTipUI());
   }
}
