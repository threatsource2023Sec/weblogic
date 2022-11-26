package weblogic.management.configuration;

public final class JoltConnectionPoolLegalHelper {
   public static void validate(JoltConnectionPoolMBean pool) throws IllegalArgumentException {
      int min = pool.getMinimumPoolSize();
      int max = pool.getMaximumPoolSize();
      if (min > max) {
         throw new IllegalArgumentException("The Minimum Pool Size is greater then Maximum Pool Size");
      }
   }
}
