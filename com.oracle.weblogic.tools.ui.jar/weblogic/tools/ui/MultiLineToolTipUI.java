package weblogic.tools.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicToolTipUI;

class MultiLineToolTipUI extends BasicToolTipUI {
   private String[] strs;
   private int maxWidth = 0;

   public void paint(Graphics g, JComponent c) {
      Font font = g.getFont();
      FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
      int strH = metrics.getHeight();
      Dimension size = c.getSize();
      g.setColor(c.getBackground());
      g.fillRect(0, 0, size.width, size.height);
      g.setColor(c.getForeground());
      if (this.strs != null) {
         for(int i = 0; i < this.strs.length; ++i) {
            g.drawString(this.strs[i], 3, strH * (i + 1));
         }
      }

   }

   public Dimension getPreferredSize(JComponent c) {
      Font font = c.getFont();
      FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
      String tipText = ((JToolTip)c).getTipText();
      if (tipText == null) {
         tipText = "";
      }

      BufferedReader br = new BufferedReader(new StringReader(tipText));
      int maxWidth = 0;
      Vector v = new Vector();

      String line;
      int height;
      try {
         while((line = br.readLine()) != null) {
            height = SwingUtilities.computeStringWidth(metrics, line);
            maxWidth = maxWidth < height ? height : maxWidth;
            v.addElement(line);
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

      int lines = v.size();
      if (lines < 1) {
         this.strs = null;
         lines = 1;
      } else {
         this.strs = new String[lines];
         v.copyInto(this.strs);
      }

      height = metrics.getHeight() * lines;
      this.maxWidth = maxWidth;
      return new Dimension(maxWidth + 6, height + 6);
   }
}
