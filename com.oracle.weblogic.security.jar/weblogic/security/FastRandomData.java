package weblogic.security;

public final class FastRandomData extends AbstractRandomData {
   private static FastRandomData theFRD = new FastRandomData();
   private static final String PROVIDER = null;
   private static final String ALGORITHM = null;
   private static final int INITIAL_SEED_SIZE = 2;
   private static final int INCREMENTAL_SEED_SIZE = 0;
   private static final int SEEDING_INTERVAL_MILLIS = 0;

   private FastRandomData() {
      super(PROVIDER, ALGORITHM, 2, 0, 0);
   }

   public static FastRandomData getInstance() {
      return theFRD;
   }
}
