package org.python.apache.xerces.impl.dv.dtd;

import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;
import org.python.apache.xerces.util.XML11Char;

public class XML11NMTOKENDatatypeValidator extends NMTOKENDatatypeValidator {
   public void validate(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      if (!XML11Char.isXML11ValidNmtoken(var1)) {
         throw new InvalidDatatypeValueException("NMTOKENInvalid", new Object[]{var1});
      }
   }
}
