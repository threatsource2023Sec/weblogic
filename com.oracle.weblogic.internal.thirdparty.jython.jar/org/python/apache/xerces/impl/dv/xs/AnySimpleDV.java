package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;

public class AnySimpleDV extends TypeValidator {
   public short getAllowedFacets() {
      return 0;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      return var1;
   }
}
