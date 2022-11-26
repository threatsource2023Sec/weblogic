package weblogic.messaging.kernel;

public class SequenceMode {
   public static final int NONE = 0;
   public static final int ASSIGN = 1;
   public static final int ELIMINATE_DUPLICATES = 2;
   public static final int REORDER = 4;
   public static final int UOW = 8;
   public static final int ALL = 15;

   private SequenceMode() {
   }

   public static String modeToString(int mode) {
      StringBuffer buf = new StringBuffer();
      boolean first = true;
      if ((mode & 1) != 0) {
         first = false;
         buf.append("ASSIGN");
      }

      if ((mode & 2) != 0) {
         if (first) {
            first = false;
         } else {
            buf.append(", ");
         }

         buf.append("ELIMINATE_DUPLICATES");
      }

      if ((mode & 4) != 0) {
         if (first) {
            first = false;
         } else {
            buf.append(", ");
         }

         buf.append("REORDER");
      }

      if ((mode & 8) != 0) {
         if (first) {
            first = false;
         } else {
            buf.append(", ");
         }

         buf.append("UOW");
      }

      return buf.toString();
   }
}
