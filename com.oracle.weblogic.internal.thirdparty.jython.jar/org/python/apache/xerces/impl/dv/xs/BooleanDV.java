package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;

public class BooleanDV extends TypeValidator {
   public short getAllowedFacets() {
      return 24;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!"false".equals(var1) && !"0".equals(var1)) {
         if (!"true".equals(var1) && !"1".equals(var1)) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var1, "boolean"});
         } else {
            return Boolean.TRUE;
         }
      } else {
         return Boolean.FALSE;
      }
   }
}
