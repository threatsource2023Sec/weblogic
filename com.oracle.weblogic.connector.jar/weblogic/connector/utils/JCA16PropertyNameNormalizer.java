package weblogic.connector.utils;

import java.beans.Introspector;

public class JCA16PropertyNameNormalizer implements PropertyNameNormalizer {
   public String normalize(String name) {
      return Introspector.decapitalize(name);
   }

   public boolean isJCA16() {
      return true;
   }

   public boolean match(String left, String right) {
      return Introspector.decapitalize(left).equals(Introspector.decapitalize(right));
   }

   public int compare(String left, String right) {
      return this.normalize(left).compareTo(this.normalize(right));
   }
}
