package weblogic.tools.ui;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TableLayoutConstraints implements TableLayoutConstants {
   public int col1;
   public int row1;
   public int col2;
   public int row2;
   public int hAlign;
   public int vAlign;

   public TableLayoutConstraints() {
      this.col1 = this.row1 = this.col2 = this.col2 = 0;
      this.hAlign = this.vAlign = 2;
   }

   public TableLayoutConstraints(String constraints) {
      StringTokenizer st = new StringTokenizer(constraints, ", ");
      this.col1 = 0;
      this.row1 = 0;
      this.col2 = 0;
      this.row2 = 0;
      this.hAlign = 2;
      this.vAlign = 2;
      String token = null;

      try {
         token = st.nextToken();
         this.col1 = Integer.parseInt(token);
         this.col2 = this.col1;
         token = st.nextToken();
         this.row1 = Integer.parseInt(token);
         this.row2 = this.row1;
         token = st.nextToken();
         this.col2 = Integer.parseInt(token);
         token = st.nextToken();
         this.row2 = Integer.parseInt(token);
      } catch (NoSuchElementException var7) {
      } catch (NumberFormatException var8) {
         try {
            if (token.equalsIgnoreCase("L")) {
               this.hAlign = 0;
            } else if (token.equalsIgnoreCase("C")) {
               this.hAlign = 1;
            } else if (token.equalsIgnoreCase("F")) {
               this.hAlign = 2;
            } else if (token.equalsIgnoreCase("R")) {
               this.hAlign = 3;
            }

            token = st.nextToken();
            if (token.equalsIgnoreCase("T")) {
               this.vAlign = 0;
            } else if (token.equalsIgnoreCase("C")) {
               this.vAlign = 1;
            } else if (token.equalsIgnoreCase("F")) {
               this.vAlign = 2;
            } else if (token.equalsIgnoreCase("B")) {
               this.vAlign = 3;
            }
         } catch (NoSuchElementException var6) {
         }
      }

      if (this.row2 < this.row1) {
         this.row2 = this.row1;
      }

      if (this.col2 < this.col1) {
         this.col2 = this.col1;
      }

   }

   public TableLayoutConstraints(int col1, int row1, int col2, int row2, int hAlign, int vAlign) {
      this.col1 = col1;
      this.row1 = row1;
      this.col2 = col2;
      this.row2 = row2;
      if (hAlign >= 0 && hAlign <= 3) {
         this.hAlign = hAlign;
      } else {
         this.hAlign = 2;
      }

      if (vAlign >= 0 && vAlign <= 3) {
         this.vAlign = vAlign;
      } else {
         this.vAlign = 2;
      }

   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.row1);
      buffer.append(", ");
      buffer.append(this.col1);
      buffer.append(", ");
      if (this.row1 == this.row2 && this.col1 == this.col2) {
         char[] h = new char[]{'L', 'C', 'F', 'R'};
         char[] v = new char[]{'T', 'C', 'F', 'B'};
         buffer.append(h[this.hAlign]);
         buffer.append(", ");
         buffer.append(v[this.vAlign]);
      } else {
         buffer.append(this.row2);
         buffer.append(", ");
         buffer.append(this.col2);
      }

      return buffer.toString();
   }
}
