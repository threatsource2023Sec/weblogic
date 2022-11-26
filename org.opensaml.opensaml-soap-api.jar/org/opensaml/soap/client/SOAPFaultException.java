package org.opensaml.soap.client;

import javax.annotation.Nullable;
import org.opensaml.soap.common.SOAPException;
import org.opensaml.soap.soap11.Fault;

public class SOAPFaultException extends SOAPException {
   private static final long serialVersionUID = 4770411452264097320L;
   private Fault soapFault;

   public SOAPFaultException() {
   }

   public SOAPFaultException(@Nullable String message) {
      super(message);
   }

   public SOAPFaultException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SOAPFaultException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }

   @Nullable
   public Fault getFault() {
      return this.soapFault;
   }

   public void setFault(@Nullable Fault fault) {
      this.soapFault = fault;
   }
}
