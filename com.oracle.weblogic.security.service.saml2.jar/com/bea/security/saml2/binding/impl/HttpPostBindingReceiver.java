package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.util.SAML2Utils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class HttpPostBindingReceiver extends BaseHttpBindingReceiver {
   private boolean logdebug = false;

   public HttpPostBindingReceiver(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

   }

   public RequestAbstractType receiveRequest() throws BindingHandlerException {
      XMLObject object = this.receive("SAMLRequest");
      if (!(object instanceof RequestAbstractType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("RequestAbstractType"), 400);
      } else {
         return (RequestAbstractType)object;
      }
   }

   public StatusResponseType receiveResponse() throws BindingHandlerException {
      XMLObject object = this.receive("SAMLResponse");
      if (!(object instanceof StatusResponseType)) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageTypeError("StatusResponse"), 400);
      } else {
         return (StatusResponseType)object;
      }
   }

   private XMLObject receive(String samlType) throws BindingHandlerException {
      String rawReqValue = this.httpRequest.getParameter(samlType);
      if (this.logdebug) {
         this.log.debug("get " + samlType + " from http request:" + rawReqValue);
      }

      if (rawReqValue == null) {
         String msg = Saml2Logger.getSAML2NoSamlMsgInHttpreq(samlType);
         if (this.logdebug) {
            this.log.debug(msg);
         }

         throw new BindingHandlerException(msg, 400);
      } else {
         try {
            byte[] xml = SAML2Utils.base64Decode(rawReqValue);
            if (this.logdebug) {
               this.log.debug("BASE64 decoded saml message:" + new String(xml));
            }

            return BindingUtil.unmarshall(xml);
         } catch (XMLParserException var4) {
            if (this.logdebug) {
               this.log.debug("can't parse BASE64 decoded saml message to an xml document.", var4);
            }

            throw new BindingHandlerException("", var4, 400);
         } catch (UnmarshallingException var5) {
            if (this.logdebug) {
               this.log.debug("can't unmarshall document to an saml object.", var5);
            }

            throw new BindingHandlerException("", var5, 400);
         } catch (IOException var6) {
            if (this.logdebug) {
               this.log.debug("can't BASE64 decode saml message.", var6);
            }

            throw new BindingHandlerException("", var6, 400);
         }
      }
   }
}
