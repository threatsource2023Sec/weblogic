package org.opensaml.soap.wssecurity.messaging;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public abstract class AbstractToken implements Token {
   private Object wrappedToken;
   private Token.ValidationStatus validationStatus;

   protected AbstractToken(@Nonnull Object token) {
      this.wrappedToken = Constraint.isNotNull(token, "Wrapped token may not be null");
      this.validationStatus = Token.ValidationStatus.VALIDATION_NOT_ATTEMPTED;
   }

   @Nonnull
   public Object getWrappedToken() {
      return this.wrappedToken;
   }

   @Nonnull
   public Token.ValidationStatus getValidationStatus() {
      return this.validationStatus;
   }

   public void setValidationStatus(@Nonnull Token.ValidationStatus status) {
      this.validationStatus = (Token.ValidationStatus)Constraint.isNotNull(status, "Validation status may not be null");
   }
}
