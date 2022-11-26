package com.bea.security.saml2.binding.impl;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import java.security.Key;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;
import org.w3c.dom.Element;

public class SoapHttpBindingSender extends BaseHttpBindingSender {
   private static final String DEFAULT_CONTENT_TYPE = "text/xml; charset=UTF-8";
   private static final String LOGGING_PREFIX = "SoapHttpBindingSender.";
   private static final QName FAULT_CODE_SERVER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server");

   public SoapHttpBindingSender(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      super(config, req, resp);
   }

   public void sendRequest(RequestAbstractType request, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("SoapHttpBindingSender.sendRequest: Set HTTP headers to prevent HTTP proxies cache SAML protocol messages.");
      }

      BindingUtil.setHttpHeaders(this.httpResponse);
      this.send(request);
   }

   public void sendResponse(StatusResponseType resp, Endpoint endpoint, WebSSOPartner partner, String relayState, Key signingKey) throws BindingHandlerException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("SoapHttpBindingSender.sendResponse: Set HTTP headers to prevent HTTP proxies cache SAML protocol messages.");
      }

      this.httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
      this.httpResponse.setHeader("Pragam", "no-cache");
      this.send(resp);
   }

   private void send(SAMLObject object) throws BindingHandlerException {
      String method = "SoapHttpBindingSender.send: ";
      if (object == null) {
         this.sendError(Saml2Logger.getSAML2ReceivingError("SAMLObject"));
      } else {
         try {
            Envelope env = BindingUtil.buildEnvelope(object);
            Element soapElement = BindingUtil.marshallXMLObject(env);
            this.httpResponse.setContentType("text/xml; charset=UTF-8");
            if (this.log.isDebugEnabled()) {
               this.log.debug(method + "the SOAP envelope to be sent is :\n");
               this.log.debug(new String(BindingUtil.transformNode(this.config, soapElement)));
            }

            this.httpResponse.getOutputStream().write(BindingUtil.transformNode(this.config, soapElement));
         } catch (Exception var5) {
            if (this.log != null && this.log.isDebugEnabled()) {
               this.log.debug(Saml2Logger.getSAML2SendingError("SAMLObject"), var5);
            }

            this.sendError(var5.getMessage());
         }

      }
   }

   private void sendError(String message) throws BindingHandlerException {
      String method = "SoapHttpBindingSender.sendError: ";
      if (this.log.isDebugEnabled()) {
         this.log.debug(method + "converting an exception to a SOAP Fault...");
      }

      Fault fault = this.buildFault(message);
      Envelope env = BindingUtil.buildEnvelope(fault);

      try {
         Element soapElement = BindingUtil.marshallXMLObject(env);
         this.httpResponse.setStatus(500);
         this.httpResponse.setContentType("text/xml; charset=UTF-8");
         if (this.log.isDebugEnabled()) {
            this.log.debug(method + "SOAP envelope with SOAP fault is: \n" + new String(BindingUtil.transformNode(this.config, soapElement)));
         }

         this.httpResponse.getOutputStream().write(BindingUtil.transformNode(this.config, soapElement));
      } catch (Exception var6) {
         if (this.log != null && this.log.isDebugEnabled()) {
            this.log.debug(Saml2Logger.getSAML2SendingError("SOAP Fault"), var6);
         }

         throw new BindingHandlerException(500);
      }
   }

   private Fault buildFault(String message) {
      Fault fault = (Fault)XMLObjectSupport.buildXMLObject(Fault.DEFAULT_ELEMENT_NAME);
      FaultCode faultCode = (FaultCode)XMLObjectSupport.buildXMLObject(FaultCode.DEFAULT_ELEMENT_NAME);
      faultCode.setValue(FAULT_CODE_SERVER);
      FaultString faultString = (FaultString)XMLObjectSupport.buildXMLObject(FaultString.DEFAULT_ELEMENT_NAME);
      faultString.setValue(message);
      fault.setCode(faultCode);
      fault.setMessage(faultString);
      return fault;
   }
}
