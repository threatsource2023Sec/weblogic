package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;

class AnyAtomicDV extends TypeValidator {
   public short getAllowedFacets() {
      return 0;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      return var1;
   }
}
