package antlr;

import antlr.collections.impl.BitSet;

public class MismatchedCharException extends RecognitionException {
   public static final int CHAR = 1;
   public static final int NOT_CHAR = 2;
   public static final int RANGE = 3;
   public static final int NOT_RANGE = 4;
   public static final int SET = 5;
   public static final int NOT_SET = 6;
   public int mismatchType;
   public int foundChar;
   public int expecting;
   public int upper;
   public BitSet set;
   public CharScanner scanner;

   public MismatchedCharException() {
      super("Mismatched char");
   }

   public MismatchedCharException(char var1, char var2, char var3, boolean var4, CharScanner var5) {
      super("Mismatched char", var5.getFilename(), var5.getLine(), var5.getColumn());
      this.mismatchType = var4 ? 4 : 3;
      this.foundChar = var1;
      this.expecting = var2;
      this.upper = var3;
      this.scanner = var5;
   }

   public MismatchedCharException(char var1, char var2, boolean var3, CharScanner var4) {
      super("Mismatched char", var4.getFilename(), var4.getLine(), var4.getColumn());
      this.mismatchType = var3 ? 2 : 1;
      this.foundChar = var1;
      this.expecting = var2;
      this.scanner = var4;
   }

   public MismatchedCharException(char var1, BitSet var2, boolean var3, CharScanner var4) {
      super("Mismatched char", var4.getFilename(), var4.getLine(), var4.getColumn());
      this.mismatchType = var3 ? 6 : 5;
      this.foundChar = var1;
      this.set = var2;
      this.scanner = var4;
   }

   public String getMessage() {
      StringBuffer var1 = new StringBuffer();
      switch (this.mismatchType) {
         case 1:
            var1.append("expecting ");
            this.appendCharName(var1, this.expecting);
            var1.append(", found ");
            this.appendCharName(var1, this.foundChar);
            break;
         case 2:
            var1.append("expecting anything but '");
            this.appendCharName(var1, this.expecting);
            var1.append("'; got it anyway");
            break;
         case 3:
         case 4:
            var1.append("expecting token ");
            if (this.mismatchType == 4) {
               var1.append("NOT ");
            }

            var1.append("in range: ");
            this.appendCharName(var1, this.expecting);
            var1.append("..");
            this.appendCharName(var1, this.upper);
            var1.append(", found ");
            this.appendCharName(var1, this.foundChar);
            break;
         case 5:
         case 6:
            var1.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
            int[] var2 = this.set.toArray();

            for(int var3 = 0; var3 < var2.length; ++var3) {
               this.appendCharName(var1, var2[var3]);
            }

            var1.append("), found ");
            this.appendCharName(var1, this.foundChar);
            break;
         default:
            var1.append(super.getMessage());
      }

      return var1.toString();
   }

   private void appendCharName(StringBuffer var1, int var2) {
      switch (var2) {
         case 9:
            var1.append("'\\t'");
            break;
         case 10:
            var1.append("'\\n'");
            break;
         case 13:
            var1.append("'\\r'");
            break;
         case 65535:
            var1.append("'<EOF>'");
            break;
         default:
            var1.append('\'');
            var1.append((char)var2);
            var1.append('\'');
      }

   }
}
