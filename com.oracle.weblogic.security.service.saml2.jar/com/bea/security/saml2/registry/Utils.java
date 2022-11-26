package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.jdkutils.WeaverUtil.Collections;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.IdPPartner;
import com.bea.common.security.store.data.Partner;
import com.bea.common.security.store.data.SPPartner;
import com.bea.common.security.utils.CommonUtils;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WSSIdPPartner;
import com.bea.security.saml2.providers.registry.WSSSPPartner;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartnerImpl;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartnerImpl;
import com.bea.security.saml2.util.SAML2Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class Utils {
   public static String convertQueryPatternForJDO(String pattern) {
      return convertQueryPatternForJDO(pattern, (StoreService)null);
   }

   public static String convertQueryPatternForJDO(String pattern, StoreService storeService) {
      return CommonUtils.convertLDAPPatternForJDO(pattern, storeService != null && storeService.isMySQLUsed());
   }

   public static Collection arrayToCollection(Object[] args) {
      if (args != null && args.length != 0) {
         ArrayList result = new ArrayList();
         Collections.addAll(result, args);
         return result;
      } else {
         return null;
      }
   }

   public static String[] stringCollectionToArray(Collection args) {
      return args != null && args.size() != 0 ? (String[])((String[])args.toArray(new String[args.size()])) : null;
   }

   public static List setIdPPartner(IdPPartner idp, com.bea.security.saml2.providers.registry.IdPPartner partner, LegacyEncryptorSpi encryptSpi, LoggerSpi log) throws Exception {
      idp.setSigningCertObject(partner instanceof WSSIdPPartner ? ((WSSIdPPartner)partner).getAssertionSigningCert() : ((WebSSOPartner)partner).getSSOSigningCert());
      idp.setAudienceURIs(arrayToCollection(partner.getAudienceURIs()));
      idp.setDescription(partner.getDescription());
      idp.setIdentityProviderNameMapperClassname(partner.getIdentityProviderNameMapperClassname());
      idp.setIssuerURI(partner.getIssuerURI());
      idp.setEnabled(partner.isEnabled());
      idp.setWantAssertionSigned(partner.isWantAssertionsSigned());
      idp.setProcessAttributes(partner.isProcessAttributes());
      idp.setVirtualUserEnabled(partner.isVirtualUserEnabled());
      List result = null;
      if (partner instanceof WebSSOIdPPartnerImpl) {
         WebSSOIdPPartnerImpl ssoPartner = (WebSSOIdPPartnerImpl)partner;
         result = setEndpoints(idp, ssoPartner.getArtifactResolutionService(), "ARTIFACTRESOLUTIONSERVICE", log);
         List tmp = setEndpoints(idp, ssoPartner.getSingleSignOnService(), "WEBSSOSERVICE", log);
         if (tmp != null) {
            if (result != null) {
               result.addAll(tmp);
            } else {
               result = tmp;
            }
         }

         String pwdEncrypted = ssoPartner.getClientPasswordEncrypted();
         String pwdUnencrypted = ssoPartner.getClientPassword();
         boolean plainPasswordChanged = ssoPartner.isPlainPasswordChanged();
         if (plainPasswordChanged) {
            if (pwdUnencrypted != null && pwdUnencrypted.trim().length() > 0) {
               idp.setClientPasswordEncrypt(encryptSpi.encryptString(pwdUnencrypted));
            } else {
               idp.setClientPasswordEncrypt((String)null);
            }
         } else if (pwdEncrypted != null && pwdEncrypted.trim().length() > 0) {
            idp.setClientPasswordEncrypt(pwdEncrypted);
         } else if (pwdUnencrypted != null && pwdUnencrypted.trim().length() > 0) {
            idp.setClientPasswordEncrypt(encryptSpi.encryptString(pwdUnencrypted));
         } else {
            idp.setClientPasswordEncrypt((String)null);
         }

         idp.setClientUserName(ssoPartner.getClientUsername());
         idp.setContactPersonCompany(ssoPartner.getContactPersonCompany());
         idp.setContactPersonEmailAddress(ssoPartner.getContactPersonEmailAddress());
         idp.setContactPersonGivenName(ssoPartner.getContactPersonGivenName());
         idp.setContactPersonSurName(ssoPartner.getContactPersonSurName());
         idp.setContactPersonTelephoneNumber(ssoPartner.getContactPersonTelephoneNumber());
         idp.setContactPersonType(ssoPartner.getContactPersonType());
         idp.setEntityId(ssoPartner.getEntityID());
         idp.setErrorURL(ssoPartner.getErrorURL());
         idp.setIdentityProviderNameMapperClassname(ssoPartner.getIdentityProviderNameMapperClassname());
         idp.setIssuerURI(ssoPartner.getIssuerURI());
         idp.setOrganizationName(ssoPartner.getOrganizationName());
         idp.setOrganizationURL(ssoPartner.getOrganizationURL());
         idp.setRedirectURIs(arrayToCollection(ssoPartner.getRedirectURIs()));
         ssoPartner.getSingleSignOnService();
         idp.setArtifactBindingPOSTForm(ssoPartner.getArtifactBindingPostForm());
         idp.setArtifactBindingUsePOST(ssoPartner.isArtifactBindingUsePOSTMethod());
         idp.setWantArtifactRequestSigned(ssoPartner.isWantArtifactRequestSigned());
         idp.setPOSTBindingPOSTForm(ssoPartner.getPostBindingPostForm());
         idp.setTransportLayerClientCertObject(ssoPartner.getTransportLayerClientCert());
         idp.setClientPasswordSet(ssoPartner.isClientPasswordSet());
         idp.setWantAuthnRequestsSigned(ssoPartner.isWantAuthnRequestsSigned());
      } else if (partner instanceof WSSIdPPartner) {
         WSSIdPPartner wssPartner = (WSSIdPPartner)partner;
         idp.setConfirmationMethod(wssPartner.getConfirmationMethod());
      }

      return result;
   }

   public static List setSPPartner(SPPartner sp, com.bea.security.saml2.providers.registry.SPPartner partner, LegacyEncryptorSpi encryptSpi, LoggerSpi log) throws Exception {
      sp.setAudienceURIs(arrayToCollection(partner.getAudienceURIs()));
      sp.setDescription(partner.getDescription());
      sp.setServicePartnerNameMapperClassName(partner.getServiceProviderNameMapperClassname());
      sp.setTimeToLive(partner.getTimeToLive());
      sp.setTimeToLiveOffset(partner.getTimeToLiveOffset());
      sp.setEnabled(partner.isEnabled());
      sp.setWantAssertionSigned(partner.isWantAssertionsSigned());
      sp.setGenerateAttributes(partner.isGenerateAttributes());
      sp.setIncludeOneTimeUseCondition(partner.isIncludeOneTimeUseCondition());
      sp.setKeyinfoInclude(partner.isKeyinfoIncluded());
      List result = null;
      if (partner instanceof WebSSOSPPartnerImpl) {
         WebSSOSPPartnerImpl ssoPartner = (WebSSOSPPartnerImpl)partner;
         result = setEndpoints(sp, ssoPartner.getArtifactResolutionService(), "ARTIFACTRESOLUTIONSERVICE", log);
         List tmp = setEndpoints(sp, ssoPartner.getAssertionConsumerService(), "ASSERTIONCONSUMERSERVICE", log);
         if (tmp != null) {
            if (result != null) {
               result.addAll(tmp);
            } else {
               result = tmp;
            }
         }

         sp.setArtifactBindingPOSTForm(ssoPartner.getArtifactBindingPostForm());
         sp.setArtifactBindingUsePOST(ssoPartner.isArtifactBindingUsePOSTMethod());
         sp.setWantArtifactRequestSigned(ssoPartner.isWantArtifactRequestSigned());
         sp.setPOSTBindingPOSTForm(ssoPartner.getPostBindingPostForm());
         sp.setSigningCertObject(ssoPartner.getSSOSigningCert());
         sp.setEncryptionCertObject(ssoPartner.getAssertionEncryptionCert());
         sp.setEncryptionAlgorithms(ssoPartner.getEncryptionAlgorithms());
         String pwdEncrypted = ssoPartner.getClientPasswordEncrypted();
         String pwdUnencrypted = ssoPartner.getClientPassword();
         boolean plainPasswordChanged = ssoPartner.isPlainPasswordChanged();
         if (plainPasswordChanged) {
            if (pwdUnencrypted != null && pwdUnencrypted.trim().length() > 0) {
               sp.setClientPasswordEncrypt(encryptSpi.encryptString(pwdUnencrypted));
            } else {
               sp.setClientPasswordEncrypt((String)null);
            }
         } else if (pwdEncrypted != null && pwdEncrypted.trim().length() > 0) {
            sp.setClientPasswordEncrypt(pwdEncrypted);
         } else if (pwdUnencrypted != null && pwdUnencrypted.trim().length() > 0) {
            sp.setClientPasswordEncrypt(encryptSpi.encryptString(pwdUnencrypted));
         } else {
            sp.setClientPasswordEncrypt((String)null);
         }

         sp.setClientUserName(ssoPartner.getClientUsername());
         sp.setContactPersonCompany(ssoPartner.getContactPersonCompany());
         sp.setContactPersonEmailAddress(ssoPartner.getContactPersonEmailAddress());
         sp.setContactPersonGivenName(ssoPartner.getContactPersonGivenName());
         sp.setContactPersonSurName(ssoPartner.getContactPersonSurName());
         sp.setContactPersonTelephoneNumber(ssoPartner.getContactPersonTelephoneNumber());
         sp.setContactPersonType(ssoPartner.getContactPersonType());
         sp.setEntityId(ssoPartner.getEntityID());
         sp.setErrorURL(ssoPartner.getErrorURL());
         sp.setOrganizationName(ssoPartner.getOrganizationName());
         sp.setOrganizationURL(ssoPartner.getOrganizationURL());
         sp.setTransportLayerClientCertObject(ssoPartner.getTransportLayerClientCert());
         sp.setClientPasswordSet(ssoPartner.isClientPasswordSet());
         sp.setWantAuthnRequestsSigned(ssoPartner.isWantAuthnRequestsSigned());
      } else if (partner instanceof WSSSPPartner) {
         WSSSPPartner wssPartner = (WSSSPPartner)partner;
         sp.setConfirmationMethod(wssPartner.getConfirmationMethod());
      }

      return result;
   }

   private static List setEndpoints(Partner partner, Endpoint[] regEndpoints, String serviceType, LoggerSpi log) throws PartnerManagerException {
      if (partner.getServices() == null) {
         if (regEndpoints != null && regEndpoints.length != 0) {
            Collection services = new ArrayList();

            for(int i = 0; i < regEndpoints.length; ++i) {
               if (regEndpoints[i] != null) {
                  if (!SAML2Utils.validateEndpointBinding(regEndpoints[i].getBinding())) {
                     Saml2Logger.logUnsupportedBindingType(log, regEndpoints[i].getBinding());
                  } else {
                     services.add(SAML2ObjectConverter.convertToJDOEndpoint(regEndpoints[i], partner.getDomainName(), partner.getRealmName(), serviceType, partner.getName()));
                  }
               }
            }

            partner.setServices(services);
            return null;
         } else {
            return null;
         }
      } else {
         com.bea.common.security.store.data.Endpoint jdoEndpoint = null;
         List deletedServices = new ArrayList();
         if (regEndpoints != null && regEndpoints.length != 0) {
            com.bea.common.security.store.data.Endpoint[] endpoints = new com.bea.common.security.store.data.Endpoint[regEndpoints.length];
            boolean isARS = "ARTIFACTRESOLUTIONSERVICE".equals(serviceType);

            for(int i = 0; i < regEndpoints.length; ++i) {
               if (!isARS && !SAML2Utils.validateEndpointBinding(regEndpoints[i].getBinding())) {
                  Saml2Logger.logUnsupportedBindingType(log, regEndpoints[i].getBinding());
               } else {
                  endpoints[i] = SAML2ObjectConverter.convertToJDOEndpoint(regEndpoints[i], partner.getDomainName(), partner.getRealmName(), serviceType, partner.getName());
               }
            }

            int i;
            boolean flag;
            if (!isARS) {
               flag = false;

               for(i = 0; i < endpoints.length; ++i) {
                  if (endpoints[i] != null) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  String cause = "ASSERTIONCONSUMERSERVICE".equals(serviceType) ? Saml2Logger.getEmptyAssertionConsumerServices() : Saml2Logger.getEmptySingleSignService();
                  throw new PartnerManagerException(cause);
               }
            }

            flag = false;
            Iterator ite = partner.getServices().iterator();

            while(ite.hasNext()) {
               flag = false;
               jdoEndpoint = (com.bea.common.security.store.data.Endpoint)ite.next();

               for(int i = 0; i < endpoints.length; ++i) {
                  if (endpoints[i] != null && compareEndpoint(endpoints[i], jdoEndpoint)) {
                     updateEndpoint(jdoEndpoint, endpoints[i]);
                     endpoints[i] = null;
                     flag = true;
                     break;
                  }
               }

               if (!flag && jdoEndpoint.getServiceType().equalsIgnoreCase(serviceType)) {
                  deletedServices.add(jdoEndpoint);
               }
            }

            partner.getServices().removeAll(deletedServices);

            for(i = 0; i < endpoints.length; ++i) {
               if (endpoints[i] != null) {
                  if (!SAML2Utils.validateEndpointBinding(endpoints[i].getBindingType())) {
                     Saml2Logger.logUnsupportedBindingType(log, endpoints[i].getBindingType());
                  } else {
                     partner.getServices().add(endpoints[i]);
                  }
               }
            }

            return deletedServices;
         } else {
            Iterator ite = partner.getServices().iterator();

            while(ite.hasNext()) {
               jdoEndpoint = (com.bea.common.security.store.data.Endpoint)ite.next();
               if (jdoEndpoint.getServiceType().equalsIgnoreCase(serviceType)) {
                  deletedServices.add(jdoEndpoint);
               }
            }

            partner.getServices().removeAll(deletedServices);
            return deletedServices;
         }
      }
   }

   private static void updateEndpoint(com.bea.common.security.store.data.Endpoint oldEnd, com.bea.common.security.store.data.Endpoint newEnd) {
      oldEnd.setDefaultEndPoint(newEnd.isDefaultEndPoint());
      oldEnd.setDefaultSet(newEnd.isDefaultSet());
      oldEnd.setIndex(newEnd.getIndex());
   }

   private static boolean compareEndpoint(com.bea.common.security.store.data.Endpoint endpoint1, com.bea.common.security.store.data.Endpoint endpoint2) {
      return endpoint1.getBindingType().equalsIgnoreCase(endpoint2.getBindingType()) && endpoint1.getBindingLocation().equalsIgnoreCase(endpoint2.getBindingLocation()) && endpoint1.getServiceType().equalsIgnoreCase(endpoint2.getServiceType());
   }
}
