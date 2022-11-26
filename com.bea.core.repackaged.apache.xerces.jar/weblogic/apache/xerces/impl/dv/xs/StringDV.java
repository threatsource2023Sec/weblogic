package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;

public class StringDV extends TypeValidator {
   public short getAllowedFacets() {
      return 2079;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      return var1;
   }
}
