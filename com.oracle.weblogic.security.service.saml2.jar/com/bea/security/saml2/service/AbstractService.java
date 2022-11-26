package com.bea.security.saml2.service;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.BindingSender;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractService implements Service {
   protected LoggerSpi log;
   protected SAML2ConfigSpi config;

   protected AbstractService(SAML2ConfigSpi config) {
      this.config = config;
      this.log = config.getLogger();
   }

   protected void logRequest(HttpServletRequest request) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Request URI: " + request.getRequestURI());
         this.log.debug("Method: " + request.getMethod());
         this.log.debug("Query string: " + request.getQueryString());
         Enumeration headers = request.getHeaderNames();

         while(headers.hasMoreElements()) {
            String s = (String)headers.nextElement();
            this.log.debug("\t" + s + ": " + request.getHeader(s));
         }
      }

   }

   protected String getBindingTypeFromURI(HttpServletRequest request, HttpServletResponse response) throws BindingHandlerException {
      String requestURI = request.getRequestURI();
      if (requestURI.endsWith("/post")) {
         return "HTTP/POST";
      } else if (requestURI.endsWith("/artifact")) {
         return "HTTP/Artifact";
      } else if (requestURI.endsWith("/redirect")) {
         return "HTTP/Redirect";
      } else {
         throw new BindingHandlerException("Unable to detect binding type from request URI", 400);
      }
   }

   protected BindingSender getSender(HttpServletRequest request, HttpServletResponse response, String bindingType) throws BindingHandlerException {
      return this.config.getBindingHandlerFactory().newBindingSender(bindingType, request, response);
   }

   protected boolean logAndSendError(HttpServletResponse response, int httpCode, Exception e) throws IOException {
      this.logError(e);
      response.sendError(httpCode);
      return true;
   }

   protected void logError(Exception e) {
      if (this.log != null && this.log.isDebugEnabled()) {
         String msg = e.getMessage();
         Throwable cause = e.getCause();
         this.log.debug(msg);
         if (cause != null) {
            this.log.debug("Caused by: " + cause.getMessage());
         }

         this.log.debug("exception info", e);
      }
   }

   protected void checkSSOCertificate() throws SAML2Exception {
      SAML2KeyManager.KeyInfo keyInfo = this.config.getSAML2KeyManager().getSSOKeyInfo();
      String ssoKeyAlias = this.config.getLocalConfiguration().getSSOSigningKeyAlias();
      Certificate cert = null;
      if (keyInfo != null) {
         cert = keyInfo.getCert();
      }

      if (cert != null) {
         X509Certificate x509cert = (X509Certificate)cert;

         try {
            x509cert.checkValidity();
         } catch (CertificateExpiredException var6) {
            if (this.log != null && this.log.isDebugEnabled()) {
               this.log.debug("Using expired certificate at alias " + ssoKeyAlias + " for signing.");
               this.log.debug("allow expired cert is " + SAML2Utils.ALLOW_EXPIRE_CERTS);
            }

            if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
               throw new SAML2Exception(Saml2Logger.getSignWithExpiredCert(ssoKeyAlias), var6, 500);
            }
         } catch (CertificateNotYetValidException var7) {
            if (this.log != null && this.log.isDebugEnabled()) {
               this.log.debug("Using not yet valid certificate at alias " + ssoKeyAlias + " for signing.");
               this.log.debug("allow expired cert is " + SAML2Utils.ALLOW_EXPIRE_CERTS);
            }

            if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
               throw new SAML2Exception(Saml2Logger.getSignWithNotYetValidCert(ssoKeyAlias), var7, 500);
            }
         }
      }

   }
}
