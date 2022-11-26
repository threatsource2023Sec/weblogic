package weblogic.tools.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.UIManager;

public class ArrowButton extends JButton {
   public static final int UP = 1;
   public static final int DOWN = 5;
   private int direction;
   private boolean allowFocusTraverse = true;
   private Dimension prefSize;

   public ArrowButton(int direct) {
      this.direction = direct == 1 ? 1 : 5;
      this.setOpaque(false);
   }

   public Dimension getPreferredSize() {
      if (this.prefSize != null) {
         return new Dimension(this.prefSize.width, this.prefSize.height);
      } else {
         Dimension d = super.getPreferredSize();
         Font f = this.getFont();
         FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
         int sq = Math.min(d.width, fm.getHeight() + 8);
         return new Dimension(sq, sq);
      }
   }

   public void setPreferredSize(Dimension d) {
      this.prefSize = d;
   }

   public void setFocusTraversable(boolean b) {
      this.allowFocusTraverse = b;
   }

   public boolean isFocusTraversable() {
      return this.allowFocusTraverse;
   }

   public void paint(Graphics g) {
      Dimension d = this.getSize();
      super.paint(g);
      int w = d.width;
      int h = d.height;
      if (this.getModel().isPressed()) {
         g.translate(1, 1);
      }

      this.paintTriangle(g, 3 * w / 8, 3 * h / 8, h / 4, this.direction, this.isEnabled());
   }

   public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
      Color oldColor = g.getColor();
      int j = 0;
      size = Math.max(size, 2);
      int mid = size / 2;
      g.translate(x, y);
      if (isEnabled) {
         g.setColor(Color.black);
      } else {
         g.setColor(UIManager.getDefaults().getColor("controlShadow"));
      }

      int i;
      switch (direction) {
         case 1:
            for(i = 0; i < size; ++i) {
               g.drawLine(mid - i, i, mid + i, i);
            }

            if (!isEnabled) {
               g.setColor(UIManager.getDefaults().getColor("controlHighlight"));
               g.drawLine(mid - i + 2, i, mid + i, i);
            }
            break;
         case 5:
            if (!isEnabled) {
               g.translate(1, 1);
               g.setColor(UIManager.getDefaults().getColor("controlHighlight"));

               for(i = size - 1; i >= 0; --i) {
                  g.drawLine(mid - i, j, mid + i, j);
                  ++j;
               }

               g.translate(-1, -1);
               g.setColor(UIManager.getDefaults().getColor("controlShadow"));
            }

            j = 0;

            for(i = size - 1; i >= 0; --i) {
               g.drawLine(mid - i, j, mid + i, j);
               ++j;
            }
      }

   }
}
