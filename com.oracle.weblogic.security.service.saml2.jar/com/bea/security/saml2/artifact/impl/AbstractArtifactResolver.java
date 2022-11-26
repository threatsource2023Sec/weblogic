package com.bea.security.saml2.artifact.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.artifact.ArtifactResolver;
import com.bea.security.saml2.artifact.SAMLArtifact;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.BindingHandlerFactory;
import com.bea.security.saml2.binding.SynchronousBindingClient;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.Partner;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.registry.PartnerManager;
import com.bea.security.saml2.registry.PartnerManagerException;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.SAMLObjectBuilder;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.w3c.dom.Element;
import weblogic.utils.Hex;

public abstract class AbstractArtifactResolver implements ArtifactResolver {
   protected LoggerSpi log = null;
   protected boolean logdebug = false;
   private PartnerManager pm = null;
   private SynchronousBindingClient binding = null;
   private BindingHandlerFactory bhFactory = null;
   private WebSSOPartner remotePartner = null;
   private IndexedEndpoint remoteEndpoint = null;
   private HttpURLConnection connection = null;
   protected String sslClientKeyAlias = null;
   protected PrivateKey sslClientKey = null;
   protected Certificate[] sslClientCert = null;
   private static final int CONNECT_TIMEOUT = 300000;
   private static final int READ_TIMEOUT = 300000;
   SingleSignOnServicesConfigSpi localConfig = null;
   SAML2KeyManager keyManager = null;
   LegacyEncryptorSpi encryptor = null;
   TransformerFactory transformerFactory = null;

   public AbstractArtifactResolver(SAML2ConfigSpi config) {
      this.bhFactory = config.getBindingHandlerFactory();
      this.log = config.getLogger();
      if (this.log != null && this.log.isDebugEnabled()) {
         this.logdebug = true;
      }

      this.pm = config.getPartnerManager();
      this.localConfig = config.getLocalConfiguration();
      this.keyManager = config.getSAML2KeyManager();
      this.encryptor = config.getEncryptSpi();
      this.getSSLClientKeyCert(config);
      this.transformerFactory = TransformerFactory.newInstance();
   }

   private PrivateKey getArtifactSignKey(SingleSignOnServicesConfigSpi localConfig) throws BindingHandlerException {
      if (this.keyManager != null && this.keyManager.getSSOKeyInfo() != null) {
         PrivateKey result = this.keyManager.getSSOKeyInfo().getKey();
         if (result == null) {
            throw new BindingHandlerException(Saml2Logger.getSAML2NoSignKeyFor("<samlp:ArtifactResolve>"), 404);
         } else {
            if (this.logdebug) {
               this.log.debug("got samlp:ArtifactResolve signing key:" + result);
            }

            return result;
         }
      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2NoSignKeyFor("<samlp:ArtifactResolve>"), 404);
      }
   }

   private Certificate getArtifactSignCert(SingleSignOnServicesConfigSpi localConfig) throws BindingHandlerException {
      if (this.keyManager != null && this.keyManager.getSSOKeyInfo() != null) {
         return this.keyManager.getSSOKeyInfo().getCert();
      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2NoSignKeyFor("<samlp:ArtifactResolve>"), 404);
      }
   }

   private void getSSLClientKeyCert(SAML2ConfigSpi config) {
      SAML2KeyManager.KeyInfo keyinfo = config.getSAML2KeyManager().getSSLKeyInfo();
      if (keyinfo == null) {
         if (this.logdebug) {
            this.log.debug("There is no ssl client key and certificate.");
         }

      } else {
         this.sslClientKey = keyinfo.getKey();
         this.sslClientCert = keyinfo.getChain();
         this.sslClientKeyAlias = this.localConfig.getTransportLayerSecurityKeyAlias();
         if (this.logdebug) {
            this.log.debug("ssl client key:" + this.sslClientKey + ", ssl client cert chain:" + this.sslClientCert);
         }

      }
   }

   protected String getBasicAuthn(WebSSOPartner partner) {
      boolean isClientPasswordSet = partner.isClientPasswordSet();
      if (this.logdebug) {
         this.log.debug("isClientPasswordSet:" + isClientPasswordSet);
      }

      if (!isClientPasswordSet) {
         return null;
      } else {
         String clientname = partner.getClientUsername();
         String clientpwd = partner.getClientPasswordEncrypted();
         if (clientpwd != null) {
            try {
               clientpwd = this.encryptor.decryptString(clientpwd);
            } catch (Exception var6) {
               this.log.warn(Saml2Logger.getDecryptPasswordError(clientpwd));
               clientpwd = null;
            }
         }

         if (this.logdebug) {
            this.log.debug("basic authentication: name :" + clientname);
         }

         return clientname + ":" + clientpwd;
      }
   }

   public SAMLObject resolve(int msgType, String base64Art) throws BindingHandlerException {
      SAMLObject result = null;
      if (base64Art != null && !base64Art.equals("")) {
         SAMLArtifact artifact = null;

         try {
            artifact = new SAMLArtifact(SAML2Utils.base64Decode(base64Art));
         } catch (IOException var10) {
            if (this.logdebug) {
               this.log.debug("can't create SAMLArtifact from BASE64 encoded artifact:" + base64Art);
            }

            throw new BindingHandlerException(var10.getMessage(), 400);
         }

         byte[] sidHashValue = artifact.getSourceId();
         short epIndex = artifact.getEndPointIndex();
         this.remotePartner = this.getRemotePartner(msgType, sidHashValue);
         if (!this.remotePartner.isEnabled()) {
            throw new BindingHandlerException(Saml2Logger.getPartnerIsNotEnabledInRegistry(this.remotePartner.getName()), 404);
         } else {
            this.remoteEndpoint = this.getRemoteARSEndpoint(this.remotePartner, epIndex);
            ArtifactResolve artReso = this.genArtResolve(base64Art, this.remotePartner);
            this.logSamlMsg(artReso);
            this.connection = this.openConnection(this.remotePartner, this.remoteEndpoint);
            this.binding = this.bhFactory.newSyncBindingClient("SOAP", this.connection);
            if (this.binding == null) {
               throw new BindingHandlerException(Saml2Logger.getSAML2CouldNotGetBindingHandler("SynchronousBindingClient"), 500);
            } else {
               SAMLObject samlObj = this.binding.sendAndReceive(artReso);
               this.closeConnection(this.connection);
               if (samlObj != null && samlObj instanceof ArtifactResponse) {
                  ArtifactResponse artResp = (ArtifactResponse)samlObj;
                  this.logSamlMsg(artResp);
                  result = this.getSamlMsg(artResp, artReso);
                  return result;
               } else {
                  throw new BindingHandlerException(Saml2Logger.getSAML2CouldNotGetSamlResponse("<samlp:ArtifactResponse>"), 403);
               }
            }
         }
      } else {
         throw new BindingHandlerException(Saml2Logger.getSAML2ArtifactIsNull("SAMLart"), 400);
      }
   }

   public abstract HttpURLConnection openConnection(WebSSOPartner var1, IndexedEndpoint var2) throws BindingHandlerException;

   private void closeConnection(HttpURLConnection conn) {
      if (conn != null) {
         conn.disconnect();
         if (this.logdebug) {
            this.log.debug("http url connection disconnect.");
         }
      }

   }

   private ArtifactResolve genArtResolve(String base64Art, WebSSOPartner remotepart) throws BindingHandlerException {
      Artifact art = SAMLObjectBuilder.buildArtifact(base64Art);
      Issuer issuer = SAMLObjectBuilder.buildIssuer(this.localConfig.getEntityID());
      ArtifactResolve artResolve = SAMLObjectBuilder.buildArtifactResolve(SAML2Utils.getXMLSafeSecureUUID(), new DateTime(), issuer, art);
      if (remotepart.isWantArtifactRequestSigned()) {
         PrivateKey signKey = this.getArtifactSignKey(this.localConfig);
         Certificate cert = this.getArtifactSignCert(this.localConfig);
         String ssoKeyAlias = this.localConfig.getSSOSigningKeyAlias();
         if (cert != null) {
            X509Certificate x509cert = (X509Certificate)cert;

            try {
               x509cert.checkValidity();
            } catch (CertificateExpiredException var12) {
               if (this.log != null && this.log.isDebugEnabled()) {
                  this.log.debug("Using expired certificate at alias " + ssoKeyAlias + " for signing.");
               }

               if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                  throw new BindingHandlerException(Saml2Logger.getSignWithExpiredCert(ssoKeyAlias), var12, 500);
               }
            } catch (CertificateNotYetValidException var13) {
               if (this.log != null && this.log.isDebugEnabled()) {
                  this.log.debug("Using not yet valid certificate at alias " + ssoKeyAlias + " for signing.");
               }

               if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                  throw new BindingHandlerException(Saml2Logger.getSignWithNotYetValidCert(ssoKeyAlias), var13, 500);
               }
            }
         }

         try {
            List certs = new ArrayList();
            certs.add(cert);
            artResolve = (ArtifactResolve)SAML2Utils.signSamlObject(signKey, artResolve, certs);
         } catch (SignatureException | MarshallingException var11) {
            throw new BindingHandlerException("MarshallingException", var11, 500);
         }
      }

      return artResolve;
   }

   private SAMLObject getSamlMsg(ArtifactResponse artResponse, ArtifactResolve artResolve) throws BindingHandlerException {
      SAMLObject result = null;
      if (this.logdebug) {
         this.log.debug("get samlp:ArtifactResponse and verify it.");
      }

      SAMLVersion respVer = artResponse.getVersion();
      if (this.logdebug) {
         this.log.debug("saml version:" + respVer.toString());
      }

      if (!respVer.toString().equals(SAMLVersion.VERSION_20.toString())) {
         throw new BindingHandlerException(Saml2Logger.getSAML2SamlResponseError("Version", SAMLVersion.VERSION_20.toString(), respVer.toString()), 403);
      } else {
         String inResponseTo = artResponse.getInResponseTo();
         if (this.logdebug) {
            this.log.debug("inResponseTo:" + inResponseTo);
         }

         if (inResponseTo != null && !inResponseTo.equals("")) {
            String reqid = artResolve.getID();
            if (!inResponseTo.equals(reqid)) {
               throw new BindingHandlerException(Saml2Logger.getSAML2SamlResponseError("InResponseTo", reqid, inResponseTo), 403);
            }
         }

         Status respStatus = artResponse.getStatus();
         StatusCode respCode = respStatus != null ? respStatus.getStatusCode() : null;
         StatusMessage respMsg = respStatus != null ? respStatus.getStatusMessage() : null;
         if (this.logdebug) {
            this.log.debug("status code: " + (respCode != null ? respCode.getValue() : null));
            this.log.debug("status message: " + (respMsg != null ? respMsg.getMessage() : null));
         }

         if (respCode != null && !respCode.getValue().equals("urn:oasis:names:tc:SAML:2.0:status:Success")) {
            String msg = respMsg != null ? respMsg.getMessage() : null;
            throw new BindingHandlerException(msg, 403);
         } else {
            artResponse.getDOM().setIdAttributeNS((String)null, "ID", true);
            Signature sig = artResponse.getSignature();
            if (sig != null) {
               if (this.logdebug) {
                  this.log.debug("<samlp:ArtifactResponse> is signed.");
               }

               try {
                  PublicKey verifyKey = SAML2Utils.getVerifyKey(this.remotePartner);
                  SAML2Utils.verifySamlObjectSignature(verifyKey, sig);
               } catch (SignatureException var11) {
                  this.log.error(var11.getMessage(), var11);
                  throw new BindingHandlerException(Saml2Logger.getSAML2VerifySignatureFail(), 403);
               } catch (CertificateException var12) {
                  this.log.error(var12.getMessage(), var12);
                  throw new BindingHandlerException(Saml2Logger.getNoVerifyingCert("<samlp:ArtifactResponse>", this.remotePartner.getName()), 403);
               } catch (KeyException var13) {
                  this.log.error(var13.getMessage(), var13);
                  throw new BindingHandlerException(Saml2Logger.getSAML2NoVerifyKeyFor("<samlp:ArtifactResponse>"), 403);
               }
            }

            if (artResponse.getExtensions() != null) {
               result = (SAMLObject)artResponse.getExtensions().getUnknownXMLObjects().get(0);
            } else {
               result = artResponse.getMessage();
            }

            if (result == null) {
               throw new BindingHandlerException(Saml2Logger.getSAML2SamlMessageIsNull(), 403);
            } else {
               if (this.logdebug) {
                  this.log.debug("get saml message " + result + " from samlp:ArtifactResponse");
               }

               return result;
            }
         }
      }
   }

   private WebSSOPartner getRemotePartner(int msgType, byte[] remoteSid) throws BindingHandlerException {
      if (this.logdebug) {
         this.log.debug("ArtifactResolver: sha-1 hash value of remote partner id is '" + Hex.asHex(remoteSid) + "'");
      }

      WebSSOPartner result = null;
      Collection partnerList = new HashSet();

      try {
         if (msgType == 1) {
            partnerList.addAll(this.pm.listSPPartners("*", 0));
         } else {
            partnerList.addAll(this.pm.listIdPPartners("*", 0));
         }
      } catch (PartnerManagerException var9) {
         throw new BindingHandlerException(var9.getMessage(), 500);
      }

      Iterator var5 = partnerList.iterator();

      while(var5.hasNext()) {
         Partner partner = (Partner)var5.next();
         if (partner instanceof WebSSOPartner) {
            WebSSOPartner ssopartner = (WebSSOPartner)partner;

            try {
               byte[] id = SAML2Utils.sha1Hash(ssopartner.getEntityID());
               if (Arrays.equals(id, remoteSid)) {
                  if (this.logdebug) {
                     this.log.debug("ArtifactResolver: found remote partner '" + ssopartner.getName() + "' with entity ID '" + ssopartner.getEntityID() + "'");
                  }

                  if (ssopartner.isEnabled()) {
                     result = ssopartner;
                  } else if (this.logdebug) {
                     this.log.debug("ArtifactResolver: remote partner is disabled, resolution failed");
                  }
                  break;
               }
            } catch (Exception var10) {
               throw new BindingHandlerException(var10.getMessage(), 500);
            }
         }
      }

      if (result == null) {
         throw new BindingHandlerException(Saml2Logger.getSAML2CouldNotGetPartner(Hex.asHex(remoteSid)), 404);
      } else {
         if (this.logdebug) {
            this.log.debug("ArtifactResolver: returning partner: " + result);
         }

         return result;
      }
   }

   private IndexedEndpoint getRemoteARSEndpoint(WebSSOPartner remotepart, short epIndex) throws BindingHandlerException {
      if (this.logdebug) {
         this.log.debug("partner entityid is" + remotepart.getEntityID() + ", end point index is:" + epIndex);
      }

      IndexedEndpoint result = null;
      String errmsg = Saml2Logger.getSAML2CouldNotGetEndpoint(remotepart.getEntityID(), "ARTIFACTRESOLUTIONSERVICE", (new Short(epIndex)).toString());
      IndexedEndpoint[] eplist = remotepart.getArtifactResolutionService();
      if (eplist != null && eplist.length != 0) {
         for(int i = 0; i < eplist.length; ++i) {
            IndexedEndpoint endpoint = eplist[i];
            if (endpoint.getIndex() == epIndex) {
               result = endpoint;
               break;
            }
         }

         if (result == null) {
            throw new BindingHandlerException(errmsg, 404);
         } else {
            if (this.logdebug) {
               this.log.debug("find end point:" + result + ", binding location is:" + result.getLocation());
            }

            return result;
         }
      } else {
         throw new BindingHandlerException(errmsg, 404);
      }
   }

   private void logSamlMsg(XMLObject obj) {
      try {
         Marshaller marshaller = XMLObjectSupport.getMarshaller(obj);
         Element ele = marshaller.marshall(obj);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         Transformer trans = this.transformerFactory.newTransformer();
         trans.transform(new DOMSource(ele), new StreamResult(out));
         if (this.logdebug) {
            this.log.debug(new String(out.toByteArray()));
         }

         out.close();
      } catch (Exception var6) {
         if (this.logdebug) {
            this.log.debug("can't print out samlp:ArtifactResolve or samlp:ArtifactResponse.");
         }
      }

   }
}
