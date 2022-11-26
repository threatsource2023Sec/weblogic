package weblogic.connector.utils;

public class JCA15PropertyNameNormalizer implements PropertyNameNormalizer {
   public String normalize(String name) {
      return name.toLowerCase();
   }

   public boolean isJCA16() {
      return false;
   }

   public boolean match(String left, String right) {
      return this.normalize(left).equals(this.normalize(right));
   }

   public int compare(String left, String right) {
      return this.normalize(left).compareTo(this.normalize(right));
   }
}
