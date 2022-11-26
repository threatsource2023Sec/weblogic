package org.opensaml.xacml.ctx.provider;

import java.util.Objects;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xacml.policy.ObligationType;

public abstract class BaseObligationHandler {
   private String id;
   private int precedence;

   protected BaseObligationHandler(String obligationId) {
      this(obligationId, Integer.MIN_VALUE);
   }

   protected BaseObligationHandler(String obligationId, int handlerPrecedence) {
      this.id = StringSupport.trimOrNull(obligationId);
      if (this.id == null) {
         throw new IllegalArgumentException("Provided obligation ID may not be null or empty");
      } else {
         this.precedence = handlerPrecedence;
      }
   }

   public String getObligationId() {
      return this.id;
   }

   public int getHandlerPrecedence() {
      return this.precedence;
   }

   public abstract void evaluateObligation(ObligationProcessingContext var1, ObligationType var2) throws ObligationProcessingException;

   public int hashCode() {
      return this.getObligationId().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof BaseObligationHandler ? Objects.equals(this.getObligationId(), ((BaseObligationHandler)obj).getObligationId()) : false;
      }
   }
}
