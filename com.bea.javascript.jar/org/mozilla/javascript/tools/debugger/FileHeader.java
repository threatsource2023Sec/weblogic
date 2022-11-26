package org.mozilla.javascript.tools.debugger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

class FileHeader extends JPanel implements MouseListener {
   FileWindow fileWindow;

   FileHeader(FileWindow var1) {
      this.fileWindow = var1;
      this.addMouseListener(this);
      this.update();
   }

   public void mouseClicked(MouseEvent var1) {
      if (var1.getComponent() == this && (var1.getModifiers() & 16) != 0) {
         int var2 = var1.getX();
         int var3 = var1.getY();
         Font var4 = this.fileWindow.textArea.getFont();
         FontMetrics var5 = this.getFontMetrics(var4);
         int var6 = var5.getHeight();
         int var7 = var3 / var6;
         this.fileWindow.toggleBreakPoint(var7 + 1);
      }

   }

   public void mouseEntered(MouseEvent var1) {
   }

   public void mouseExited(MouseEvent var1) {
   }

   public void mousePressed(MouseEvent var1) {
   }

   public void mouseReleased(MouseEvent var1) {
   }

   public void paint(Graphics var1) {
      super.paint(var1);
      FileTextArea var2 = this.fileWindow.textArea;
      Font var3 = var2.getFont();
      var1.setFont(var3);
      FontMetrics var4 = this.getFontMetrics(var3);
      Rectangle var5 = var1.getClipBounds();
      var1.setColor(this.getBackground());
      var1.fillRect(var5.x, var5.y, var5.width, var5.height);
      int var6 = this.getX();
      int var7 = var4.getMaxAscent();
      int var8 = var4.getHeight();
      int var9 = var2.getLineCount() + 1;
      String var10 = Integer.toString(var9);
      if (var10.length() < 2) {
         var10 = "99";
      }

      var4.stringWidth(var10);
      int var12 = var5.y / var8;
      int var13 = (var5.y + var5.height) / var8 + 1;
      int var14 = this.getWidth();
      if (var13 > var9) {
         var13 = var9;
      }

      for(int var15 = var12; var15 < var13; ++var15) {
         int var17 = -2;

         try {
            var17 = var2.getLineStartOffset(var15);
         } catch (BadLocationException var25) {
         }

         boolean var18 = false;
         if (this.fileWindow.breakpoints.get(new Integer(var17)) != null) {
            var18 = true;
         }

         String var16 = Integer.toString(var15 + 1) + " ";
         var4.stringWidth(var16);
         int var20 = var15 * var8;
         var1.setColor(Color.blue);
         var1.drawString(var16, 0, var20 + var7);
         int var21 = var14 - var7;
         if (var18) {
            var1.setColor(new Color(128, 0, 0));
            int var22 = var20 + var7 - 9;
            var1.fillOval(var21, var22, 9, 9);
            var1.drawOval(var21, var22, 8, 8);
            var1.drawOval(var21, var22, 9, 9);
         }

         if (var17 == this.fileWindow.currentPos) {
            Polygon var26 = new Polygon();
            int var23 = var21;
            var20 += var7 - 10;
            var26.addPoint(var21, var20 + 3);
            var26.addPoint(var21 + 5, var20 + 3);

            for(var21 += 5; var21 <= var23 + 10; ++var20) {
               var26.addPoint(var21, var20);
               ++var21;
            }

            for(var21 = var23 + 9; var21 >= var23 + 5; ++var20) {
               var26.addPoint(var21, var20);
               --var21;
            }

            var26.addPoint(var23 + 5, var20 + 7);
            var26.addPoint(var23, var20 + 7);
            var1.setColor(Color.yellow);
            var1.fillPolygon(var26);
            var1.setColor(Color.black);
            var1.drawPolygon(var26);
         }
      }

   }

   void update() {
      FileTextArea var1 = this.fileWindow.textArea;
      Font var2 = var1.getFont();
      this.setFont(var2);
      FontMetrics var3 = this.getFontMetrics(var2);
      int var4 = var3.getHeight();
      int var5 = var1.getLineCount() + 1;
      String var6 = Integer.toString(var5);
      if (var6.length() < 2) {
         var6 = "99";
      }

      Dimension var7 = new Dimension();
      var7.width = var3.stringWidth(var6) + 16;
      var7.height = var5 * var4 + 100;
      this.setPreferredSize(var7);
      this.setSize(var7);
   }
}
