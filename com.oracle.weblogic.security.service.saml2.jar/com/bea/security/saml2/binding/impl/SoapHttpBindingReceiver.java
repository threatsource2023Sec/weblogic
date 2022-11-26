package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.soap.soap11.Envelope;
import org.xml.sax.InputSource;

public class SoapHttpBindingReceiver extends BaseHttpBindingReceiver {
   public SoapHttpBindingReceiver(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
   }

   public RequestAbstractType receiveRequest() throws BindingHandlerException {
      XMLObject req = this.receive();
      if (req != null && req instanceof RequestAbstractType) {
         return (RequestAbstractType)req;
      } else {
         throw new BindingHandlerException(400);
      }
   }

   public StatusResponseType receiveResponse() throws BindingHandlerException {
      XMLObject res = this.receive();
      if (res != null && res instanceof StatusResponseType) {
         return (StatusResponseType)res;
      } else {
         throw new BindingHandlerException(400);
      }
   }

   private XMLObject receive() throws BindingHandlerException {
      try {
         XMLObject soapObject = BindingUtil.unmarshall(new InputSource(this.httpRequest.getInputStream()));
         if (soapObject instanceof Envelope) {
            Envelope envelope = (Envelope)soapObject;
            if (envelope.getBody().getOrderedChildren().size() == 1) {
               return (XMLObject)envelope.getBody().getOrderedChildren().get(0);
            }
         }

         return null;
      } catch (IOException var3) {
         if (this.log != null && this.log.isDebugEnabled()) {
            this.log.debug(Saml2Logger.getSAML2ReceivingError("SAMLObject"), var3);
         }

         throw new BindingHandlerException(500);
      } catch (Exception var4) {
         if (this.log != null && this.log.isDebugEnabled()) {
            this.log.debug(Saml2Logger.getSAML2ReceivingError("SAMLObject"), var4);
         }

         throw new BindingHandlerException(400);
      }
   }
}
