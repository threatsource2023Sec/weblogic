package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.util.SAML2Utils;
import java.io.PrintWriter;
import java.security.Key;
import java.security.PrivateKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weblogic.security.providers.utils.XSSSanitizer;

public class HttpRedirectBindingSender extends BaseHttpBindingSender {
   private boolean logdebug = false;

   public HttpRedirectBindingSender(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

   }

   public void sendRequest(RequestAbstractType request, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send(request, endpoint, relayState, signingKey, "SAMLRequest");
   }

   private void send(SignableSAMLObject samlObj, Endpoint endpoint, String relayState, Key signingKey, String reqOrRes) throws BindingHandlerException {
      try {
         String algo = BindingUtil.getSamlSignAlgorithm(samlObj);
         if (this.logdebug) {
            this.log.debug("signature algorithm of saml object: " + algo);
         }

         Element reqWithoutSig = this.removeSignature(samlObj);
         byte[] samlBytes = BindingUtil.transformNode(this.config, reqWithoutSig);
         byte[] samlDeflateEncode = BindingUtil.deflateEncode(samlBytes);
         String samlBase64Encode = SAML2Utils.base64Encode(samlDeflateEncode);
         String samlUrlEncode = SAML2Utils.urlEncode(samlBase64Encode);
         String rsUrlEncode = null;
         if (relayState != null && !relayState.equals("")) {
            rsUrlEncode = SAML2Utils.urlEncode(relayState);
         }

         if (this.logdebug) {
            this.log.debug("URL encoded saml message:" + samlUrlEncode);
            this.log.debug("URL encoded relay state:" + rsUrlEncode);
         }

         StringBuffer qryStrBuffer = new StringBuffer();
         qryStrBuffer.append(reqOrRes + "=" + samlUrlEncode);
         if (rsUrlEncode != null && !rsUrlEncode.equals("")) {
            qryStrBuffer.append("&RelayState=" + rsUrlEncode);
         }

         if (this.logdebug) {
            this.log.debug("QueryString without signature:" + qryStrBuffer.toString());
         }

         String url;
         if (algo != null && !algo.equals("")) {
            if (signingKey == null) {
               url = Saml2Logger.getSAML2NoSignKeyFor(reqOrRes);
               if (this.logdebug) {
                  this.log.debug(url);
               }

               throw new BindingHandlerException(url, 404);
            }

            qryStrBuffer.append("&SigAlg=" + SAML2Utils.urlEncode(algo));
            url = null;

            byte[] sig;
            try {
               String signAlgo = BindingUtil.xmlSigAlgoToSigAlgo(algo);
               if (this.logdebug) {
                  this.log.debug("Sign: QueryString to be signed:" + qryStrBuffer.toString());
                  this.log.debug("Sign: sign algorithm: " + signAlgo);
               }

               sig = SAML2Utils.signString(qryStrBuffer.toString().getBytes("UTF-8"), signAlgo, (PrivateKey)signingKey);
               if (this.logdebug) {
                  this.log.debug("signature of QueryString:" + sig);
               }
            } catch (Exception var19) {
               if (this.logdebug) {
                  this.log.debug("can't sign QueryString.", var19);
               }

               throw new BindingHandlerException(var19.getMessage(), 500);
            }

            qryStrBuffer.append("&Signature=" + SAML2Utils.urlEncode(SAML2Utils.base64Encode(sig)));
         }

         url = endpoint.getLocation();
         url = url + SAML2Utils.getDelimiterForQueryParams(url) + qryStrBuffer.toString();
         if (this.logdebug) {
            this.log.debug("URL:" + url);
         }

         this.httpResponse.setContentType("text/html");
         this.httpResponse.setStatus(302);
         BindingUtil.setHttpHeaders(this.httpResponse);
         PrintWriter out = this.httpResponse.getWriter();
         String url1 = XSSSanitizer.getValidInput(url);
         if (this.logdebug) {
            this.log.debug("URL: " + url);
         }

         out.println("<HTML><HEAD></HEAD><BODY>Location: " + url1 + "</BODY></HTML>");
         String redirectURL = SAML2Utils.ENABLE_URL_REWRITING ? this.httpResponse.encodeRedirectURL(url1) : url1;
         String validURL = XSSSanitizer.getValidInput(redirectURL);
         if (this.logdebug) {
            this.log.debug("validURL: " + validURL);
         }

         this.httpResponse.sendRedirect(validURL);
      } catch (Exception var20) {
         throw new BindingHandlerException(var20.getMessage(), 500);
      }
   }

   private Element removeSignature(SignableSAMLObject samlObj) throws BindingHandlerException {
      Element result = null;

      try {
         Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(samlObj);
         result = marshaller.marshall(samlObj);
         NodeList children = result.getChildNodes();

         for(int i = 0; i < children.getLength(); ++i) {
            if (children.item(i).getNodeType() == 1) {
               Element childElement = (Element)children.item(i);
               if (childElement.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && childElement.getLocalName().equals("Signature")) {
                  result.removeChild(childElement);
               }
            }
         }

         return result;
      } catch (MarshallingException var7) {
         if (this.logdebug) {
            this.log.debug("can't marshall saml object to xml document.", var7);
         }

         throw new BindingHandlerException(var7.getMessage(), 500);
      }
   }

   public void sendResponse(StatusResponseType resp, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      this.send(resp, endpoint, relayState, signingKey, "SAMLResponse");
   }
}
