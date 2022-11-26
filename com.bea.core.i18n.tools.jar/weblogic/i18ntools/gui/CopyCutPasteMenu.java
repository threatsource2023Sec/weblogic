package weblogic.i18ntools.gui;

import java.awt.Toolkit;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public final class CopyCutPasteMenu extends JPopupMenu {
   private JMenuItem myCopyItem = null;
   private JMenuItem myCutItem = null;
   private JMenuItem myPasteItem = null;
   private JTextComponent mySource = null;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();

   public JTextComponent getSource() {
      return this.mySource;
   }

   public void setSource(JTextComponent src) {
      this.mySource = src;
   }

   public CopyCutPasteMenu(MessageLocalizer parent) {
      this.myCopyItem = new JMenuItem(this.fmt.menuCopy());
      this.myCopyItem.addActionListener(parent);
      this.add(this.myCopyItem);
      this.myCutItem = new JMenuItem(this.fmt.menuCut());
      this.myCutItem.addActionListener(parent);
      this.add(this.myCutItem);
      this.myPasteItem = new JMenuItem(this.fmt.menuPaste());
      this.myPasteItem.addActionListener(parent);
      this.add(this.myPasteItem);
   }

   public void setEnabledItems(JTextComponent src) {
      String selectedText = src.getSelectedText();
      if (selectedText == null) {
         this.myCutItem.setEnabled(false);
         this.myCopyItem.setEnabled(false);
      } else {
         this.myCutItem.setEnabled(true);
         this.myCopyItem.setEnabled(true);
      }

      if (Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this) == null) {
         this.myPasteItem.setEnabled(false);
      } else {
         this.myPasteItem.setEnabled(true);
      }

   }
}
