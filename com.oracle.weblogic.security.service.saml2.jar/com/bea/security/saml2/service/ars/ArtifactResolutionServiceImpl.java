package com.bea.security.saml2.service.ars;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.artifact.ArtifactDataObject;
import com.bea.security.saml2.artifact.SAML2ArtifactException;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.BindingReceiver;
import com.bea.security.saml2.binding.BindingSender;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.registry.PartnerManagerException;
import com.bea.security.saml2.service.AbstractService;
import com.bea.security.saml2.service.SAML2DetailedException;
import com.bea.security.saml2.service.SAML2Exception;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.SAMLObjectBuilder;
import java.io.IOException;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;

public class ArtifactResolutionServiceImpl extends AbstractService {
   private static final String BASIC_AUTH_HEADER = "Authorization";
   private static final String BASIC_AUTH_TOKEN = "Basic";
   private static final String X509CERT_ATTRIBUTE = "javax.servlet.request.X509Certificate";
   private static final String LOGGING_PREFIX = "ArtifactResolutionService.";

   public ArtifactResolutionServiceImpl(SAML2ConfigSpi config) {
      super(config);
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      boolean debug = this.log.isDebugEnabled();
      String method = "ArtifactResolutionService.process: ";
      String reqURI = request.getRequestURI();
      String serviceType = null;
      if (reqURI.endsWith("/sp/ars/soap")) {
         serviceType = "sp";
      } else {
         if (!reqURI.endsWith("/idp/ars/soap")) {
            if (debug) {
               this.log.debug(method + "ARS service called on unexpected URI: '" + reqURI + "', returning NOT_FOUND");
            }

            response.sendError(404);
            return true;
         }

         serviceType = "idp";
      }

      BindingReceiver receiver = null;
      BindingSender sender = null;
      X509Certificate clientCert = null;

      String basicAuth;
      X509Certificate[] certs;
      try {
         label133: {
            if (debug) {
               this.log.debug(method + "get SoapHttpBindingReceiver as receiver and SoapHttpBindingSender as sender.");
            }

            receiver = this.config.getBindingHandlerFactory().newBindingReceiver("SOAP", request, response);
            sender = this.getSender(request, response, "SOAP");
            if (receiver != null && sender != null) {
               if (request.isSecure()) {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug(method + "connection is via SSL connection.");
                  }

                  certs = (X509Certificate[])((X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate"));
                  if (certs != null && certs.length > 0) {
                     clientCert = certs[0];
                     if (debug) {
                        this.log.debug(method + "got one client certifacte, its subjectDN is: " + clientCert.getSubjectDN().getName());
                     }
                  }
               }

               basicAuth = request.getHeader("Authorization");
               if (basicAuth == null) {
                  break label133;
               }

               String[] tokens = basicAuth.split("\\s");
               if (tokens.length == 2 && tokens[0].equals("Basic")) {
                  basicAuth = tokens[1];
                  break label133;
               }

               String message = "Invalid Authorization header '" + basicAuth + "' found in request, should return BAD_REQUEST";
               throw new Exception(message);
            }

            if (debug) {
               this.log.debug(method + "can not get corresponding binding receiver/sender.");
            }

            throw new Exception(Saml2Logger.getSAML2CouldNotGetBindingHandler("SOAP"));
         }
      } catch (Exception var24) {
         this.logAndSendError(response, 403, var24);
         return true;
      }

      certs = null;

      ArtifactResolve resolve;
      try {
         resolve = (ArtifactResolve)receiver.receiveRequest();
         if (resolve == null) {
            throw new Exception(Saml2Logger.getSAML2InvalidSAMLRequest("<samlp:ArtifactResponse>"));
         }
      } catch (Exception var23) {
         if (this.log != null && this.log.isDebugEnabled()) {
            this.log.debug(Saml2Logger.getSAML2InvalidSAMLRequest("ArtifactResolve"), var23);
         }

         try {
            sender.sendResponse((StatusResponseType)null, (Endpoint)null, (WebSSOPartner)null, (String)null, (Key)null);
         } catch (BindingHandlerException var16) {
            this.logAndSendError(response, var16.getHttpStatusCode(), var16);
         }

         return true;
      }

      try {
         SAMLObject object = this.lookupByArtifact(serviceType, resolve, clientCert, basicAuth);
         ArtifactResponse artResponse = this.buildArtifactResponse(object, resolve.getID());

         try {
            this.checkSSOCertificate();
         } catch (SAML2Exception var18) {
            throw (new SAML2DetailedException(var18.getMessage(), var18, 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", "urn:oasis:names:tc:SAML:2.0:status:RequestUnsupported");
         }

         try {
            artResponse = (ArtifactResponse)SAML2Utils.signSamlObject(this.config.getSAML2KeyManager().getSSOKeyInfo().getKey(), artResponse, this.config.getSAML2KeyManager().getSSOKeyInfo().getCertAsList());
         } catch (MarshallingException var19) {
            if (this.log != null && this.log.isDebugEnabled()) {
               this.log.debug(Saml2Logger.getSAML2SigningErrors("<samlp:ArtifactResponse>"), var19);
            }

            throw (new SAML2DetailedException(Saml2Logger.getSAML2SigningErrors("<samlp:ArtifactResponse>"), var19, 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", "urn:oasis:names:tc:SAML:2.0:status:RequestUnsupported");
         }

         sender.sendResponse(artResponse, (Endpoint)null, (WebSSOPartner)null, (String)null, (Key)null);
      } catch (BindingHandlerException var20) {
         this.logAndSendError(response, var20.getHttpStatusCode(), var20);
      } catch (SAML2DetailedException var21) {
         SAML2DetailedException sde = (new SAML2DetailedException(var21.getMessage(), var21.getHttpStatusCode())).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Success", (String)null);
         ArtifactResponse res = this.buildArtifactResponse(sde, resolve.getID());

         try {
            sender.sendResponse(res, (Endpoint)null, (WebSSOPartner)null, (String)null, (Key)null);
         } catch (BindingHandlerException var17) {
            this.logAndSendError(response, var17.getHttpStatusCode(), var21);
         }
      } catch (Exception var22) {
         this.logAndSendError(response, 500, var22);
      }

      return true;
   }

   private SAMLObject lookupByArtifact(String serviceType, ArtifactResolve resolve, X509Certificate clientCert, String basicAuth) throws SAML2DetailedException {
      ArtifactDataObject ado = null;
      String art = resolve.getArtifact().getArtifact();

      try {
         ado = this.config.getArtifactStore().retrieve(art);
      } catch (SAML2ArtifactException var14) {
         throw new SAML2DetailedException(var14);
      }

      if (ado == null) {
         throw (new SAML2DetailedException(Saml2Logger.getSAML2SamlMessageIsNull(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
      } else {
         WebSSOPartner partnerConfig = null;

         try {
            partnerConfig = this.getPartnerByName(serviceType, ado.getPartnerName());
         } catch (PartnerManagerException var13) {
            this.log.warn(Saml2Logger.getFindPartnerByNameError(ado.getPartnerName()));
            throw (new SAML2DetailedException(Saml2Logger.getFindPartnerByNameError(ado.getPartnerName()), var13, 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
         }

         if (partnerConfig == null) {
            throw (new SAML2DetailedException(Saml2Logger.getNoPartnerByName("unknown", ado.getPartnerName()), 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
         } else if (!partnerConfig.isEnabled()) {
            throw (new SAML2DetailedException(Saml2Logger.getPartnerIsNotEnabledInRegistry(ado.getPartnerName()), 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
         } else if (!this.validateArtifactRequester(partnerConfig, clientCert, basicAuth)) {
            this.log.warn(Saml2Logger.getSAML2ArtifactATNFailed());
            throw (new SAML2DetailedException(Saml2Logger.getSAML2ArtifactATNFailed(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
         } else {
            resolve.getDOM().setIdAttributeNS((String)null, "ID", true);
            Signature sig = resolve.getSignature();
            if (this.config.getLocalConfiguration().isWantArtifactRequestsSigned() || sig != null) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Verifying the signature of <samlp:ArtifactResolve> message.");
               }

               try {
                  PublicKey verifyKey = SAML2Utils.getVerifyKey(partnerConfig);
                  SAML2Utils.verifySamlObjectSignature(verifyKey, sig);
               } catch (SignatureException var10) {
                  this.log.error(var10.getMessage(), var10);
                  throw (new SAML2DetailedException(Saml2Logger.getSAML2VerifySignatureFail(), 403)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
               } catch (CertificateException var11) {
                  this.log.error(var11.getMessage(), var11);
                  throw (new SAML2DetailedException(Saml2Logger.getNoVerifyingCert("<samlp:ArtifactResolve>", partnerConfig.getName()), 403)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
               } catch (KeyException var12) {
                  this.log.error(var12.getMessage(), var12);
                  throw (new SAML2DetailedException(Saml2Logger.getSAML2NoVerifyKeyFor("<samlp:ArtifactResolve>"), 403)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed");
               }
            }

            return ado.getData();
         }
      }
   }

   private WebSSOPartner getPartnerByName(String serviceType, String name) throws PartnerManagerException {
      return serviceType.equals("idp") ? (WebSSOPartner)this.config.getPartnerManager().getSPPartner(name) : (WebSSOPartner)this.config.getPartnerManager().getIdPPartner(name);
   }

   private boolean validateArtifactRequester(WebSSOPartner partner, X509Certificate clientCert, String basicAuth) {
      String method = "ArtifactResolutionService.validateArtifactRequester: ";
      String username;
      if (this.config.getLocalConfiguration().isWantBasicAuthClientAuthentication()) {
         if (basicAuth == null) {
            return false;
         }

         username = this.config.getLocalConfiguration().getBasicAuthUsername();
         String passwd = this.config.getLocalConfiguration().getBasicAuthPassword();
         byte[] decodedAuthBytes = null;

         try {
            decodedAuthBytes = SAML2Utils.base64Decode(basicAuth);
         } catch (IOException var11) {
            this.log.warn(var11.getMessage());
         }

         String authString = username + ":" + passwd;
         String clientAuth = decodedAuthBytes == null ? "" : new String(decodedAuthBytes);
         if (this.log.isDebugEnabled()) {
            int endIndex = clientAuth.indexOf(":");
            if (endIndex < 0) {
               endIndex = clientAuth.length();
            }

            this.log.debug(method + "local basic username: " + username + "; client basic username: " + clientAuth.substring(0, endIndex));
         }

         if (!authString.equals(clientAuth)) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Client basic auth is invalid, authentication is failed.");
            }

            return false;
         }
      }

      if (this.config.getLocalConfiguration().isWantTransportLayerSecurityClientAuthentication()) {
         if (clientCert == null) {
            if (this.log.isDebugEnabled()) {
               this.log.debug(method + "certificate from client is null, authentication is failed.");
            }

            return false;
         }

         username = null;
         X509Certificate trustedCert = partner.getTransportLayerClientCert();
         if (!clientCert.equals(trustedCert)) {
            if (this.log.isDebugEnabled()) {
               this.log.debug(method + "certificate from client is invalid, authentication is failed.");
            }

            return false;
         }
      }

      return true;
   }

   private ArtifactResponse buildArtifactResponse(SAMLObject samlObject, String inResponseTo) {
      ArtifactResponse response = SAMLObjectBuilder.buildArtifactResponse();
      Issuer issuer = SAMLObjectBuilder.buildIssuer(this.config.getLocalConfiguration().getEntityID());
      response.setIssuer(issuer);
      response.setInResponseTo(inResponseTo);
      if (response.getExtensions() != null && response.getExtensions().getUnknownXMLObjects() != null) {
         response.getExtensions().getUnknownXMLObjects().add(samlObject);
      } else {
         response.setMessage(samlObject);
      }

      return response;
   }

   private ArtifactResponse buildArtifactResponse(SAML2DetailedException se, String inResponseTo) {
      StatusMessage statusMessage = null;
      String message = se.getMessage();
      if (message != null && message.length() > 0) {
         statusMessage = SAMLObjectBuilder.buildStatusMessage(message);
      }

      Status status = SAMLObjectBuilder.buildStatus((StatusCode)se.getStatus(), (StatusMessage)statusMessage, (StatusDetail)null);
      ArtifactResponse response = SAMLObjectBuilder.buildArtifactResponse();
      Issuer issuer = SAMLObjectBuilder.buildIssuer(this.config.getLocalConfiguration().getEntityID());
      response.setIssuer(issuer);
      response.setInResponseTo(inResponseTo);
      response.setStatus(status);
      return response;
   }
}
