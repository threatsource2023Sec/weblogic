package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.IdPPartner;
import com.bea.common.security.store.data.Partner;
import com.bea.common.security.store.data.SPPartner;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.EndpointImpl;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.IndexedEndpointImpl;
import com.bea.security.saml2.providers.registry.WSSIdPPartner;
import com.bea.security.saml2.providers.registry.WSSIdPPartnerImpl;
import com.bea.security.saml2.providers.registry.WSSSPPartner;
import com.bea.security.saml2.providers.registry.WSSSPPartnerImpl;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartnerImpl;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartnerImpl;
import com.bea.security.saml2.util.SAML2Utils;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SAML2ObjectConverter {
   public static IdPPartner convertToJDOIdPPartner(com.bea.security.saml2.providers.registry.IdPPartner idpPartner, String domain, String realm, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      return convertToJDOIdPPartner(idpPartner, domain, realm, encryptSpi, (LoggerSpi)null, false);
   }

   public static IdPPartner convertToJDOIdPPartner(com.bea.security.saml2.providers.registry.IdPPartner idpPartner, String domain, String realm, LegacyEncryptorSpi encryptSpi, LoggerSpi log, boolean filterEndpoint) throws PartnerManagerException {
      if (idpPartner == null) {
         return null;
      } else if (idpPartner instanceof WebSSOIdPPartner) {
         return webSSOIdP2JDOIdP((WebSSOIdPPartner)idpPartner, domain, realm, encryptSpi, log, filterEndpoint);
      } else {
         return idpPartner instanceof WSSIdPPartner ? wssIdP2JDOIdP((WSSIdPPartner)idpPartner, domain, realm) : null;
      }
   }

   public static ArrayList convertToJDOIdPPartners(com.bea.security.saml2.providers.registry.IdPPartner[] idpPartners, String domain, String realm, LegacyEncryptorSpi encryptSpi) {
      ArrayList list = new ArrayList();
      if (idpPartners == null) {
         return list;
      } else {
         for(int i = 0; i < idpPartners.length; ++i) {
            com.bea.security.saml2.providers.registry.IdPPartner idpPartner = idpPartners[i];

            try {
               list.add(convertToJDOIdPPartner(idpPartner, domain, realm, encryptSpi));
            } catch (PartnerManagerException var8) {
            }
         }

         return list;
      }
   }

   private static IdPPartner wssIdP2JDOIdP(WSSIdPPartner idpPartner, String domain, String realm) throws PartnerManagerException {
      IdPPartner jdoIDP = new IdPPartner();
      jdoIDP.setDomainName(domain);
      jdoIDP.setRealmName(realm);
      setPartnerAttrs(idpPartner, jdoIDP);
      setIdPPartnerAttrs(idpPartner, jdoIDP);

      try {
         jdoIDP.setSigningCertObject(idpPartner.getAssertionSigningCert());
      } catch (CertificateException var5) {
         throw new PartnerManagerException(var5);
      }

      jdoIDP.setConfirmationMethod(idpPartner.getConfirmationMethod());
      jdoIDP.setPartnerType("WSSIDPPARTNER");
      jdoIDP.setWantAssertionSigned(idpPartner.isWantAssertionsSigned());
      return jdoIDP;
   }

   private static IdPPartner webSSOIdP2JDOIdP(WebSSOIdPPartner idpPartner, String domain, String realm, LegacyEncryptorSpi encryptSpi, LoggerSpi log, boolean filterEndpoint) throws PartnerManagerException {
      IdPPartner jdoIDP = new IdPPartner();
      jdoIDP.setDomainName(domain);
      jdoIDP.setRealmName(realm);
      setPartnerAttrs(idpPartner, jdoIDP);
      setWebSSOPartnerAttrs(idpPartner, jdoIDP, encryptSpi);
      setIdPPartnerAttrs(idpPartner, jdoIDP);

      try {
         jdoIDP.setSigningCertObject(idpPartner.getSSOSigningCert());
      } catch (CertificateException var15) {
         throw new PartnerManagerException(var15);
      }

      jdoIDP.setRedirectURIs(Utils.arrayToCollection(idpPartner.getRedirectURIs()));
      jdoIDP.setWantAssertionSigned(idpPartner.isWantAssertionsSigned());
      jdoIDP.setWantAuthnRequestsSigned(idpPartner.isWantAuthnRequestsSigned());
      ArrayList services = new ArrayList();
      IndexedEndpoint[] indexedEndpoints = idpPartner.getArtifactResolutionService();
      if (indexedEndpoints != null && indexedEndpoints.length != 0) {
         for(int i = 0; i < idpPartner.getArtifactResolutionService().length; ++i) {
            IndexedEndpoint indexedEndpoint = indexedEndpoints[i];
            String bindingType = indexedEndpoint.getBinding();
            if (filterEndpoint && !SAML2Utils.validateEndpointBinding(bindingType)) {
               Saml2Logger.logUnsupportedBindingType(log, bindingType);
            } else {
               services.add(convertToJDOEndpoint(indexedEndpoint, domain, realm, "ARTIFACTRESOLUTIONSERVICE", idpPartner.getName()));
            }
         }
      }

      Endpoint[] endpoints = idpPartner.getSingleSignOnService();
      if (endpoints != null && endpoints.length != 0) {
         List ssoServices = new ArrayList();

         int i;
         for(i = 0; i < endpoints.length; ++i) {
            Endpoint endpoint = endpoints[i];
            String bindingType = endpoint.getBinding();
            if (filterEndpoint && !SAML2Utils.validateEndpointBinding(bindingType)) {
               Saml2Logger.logUnsupportedBindingType(log, bindingType);
            } else {
               com.bea.common.security.store.data.Endpoint jdoEndpoint = convertToJDOEndpoint(endpoint, domain, realm, "WEBSSOSERVICE", idpPartner.getName());
               ssoServices.add(jdoEndpoint);
            }
         }

         if (ssoServices.size() == 0) {
            throw new PartnerManagerException(Saml2Logger.getEmptySingleSignService());
         }

         for(i = 0; i < ssoServices.size(); ++i) {
            ((com.bea.common.security.store.data.Endpoint)ssoServices.get(i)).setIndex(i);
         }

         services.addAll(ssoServices);
      }

      jdoIDP.setServices(services);
      jdoIDP.setPartnerType("WEBSSOIDPPARTNER");
      return jdoIDP;
   }

   private static void setIdPPartnerAttrs(com.bea.security.saml2.providers.registry.IdPPartner idpPartner, IdPPartner jdoIDP) throws PartnerManagerException {
      jdoIDP.setIdentityProviderNameMapperClassname(idpPartner.getIdentityProviderNameMapperClassname());
      jdoIDP.setIssuerURI(idpPartner.getIssuerURI());
      jdoIDP.setProcessAttributes(idpPartner.isProcessAttributes());
      jdoIDP.setVirtualUserEnabled(idpPartner.isVirtualUserEnabled());
   }

   private static void setWebSSOPartnerAttrs(WebSSOPartner partner, Partner jdoPartner, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      jdoPartner.setContactPersonCompany(partner.getContactPersonCompany());
      jdoPartner.setContactPersonEmailAddress(partner.getContactPersonEmailAddress());
      jdoPartner.setContactPersonGivenName(partner.getContactPersonGivenName());
      jdoPartner.setContactPersonSurName(partner.getContactPersonSurName());
      jdoPartner.setContactPersonTelephoneNumber(partner.getContactPersonTelephoneNumber());
      jdoPartner.setContactPersonType(partner.getContactPersonType());
      jdoPartner.setEntityId(partner.getEntityID());
      jdoPartner.setErrorURL(partner.getErrorURL());
      jdoPartner.setOrganizationName(partner.getOrganizationName());
      jdoPartner.setOrganizationURL(partner.getOrganizationURL());
      String clientPassword = null;
      String clientPasswordEncrypted = null;
      boolean plainPasswordChanged = false;
      if (partner instanceof WebSSOSPPartnerImpl) {
         clientPassword = ((WebSSOSPPartnerImpl)partner).getClientPassword();
         clientPasswordEncrypted = ((WebSSOSPPartnerImpl)partner).getClientPasswordEncrypted();
         plainPasswordChanged = ((WebSSOSPPartnerImpl)partner).isPlainPasswordChanged();
      } else if (partner instanceof WebSSOIdPPartnerImpl) {
         clientPassword = ((WebSSOIdPPartnerImpl)partner).getClientPassword();
         clientPasswordEncrypted = ((WebSSOIdPPartnerImpl)partner).getClientPasswordEncrypted();
         plainPasswordChanged = ((WebSSOIdPPartnerImpl)partner).isPlainPasswordChanged();
      }

      if (plainPasswordChanged) {
         if (clientPassword != null && clientPassword.trim().length() > 0) {
            try {
               jdoPartner.setClientPasswordEncrypt(encryptSpi.encryptString(clientPassword));
            } catch (Exception var10) {
               throw new PartnerManagerException(var10);
            }
         } else {
            jdoPartner.setClientPasswordEncrypt((String)null);
         }
      } else if (clientPasswordEncrypted != null && clientPasswordEncrypted.trim().length() > 0) {
         try {
            encryptSpi.decryptString(clientPasswordEncrypted);
         } catch (Exception var9) {
            throw new PartnerManagerException(ProvidersLogger.getCouldNotDecryptPassword(clientPasswordEncrypted));
         }

         jdoPartner.setClientPasswordEncrypt(clientPasswordEncrypted);
      } else if (clientPassword != null && clientPassword.trim().length() > 0) {
         try {
            jdoPartner.setClientPasswordEncrypt(encryptSpi.encryptString(clientPassword));
         } catch (Exception var8) {
            throw new PartnerManagerException(var8);
         }
      } else {
         jdoPartner.setClientPasswordEncrypt((String)null);
      }

      jdoPartner.setClientUserName(partner.getClientUsername());
      jdoPartner.setClientPasswordSet(partner.isClientPasswordSet());
      jdoPartner.setArtifactBindingPOSTForm(partner.getArtifactBindingPostForm());
      jdoPartner.setPOSTBindingPOSTForm(partner.getPostBindingPostForm());
      jdoPartner.setWantArtifactRequestSigned(partner.isWantArtifactRequestSigned());

      try {
         jdoPartner.setTransportLayerClientCertObject(partner.getTransportLayerClientCert());
      } catch (CertificateException var7) {
         throw new PartnerManagerException(var7);
      }

      jdoPartner.setArtifactBindingUsePOST(partner.isArtifactBindingUsePOSTMethod());
   }

   private static void setPartnerAttrs(com.bea.security.saml2.providers.registry.Partner partner, Partner jdoPartner) {
      jdoPartner.setAudienceURIs(Utils.arrayToCollection(partner.getAudienceURIs()));
      jdoPartner.setDescription(partner.getDescription());
      jdoPartner.setName(partner.getName());
      jdoPartner.setEnabled(partner.isEnabled());
   }

   public static SPPartner convertToJDOSPPartner(com.bea.security.saml2.providers.registry.SPPartner partner, String domain, String realm, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      return convertToJDOSPPartner(partner, domain, realm, encryptSpi, (LoggerSpi)null, false);
   }

   public static SPPartner convertToJDOSPPartner(com.bea.security.saml2.providers.registry.SPPartner partner, String domain, String realm, LegacyEncryptorSpi encryptSpi, LoggerSpi log, boolean filterEndpoint) throws PartnerManagerException {
      if (partner == null) {
         return null;
      } else if (partner instanceof WebSSOSPPartner) {
         return webSSOSP2JDOSP((WebSSOSPPartner)partner, domain, realm, encryptSpi, log, filterEndpoint);
      } else {
         return partner instanceof WSSSPPartner ? wssSP2JDOSP((WSSSPPartner)partner, domain, realm) : null;
      }
   }

   public static ArrayList convertToJDOSPPartners(com.bea.security.saml2.providers.registry.SPPartner[] partners, String domain, String realm, LegacyEncryptorSpi encryptSpi) {
      ArrayList list = new ArrayList();
      if (partners == null) {
         return list;
      } else {
         for(int i = 0; i < partners.length; ++i) {
            com.bea.security.saml2.providers.registry.SPPartner partner = partners[i];

            try {
               list.add(convertToJDOSPPartner(partner, domain, realm, encryptSpi));
            } catch (PartnerManagerException var8) {
            }
         }

         return list;
      }
   }

   private static SPPartner wssSP2JDOSP(WSSSPPartner partner, String domain, String realm) {
      SPPartner jdoSP = new SPPartner();
      jdoSP.setDomainName(domain);
      jdoSP.setRealmName(realm);
      setPartnerAttrs(partner, jdoSP);
      setSPPartnerAttrs(partner, jdoSP);
      jdoSP.setConfirmationMethod(partner.getConfirmationMethod());
      jdoSP.setPartnerType("WSSSPPARTNER");
      jdoSP.setWantAssertionSigned(partner.isWantAssertionsSigned());
      return jdoSP;
   }

   private static void setSPPartnerAttrs(com.bea.security.saml2.providers.registry.SPPartner partner, SPPartner jdoSP) {
      jdoSP.setServicePartnerNameMapperClassName(partner.getServiceProviderNameMapperClassname());
      jdoSP.setTimeToLive(partner.getTimeToLive());
      jdoSP.setTimeToLiveOffset(partner.getTimeToLiveOffset());
      jdoSP.setGenerateAttributes(partner.isGenerateAttributes());
      jdoSP.setIncludeOneTimeUseCondition(partner.isIncludeOneTimeUseCondition());
      jdoSP.setKeyinfoInclude(partner.isKeyinfoIncluded());
      jdoSP.setWantAssertionSigned(partner.isWantAssertionsSigned());
   }

   private static SPPartner webSSOSP2JDOSP(WebSSOSPPartner partner, String domain, String realm, LegacyEncryptorSpi encryptSpi, LoggerSpi log, boolean filterEndpoint) throws PartnerManagerException {
      SPPartner jdoSP = new SPPartner();
      jdoSP.setDomainName(domain);
      jdoSP.setRealmName(realm);
      setPartnerAttrs(partner, jdoSP);
      setSPPartnerAttrs(partner, jdoSP);
      setWebSSOPartnerAttrs(partner, jdoSP, encryptSpi);

      try {
         jdoSP.setSigningCertObject(partner.getSSOSigningCert());
         jdoSP.setEncryptionCertObject(partner.getAssertionEncryptionCert());
         jdoSP.setEncryptionAlgorithms(partner.getEncryptionAlgorithms());
      } catch (CertificateException var13) {
         throw new PartnerManagerException(var13);
      }

      jdoSP.setWantAssertionSigned(partner.isWantAssertionsSigned());
      jdoSP.setWantAuthnRequestsSigned(partner.isWantAuthnRequestsSigned());
      ArrayList services = new ArrayList();
      IndexedEndpoint[] indexedEndpoints = partner.getArtifactResolutionService();
      if (indexedEndpoints != null && indexedEndpoints.length != 0) {
         for(int i = 0; i < partner.getArtifactResolutionService().length; ++i) {
            IndexedEndpoint indexedEndpoint = indexedEndpoints[i];
            String bindingType = indexedEndpoint.getBinding();
            if (filterEndpoint && !SAML2Utils.validateEndpointBinding(bindingType)) {
               Saml2Logger.logUnsupportedBindingType(log, bindingType);
            } else {
               services.add(convertToJDOEndpoint(indexedEndpoint, domain, realm, "ARTIFACTRESOLUTIONSERVICE", partner.getName()));
            }
         }
      }

      indexedEndpoints = partner.getAssertionConsumerService();
      if (indexedEndpoints != null && indexedEndpoints.length != 0) {
         List acServices = new ArrayList();

         for(int i = 0; i < indexedEndpoints.length; ++i) {
            IndexedEndpoint indexedEndpoint = indexedEndpoints[i];
            String bindingType = indexedEndpoint.getBinding();
            if (filterEndpoint && !SAML2Utils.validateEndpointBinding(bindingType)) {
               Saml2Logger.logUnsupportedBindingType(log, bindingType);
            } else {
               acServices.add(convertToJDOEndpoint(indexedEndpoint, domain, realm, "ASSERTIONCONSUMERSERVICE", partner.getName()));
            }
         }

         if (acServices.size() == 0) {
            throw new PartnerManagerException(Saml2Logger.getEmptyAssertionConsumerServices());
         }

         services.addAll(acServices);
      }

      jdoSP.setServices(services);
      jdoSP.setPartnerType("WEBSSOSPPARTNER");
      return jdoSP;
   }

   public static com.bea.security.saml2.providers.registry.IdPPartner convertToREGIdPPartner(IdPPartner idp, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      if (idp == null) {
         return null;
      } else if (idp.getPartnerType().equals("WEBSSOIDPPARTNER")) {
         return convertTOREGWEBSSOIdPPartner(idp, encryptSpi);
      } else {
         return idp.getPartnerType().equals("WSSIDPPARTNER") ? convertTOREGWSSIdPPartner(idp) : null;
      }
   }

   private static com.bea.security.saml2.providers.registry.IdPPartner convertTOREGWSSIdPPartner(IdPPartner idp) throws PartnerManagerException {
      WSSIdPPartner partner = new WSSIdPPartnerImpl();
      partner.setName(idp.getName());

      try {
         partner.setAssertionSigningCert(idp.getSigningCertObject());
      } catch (CertificateException var3) {
         throw new PartnerManagerException(var3);
      }

      partner.setAudienceURIs(Utils.stringCollectionToArray(idp.getAudienceURIs()));
      partner.setDescription(idp.getDescription());
      partner.setEnabled(idp.isEnabled());
      partner.setIdentityProviderNameMapperClassname(idp.getIdentityProviderNameMapperClassname());
      partner.setIssuerURI(idp.getIssuerURI());
      partner.setWantAssertionsSigned(idp.isWantAssertionSigned());
      partner.setProcessAttributes(idp.isProcessAttributes());
      partner.setVirtualUserEnabled(idp.isVirtualUserEnabled());
      partner.setConfirmationMethod(idp.getConfirmationMethod());
      return partner;
   }

   private static com.bea.security.saml2.providers.registry.IdPPartner convertTOREGWEBSSOIdPPartner(IdPPartner idp, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      WebSSOIdPPartnerImpl partner = new WebSSOIdPPartnerImpl();
      partner.setName(idp.getName());
      if (idp.getArtifactResolutionServices() != null && idp.getArtifactResolutionServices().length != 0) {
         IndexedEndpoint[] artifactResolutionService = new IndexedEndpoint[idp.getArtifactResolutionServices().length];

         for(int i = 0; i < artifactResolutionService.length; ++i) {
            artifactResolutionService[i] = convertToREGEndpoint(idp.getArtifactResolutionServices()[i]);
         }

         partner.setArtifactResolutionService(artifactResolutionService);
      }

      partner.setAudienceURIs(Utils.stringCollectionToArray(idp.getAudienceURIs()));
      if (idp.getClientPasswordEncrypt() != null) {
         try {
            partner.setClientPassword(encryptSpi.decryptString(idp.getClientPasswordEncrypt()));
            partner.setClientPasswordEncrypted(idp.getClientPasswordEncrypt());
         } catch (Exception var7) {
            throw new PartnerManagerException(var7);
         }
      }

      partner.setClientUsername(idp.getClientUserName());
      partner.setContactPersonCompany(idp.getContactPersonCompany());
      partner.setContactPersonEmailAddress(idp.getContactPersonEmailAddress());
      partner.setContactPersonSurName(idp.getContactPersonSurName());
      partner.setContactPersonTelephoneNumber(idp.getContactPersonTelephoneNumber());
      partner.setContactPersonType(idp.getContactPersonType());
      partner.setContactPersonGivenName(idp.getContactPersonGivenName());
      partner.setDescription(idp.getDescription());
      partner.setEnabled(idp.isEnabled());
      partner.setEntityID(idp.getEntityId());
      partner.setErrorURL(idp.getErrorURL());
      partner.setWantAssertionsSigned(idp.isWantAssertionSigned());
      partner.setWantAuthnRequestsSigned(idp.isWantAuthnRequestsSigned());
      partner.setIdentityProviderNameMapperClassname(idp.getIdentityProviderNameMapperClassname());
      partner.setIssuerURI(idp.getIssuerURI());
      partner.setOrganizationName(idp.getOrganizationName());
      partner.setOrganizationURL(idp.getOrganizationURL());
      partner.setProcessAttributes(idp.isProcessAttributes());
      partner.setRedirectURIs(Utils.stringCollectionToArray(idp.getRedirectURIs()));
      if (idp.getSingleSignOnServices() != null && idp.getSingleSignOnServices().length != 0) {
         com.bea.common.security.store.data.Endpoint[] jdoEndpoints = idp.getSingleSignOnServices();
         Arrays.sort(jdoEndpoints, new Comparator() {
            public int compare(com.bea.common.security.store.data.Endpoint endpoint1, com.bea.common.security.store.data.Endpoint endpoint2) {
               if (endpoint1.getIndex() < endpoint2.getIndex()) {
                  return -1;
               } else {
                  return endpoint1.getIndex() > endpoint2.getIndex() ? 1 : 0;
               }
            }
         });
         Endpoint[] ssoService = new Endpoint[jdoEndpoints.length];

         for(int i = 0; i < ssoService.length; ++i) {
            ssoService[i] = convertToREGBinding(jdoEndpoints[i]);
         }

         partner.setSingleSignOnService(ssoService);
      }

      partner.setArtifactBindingPostForm(idp.getArtifactBindingPOSTForm());
      partner.setArtifactBindingUsePOSTMethod(idp.isArtifactBindingUsePOST());
      partner.setWantArtifactRequestSigned(idp.isWantArtifactRequestSigned());
      partner.setPostBindingPostForm(idp.getPOSTBindingPOSTForm());
      partner.setVirtualUserEnabled(idp.isVirtualUserEnabled());

      try {
         partner.setSSOSigningCert(idp.getSigningCertObject());
         partner.setTransportLayerClientCert(idp.getTransportLayerClientCertObject());
         return partner;
      } catch (CertificateException var6) {
         throw new PartnerManagerException(var6);
      }
   }

   public static com.bea.security.saml2.providers.registry.SPPartner convertToREGSPPartner(SPPartner sp, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      if (sp == null) {
         return null;
      } else if (sp.getPartnerType().equals("WEBSSOSPPARTNER")) {
         return convertToREGWEBSSOSPPartner(sp, encryptSpi);
      } else {
         return sp.getPartnerType().equals("WSSSPPARTNER") ? convertToREGWSSSPPartner(sp) : null;
      }
   }

   private static com.bea.security.saml2.providers.registry.SPPartner convertToREGWSSSPPartner(SPPartner sp) {
      WSSSPPartner partner = new WSSSPPartnerImpl();
      partner.setName(sp.getName());
      partner.setAudienceURIs(Utils.stringCollectionToArray(sp.getAudienceURIs()));
      partner.setDescription(sp.getDescription());
      partner.setEnabled(sp.isEnabled());
      partner.setServiceProviderNameMapperClassname(sp.getServicePartnerNameMapperClassName());
      partner.setWantAssertionsSigned(sp.isWantAssertionSigned());
      partner.setGenerateAttributes(sp.isGenerateAttributes());
      partner.setIncludeOneTimeUseCondition(sp.isIncludeOneTimeUseCondition());
      partner.setKeyinfoIncluded(sp.isKeyinfoInclude());
      partner.setTimeToLive(sp.getTimeToLive());
      partner.setTimeToLiveOffset(sp.getTimeToLiveOffset());
      partner.setConfirmationMethod(sp.getConfirmationMethod());
      partner.setServiceProviderNameMapperClassname(sp.getServicePartnerNameMapperClassName());
      return partner;
   }

   private static com.bea.security.saml2.providers.registry.SPPartner convertToREGWEBSSOSPPartner(SPPartner sp, LegacyEncryptorSpi encryptSpi) throws PartnerManagerException {
      WebSSOSPPartnerImpl partner = new WebSSOSPPartnerImpl();
      partner.setName(sp.getName());
      IndexedEndpoint[] assertionConsumerService;
      int i;
      if (sp.getArtifactResolutionServices() != null && sp.getArtifactResolutionServices().length != 0) {
         assertionConsumerService = new IndexedEndpoint[sp.getArtifactResolutionServices().length];

         for(i = 0; i < assertionConsumerService.length; ++i) {
            assertionConsumerService[i] = convertToREGEndpoint(sp.getArtifactResolutionServices()[i]);
         }

         partner.setArtifactResolutionService(assertionConsumerService);
      }

      partner.setAudienceURIs(Utils.stringCollectionToArray(sp.getAudienceURIs()));
      if (sp.getClientPasswordEncrypt() != null) {
         try {
            partner.setClientPassword(encryptSpi.decryptString(sp.getClientPasswordEncrypt()));
            partner.setClientPasswordEncrypted(sp.getClientPasswordEncrypt());
         } catch (Exception var6) {
            throw new PartnerManagerException(var6);
         }
      }

      partner.setClientUsername(sp.getClientUserName());
      partner.setContactPersonCompany(sp.getContactPersonCompany());
      partner.setContactPersonEmailAddress(sp.getContactPersonEmailAddress());
      partner.setContactPersonSurName(sp.getContactPersonSurName());
      partner.setContactPersonTelephoneNumber(sp.getContactPersonTelephoneNumber());
      partner.setContactPersonType(sp.getContactPersonType());
      partner.setContactPersonGivenName(sp.getContactPersonGivenName());
      partner.setDescription(sp.getDescription());
      partner.setEnabled(sp.isEnabled());
      partner.setEntityID(sp.getEntityId());
      partner.setErrorURL(sp.getErrorURL());
      partner.setWantAuthnRequestsSigned(sp.isWantAuthnRequestsSigned());
      partner.setServiceProviderNameMapperClassname(sp.getServicePartnerNameMapperClassName());
      partner.setOrganizationName(sp.getOrganizationName());
      partner.setOrganizationURL(sp.getOrganizationURL());
      partner.setWantAssertionsSigned(sp.isWantAssertionSigned());
      partner.setGenerateAttributes(sp.isGenerateAttributes());
      if (sp.getAssertionConsumerServices() != null && sp.getAssertionConsumerServices().length != 0) {
         assertionConsumerService = new IndexedEndpoint[sp.getAssertionConsumerServices().length];

         for(i = 0; i < assertionConsumerService.length; ++i) {
            assertionConsumerService[i] = convertToREGEndpoint(sp.getAssertionConsumerServices()[i]);
         }

         partner.setAssertionConsumerService(assertionConsumerService);
      }

      partner.setArtifactBindingPostForm(sp.getArtifactBindingPOSTForm());
      partner.setArtifactBindingUsePOSTMethod(sp.isArtifactBindingUsePOST());
      partner.setWantArtifactRequestSigned(sp.isWantArtifactRequestSigned());
      partner.setPostBindingPostForm(sp.getPOSTBindingPOSTForm());
      partner.setIncludeOneTimeUseCondition(sp.isIncludeOneTimeUseCondition());
      partner.setWantAuthnRequestsSigned(sp.isWantAuthnRequestsSigned());
      partner.setKeyinfoIncluded(sp.isKeyinfoInclude());
      partner.setTimeToLive(sp.getTimeToLive());
      partner.setTimeToLiveOffset(sp.getTimeToLiveOffset());
      partner.setServiceProviderNameMapperClassname(sp.getServicePartnerNameMapperClassName());

      try {
         partner.setTransportLayerClientCert(sp.getTransportLayerClientCertObject());
         partner.setSSOSigningCert(sp.getSigningCertObject());
         partner.setAssertionEncryptionCert(sp.getEncryptionCertObject());
         partner.setEncryptionAlgorithms(sp.getEncryptionAlgorithms());
         return partner;
      } catch (CertificateException var5) {
         throw new PartnerManagerException(var5);
      }
   }

   private static void setBindInfo(com.bea.common.security.store.data.Endpoint endpoint, Endpoint e) {
      endpoint.setBindingLocation(e.getLocation());
      endpoint.setBindingType(e.getBinding());
   }

   public static com.bea.common.security.store.data.Endpoint convertToJDOEndpoint(Endpoint regEndpoint, String domain, String realm, String serviceType, String partnerName) {
      if (regEndpoint == null) {
         return null;
      } else {
         com.bea.common.security.store.data.Endpoint endpoint = new com.bea.common.security.store.data.Endpoint();
         endpoint.setDomainName(domain);
         endpoint.setRealmName(realm);
         endpoint.setServiceType(serviceType);
         endpoint.setPartnerName(partnerName);
         setBindInfo(endpoint, regEndpoint);
         if (regEndpoint instanceof IndexedEndpoint) {
            IndexedEndpoint tmp = (IndexedEndpoint)regEndpoint;
            endpoint.setDefaultEndPoint(tmp.isDefault());
            endpoint.setIndex(tmp.getIndex());
            endpoint.setDefaultSet(tmp.isDefaultSet());
         }

         return endpoint;
      }
   }

   public static IndexedEndpoint convertToREGEndpoint(com.bea.common.security.store.data.Endpoint endpoint) {
      if (endpoint == null) {
         return null;
      } else {
         IndexedEndpoint indexedEndpoint = new IndexedEndpointImpl();
         if (endpoint.isDefaultSet()) {
            indexedEndpoint.setDefault(endpoint.isDefaultEndPoint());
         }

         indexedEndpoint.setIndex(endpoint.getIndex());
         Endpoint e = convertToREGBinding(endpoint);
         indexedEndpoint.setLocation(e.getLocation());
         indexedEndpoint.setBinding(e.getBinding());
         return indexedEndpoint;
      }
   }

   public static Endpoint convertToREGBinding(com.bea.common.security.store.data.Endpoint endpoint) {
      if (endpoint == null) {
         return null;
      } else {
         EndpointImpl e = new EndpointImpl();
         e.setLocation(endpoint.getBindingLocation());
         e.setBinding(endpoint.getBindingType());
         return e;
      }
   }
}
