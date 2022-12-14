package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;
import weblogic.apache.xerces.util.XMLChar;

public class EntityDV extends TypeValidator {
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
      if (!var2.isEntityUnparsed((String)var1)) {
         throw new InvalidDatatypeValueException("UndeclaredEntity", new Object[]{var1});
      }
   }
}
