package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.util.SAML2Utils;
import java.security.Key;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.w3c.dom.Element;

public class HttpPostBindingSender extends BaseHttpBindingSender {
   private boolean logdebug = false;

   public HttpPostBindingSender(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

   }

   public void sendRequest(RequestAbstractType request, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send("SAMLRequest", request, partner, endpoint, relayState);
   }

   public void sendResponse(StatusResponseType resp, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send("SAMLResponse", resp, partner, endpoint, relayState);
   }

   private void send(String name, XMLObject object, WebSSOPartner partner, Endpoint endpoint, String relayState) throws BindingHandlerException {
      String postTemplate = partner.getPostBindingPostForm();
      if (this.logdebug) {
         this.log.debug("post from template url: " + postTemplate);
      }

      String stringOfSamlObject = null;

      try {
         Element ele = BindingUtil.marshallXMLObject(object);
         byte[] domBytes = BindingUtil.transformNode(this.config, ele);
         stringOfSamlObject = SAML2Utils.base64Encode(domBytes);
      } catch (TransformerException var10) {
         if (this.logdebug) {
            this.log.debug("can't change saml object to string.", var10);
         }

         throw new BindingHandlerException("", var10, 500);
      } catch (MarshallingException var11) {
         if (this.logdebug) {
            this.log.debug("can't change saml object to string.", var11);
         }

         throw new BindingHandlerException("", var11, 500);
      }

      BindingUtil.sendPostForm(this.httpRequest, this.httpResponse, postTemplate, endpoint.getLocation(), relayState, stringOfSamlObject, name);
   }
}
