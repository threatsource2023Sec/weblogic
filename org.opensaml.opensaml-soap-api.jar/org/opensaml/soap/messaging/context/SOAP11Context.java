package org.opensaml.soap.messaging.context;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;

public class SOAP11Context extends BaseContext {
   private Envelope envelope;
   private Fault fault;
   private Integer httpResponseStatus;

   @Nullable
   public Envelope getEnvelope() {
      return this.envelope;
   }

   public void setEnvelope(@Nullable Envelope newEnvelope) {
      this.envelope = newEnvelope;
   }

   @Nullable
   public Fault getFault() {
      return this.fault;
   }

   public void setFault(@Nullable Fault newFault) {
      this.fault = newFault;
   }

   @Nullable
   public Integer getHTTPResponseStatus() {
      return this.httpResponseStatus;
   }

   public void setHTTPResponseStatus(@Nullable Integer status) {
      this.httpResponseStatus = status;
   }
}
