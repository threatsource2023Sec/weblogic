package weblogic.tools.ui;

import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JComboBox;

public class SortedComboBox extends JComboBox implements Comparator {
   public SortedComboBox() {
   }

   public SortedComboBox(Object[] x) {
      this.sort(x);

      for(int i = 0; x != null && i < x.length; ++i) {
         if (x[i] != null) {
            super.addItem(x[i]);
         }
      }

   }

   private void sort(Object[] x) {
      Arrays.sort(x, this);
   }

   public int compare(Object a, Object b) {
      String x = "";
      if (a != null) {
         x = a.toString();
      }

      String y = "";
      if (b != null) {
         y = b.toString();
      }

      return x.compareTo(y);
   }

   public boolean equals(Object o) {
      return o == this;
   }
}
