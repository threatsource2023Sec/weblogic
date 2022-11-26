package weblogic.apache.xerces.impl.dv.dtd;

import java.util.StringTokenizer;
import weblogic.apache.xerces.impl.dv.DatatypeValidator;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;

public class ListDatatypeValidator implements DatatypeValidator {
   final DatatypeValidator fItemValidator;

   public ListDatatypeValidator(DatatypeValidator var1) {
      this.fItemValidator = var1;
   }

   public void validate(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      StringTokenizer var3 = new StringTokenizer(var1, " ");
      int var4 = var3.countTokens();
      if (var4 == 0) {
         throw new InvalidDatatypeValueException("EmptyList", (Object[])null);
      } else {
         while(var3.hasMoreTokens()) {
            this.fItemValidator.validate(var3.nextToken(), var2);
         }

      }
   }
}
