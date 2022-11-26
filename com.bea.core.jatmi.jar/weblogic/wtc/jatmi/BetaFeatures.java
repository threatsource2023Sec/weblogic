package weblogic.wtc.jatmi;

public final class BetaFeatures {
   public static final int CONVERSATIONS = 0;
   public static final int INBOUND_TRANSACTIONS = 1;
   private static final int TOTAL_FEATURES = 2;
   private boolean[] useFeatures;

   public BetaFeatures() {
   }

   public BetaFeatures(boolean conversations, boolean inboundTransactions) {
      this.useFeatures = new boolean[2];
      this.useFeatures[0] = conversations;
      this.useFeatures[1] = inboundTransactions;
   }

   public boolean useBetaFeature(int feature) {
      if (this.useFeatures == null) {
         return false;
      } else {
         boolean rc;
         switch (feature) {
            case 0:
            case 1:
               rc = this.useFeatures[feature];
               break;
            default:
               rc = false;
         }

         return rc;
      }
   }
}
