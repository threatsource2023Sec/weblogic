package com.bea.security.saml2.service.spinitiator;

import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.utils.CSSPlatformProxy;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingSender;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.service.AbstractService;
import com.bea.security.saml2.service.SAML2Exception;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.SAMLObjectBuilder;
import com.bea.security.saml2.util.cache.SAML2Cache;
import com.bea.security.saml2.util.cache.SAML2CacheFactory;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.xmlsec.signature.support.SignatureException;
import weblogic.utils.StringUtils;

public class SPInitiatorImpl extends AbstractService {
   private SAML2Cache authnReqCache = null;
   private PrivateKey signkey = null;
   private boolean allowDiffHostAccess = false;
   private List certList;

   public SPInitiatorImpl(SAML2ConfigSpi config) {
      super(config);
      this.authnReqCache = SAML2CacheFactory.createAuthRequestCache(config);
      SAML2KeyManager.KeyInfo keyInfo = config.getSAML2KeyManager().getSSOKeyInfo();
      if (keyInfo != null) {
         this.signkey = keyInfo.getKey();
         this.certList = keyInfo.getCertAsList();
      }

      this.allowDiffHostAccess = Boolean.valueOf(System.getProperty("css.saml.AllowDiffHostAccess", "false"));
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.logDebug("SP initiating authn request: processing");

      try {
         if (!this.config.getLocalConfiguration().isServiceProviderEnabled()) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Service provider is not enabled.");
            }

            return false;
         }

         String requestURI = request.getRequestURI();
         String partnerName = null;
         String targetURL = null;
         WebSSOIdPPartner idpPartner = null;
         if (requestURI.endsWith("/sp/sso/initiator")) {
            partnerName = request.getParameter("IdpName");
            targetURL = request.getParameter("RequestURL");
            if (partnerName == null || partnerName.length() == 0 || targetURL == null || targetURL.length() == 0) {
               throw new SAML2Exception("Idp or request URL not specified in the request", 400);
            }

            String[] allowedTargetHosts = this.config.getLocalConfiguration().getAllowedTargetHosts();
            if (allowedTargetHosts != null && allowedTargetHosts.length > 0 && (targetURL.startsWith("http://") || targetURL.startsWith("https://")) && !SAMLUtil.isTargetRedirectHostAllowed(targetURL, allowedTargetHosts)) {
               throw new SAML2Exception(Saml2Logger.getTargetRedirectHostNotAllowed(targetURL, StringUtils.join(allowedTargetHosts, ",")));
            }

            Set paramNames = request.getParameterMap().keySet();
            if (paramNames.size() > 2) {
               StringBuffer buf = new StringBuffer();
               Iterator it = paramNames.iterator();

               while(it.hasNext()) {
                  String paramName = (String)it.next();
                  if (!"IdpName".equals(paramName) && !"RequestURL".equals(paramName)) {
                     buf.append('&').append(paramName).append('=').append(request.getParameter(paramName));
                  }
               }

               if (buf.length() > 0) {
                  buf.setCharAt(0, '?');
               }

               targetURL = targetURL + buf.toString();
            }

            idpPartner = (WebSSOIdPPartner)this.config.getPartnerManager().getIdPPartner(partnerName);
         } else {
            StringBuffer reqURL = request.getRequestURL();
            if (request.getQueryString() != null) {
               reqURL.append("?").append(request.getQueryString());
            }

            targetURL = new String(reqURL);
            PartnerCache partnerCache = PartnerCacheManager.getPartnerCache(this.config);
            idpPartner = partnerCache.getIdPByRedirectURI(requestURI);
            if (idpPartner == null || !idpPartner.isEnabled()) {
               return false;
            }

            if (!CSSPlatformProxy.getInstance().isOnWLS() && !this.allowDiffHostAccess) {
               URL target = new URL(targetURL);
               URL publishedSite = new URL(this.config.getLocalConfiguration().getPublishedSiteURL());
               if (!publishedSite.getHost().equalsIgnoreCase(target.getHost())) {
                  this.log.debug("The host name in the request URL is not same as the host configured by published site URL.");
                  throw new SAML2Exception(Saml2Logger.getNoPartnerForRequest(partnerName, requestURI), 400);
               }
            }
         }

         int httpCode = 404;
         if (idpPartner == null) {
            throw new SAML2Exception(Saml2Logger.getNoPartnerForRequest(partnerName, requestURI), httpCode);
         }

         if (!idpPartner.isEnabled()) {
            throw new SAML2Exception(Saml2Logger.getIdPNotEnabled(idpPartner.getName()), httpCode);
         }

         Endpoint[] services = idpPartner.getSingleSignOnService();
         if (services == null || services.length == 0) {
            throw new SAML2Exception(Saml2Logger.getNoSSOServicesForPartner(idpPartner.getName()), httpCode);
         }

         Endpoint ep = services[0];
         this.logDebug("SP initiating authn request: partner id is " + partnerName);
         AuthnRequest atn = this.createRequest(idpPartner, ep);
         this.authnReqCache.put(atn.getID(), targetURL);
         this.logDebug("SP initiating authn request: use partner binding " + ep.getBinding());
         BindingSender handler = this.getSender(request, response, ep.getBinding());
         handler.sendRequest(atn, ep, idpPartner, (String)null, this.signkey);
      } catch (SAML2Exception var12) {
         this.logAndSendError(response, var12.getHttpStatusCode(), var12);
      } catch (Exception var13) {
         this.logAndSendError(response, 500, var13);
      }

      return true;
   }

   public SAML2Cache getAuthnReqCache() {
      return this.authnReqCache;
   }

   public void removePartnerCache() {
      PartnerCacheManager.removePartnerCache(this.config);
   }

   private AuthnRequest createRequest(WebSSOIdPPartner idp, Endpoint binding) throws SAML2Exception {
      AuthnRequest atn = (AuthnRequest)XMLObjectSupport.buildXMLObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
      atn.setID(SAML2Utils.getXMLSafeSecureUUID());
      atn.setVersion(SAMLVersion.VERSION_20);
      atn.setDestination(binding.getLocation());
      atn.setIssueInstant(new DateTime());
      Issuer issuer = SAMLObjectBuilder.buildIssuer(this.config.getLocalConfiguration().getEntityID());
      atn.setIssuer(issuer);
      atn.setForceAuthn(this.config.getLocalConfiguration().isForceAuthn() ? Boolean.TRUE : Boolean.FALSE);
      atn.setIsPassive(this.config.getLocalConfiguration().isPassive() ? Boolean.TRUE : Boolean.FALSE);
      if (this.needSignRequest(idp)) {
         this.signAuthnRequest(atn);
      }

      return atn;
   }

   private void signAuthnRequest(AuthnRequest atn) throws SAML2Exception {
      if (this.signkey == null) {
         throw new SAML2Exception(Saml2Logger.getNoSignKeyFor("AuthnRequest"), 404);
      } else {
         try {
            this.checkSSOCertificate();
            SAML2Utils.signSamlObject(this.signkey, atn, this.certList);
         } catch (SignatureException | MarshallingException var3) {
            throw new SAML2Exception("Sign authn request error.", 500);
         }
      }
   }

   private boolean needSignRequest(WebSSOIdPPartner idp) {
      return this.config.getLocalConfiguration().isSignAuthnRequests() ? true : idp.isWantAuthnRequestsSigned();
   }

   private void logDebug(String msg) {
      if (this.log != null && this.log.isDebugEnabled()) {
         this.log.debug(msg);
      }

   }
}
