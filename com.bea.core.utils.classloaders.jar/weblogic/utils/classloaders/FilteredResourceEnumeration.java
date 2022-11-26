package weblogic.utils.classloaders;

import java.util.Enumeration;
import java.util.List;

public final class FilteredResourceEnumeration extends ResourceEnumeration {
   private final Enumeration enumeration;

   public FilteredResourceEnumeration(Enumeration e) {
      this.enumeration = e;
   }

   protected List getEnumerations() {
      this.addEnumeration(this.enumeration);
      return super.getEnumerations();
   }
}
