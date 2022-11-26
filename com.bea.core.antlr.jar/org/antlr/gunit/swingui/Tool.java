package org.antlr.gunit.swingui;

import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Tool {
   public static void main(String[] args) throws IOException {
      if (args.length == 1 && "-version".equals(args[0])) {
         System.out.println("gUnitEditor Swing GUI\nby Shaoting Cai\n");
      } else {
         showUI();
      }

   }

   private static void showUI() {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var1) {
      }

      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            WorkSpaceController control = new WorkSpaceController();
            control.show();
         }
      });
   }
}
