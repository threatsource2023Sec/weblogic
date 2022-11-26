package org.python.apache.xerces.impl.dv.dtd;

import org.python.apache.xerces.impl.dv.DatatypeValidator;
import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;
import org.python.apache.xerces.util.XMLChar;

public class NMTOKENDatatypeValidator implements DatatypeValidator {
   public void validate(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!XMLChar.isValidNmtoken(var1)) {
         throw new InvalidDatatypeValueException("NMTOKENInvalid", new Object[]{var1});
      }
   }
}
