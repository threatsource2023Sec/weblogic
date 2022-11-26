package antlr.debug.misc;

import antlr.ASTFactory;
import antlr.CommonAST;
import antlr.collections.AST;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class ASTFrame extends JFrame {
   static final int WIDTH = 200;
   static final int HEIGHT = 300;

   public ASTFrame(String var1, AST var2) {
      super(var1);
      new MyTreeSelectionListener();
      JTreeASTPanel var4 = new JTreeASTPanel(new JTreeASTModel(var2), (TreeSelectionListener)null);
      Container var5 = this.getContentPane();
      var5.add(var4, "Center");
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            Frame var2 = (Frame)var1.getSource();
            var2.setVisible(false);
            var2.dispose();
         }
      });
      this.setSize(200, 300);
   }

   public static void main(String[] var0) {
      ASTFactory var1 = new ASTFactory();
      CommonAST var2 = (CommonAST)var1.create(0, "ROOT");
      var2.addChild((CommonAST)var1.create(0, "C1"));
      var2.addChild((CommonAST)var1.create(0, "C2"));
      var2.addChild((CommonAST)var1.create(0, "C3"));
      ASTFrame var3 = new ASTFrame("AST JTree Example", var2);
      var3.setVisible(true);
   }

   class MyTreeSelectionListener implements TreeSelectionListener {
      public void valueChanged(TreeSelectionEvent var1) {
         TreePath var2 = var1.getPath();
         System.out.println("Selected: " + var2.getLastPathComponent());
         Object[] var3 = var2.getPath();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            System.out.print("->" + var3[var4]);
         }

         System.out.println();
      }
   }
}
