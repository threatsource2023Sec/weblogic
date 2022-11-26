package org.opensaml.soap.soap11.messaging.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.error.TypedMessageErrorHandler;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.util.SOAPSupport;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;

public class SOAP11FaultContextDecoratingErrorHandler implements TypedMessageErrorHandler {
   @Nonnull
   private Class handledThrowable;
   @Nonnull
   private QName faultCode;
   @Nonnull
   private String faultString;
   @Nullable
   private String faultActor;
   @Nullable
   private String wsAddressingActionURI;

   public SOAP11FaultContextDecoratingErrorHandler(@Nonnull Class throwable, @Nonnull QName code, @Nonnull String message) {
      this(throwable, code, message, (String)null, (String)null);
   }

   public SOAP11FaultContextDecoratingErrorHandler(@Nonnull Class throwable, @Nonnull QName code, @Nonnull String message, @Nullable String actor, @Nullable String addressingActionURI) {
      this.handledThrowable = (Class)Constraint.isNotNull(throwable, "Handled Throwable type cannot be null");
      this.faultCode = (QName)Constraint.isNotNull(code, "Fault code cannot be null");
      this.faultString = (String)Constraint.isNotNull(StringSupport.trim(message), "Fault string cannot be null or empty");
      this.faultActor = StringSupport.trimOrNull(actor);
      this.wsAddressingActionURI = StringSupport.trimOrNull(addressingActionURI);
   }

   public boolean handlesError(@Nonnull Throwable t) {
      return this.handledThrowable.isInstance(t);
   }

   public boolean handleError(@Nonnull Throwable t, @Nonnull MessageContext messageContext) {
      Fault fault = SOAPSupport.buildSOAP11Fault(this.faultCode, this.faultString, this.faultActor, (List)null, (Map)null);
      SOAPMessagingSupport.registerSOAP11Fault(messageContext, fault);
      if (this.wsAddressingActionURI != null) {
         ((WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class, true)).setFaultActionURI(this.wsAddressingActionURI);
      }

      return true;
   }
}
