package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.artifact.ArtifactStore;
import com.bea.security.saml2.artifact.SAML2ArtifactException;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.util.SAML2Utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import weblogic.security.providers.utils.XSSSanitizer;

public class ArtifactBindingSender extends BaseHttpBindingSender {
   private ArtifactStore store = null;
   private boolean logdebug = false;

   public ArtifactBindingSender(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

      this.store = config.getArtifactStore();
   }

   private void send(SAMLObject object, String relayState, WebSSOPartner partner, Endpoint endpoint) throws BindingHandlerException {
      String base64Art = null;

      try {
         base64Art = this.store.store(object, partner.getName());
      } catch (SAML2ArtifactException var9) {
         throw new BindingHandlerException(var9);
      }

      if (this.logdebug) {
         this.log.debug("store saml object " + object + ", BASE64 encoded artifact is " + base64Art);
      }

      if (base64Art != null && !base64Art.equals("")) {
         boolean postArtifact = partner.isArtifactBindingUsePOSTMethod();
         String locationUrl = endpoint.getLocation();
         String postForm = partner.getArtifactBindingPostForm();
         if (this.logdebug) {
            this.log.debug("post artifact: " + postArtifact);
            this.log.debug("local ARS binding location: " + locationUrl);
            this.log.debug("post form template url: " + postForm);
         }

         if (postArtifact) {
            this.encodeAsForm(base64Art, relayState, locationUrl, postForm);
         } else {
            this.encodeAsUrl(base64Art, relayState, locationUrl);
         }

      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2CouldnotGetArtifactFromStore(), 500);
      }
   }

   private void encodeAsForm(String artifact, String relayState, String locationUrl, String postForm) throws BindingHandlerException {
      if (this.logdebug) {
         this.log.debug("artifact is sent in http form.");
      }

      BindingUtil.sendPostForm(this.httpRequest, this.httpResponse, postForm, locationUrl, relayState, artifact, "SAMLart");
   }

   private void encodeAsUrl(String base64Art, String relayState, String locationUrl) throws BindingHandlerException {
      String artUrlEncode = null;
      String rsUrlEncode = null;

      try {
         artUrlEncode = SAML2Utils.urlEncode(base64Art);
         rsUrlEncode = relayState != null && !relayState.equals("") ? SAML2Utils.urlEncode(relayState) : null;
         if (this.logdebug) {
            this.log.debug("URL encoded artifact: " + artUrlEncode);
            this.log.debug("URL encoded relay state: " + rsUrlEncode);
         }
      } catch (UnsupportedEncodingException var10) {
         throw new BindingHandlerException(var10.getMessage(), 500);
      }

      String url = locationUrl + SAML2Utils.getDelimiterForQueryParams(locationUrl) + "SAMLart" + "=" + artUrlEncode;
      if (rsUrlEncode != null && !rsUrlEncode.equals("")) {
         url = url + "&RelayState=" + rsUrlEncode;
      }

      if (this.logdebug) {
         this.log.debug("artifact is sent in http url:" + url);
      }

      try {
         BindingUtil.setHttpHeaders(this.httpResponse);
         String redirectURL = SAML2Utils.ENABLE_URL_REWRITING ? this.httpResponse.encodeRedirectURL(url) : url;
         String validURL = XSSSanitizer.getValidInput(redirectURL);
         if (this.logdebug) {
            this.log.debug("validURL: " + validURL);
         }

         this.httpResponse.sendRedirect(validURL);
      } catch (IOException var9) {
         throw new BindingHandlerException(var9.getMessage(), 500);
      }
   }

   public void sendRequest(RequestAbstractType request, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send(request, relayState, partner, endpoint);
   }

   public void sendResponse(StatusResponseType resp, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send(resp, relayState, partner, endpoint);
   }
}
