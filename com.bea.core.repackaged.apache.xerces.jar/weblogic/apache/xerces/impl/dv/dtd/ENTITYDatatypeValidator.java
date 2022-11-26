package weblogic.apache.xerces.impl.dv.dtd;

import weblogic.apache.xerces.impl.dv.DatatypeValidator;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;

public class ENTITYDatatypeValidator implements DatatypeValidator {
   public void validate(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!var2.isEntityUnparsed(var1)) {
         throw new InvalidDatatypeValueException("ENTITYNotUnparsed", new Object[]{var1});
      }
   }
}
