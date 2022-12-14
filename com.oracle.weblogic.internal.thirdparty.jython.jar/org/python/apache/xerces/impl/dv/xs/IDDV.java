package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;
import org.python.apache.xerces.util.XMLChar;

public class IDDV extends TypeValidator {
   public short getAllowedFacets() {
      return 2079;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!XMLChar.isValidNCName(var1)) {
         throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var1, "NCName"});
      } else {
         return var1;
      }
   }

   public void checkExtraRules(Object var1, ValidationContext var2) throws InvalidDatatypeValueException {
      String var3 = (String)var1;
      if (var2.isIdDeclared(var3)) {
         throw new InvalidDatatypeValueException("cvc-id.2", new Object[]{var3});
      } else {
         var2.addId(var3);
      }
   }
}
