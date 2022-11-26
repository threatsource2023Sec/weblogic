package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.artifact.ArtifactResolver;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class ArtifactBindingReceiver extends BaseHttpBindingReceiver {
   private ArtifactResolver resolver = null;
   private boolean logdebug = false;

   public ArtifactBindingReceiver(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

      this.resolver = config.getArtifactResolver();
   }

   public RequestAbstractType receiveRequest() throws BindingHandlerException {
      SAMLObject object = this.resolve(1);
      if (!(object instanceof RequestAbstractType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("RequestAbstractType"), 400);
      } else {
         return (RequestAbstractType)object;
      }
   }

   public StatusResponseType receiveResponse() throws BindingHandlerException {
      SAMLObject object = this.resolve(2);
      if (!(object instanceof StatusResponseType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("StatusResponse"), 400);
      } else {
         return (StatusResponseType)object;
      }
   }

   private SAMLObject resolve(int msgType) throws BindingHandlerException {
      String artifact = this.httpRequest.getParameter("SAMLart");
      if (this.logdebug) {
         this.log.debug("get BASE64 encoded artifact from http request, value is:" + artifact);
      }

      if (artifact != null && !artifact.equals("")) {
         SAMLObject object = this.resolver.resolve(msgType, artifact);
         return object;
      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2ArtifactIsNull("SAMLart"), 400);
      }
   }
}
