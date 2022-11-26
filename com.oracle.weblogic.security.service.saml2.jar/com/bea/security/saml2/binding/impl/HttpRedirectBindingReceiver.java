package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.util.SAML2Utils;
import java.security.PublicKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class HttpRedirectBindingReceiver extends BaseHttpBindingReceiver {
   private boolean logdebug = false;

   public HttpRedirectBindingReceiver(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

   }

   public RequestAbstractType receiveRequest() throws BindingHandlerException {
      XMLObject object = this.getSamlObject("SAMLRequest");
      if (!(object instanceof RequestAbstractType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("RequestAbstractType"), 400);
      } else {
         return (RequestAbstractType)object;
      }
   }

   public StatusResponseType receiveResponse() throws BindingHandlerException {
      XMLObject object = this.getSamlObject("SAMLResponse");
      if (!(object instanceof StatusResponseType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("StatusResponse"), 400);
      } else {
         return (StatusResponseType)object;
      }
   }

   public boolean verifySignature(PublicKey key) throws BindingHandlerException {
      String algorithm = this.httpRequest.getParameter("SigAlg");
      if (this.logdebug) {
         this.log.debug("get signature algorithm from http request, value is: " + algorithm);
      }

      if (algorithm != null && !algorithm.equals("")) {
         String base64sig = this.httpRequest.getParameter("Signature");
         if (this.logdebug) {
            this.log.debug("get BASE64 encoded signature from http request, value is: " + base64sig);
         }

         if (base64sig != null && !base64sig.equals("")) {
            if (key == null) {
               throw new BindingHandlerException(Saml2Logger.getSAML2NoVerifyKeyFor("SAMLRequest"), 404);
            } else {
               try {
                  byte[] sigValue = SAML2Utils.base64Decode(base64sig);
                  String qryString = this.httpRequest.getQueryString();
                  int index = qryString.indexOf("Signature");
                  String verifyStr = qryString.substring(0, index - 1);
                  String sigAlgo = BindingUtil.xmlSigAlgoToSigAlgo(algorithm);
                  if (this.logdebug) {
                     this.log.debug("verify signature: signature value: " + sigValue);
                     this.log.debug("verify signature: string to be veritifed: " + verifyStr);
                     this.log.debug("verify signature: signature algorithm: " + sigAlgo);
                  }

                  return SAML2Utils.verifyStringSignature(verifyStr.getBytes("UTF-8"), sigValue, sigAlgo, key);
               } catch (Exception var9) {
                  throw new BindingHandlerException("", var9, 403);
               }
            }
         } else {
            throw new BindingHandlerException(Saml2Logger.getSAML2CouldnotGetSigFromHttpreq("Signature"), 400);
         }
      } else {
         return false;
      }
   }

   private XMLObject getSamlObject(String samlType) throws BindingHandlerException {
      String samlmsg = this.httpRequest.getParameter(samlType);
      if (this.logdebug) {
         this.log.debug("get " + samlType + " from http request, value:" + samlmsg);
      }

      if (samlmsg != null && !samlmsg.equals("")) {
         try {
            byte[] base64Decoded = SAML2Utils.base64Decode(samlmsg);
            byte[] deflateDecoded = BindingUtil.deflateDecode(base64Decoded);
            return BindingUtil.unmarshall(deflateDecoded);
         } catch (Exception var5) {
            if (this.logdebug) {
               this.log.debug("can't unmarshall to a saml object.", var5);
            }

            throw new BindingHandlerException("", var5, 400);
         }
      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2NoSamlMsgInHttpreq(samlType), 400);
      }
   }
}
