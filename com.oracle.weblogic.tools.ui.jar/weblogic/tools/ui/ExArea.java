package weblogic.tools.ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.StringTokenizer;
import javax.swing.JTextArea;
import javax.swing.border.Border;

class ExArea extends JTextArea {
   char[][] vals;
   int W;
   int H;
   int fh;
   static final int PAD = 10;
   boolean want2BeSeen = false;
   boolean outOfCTOR = false;
   static final int MAX_W = 600;
   static final int MAX_H = 600;

   public ExArea(String s) {
      super(s);
      this.parseStackTrace(s);
      this.setEditable(false);
      this.outOfCTOR = true;
      this.setCaretPosition(0);
   }

   public void setFont(Font f) {
      super.setFont(f);
      if (this.outOfCTOR) {
         this.parseStackTrace(this.getText());
      }

   }

   public void setBorder(Border bdr) {
      super.setBorder(bdr);
      if (this.outOfCTOR) {
         this.parseStackTrace(this.getText());
      }

   }

   private void parseStackTrace(String txt) {
      Font f = this.getFont();
      StringTokenizer tok = new StringTokenizer(txt, "\t\n\r", false);
      this.vals = new char[tok.countTokens()][];
      FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
      this.fh = fm.getHeight();
      this.W = this.H = 0;

      for(int i = 0; i < this.vals.length; ++i) {
         if (i == 0) {
            this.vals[i] = tok.nextToken().toCharArray();
         } else {
            this.vals[i] = ("  " + tok.nextToken()).toCharArray();
         }

         this.W = Math.max(this.W, fm.charsWidth(this.vals[i], 0, this.vals[i].length));
         this.H += this.fh;
      }

      this.H += 40;
      this.W += 40;
      this.W = Math.min(this.W, 600);
      this.H = Math.min(this.H, 600);
      Border bdr = this.getBorder();
      if (bdr != null) {
         Insets I = bdr.getBorderInsets(this);
         if (I != null) {
            this.H += I.top + I.bottom;
            this.W += I.left + I.right;
         }
      }
   }

   static void p(String s) {
      System.err.println("[ExArea]: " + s);
   }
}
