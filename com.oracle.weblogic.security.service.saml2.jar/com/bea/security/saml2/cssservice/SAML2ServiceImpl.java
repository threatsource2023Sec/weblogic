package com.bea.security.saml2.cssservice;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.saml2.ConfigValidationException;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.saml2.utils.SAML2SchemaValidator;
import com.bea.common.security.service.SAML2PublishException;
import com.bea.common.security.service.SAML2Service;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.service.Service;
import com.bea.security.saml2.service.ServiceFactory;
import com.bea.security.saml2.util.SAML2Constants;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.OrganizationURL;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Element;
import weblogic.utils.StringUtils;

public class SAML2ServiceImpl implements SAML2Service, SAML2Constants {
   private SAML2ConfigSpi config;
   private LoggerSpi log;
   private Map urlServiceMap = new HashMap();
   private String saml2AppContext = null;
   private static final String SUPPORTED_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";

   public SAML2ServiceImpl(SAML2ConfigSpi config) throws MalformedURLException {
      this.config = config;
      this.log = config.getLogger();

      try {
         URL publishURL = new URL(config.getLocalConfiguration().getPublishedSiteURL());
         this.saml2AppContext = publishURL.getPath();
      } catch (MalformedURLException var3) {
         this.saml2AppContext = null;
         if (this.log.isDebugEnabled()) {
            this.log.debug("SAML2ServiceImpl(): Invalid published site URL: '" + config.getLocalConfiguration().getPublishedSiteURL() + "'");
         }
      }

      while(this.saml2AppContext != null && this.saml2AppContext.endsWith("/")) {
         this.saml2AppContext = this.saml2AppContext.substring(0, this.saml2AppContext.length() - 1);
      }

      if (this.log.isDebugEnabled()) {
         this.log.debug("SAML2ServiceImpl(): service application context is: '" + this.saml2AppContext + "'");
      }

      this.urlServiceMap.put("/idp/sso/artifact", "SSO");
      this.urlServiceMap.put("/idp/sso/post", "SSO");
      this.urlServiceMap.put("/idp/sso/redirect", "SSO");
      this.urlServiceMap.put("/idp/sso/initiator", "SSO");
      this.urlServiceMap.put("/idp/sso/login-return", "SSO");
      this.urlServiceMap.put("/idp/login", "SSO");
      this.urlServiceMap.put("/idp/ars/soap", "ARS");
      this.urlServiceMap.put("/sp/acs/artifact", "ACS");
      this.urlServiceMap.put("/sp/acs/post", "ACS");
      this.urlServiceMap.put("/sp/sso/initiator", "SPinitiator");
      this.urlServiceMap.put("/sp/ars/soap", "ARS");
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ServiceFactory factory = this.config.getServiceFactory();
      String requestURI = request.getRequestURI();
      String contextPath = request.getContextPath();
      String serviceType = this.getServiceTypeFromURI(contextPath, requestURI);
      if (serviceType == null) {
         return false;
      } else {
         Service service = factory.getService(serviceType);
         return service.process(request, response);
      }
   }

   private String getServiceTypeFromURI(String contextPath, String requestURI) {
      if (this.saml2AppContext == null) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("getServiceTypeFromURI(): saml2AppContext not set, returning null");
         }

         return null;
      } else {
         String serviceType = null;
         if (this.log.isDebugEnabled()) {
            this.log.debug("getServiceTypeFromURI(): request URI is '" + requestURI + "'");
         }

         if (!contextPath.equals(this.saml2AppContext)) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getServiceTypeFromURI(): request URI is not a service URI");
            }

            serviceType = "SPinitiator";
         } else {
            requestURI = requestURI.substring(this.saml2AppContext.length());
            if (this.log.isDebugEnabled()) {
               this.log.debug("getServiceTypeFromURI(): service URI is '" + requestURI + "'");
            }

            serviceType = (String)this.urlServiceMap.get(requestURI);
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("getServiceTypeFromURI(): returning service type '" + serviceType + "'");
         }

         return serviceType;
      }
   }

   public void publish(String filename) throws SAML2PublishException {
      this.publish(filename, false);
   }

   public void publish(String filename, boolean prohibitOverwrite) throws SAML2PublishException {
      if (filename != null && !filename.trim().equals("")) {
         File f = new File(filename);
         if (prohibitOverwrite && f.exists()) {
            throw new SAML2PublishException.OverwriteProhibitedException("File overwrite prohibited");
         } else {
            FileOutputStream fos = null;

            try {
               fos = new FileOutputStream(f);
            } catch (Exception var14) {
               throw new SAML2PublishException.FileCreateException("Error creating file", var14);
            }

            try {
               this.publish((OutputStream)fos);
            } finally {
               try {
                  fos.flush();
                  fos.close();
               } catch (IOException var12) {
                  throw new SAML2PublishException.FileCreateException("Error writing file", var12);
               }
            }

         }
      } else {
         throw new IllegalArgumentException("Invalid filename parameter");
      }
   }

   private void publish(OutputStream outputStream) throws SAML2PublishException {
      SingleSignOnServicesConfigSpi lc = this.config.getLocalConfiguration();
      if (!lc.isIdentityProviderEnabled() && !lc.isServiceProviderEnabled()) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("publish(): Unable to publish: IdP and SP disabled");
         }

         throw new SAML2PublishException.NotEnabledException("SAML2 services not enabled");
      } else {
         try {
            SAML2Utils.validateLocalConfig(lc);
         } catch (ConfigValidationException var7) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("publish(): Unable to publish: Illegal local config");
            }

            throw new SAML2PublishException.InvalidConfigException(var7.getMessage());
         }

         Element edElement = this.buildMetadata(lc);

         try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty("indent", "yes");
            t.transform(new DOMSource(edElement.getOwnerDocument()), new StreamResult(outputStream));
         } catch (TransformerException var6) {
            throw new SAML2PublishException.MetadataXMLException("Error generating metadata XML", var6);
         }
      }
   }

   public String exportMetadata() throws SAML2PublishException {
      SingleSignOnServicesConfigSpi lc = this.config.getLocalConfiguration();
      if (!lc.isIdentityProviderEnabled() && !lc.isServiceProviderEnabled()) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("publish(): Unable to publish: IdP and SP disabled");
         }

         throw new SAML2PublishException.NotEnabledException("SAML2 services not enabled");
      } else {
         try {
            SAML2Utils.validateLocalConfig(lc);
         } catch (ConfigValidationException var9) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("publish(): Unable to publish: Illegal local config");
            }

            throw new SAML2PublishException.InvalidConfigException(var9.getMessage());
         }

         Element edElement = this.buildMetadata(lc);

         try {
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty("indent", "yes");
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(edElement.getOwnerDocument());
            trans.transform(source, result);
            return sw.toString();
         } catch (TransformerException var8) {
            throw new SAML2PublishException.MetadataXMLException("Error generating metadata XML", var8);
         }
      }
   }

   private Element buildMetadata(SingleSignOnServicesConfigSpi lc) throws SAML2PublishException {
      String publishedSiteURL;
      for(publishedSiteURL = lc.getPublishedSiteURL(); publishedSiteURL.endsWith("/"); publishedSiteURL = publishedSiteURL.substring(0, publishedSiteURL.length() - 1)) {
      }

      try {
         EntityDescriptor ed = (EntityDescriptor)XMLObjectSupport.buildXMLObject(EntityDescriptor.DEFAULT_ELEMENT_NAME);
         if (this.isContactPersonSet(lc)) {
            String contactPersonType = lc.getContactPersonType();
            if (contactPersonType == null) {
               throw new SAML2PublishException.InvalidConfigException("Error generating metadata XML: ContactPersonType is not set");
            }

            ContactPerson cp = (ContactPerson)XMLObjectSupport.buildXMLObject(ContactPerson.DEFAULT_ELEMENT_NAME);
            GivenName gn = (GivenName)XMLObjectSupport.buildXMLObject(GivenName.DEFAULT_ELEMENT_NAME);
            gn.setName(lc.getContactPersonGivenName());
            cp.setGivenName(gn);
            SurName sn = (SurName)XMLObjectSupport.buildXMLObject(SurName.DEFAULT_ELEMENT_NAME);
            sn.setName(lc.getContactPersonSurName());
            cp.setSurName(sn);
            ContactPersonTypeEnumeration cpte;
            if (ContactPersonTypeEnumeration.ADMINISTRATIVE.toString().equals(contactPersonType)) {
               cpte = ContactPersonTypeEnumeration.ADMINISTRATIVE;
            } else if (ContactPersonTypeEnumeration.TECHNICAL.toString().equals(contactPersonType)) {
               cpte = ContactPersonTypeEnumeration.TECHNICAL;
            } else if (ContactPersonTypeEnumeration.BILLING.toString().equals(contactPersonType)) {
               cpte = ContactPersonTypeEnumeration.BILLING;
            } else if (ContactPersonTypeEnumeration.SUPPORT.toString().equals(contactPersonType)) {
               cpte = ContactPersonTypeEnumeration.SUPPORT;
            } else {
               cpte = ContactPersonTypeEnumeration.OTHER;
            }

            cp.setType(cpte);
            Company c = (Company)XMLObjectSupport.buildXMLObject(Company.DEFAULT_ELEMENT_NAME);
            c.setName(lc.getContactPersonCompany());
            cp.setCompany(c);
            EmailAddress email = (EmailAddress)XMLObjectSupport.buildXMLObject(EmailAddress.DEFAULT_ELEMENT_NAME);
            email.setAddress(lc.getContactPersonEmailAddress());
            cp.getEmailAddresses().add(email);
            TelephoneNumber phone = (TelephoneNumber)XMLObjectSupport.buildXMLObject(TelephoneNumber.DEFAULT_ELEMENT_NAME);
            phone.setNumber(lc.getContactPersonTelephoneNumber());
            cp.getTelephoneNumbers().add(phone);
            List cps = ed.getContactPersons();
            cps.add(cp);
         }

         List ssoss;
         List arss;
         if (this.isOrganizationSet(lc)) {
            Organization o = (Organization)XMLObjectSupport.buildXMLObject(Organization.DEFAULT_ELEMENT_NAME);
            ssoss = o.getOrganizationNames();
            OrganizationName on = (OrganizationName)XMLObjectSupport.buildXMLObject(OrganizationName.DEFAULT_ELEMENT_NAME);
            on.setValue(lc.getOrganizationName());
            on.setXMLLang(Locale.getDefault().getLanguage());
            ssoss.add(on);
            OrganizationDisplayName on1 = (OrganizationDisplayName)XMLObjectSupport.buildXMLObject(OrganizationDisplayName.DEFAULT_ELEMENT_NAME);
            on1.setValue(lc.getOrganizationName());
            on1.setXMLLang(Locale.getDefault().getLanguage());
            o.getDisplayNames().add(on1);
            arss = o.getURLs();
            OrganizationURL ou = (OrganizationURL)XMLObjectSupport.buildXMLObject(OrganizationURL.DEFAULT_ELEMENT_NAME);
            ou.setValue(lc.getOrganizationURL());
            ou.setXMLLang(Locale.getDefault().getLanguage());
            arss.add(ou);
            ed.setOrganization(o);
         }

         ed.setEntityID(lc.getEntityID());
         IDPSSODescriptor idp = null;
         List kds;
         List acss;
         SAML2KeyManager.KeyInfo key;
         if (lc.isIdentityProviderEnabled()) {
            idp = (IDPSSODescriptor)XMLObjectSupport.buildXMLObject(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
            idp.setErrorURL(lc.getErrorPath());
            idp.setWantAuthnRequestsSigned(lc.isWantAuthnRequestsSigned() ? Boolean.TRUE : Boolean.FALSE);
            ssoss = idp.getSingleSignOnServices();
            SingleSignOnService sso;
            if (lc.isIdentityProviderArtifactBindingEnabled()) {
               sso = (SingleSignOnService)XMLObjectSupport.buildXMLObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
               sso.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact");
               sso.setLocation(publishedSiteURL + "/idp/sso/artifact");
               ssoss.add(sso);
               kds = idp.getArtifactResolutionServices();
               ArtifactResolutionService ars = (ArtifactResolutionService)XMLObjectSupport.buildXMLObject(ArtifactResolutionService.DEFAULT_ELEMENT_NAME);
               ars.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:SOAP");
               ars.setLocation(publishedSiteURL + "/idp/ars/soap");
               ars.setIndex(new Integer(0));
               ars.setIsDefault(Boolean.TRUE);
               kds.add(ars);
            }

            if (lc.isIdentityProviderPOSTBindingEnabled()) {
               sso = (SingleSignOnService)XMLObjectSupport.buildXMLObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
               sso.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
               sso.setLocation(publishedSiteURL + "/idp/sso/post");
               if ("HTTP/POST".equals(lc.getIdentityProviderPreferredBinding())) {
                  ssoss.add(0, sso);
               } else {
                  ssoss.add(sso);
               }
            }

            if (lc.isIdentityProviderRedirectBindingEnabled()) {
               sso = (SingleSignOnService)XMLObjectSupport.buildXMLObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
               sso.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");
               sso.setLocation(publishedSiteURL + "/idp/sso/redirect");
               if ("HTTP/Redirect".equals(lc.getIdentityProviderPreferredBinding())) {
                  ssoss.add(0, sso);
               } else {
                  ssoss.add(sso);
               }
            }

            acss = idp.getKeyDescriptors();
            KeyDescriptor kd = (KeyDescriptor)XMLObjectSupport.buildXMLObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
            if (this.config.getSAML2KeyManager() != null && this.config.getSAML2KeyManager().getSSOKeyInfo() != null) {
               key = this.config.getSAML2KeyManager().getSSOKeyInfo();
               KeyInfo ki = SAML2Utils.buildCertKeyInfo(key.getCert());
               kd.setKeyInfo(ki);
               kd.setUse(UsageType.SIGNING);
               acss.add(kd);
            }

            idp.addSupportedProtocol("urn:oasis:names:tc:SAML:2.0:protocol");
         }

         SPSSODescriptor sp = null;
         if (lc.isServiceProviderEnabled()) {
            sp = (SPSSODescriptor)XMLObjectSupport.buildXMLObject(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
            sp.setAuthnRequestsSigned(lc.isSignAuthnRequests() ? Boolean.TRUE : Boolean.FALSE);
            sp.setErrorURL(lc.getErrorPath());
            sp.setWantAssertionsSigned(lc.isWantAssertionsSigned() ? Boolean.TRUE : Boolean.FALSE);
            acss = sp.getAssertionConsumerServices();
            AssertionConsumerService acs;
            if (lc.isServiceProviderArtifactBindingEnabled()) {
               acs = (AssertionConsumerService)XMLObjectSupport.buildXMLObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
               acs.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact");
               acs.setLocation(publishedSiteURL + "/sp/acs/artifact");
               if ("HTTP/Artifact".equals(lc.getServiceProviderPreferredBinding())) {
                  acs.setIsDefault(Boolean.TRUE);
               }

               acss.add(acs);
               arss = sp.getArtifactResolutionServices();
               ArtifactResolutionService ars = (ArtifactResolutionService)XMLObjectSupport.buildXMLObject(ArtifactResolutionService.DEFAULT_ELEMENT_NAME);
               ars.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:SOAP");
               ars.setLocation(publishedSiteURL + "/sp/ars/soap");
               ars.setIndex(new Integer(0));
               ars.setIsDefault(Boolean.TRUE);
               arss.add(ars);
            }

            if (lc.isServiceProviderPOSTBindingEnabled()) {
               acs = (AssertionConsumerService)XMLObjectSupport.buildXMLObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
               acs.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
               acs.setLocation(publishedSiteURL + "/sp/acs/post");
               if ("HTTP/POST".equals(lc.getServiceProviderPreferredBinding())) {
                  acs.setIsDefault(Boolean.TRUE);
               }

               if ("HTTP/POST".equals(lc.getServiceProviderPreferredBinding())) {
                  acss.add(0, acs);
               } else {
                  acss.add(acs);
               }
            }

            int i = 0;

            for(int n = acss.size(); i < n; ++i) {
               AssertionConsumerService acs = (AssertionConsumerService)acss.get(i);
               acs.setIndex(new Integer(i));
            }

            kds = sp.getKeyDescriptors();
            if (this.config.getSAML2KeyManager() != null) {
               key = this.config.getSAML2KeyManager().getSSOKeyInfo();
               if (key != null && key.getCert() != null) {
                  KeyDescriptor signingKD = (KeyDescriptor)XMLObjectSupport.buildXMLObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
                  KeyInfo ki = SAML2Utils.buildCertKeyInfo(key.getCert());
                  signingKD.setKeyInfo(ki);
                  signingKD.setUse(UsageType.SIGNING);
                  kds.add(signingKD);
               } else if (this.log.isDebugEnabled()) {
                  this.log.debug("No signing certificate to include in the SP metadata.");
               }

               SAML2KeyManager.KeyInfo encryptionCertKeyInfo = this.config.getSAML2KeyManager().getAssertionEncryptionDecryptionKeyInfo();
               if (encryptionCertKeyInfo != null && encryptionCertKeyInfo.getCert() != null) {
                  X509Certificate encryptionCert = (X509Certificate)encryptionCertKeyInfo.getCert();

                  try {
                     encryptionCert.checkValidity();
                  } catch (CertificateExpiredException var13) {
                     if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                        throw var13;
                     }

                     if (this.log.isDebugEnabled()) {
                        this.log.debug("Adding an expired SAML Assertion encryption certificate to the metadata: " + encryptionCert.getSubjectDN());
                     }
                  } catch (CertificateNotYetValidException var14) {
                     if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                        throw var14;
                     }

                     if (this.log.isDebugEnabled()) {
                        this.log.debug("Adding a not yet valid SAML Assertion encryption certificate to the metadata: " + encryptionCert.getSubjectDN());
                     }
                  }

                  KeyDescriptor encryptionKD = (KeyDescriptor)XMLObjectSupport.buildXMLObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
                  KeyInfo encryptionKeyInfo = SAML2Utils.buildCertKeyInfo(encryptionCertKeyInfo.getCert());
                  encryptionKD.setKeyInfo(encryptionKeyInfo);
                  encryptionKD.setUse(UsageType.ENCRYPTION);
                  encryptionKD.getEncryptionMethods().addAll(SAML2Utils.getSupportedEncryptionMethods(this.config.getLocalConfiguration().getMetadataEncryptionAlgorithms()));
                  kds.add(encryptionKD);
               } else if (this.log.isDebugEnabled()) {
                  this.log.debug("No encryption certificate to include in the SP metadata.");
               }
            }

            sp.addSupportedProtocol("urn:oasis:names:tc:SAML:2.0:protocol");
         }

         acss = ed.getRoleDescriptors();
         if (idp != null) {
            acss.add(idp);
         }

         if (sp != null) {
            acss.add(sp);
         }

         MarshallerFactory marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();
         Marshaller marshaller = marshallerFactory.getMarshaller(ed);
         Element edElement = marshaller.marshall(ed);
         if (edElement != null) {
            SAML2SchemaValidator.validateElement(edElement);
         }

         return edElement;
      } catch (Exception var15) {
         throw new SAML2PublishException.MetadataXMLException("Error generating metadata XML", var15);
      }
   }

   private boolean isContactPersonSet(SingleSignOnServicesConfigSpi lc) {
      return !StringUtils.isEmptyString(lc.getContactPersonCompany()) || !StringUtils.isEmptyString(lc.getContactPersonEmailAddress()) || !StringUtils.isEmptyString(lc.getContactPersonGivenName()) || !StringUtils.isEmptyString(lc.getContactPersonSurName()) || !StringUtils.isEmptyString(lc.getContactPersonTelephoneNumber()) || !StringUtils.isEmptyString(lc.getContactPersonType());
   }

   private boolean isOrganizationSet(SingleSignOnServicesConfigSpi lc) {
      return !StringUtils.isEmptyString(lc.getOrganizationName()) || !StringUtils.isEmptyString(lc.getOrganizationURL());
   }
}
