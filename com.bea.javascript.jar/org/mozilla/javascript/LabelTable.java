package org.mozilla.javascript;

public class LabelTable {
   private static final boolean DEBUGLABELS = false;
   private static final int LabelTableSize = 32;
   protected Label[] itsLabelTable;
   protected int itsLabelTableTop;

   public int acquireLabel() {
      if (this.itsLabelTable == null) {
         this.itsLabelTable = new Label[32];
         this.itsLabelTable[0] = new Label();
         this.itsLabelTableTop = 1;
         return Integer.MIN_VALUE;
      } else {
         if (this.itsLabelTableTop == this.itsLabelTable.length) {
            Label[] var1 = this.itsLabelTable;
            this.itsLabelTable = new Label[this.itsLabelTableTop * 2];
            System.arraycopy(var1, 0, this.itsLabelTable, 0, this.itsLabelTableTop);
         }

         this.itsLabelTable[this.itsLabelTableTop] = new Label();
         int var2 = this.itsLabelTableTop++;
         return var2 | Integer.MIN_VALUE;
      }
   }

   public int markLabel(int var1, int var2) {
      var1 &= Integer.MAX_VALUE;
      this.itsLabelTable[var1].setPC((short)var2);
      return var1 | Integer.MIN_VALUE;
   }
}
