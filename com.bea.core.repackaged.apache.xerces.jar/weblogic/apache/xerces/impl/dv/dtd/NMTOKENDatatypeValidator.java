package weblogic.apache.xerces.impl.dv.dtd;

import weblogic.apache.xerces.impl.dv.DatatypeValidator;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;
import weblogic.apache.xerces.util.XMLChar;

public class NMTOKENDatatypeValidator implements DatatypeValidator {
   public void validate(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!XMLChar.isValidNmtoken(var1)) {
         throw new InvalidDatatypeValueException("NMTOKENInvalid", new Object[]{var1});
      }
   }
}
