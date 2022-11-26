package org.opensaml.soap.common;

import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.soap.soap11.Fault;

public class SOAP11FaultDecodingException extends MessageDecodingException {
   private static final long serialVersionUID = 7013840493662326895L;
   private final Fault fault;

   public SOAP11FaultDecodingException(Fault soapFault) {
      this.fault = soapFault;
   }

   public SOAP11FaultDecodingException(Fault soapFault, String message) {
      super(message);
      this.fault = soapFault;
   }

   public Fault getFault() {
      return this.fault;
   }
}
