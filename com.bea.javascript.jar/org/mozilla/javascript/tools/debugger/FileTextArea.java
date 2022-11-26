package org.mozilla.javascript.tools.debugger;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;

class FileTextArea extends JTextArea implements ActionListener, PopupMenuListener, KeyListener, MouseListener {
   FileWindow w;
   FilePopupMenu popup;

   FileTextArea(FileWindow var1) {
      this.w = var1;
      this.popup = new FilePopupMenu(this);
      this.popup.addPopupMenuListener(this);
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.setFont(new Font("Monospaced", 0, 12));
   }

   public void actionPerformed(ActionEvent var1) {
      int var2 = this.viewToModel(new Point(this.popup.x, this.popup.y));
      this.popup.setVisible(false);
      String var3 = var1.getActionCommand();
      int var4 = -1;

      try {
         var4 = this.getLineOfOffset(var2);
      } catch (Exception var5) {
      }

      if (var3.equals("Set Breakpoint")) {
         this.w.setBreakPoint(var4 + 1);
      } else if (var3.equals("Clear Breakpoint")) {
         this.w.clearBreakPoint(var4 + 1);
      } else if (var3.equals("Run to Cursor")) {
         this.w.runToCursor(var1);
      } else if (var3.equals("Run")) {
         this.w.load();
      }

   }

   private void checkPopup(MouseEvent var1) {
      if (var1.isPopupTrigger()) {
         this.popup.show(this, var1.getX(), var1.getY());
      }

   }

   public void keyPressed(KeyEvent var1) {
      switch (var1.getKeyCode()) {
         case 8:
         case 10:
         case 127:
            var1.consume();
         default:
      }
   }

   public void keyReleased(KeyEvent var1) {
      var1.consume();
   }

   public void keyTyped(KeyEvent var1) {
      var1.consume();
   }

   public void mouseClicked(MouseEvent var1) {
      this.checkPopup(var1);
      this.requestFocus();
      this.getCaret().setVisible(true);
   }

   public void mouseEntered(MouseEvent var1) {
   }

   public void mouseExited(MouseEvent var1) {
   }

   public void mousePressed(MouseEvent var1) {
      this.checkPopup(var1);
   }

   public void mouseReleased(MouseEvent var1) {
      this.checkPopup(var1);
   }

   public void popupMenuCanceled(PopupMenuEvent var1) {
   }

   public void popupMenuWillBecomeInvisible(PopupMenuEvent var1) {
   }

   public void popupMenuWillBecomeVisible(PopupMenuEvent var1) {
   }

   void select(int var1) {
      if (var1 >= 0) {
         try {
            int var2 = this.getLineOfOffset(var1);
            Rectangle var3 = this.modelToView(var1);
            if (var3 == null) {
               this.select(var1, var1);
            } else {
               try {
                  Rectangle var4 = this.modelToView(this.getLineStartOffset(var2 + 1));
                  if (var4 != null) {
                     var3 = var4;
                  }
               } catch (Exception var6) {
               }

               JViewport var8 = (JViewport)this.getParent();
               Rectangle var5 = var8.getViewRect();
               if (var5.y + var5.height > var3.y) {
                  this.select(var1, var1);
               } else {
                  var3.y += (var5.height - var3.height) / 2;
                  this.scrollRectToVisible(var3);
                  this.select(var1, var1);
               }
            }
         } catch (BadLocationException var7) {
            this.select(var1, var1);
         }
      }

   }
}
