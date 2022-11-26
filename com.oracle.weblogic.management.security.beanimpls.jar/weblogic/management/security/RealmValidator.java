package weblogic.management.security;

public class RealmValidator {
   public static void validateMaxWebLogicPrincipalsInCache(Integer value) throws IllegalArgumentException {
      if (value <= 0) {
         throw new IllegalArgumentException("Illegal value for MaxWebLogicPrincipalsInCache: " + value);
      }
   }
}
