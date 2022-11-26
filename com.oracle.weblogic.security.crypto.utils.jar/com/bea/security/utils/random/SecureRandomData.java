package com.bea.security.utils.random;

public final class SecureRandomData extends AbstractRandomData {
   private static SecureRandomData theSRD = new SecureRandomData();
   private static final String PROVIDER = null;
   private static final String ALGORITHM = null;
   private static final int INITIAL_SEED_SIZE = 80;
   private static final int INCREMENTAL_SEED_SIZE = 4;
   private static final int SEEDING_INTERVAL_MILLIS = 60000;

   private SecureRandomData() {
      super(PROVIDER, ALGORITHM, 80, 4, 60000);
   }

   public static SecureRandomData getInstance() {
      return theSRD;
   }
}
